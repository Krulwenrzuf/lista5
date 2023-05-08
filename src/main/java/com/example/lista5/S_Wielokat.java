package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class S_Wielokat extends Polygon implements Shaper {
    protected double centerx;
    protected double centery;
    protected double firstx;
    protected double firsty;
    public Translate translation = new Translate();
    public Rotate rotation = new Rotate();
    public Scale scalation = new Scale();

    protected int xgon; // ilość boków x-ścianu foremnego
    protected double angle; //kąt środkowy wielokątu

    public S_Wielokat() {
        xgon = 6;
        angle = (2 * Math.PI) / xgon;
    }

    public S_Wielokat(int n) {
        xgon = n;
        angle = (2 * Math.PI) / xgon;
    }

    @Override
    public String toString() {
        return xgon + " - ścian foremny";
    }

    @Override
    public Shape getShape() {
        return this;
    }

    @Override
    public Shaper newShape() {
        return new S_Wielokat(xgon);
    }

    @Override
    public void setStart(double x, double y) {
        centerx = x;
        centery = y;

        this.setFill(generateColor());
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(5);

        this.getTransforms().addAll(translation, rotation, scalation);
        rotation.setPivotX(this.centerx);
        rotation.setPivotY(this.centery);
        scalation.setPivotX(this.centerx);
        scalation.setPivotY(this.centery);
    }

    @Override
    public void setEnd(double x, double y) {
        firstx = x;
        firsty = y;
        this.getPoints().clear();
        this.getPoints().addAll(generateXgon());
    }

    @Override
    public void moveShape(double x, double y) {
        firstx = firstx - (centerx - x);
        firsty = firsty - (centery - y);
        centerx = x;
        centery = y;
        this.getPoints().clear();
        this.getPoints().addAll(generateXgon());
    }

    @Override
    public double getAnchorX() {
        return centerx;
    }

    @Override
    public double getAnchorY() {
        return centery;
    }

    @Override
    public Translate getTranslation() {
        return translation;
    }

    @Override
    public Scale getScalation() {
        return scalation;
    }

    @Override
    public Rotate getRotation() {
        return rotation;
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

    public Color generateColor() {
        int colorNum = (xgon + "qwerty").hashCode();                           //tworzy hash liczby xgon
        colorNum = Math.abs(colorNum) % 16777215;                               //liczy modulo aby liczba nie była większa niż FFFFFF
        String colorStr = Integer.toHexString(colorNum);                        //zamienia int na str
        String colorEnd = ("000000" + colorStr).substring(colorStr.length());   //dodaje wiodące zera
        return Color.web(colorEnd);
    }
}
