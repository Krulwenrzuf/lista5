package com.example.lista5;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class S_Wielokat implements Shaper {
    protected Polygon shape;

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

    }

    @Override
    public void setEnd(double x, double y) {

    }

    public void setOpacity(double n){
        shape.setOpacity(n);
    }
}
