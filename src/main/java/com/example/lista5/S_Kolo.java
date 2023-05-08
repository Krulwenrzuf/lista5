package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class S_Kolo extends Circle implements Shaper {
    public Translate translation = new Translate();
    public Rotate rotation = new Rotate();
    public Scale scalation = new Scale();
    public static final int min = 5;

    @Override
    public String toString() {
        return "Koło";
    }

    @Override
    public Shape getShape() {
        return this;
    }

    @Override
    public Shaper newShape() {
        return new S_Kolo();
    }

    @Override
    public void setStart(double x, double y) {
        this.setFill(Color.ORANGERED);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(min);

        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(min);

        this.getTransforms().addAll(translation, scalation, rotation);
        rotation.setPivotX(this.getCenterX());
        rotation.setPivotY(this.getCenterY());
        scalation.setPivotX(this.getCenterX());
        scalation.setPivotY(this.getCenterY());
    }

    @Override
    public void setEnd(double x, double y) {
        double radius = Math.sqrt(Math.pow(x - this.getCenterX(), 2) + Math.pow(y - this.getCenterY(), 2));
        if (radius < min) {
            radius = min;
        }
        this.setRadius(radius);
    }

    @Override
    public void moveShape(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }

    @Override
    public double getAnchorX() {
        return this.getCenterX();
    }

    @Override
    public double getAnchorY() {
        return this.getCenterY();
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
}
