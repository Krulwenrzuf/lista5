package com.example.lista5;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class ShapeEdit extends ShapeDraw {
    @FXML
    public ToggleButton edit;
    protected boolean editMode = false; //czy tryb edycji jest aktywny
    protected Shaper selected; //aktualnie wybrany kształt
    protected Color defColor; //kolor kształtu przed jego wybraniem

//    protected double xdif;
//    protected double ydif;

    protected double xclick;
    protected double yclick;

    @Override
    public void delayedConstructor() {
        super.delayedConstructor();
    }

    @Override
    public void setShape() { //funkcja wywoływana przez aktywacje menu wyboru kształtu
        super.setShape();
        disableEditMode();
    }

    @FXML
    protected void setEditMode() { //włącza i wyłącza tryb edycji
        if (!editMode) {
            enableEditMode();
        } else {
            disableEditMode();
        }
    }

    protected void enableEditMode() { //aktywuje tryb edycji i przypisuje figurom Listenery
        canvas.setOnMousePressed(this::selectShape);
        canvas.setOnMouseDragged(this::moveShape);
        canvas.setOnMouseReleased(null);
        canvas.setOnScroll(this::scaleShape);
        canvas.getScene().setOnKeyPressed(this::setControlMode);
        canvas.getScene().setOnKeyReleased(this::setControlMode);
        editMode = true;
        edit.setSelected(true);
    }

    protected void disableEditMode() { //dezaktywuje tryb edycji i przywraca domyślne listenery
        canvas.setOnMousePressed(this::drawStart);
        canvas.setOnMouseDragged(this::drawDraw);
        canvas.setOnMouseReleased(this::drawEnd);
        if (selected != null) {
            selected.getShape().setFill(defColor);
        }
        selected = null;
        defColor = null;
        editMode = false;
        edit.setSelected(false);
    }

    protected void setControlMode(KeyEvent keyEvent) { //reaguje na wciśnięcie klawisza control i zmnienia zachowanie scrolla
        if (keyEvent.isControlDown()) {
            canvas.setOnScroll(this::rotateShape);
        } else {
            canvas.setOnScroll(this::scaleShape);
        }
    }

    public void selectShape(MouseEvent mouseEvent) { //kliknięcie -> oznacza kształt jako aktywny
        if (mouseEvent.getTarget() instanceof Shaper) {
            if (selected != null) {
                selected.getShape().setFill(defColor);
            }
            selected = (Shaper) mouseEvent.getTarget();
            defColor = (Color) selected.getShape().getFill();
            selected.getShape().setFill(Color.BLUE);
            selected.getShape().toFront();

//            xdif = mouseEvent.getX() - selected.getAnchorX();
//            ydif = mouseEvent.getY() - selected.getAnchorY();

            xclick = mouseEvent.getX();
            yclick = mouseEvent.getY();
        }
    }

    public void moveShape(MouseEvent mouseEvent) { //poruszenie myszką -> zmienia położenie kształtu
        if (mouseEvent.getTarget() instanceof Shaper  && selected != null) {
            Translate translation = selected.getTranslation();
            translation.setX(translation.getX() + mouseEvent.getX() - xclick); //zmiana położenia figury o dystans jaki pokonała mysz od ostatniego uruchomienia funkcji
            translation.setY(translation.getY() + mouseEvent.getY() - yclick); // xyclick - punkt ostatniej zmiany położenia

            xclick = mouseEvent.getX();
            yclick = mouseEvent.getY();
        }
    }

    public void scaleShape(ScrollEvent scrollEvent) { //obrócenie scrolla -> powiększa i zmniejsza kształt
        double scalingConst = 1000; //ustala szybkość skalowania
        if (scrollEvent.getTarget() instanceof Shaper && selected != null) {
            Scale scalation = selected.getScalation();
            scalation.setX(scalation.getX() * (1+(scrollEvent.getDeltaY() / scalingConst)));
            scalation.setY(scalation.getY() * (1+(scrollEvent.getDeltaY() / scalingConst)));
            if (selected.getShape().getBoundsInParent().getHeight() < 20) {
                scalation.setX(scalation.getX() / (1+(scrollEvent.getDeltaY() / scalingConst)));
                scalation.setY(scalation.getY() / (1+(scrollEvent.getDeltaY() / scalingConst)));
            }
            selected.getShape().setStrokeWidth(5/scalation.getX());
        }
    }

    public void rotateShape(ScrollEvent scrollEvent) { //wciśnięcie control + obrócenie scrolla -> obraca kształt
        if (scrollEvent.getTarget() instanceof Shaper  && selected != null) {
            Rotate rotation = selected.getRotation();
            rotation.setAngle(rotation.getAngle() + scrollEvent.getDeltaY() / 16);
        }
    }
}
