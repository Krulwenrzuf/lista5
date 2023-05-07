package com.example.lista5;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class S_Wielokat implements Shaper {
    protected Polygon shape;

    protected double centerx;
    protected double centery;

    @Override
    public String toString() {
        return "Wielokąt";
    }

    @Override
    public Shape getShape() {
        return shape;
    }
    @Override
    public void setStart(double x, double y) {
        centerx = x;
        centery = y;

        shape = new Polygon();
    }

    @Override
    public void setEnd(double x, double y) {

    }
}
