package com.example.lista5;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Editable extends HelloController {
    protected boolean editMode = false;
    protected Shaper selected;
    protected double xdif;
    protected double ydif;

    @FXML
    protected void editShape() {
//        canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED,this::drawStart);
//        canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED,this::drawDraw);
//        canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED,this::drawEnd);
        if (!editMode) {

            canvas.setOnMousePressed(mouseEvent -> {
                if (mouseEvent.getTarget() instanceof Shaper) {
                    if (selected != null) {
                        selected.resetShape();
                    }
                    selected = (Shaper) mouseEvent.getTarget();
                    selected.getShape().setFill(Color.BLUE);
                    xdif = mouseEvent.getX() - selected.getStartX();
                    ydif = mouseEvent.getY() - selected.getStartY();
                    System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
                    System.out.println(selected.getStartX() + " " + selected.getStartY());
                    System.out.println(xdif + " " + ydif);
                }
            });
            canvas.setOnMouseDragged(mouseEvent -> {
                if (mouseEvent.getTarget() instanceof Shaper) {
                    selected.moveShape(mouseEvent.getX() - xdif, mouseEvent.getY() - ydif);
                }
            });
            canvas.setOnMouseReleased(null);
            editMode = true;
        } else {
            canvas.setOnMousePressed(this::drawStart);
            canvas.setOnMouseDragged(this::drawDraw);
            canvas.setOnMouseReleased(this::drawEnd);
            if (selected != null) {
                selected.resetShape();
            }
            selected = null;
            editMode = false;
        }
    }
}
