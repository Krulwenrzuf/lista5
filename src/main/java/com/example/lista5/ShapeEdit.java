package com.example.lista5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class ShapeEdit extends ShapeDraw {
    /**
     * Przełącznik trybu edycji
     */
    @FXML
    public ToggleButton edit;

    /**
     * Czy tryb edycji jest aktywny
     */
    protected boolean editMode = false;

    /**
     * Aktualnie zaznaczony kształt
     */
    protected Shaper selected;

//    protected double xdif;
//    protected double ydif;

    /**
     * Wartość x myszy po kliknięciu lub przesunięciu figury
     */
    protected double xclick;

    /**
     * Wartość y myszy po kliknięciu lub przesunięciu figury
     */
    protected double yclick;

    ColorPicker colorPicker;

    /**
     * Funkcja wywoływana przez aktywacje menu wyboru kształtu
     * Wyłącza tryb edycji
     */
    @Override
    public void setShape() {
        super.setShape();
        disableEditMode();
    }

    /**
     * Przełącza tryb edycji
     */
    @FXML
    protected void setEditMode() {
        if (!editMode) {
            enableEditMode();
        } else {
            disableEditMode();
        }
    }

    /**
     * Włącza tryb edycji i ustawia listenery do edycji figur
     */
    protected void enableEditMode() {
        canvas.setOnMousePressed(this::selectShape);
        canvas.setOnMouseDragged(this::moveShape);
        canvas.setOnMouseReleased(null);
        canvas.setOnScroll(this::scaleShape);
        canvas.getScene().setOnKeyPressed(this::setControlMode);
        canvas.getScene().setOnKeyReleased(this::setControlMode);
        canvas.setOnContextMenuRequested(this::colorPick);

        editMode = true;
        edit.setSelected(true);
    }

    /**
     * Wyłącza tryb edycji i przywraca domyślne listenery
     */
    protected void disableEditMode() {
        canvas.setOnMousePressed(this::drawStart);
        canvas.setOnMouseDragged(this::drawDraw);
        canvas.setOnMouseReleased(this::drawEnd);
        if (selected != null) {
            selected.getShape().setFill(selected.getData().color);
        }
        if (colorPicker != null){
            canvas.getChildren().remove(colorPicker);
        }
        selected = null;
        editMode = false;
        edit.setSelected(false);
    }

    /**
     * Sprawdza, czy jest wciśnięty klawisz control, jeśli tak, zmienia zachowanie scrolla (ze skalowania na obrót)
     * @param keyEvent Event wciśnięcia klawisza
     */
    protected void setControlMode(KeyEvent keyEvent) {
        if (keyEvent.isControlDown()) {
            canvas.setOnScroll(this::rotateShape);
        } else {
            canvas.setOnScroll(this::scaleShape);
        }
    }

    /**
     * Po kliknięciu przywraca poprzedni kształt <code>selected</code> do jego pierwotnego wyglądu (jeśli istnieje).
     * Następnie sprawdza, czy kliknięto kształt, jeśli tak, ustawia go do zmiennej <code>selected</code> i zmienia kolor na niebieski
     * @param mouseEvent Event kliknięcia myszy
     */
    public void selectShape(MouseEvent mouseEvent) {
        if (colorPicker != null){
            canvas.getChildren().remove(colorPicker);
        }
        if (mouseEvent.getTarget() instanceof Shaper) {
            if (selected != mouseEvent.getTarget() && selected != null){
                selected.getShape().setFill(selected.getData().color);
            }
            selected = (Shaper) mouseEvent.getTarget();
            selected.getShape().setFill(Color.BLUE);
            selected.getShape().toFront();

//            xdif = mouseEvent.getX() - selected.getAnchorX();
//            ydif = mouseEvent.getY() - selected.getAnchorY();

            xclick = mouseEvent.getX();
            yclick = mouseEvent.getY();
        }else {
            if (selected != null) {
                selected.getShape().setFill(selected.getData().color);
            }
            selected = null;
        }
    }

    /**
     * Przesuwa kształt <code>selected</code> o wielkość przesunięcia myszy od kliknięcia, lub ostatniego wywołania funkcji
     * @param mouseEvent Event przesunięcia myszy
     */
    public void moveShape(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget() instanceof Shaper  && selected != null) {
            Translate translation = selected.getData().translate;
            translation.setX(translation.getX() + mouseEvent.getX() - xclick); //zmiana położenia figury o dystans, jaki pokonała mysz od ostatniego uruchomienia funkcji
            translation.setY(translation.getY() + mouseEvent.getY() - yclick); // xyclick-punkt ostatniej zmiany położenia

            xclick = mouseEvent.getX();
            yclick = mouseEvent.getY();
        }
    }

    /**<p>Skaluje kształt <code>selected</code></p>
     * <p>ilość skalowania jest wprost proporcjonalna do przescrollowanej odległości, odwrotnie proporcjonalna do rzeczywistej wielkości figury (aby uzyskać płynne skalowanie)</p>
     * @param scrollEvent Event przesunięcia scrolla
     *
     */
    public void scaleShape(ScrollEvent scrollEvent) {
        if (scrollEvent.getTarget() instanceof Shaper && selected != null) {
            //Zmienna ustalająca ilość skalowania, wprost proporcjonalna do przescrollowanej odległości, odwrotnie proporcjonalna do rzeczywistej wielkości figury (aby uzyskać płynne skalowanie)
            double scalingConst = (1+(scrollEvent.getDeltaY() / (5 * selected.getShape().getBoundsInParent().getWidth())));
            Scale scalation = selected.getData().scale;
            scalation.setX(scalation.getX() * scalingConst);
            scalation.setY(scalation.getY() * scalingConst);
            if (selected.getShape().getBoundsInParent().getHeight() < 16) {
                scalation.setX(scalation.getX() / scalingConst);
                scalation.setY(scalation.getY() / scalingConst);
            }
            selected.getShape().setStrokeWidth(5/scalation.getX());
        }
    }

    /**
     * Obraca kształt <code>selected</code> o wielkość przesunięcia scrolla
     * @param scrollEvent - Event przesunięcia scrolla
     */
    public void rotateShape(ScrollEvent scrollEvent) {
        if (scrollEvent.getTarget() instanceof Shaper  && selected != null) {
            Rotate rotation = selected.getData().rotate;
            rotation.setAngle(rotation.getAngle() + scrollEvent.getDeltaY() / 16);
        }
    }

    /**
     * Powoduje pojawienie się okienka wyboru kolorów
     * @param contextMenuEvent event contextMenuRequested
     */
    private void colorPick(ContextMenuEvent contextMenuEvent) {
        if (selected != null && contextMenuEvent.getTarget() instanceof Shaper) {
            selected.getShape().setFill(selected.getData().color);
            colorPicker = new ColorPicker(selected.getData().color);
            colorPicker.setLayoutX(contextMenuEvent.getX());
            colorPicker.setLayoutY(contextMenuEvent.getY());
            canvas.getChildren().add(colorPicker);
            colorPicker.setOnAction(this::colorShape);
        }
    }

    /**
     * Zmienia kolor kształtu <code>selected</code> na ten wybrany w okienku colorpickera
     * @param actionEvent actionEvent
     */
    private void colorShape(ActionEvent actionEvent) {
        Color color = colorPicker.getValue();
        selected.getData().color = color;
        selected.getShape().setFill(color);
    }
}
