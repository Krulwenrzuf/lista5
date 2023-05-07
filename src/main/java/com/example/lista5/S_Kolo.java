package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class S_Kolo implements Shaper {
    public Circle shape;
    public double startx;
    public double starty;

    @Override
    public String toString() {
        return "Koło";
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public void setStart(double x, double y) {
        shape = new Circle();
        shape.setFill(Color.ORANGERED);
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(5);
        shape.setOnMouseClicked(event -> shapeselected());

        startx = x;
        starty = y;
        shape.setCenterX(x);
        shape.setCenterY(y);
    }

    @Override
    public void setEnd(double x, double y) {
        double radius = Math.sqrt(Math.pow(x - shape.getCenterX(), 2) + Math.pow(y - shape.getCenterY(), 2));
        shape.setRadius(radius);
    }

    void shapeselected(){
        shape.setFill(Color.BLUE);
    }
}
