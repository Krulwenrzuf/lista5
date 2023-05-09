package com.example.lista5;

import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;

public class ShapeSave extends ShapeEdit{
    public void shapeSave() throws IOException {
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

    public void shapeLoad() throws IOException, ClassNotFoundException {
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

            ArrayList<Shaper> arrayList = (ArrayList<Shaper>) objectInputStream.readObject();

            for (Shaper shaper: arrayList) {
                shaper.setStart();
                shaper.setEnd();
                canvas.getChildren().add((Node) shaper);
            }
            objectInputStream.close();
        }
    }
}
