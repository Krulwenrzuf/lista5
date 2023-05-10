package com.example.lista5;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class ShapeDraw {
    /**
     * Pole, na którym rysuje się figury
     */
    @FXML
    protected Pane canvas;

    /**
     * Okienko wyboru figury
     */
    @FXML
    protected ChoiceBox<Shaper> wyborFigury;

    /**
     * Pole input do rysowania dowolnego x-kąta foremnego
     */
    @FXML
    protected TextField XGonField;

    /**
     * Aktualnie tworzony kształt
     */
    protected Shaper shape;

    /**
     * Funkcja uruchomiana po załadowaniu programu
     */
    @FXML
    public void initialize() {
        wyborFigury.getItems().addAll(
                new S_Kolo(),
                new S_Prostokat(),
                new S_Foremny(6),
                new S_Foremny(5), // wielokąt używany do customXgon
                new S_Wielokat()

        );
        wyborFigury.getSelectionModel().selectFirst();

        wyborFigury.setOnAction(actionEvent -> setShape());
        setShape();

        canvas.setOnMousePressed(this::drawStart);
        canvas.setOnMouseDragged(this::drawDraw);
        canvas.setOnMouseReleased(mouseEvent -> drawEnd());
        //canvas.setOnContextMenuRequested(contextMenuEvent -> drawFinish());

        //↓ ustawia clipping
        canvas.setClip(new Rectangle(canvas.getWidth(), canvas.getHeight()));
        //↓ responsywny clipping
        canvas.widthProperty().addListener((obs, oldval, newval) -> canvas.setClip(new Rectangle(canvas.getWidth(), canvas.getHeight())));
        canvas.heightProperty().addListener((obs, oldval, newval) -> canvas.setClip(new Rectangle(canvas.getWidth(), canvas.getHeight())));
    }

    /**
     * Ustawia <code>shape</code> na kształt typu wybranego w menu wyboru
     */
    public void setShape() {
        shape = wyborFigury.getSelectionModel().getSelectedItem().newShape();
    }

    /**
     * Ustala początek rysowania kształtu, dodaje nowy kształt, ustawia przezroczystość na czas rysowania
     *
     * @param event Event wciśnięcia myszy
     */
    public void drawStart(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            if (shape.drawEnd()) {
                shape = shape.newShape();
            }
            shape.drawStart(event.getX(), event.getY());
            if (!canvas.getChildren().contains(shape)) {
                canvas.getChildren().add((Node) shape);
                shape.getShape().setOpacity(0.5);
            }
        } else if (event.isSecondaryButtonDown()) {
            drawFinish();
        }

    }

    /**
     * Ustawia aktualny koniec rysowania kształtu
     *
     * @param event Event przesunięcia myszy
     */
    public void drawDraw(MouseEvent event) {
        if (event.isPrimaryButtonDown()){
            shape.drawDraw(event.getX(), event.getY());
        }
    }

    /**
     * Sprawdza czy zakończono rysowanie kształtu
     */
    public void drawEnd() {
        if (shape.drawEnd()) {
            shape.getShape().setOpacity(1);
            //shape = shape.newShape();
        }
    }

    /**
     * Zakańcza ryswoanie kształtu jeśli tego nie zrobiono
     */
    public void drawFinish() {
        if (!shape.drawEnd()) {
            shape.drawStart(shape.getData().startX, shape.getData().startY);
            System.out.println("pies");

            if (!shape.drawEnd()){
                canvas.getChildren().remove(shape);
                shape = shape.newShape();
                System.out.println("kot");
            }
            drawEnd();


        }
    }

    /**
     * Usuwa wszystkie figury
     */
    @FXML
    protected void erase() {
        if (shape != null) {
            drawFinish();
        }
        canvas.getChildren().clear();
    }

    /**
     * Tworzy obiekt wielokąt o ilości kątów podanej w panelu <code>XFonField</code>
     */
    @FXML
    public void customXGon() {
        int n;
        try {
            n = Integer.parseInt(XGonField.getText());
            if (n < 3) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            n = 5;
        }
        shape = new S_Foremny(n);
        wyborFigury.getItems().set(3, shape);
        wyborFigury.getSelectionModel().select(shape);
    }
}