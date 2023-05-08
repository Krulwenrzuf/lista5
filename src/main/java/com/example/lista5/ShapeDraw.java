package com.example.lista5;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ShapeDraw {
    @FXML
    private Label welcomeText;

    @FXML
    protected Pane canvas;

    @FXML
    protected ChoiceBox<Shaper> wyborFigury;

    @FXML
    protected TextField XGonField;

    protected Shaper shape;



    public ShapeDraw() {
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(100));
        pauseTransition.setOnFinished(actionEvent -> delayedConstructor());
        pauseTransition.play();
    }

    public void delayedConstructor() {
        wyborFigury.getItems().addAll(
                new S_Kolo(),
                new S_Prostokat(),
                new S_Wielokat(6),
                new S_Wielokat(5)

        );
        wyborFigury.getSelectionModel().selectFirst();

        shape = wyborFigury.getSelectionModel().getSelectedItem();
        wyborFigury.setOnAction(actionEvent -> setShape());

        canvas.setOnMousePressed(this::drawStart);
        canvas.setOnMouseDragged(this::drawDraw);
        canvas.setOnMouseReleased(this::drawEnd);


        canvas.setClip(new Rectangle(canvas.getWidth(), canvas.getHeight())); //ustawia clipping
        //↓ responsywny clipping
        canvas.widthProperty().addListener((obs, oldval, newval) -> canvas.setClip(new Rectangle(canvas.getWidth(), canvas.getHeight())));
        canvas.heightProperty().addListener((obs, oldval, newval) -> canvas.setClip(new Rectangle(canvas.getWidth(), canvas.getHeight())));
    }

    public void setShape(){
        shape = wyborFigury.getSelectionModel().getSelectedItem().newShape();
    }

    public void drawStart(MouseEvent event) {
        shape = shape.newShape();
        welcomeText.setText(event.getX() + " " + event.getY());
        shape.setStart(event.getX(), event.getY());
        canvas.getChildren().add((Node) shape);
        shape.getShape().setOpacity(0.5);
    }

    public void drawDraw(MouseEvent event) {
        shape.setEnd(event.getX(), event.getY());
    }

    public void drawEnd(MouseEvent event) {
        welcomeText.setText(event.getX() + " " + event.getY());
        shape.getShape().setOpacity(1);

    }
    @FXML
    protected void onHelloButtonClick() {
        canvas.getChildren().clear();
    }

    @FXML
    public void customXGon(){
        int n;
        try {
            n = Integer.parseInt(XGonField.getText());
            if (n<3){
                throw new IllegalArgumentException();
            }
        } catch (Exception e){
            n = 5;
        }
        shape = new S_Wielokat(n);
        wyborFigury.getItems().set(3, shape);
        wyborFigury.getSelectionModel().select(shape);
    }
}