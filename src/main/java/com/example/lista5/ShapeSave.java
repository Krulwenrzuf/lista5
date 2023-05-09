package com.example.lista5;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class ShapeSave extends ShapeEdit{
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
    public void shapeLoad() throws IOException{

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
                popupError("niepoprawny plik");
            }

            for (Shaper shaper: arrayList) {
                shaper.setStart();
                shaper.setEnd();
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
        Stage primaryStage = (Stage) canvas.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.TOP_CENTER);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setSpacing(10);

        dialogVbox.getChildren().add(new Text(string));

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
