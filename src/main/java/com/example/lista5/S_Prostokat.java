package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class S_Prostokat implements Shaper {
    public Rectangle shape;
    public double startx;
    public double starty;

    @Override
    public String toString() {
        return "Prostokąt";
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public void setStart(double x, double y) {
        shape = new Rectangle();
        shape.setFill(Color.ORANGERED);
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(5);

        startx = x;
        starty = y;
        shape.setX(x);
        shape.setY(y);
    }

    @Override
    public void setEnd(double x, double y) {
        shape.setX(Math.min(x,startx));
        shape.setY(Math.min(y,starty));
        shape.setWidth(Math.abs(x-startx));
        shape.setHeight(Math.abs(y-starty));

    }

    @Override
    public void setOpacity(double n){
        shape.setOpacity(n);
    }
}
