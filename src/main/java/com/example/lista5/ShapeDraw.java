package com.example.lista5;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ShapeDraw {
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
        shape.setStart(event.getX(), event.getY());
        canvas.getChildren().add((Node) shape);
        shape.getShape().setOpacity(0.5);
    }

    public void drawDraw(MouseEvent event) {
        shape.setEnd(event.getX(), event.getY());
    }

    public void drawEnd(MouseEvent event) {
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

    @FXML public void info(){
        Stage primaryStage = (Stage) canvas.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.TOP_CENTER);
        dialogVbox.setPadding(new Insets(20,20,20,20));
        dialogVbox.setSpacing(10);

        Text title = new Text("Shaper!");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD ,30.0));
        //System.out.println(Font.getFamilies());
        dialogVbox.getChildren().add(title);
        dialogVbox.getChildren().add(new Text("wersja 2.1"));
        dialogVbox.getChildren().add(new Text("Program do rysowania kształtów!"));
        dialogVbox.getChildren().add(new Text("Autor: Paweł Stanik"));

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @FXML public void manual(){
        Stage primaryStage = (Stage) canvas.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

//        VBox dialogVbox = new VBox(20);
//        dialogVbox.setAlignment(Pos.TOP_LEFT);
//        dialogVbox.setPadding(new Insets(20,20,20,20));
//        dialogVbox.setSpacing(10);

        TextFlow dialogBox = new TextFlow();
        dialogBox.setTextAlignment(TextAlignment.LEFT);
        dialogBox.setPadding(new Insets(20,20,20,20));
        dialogBox.setLineSpacing(10);


        Text title = new Text("Instrukcja obsługi\n");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD ,30.0));
        //System.out.println(Font.getFamilies());
        dialogBox.getChildren().add(title);
        dialogBox.getChildren().add(new Text("1. Aby stworzyć nową figurę wybierz ją z menu wyboru, następne naciśnij w miejsce gdzie chcesz narysować kształt, i trzymając przycisk myszki przeciągnij kursorem aby ustawić jego wielkość\n"));
        dialogBox.getChildren().add(new Text("  I. Możesz stworzyć dowolny wielokąt foremny wpisując w pole edycji liczbe kątów\n"));
        dialogBox.getChildren().add(new Text("2. Aby edytować kształt, kliknij przycisk edycja a następnie wybierz kształt\n"));
        dialogBox.getChildren().add(new Text("  I. Aby przesunąć kształt, przeciągnij go na wybrane miejsce\n"));
        dialogBox.getChildren().add(new Text("  II. Aby powiększyć lub zmniejszyć kształt użyj koła myszki\n"));
        dialogBox.getChildren().add(new Text("  III. Aby obróćić kształt, przytrzymaj klawisz control i użyj koła myszki\n"));
        dialogBox.getChildren().add(new Text("3. Aby wyczyścić planszę wybierz z menu edycja pole wyczyść\n"));
        Scene dialogScene = new Scene(dialogBox, 500, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}