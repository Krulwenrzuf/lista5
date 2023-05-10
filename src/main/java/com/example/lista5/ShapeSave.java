package com.example.lista5;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class ShapeSave extends ShapeEdit{

    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Otwiera okienko zapisu i zapisuje wszystkie kształty
     * @throws IOException a
     */
    public void shapeSave() throws IOException {
        disableEditMode();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pliki programu SHAPER (*.shaper)", "*.shaper");
        fileChooser.setInitialFileName("mojPlik");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        if (file != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            ArrayList<Shaper> arrayList = new ArrayList<>();
            for (int i = 0; i < canvas.getChildren().size(); i++) {
                arrayList.add((Shaper) canvas.getChildren().get(i));
            }
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.flush();
            objectOutputStream.close();
        }


    }

    /**
     * Otwiera okienko wczytania i wczytuje figury
     * @throws IOException a
     */
    public void shapeLoad() throws IOException {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pliki programu SHAPER (*.shaper)", "*.shaper");
        //fileChooser.setInitialFileName("mojPlik");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        if (file != null) {
            FileInputStream fileInputStream
                    = new FileInputStream(file);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            canvas.getChildren().clear();

            ArrayList<Shaper> arrayList = new ArrayList<>();
            try {
                arrayList = (ArrayList<Shaper>) objectInputStream.readObject();
            } catch (Exception e){
                popupError("niepoprawny plik: " + e);
            }

            for (Shaper shaper: arrayList) {
                ShapeData shapeData = shaper.getData().clone();
                shaper.drawStart(shapeData.startX,shapeData.startY);
                shaper.drawDraw(shapeData.endX,shapeData.endY);
                canvas.getChildren().add((Node) shaper);
            }
            objectInputStream.close();
        }
    }

    /**
     * Powoduje wyskoczenie okienka błędu z wiadomością
     * @param string wiadomość
     */
    public void popupError(String string){

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.TOP_CENTER);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setSpacing(10);

        dialogVbox.getChildren().add(new Text(string));

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * Wyskakujące okienko informacji
     */
    @FXML
    public void info() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.TOP_CENTER);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setSpacing(10);

        Text title = new Text("Shaper!");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30.0));
        //System.out.println(Font.getFamilies());
        dialogVbox.getChildren().add(title);
        dialogVbox.getChildren().add(new Text("wersja 2.1"));
        dialogVbox.getChildren().add(new Text("Program do rysowania kształtów!"));
        dialogVbox.getChildren().add(new Text("Autor: Paweł Stanik"));

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * Wyskakujące okienko z instrukcją
     */
    @FXML
    public void manual() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        TextFlow dialogBox = new TextFlow();
        dialogBox.setTextAlignment(TextAlignment.LEFT);
        dialogBox.setPadding(new Insets(20, 20, 20, 20));
        dialogBox.setLineSpacing(10);

        Text title = new Text("Instrukcja obsługi\n");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30.0));
        //System.out.println(Font.getFamilies());
        dialogBox.getChildren().add(title);
        dialogBox.getChildren().add(new Text("1. Aby stworzyć nową figurę wybierz ją z menu wyboru, następne naciśnij w miejsce gdzie chcesz narysować kształt, i trzymając przycisk myszki przeciągnij kursorem aby ustawić jej wielkość\n"));
        dialogBox.getChildren().add(new Text("  I. Możesz stworzyć dowolny wielokąt foremny wpisując w pole edycji liczbę kątów\n"));
        dialogBox.getChildren().add(new Text("2. Aby edytować kształt, kliknij przycisk edycja a następnie wybierz kształt\n"));
        dialogBox.getChildren().add(new Text("  I. Aby przesunąć kształt, przeciągnij go na wybrane miejsce\n"));
        dialogBox.getChildren().add(new Text("  II. Aby powiększyć lub zmniejszyć kształt użyj koła myszki\n"));
        dialogBox.getChildren().add(new Text("  III. Aby obrócić kształt, przytrzymaj klawisz control i użyj koła myszki\n"));
        dialogBox.getChildren().add(new Text("  IV. Aby zmienić kolor kliknij prawym przyciskiem myszy i wybierz dowolny kolor\n"));
        dialogBox.getChildren().add(new Text("3. Aby wyczyścić planszę wybierz z menu edycja pole wyczyść\n"));
        dialogBox.getChildren().add(new Text("4. Możesz zapisać lub otworzyć swój rysunek korzystając z menu \"plik\"\n"));
        Scene dialogScene = new Scene(dialogBox, 500, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
