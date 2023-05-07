package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class S_Prostokat extends Rectangle implements Shaper {
    public double startx;
    public double starty;
    public static final int min = 10;

    @Override
    public String toString() {
        return "Prostokąt";
    }

    @Override
    public Shape getShape() {
        return this;
    }

    @Override
    public Shaper newShape(){
        return new S_Prostokat();
    }

    @Override
    public void setStart(double x, double y) {
        this.setFill(Color.GREENYELLOW);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(5);

        startx = x;
        starty = y;
        this.setX(x);
        this.setY(y);
        this.setWidth(min);
        this.setHeight(min);
    }

    @Override
    public void setEnd(double x, double y) {
        if (x < startx && startx - x < min) {
            this.setX(startx - min);
        } else {
            this.setX(Math.min(x, startx));
        }
        if (y < starty && starty - y < min) {
            this.setY(starty - min);
        } else {
            this.setY(Math.min(y, starty));
        }

        double width = Math.abs(x - startx);
        if (width < min) {
            width = min;
        }
        double height = Math.abs(y - starty);
        if (height < min) {
            height = min;
        }
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void resetShape() {
        this.setFill(Color.GREENYELLOW);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(5);
    }

    @Override
    public void moveShape(double x, double y) {
        this.setX(x);
        this.setY(y);
        startx = x;
        starty = y;
    }

    @Override
    public double getStartX() {
        return startx;
    }

    @Override
    public double getStartY() {
        return starty;
    }
}
