package com.example.lista5;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected Pane canvas;

    @FXML
    protected ChoiceBox<Shaper> wyborFigury;

    protected Shaper shape;

    public HelloController() {
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(100));
        pauseTransition.setOnFinished(actionEvent -> delayedConstructor());
        pauseTransition.play();
    }
    public void delayedConstructor() {
        wyborFigury.getItems().add(new S_Kolo());
        wyborFigury.getItems().add(new S_Prostokat());
        wyborFigury.getItems().add(new S_Wielokat());
        wyborFigury.setValue(new S_Kolo());

        shape = wyborFigury.getSelectionModel().getSelectedItem();
        wyborFigury.setOnAction(actionEvent -> shape = wyborFigury.getSelectionModel().getSelectedItem());

        canvas.setOnMousePressed(this::drawStart);
        canvas.setOnMouseReleased(this::drawEnd);
        canvas.setOnMouseDragged(this::drawDraw);

        canvas.setClip(new Rectangle(canvas.getWidth(),canvas.getHeight()));
        canvas.widthProperty().addListener((obs,oldval,newval)-> canvas.setClip(new Rectangle(canvas.getWidth(),canvas.getHeight())));
        canvas.heightProperty().addListener((obs,oldval,newval)-> canvas.setClip(new Rectangle(canvas.getWidth(),canvas.getHeight())));

    }
    public void drawStart(MouseEvent event){
        welcomeText.setText(event.getX() + " " + event.getY());
        shape.setStart(event.getX(),event.getY());
        canvas.getChildren().add(shape.getShape());
        shape.getShape().setOpacity(0.5);
    }
    public void drawDraw(MouseEvent event){
        shape.setEnd(event.getX(),event.getY());
    }
    public void drawEnd(MouseEvent event){
        welcomeText.setText(event.getX() + " " + event.getY());
        shape.getShape().setOpacity(1);
    }

    @FXML
    protected void onHelloButtonClick() {
        canvas.getChildren().clear();
    }
}