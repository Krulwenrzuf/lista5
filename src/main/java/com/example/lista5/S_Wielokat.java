package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class S_Wielokat extends Polygon implements Shaper {
    protected Polygon shape;

    protected double centerx;
    protected double centery;

    protected double firstx;
    protected double firsty;

    protected static final int xgon = 6;
    protected static final double angle = (2 * Math.PI) / xgon;

    @Override
    public String toString() {
        return "Wielokąt";
    }

    @Override
    public Shape getShape() {
        return this;
    }

    @Override
    public void setStart(double x, double y) {
        centerx = x;
        centery = y;

        shape = new Polygon();
        this.setFill(Color.BLUEVIOLET);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(5);
    }

    @Override
    public void setEnd(double x, double y) {
        firstx = x;
        firsty = y;
        this.getPoints().clear();
        this.getPoints().addAll(generateXgon());
    }

    @Override
    public void shapeSelected() {

    }

    public Double[] generateXgon() {
        Double[] vertexes = new Double[xgon * 2];
        vertexes[0] = firstx;
        vertexes[1] = firsty;

        for (int i = 1; i < xgon; i++) {
            vertexes[i * 2] = centerx + (firstx - centerx) * Math.cos(angle * i) - (firsty - centery) * Math.sin(angle * i);
            vertexes[i * 2 + 1] = centery + (firstx - centerx) * Math.sin(angle * i) + (firsty - centery) * Math.cos(angle * i);
        }
        return vertexes;
    }

}
