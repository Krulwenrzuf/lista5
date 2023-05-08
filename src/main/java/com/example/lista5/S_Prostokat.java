package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class S_Prostokat extends Rectangle implements Shaper {
    public double startX;
    public double startY;
    public Translate translation = new Translate();
    public Rotate rotation = new Rotate();
    public Scale scalation = new Scale();
    public static final int min = 14; //minimalny rozmiar

    @Override
    public String toString() {
        return "Prostokąt";
    }

    @Override
    public Shape getShape() {
        return this;
    }

    @Override
    public Shaper newShape() {
        return new S_Prostokat();
    }

    @Override
    public void setStart(double x, double y) {
        this.setFill(Color.GREENYELLOW);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(5);

        startX = x;
        startY = y;
        this.setX(x);
        this.setY(y);
        this.getTransforms().addAll(translation, rotation, scalation);

        this.setEnd(x+min,y+min);
    }

    @Override
    public void setEnd(double x, double y) {
        //↓ metody pozwalające rysować prostokąt w dowolną stronę, a nie tylko w kierunku +x i +y
        if (x < startX && startX - x < min) {
            this.setX(startX - min);
        } else {
            this.setX(Math.min(x, startX));
        }
        if (y < startY && startY - y < min) {
            this.setY(startY - min);
        } else {
            this.setY(Math.min(y, startY));
        }

        double width = Math.abs(x - startX);
        if (width < min) {
            width = min;
        }
        double height = Math.abs(y - startY);
        if (height < min) {
            height = min;
        }

        this.setWidth(width);
        this.setHeight(height);

        //↓ ustawienie punktu obrotu i skalowania na środek prostokąta
        rotation.setPivotX(this.getX() + (width / 2));
        rotation.setPivotY(this.getY() + (height / 2));
        scalation.setPivotX(this.getX() + (width / 2));
        scalation.setPivotY(this.getY() + (height / 2));
    }

    @Override
    public void moveShape(double x, double y) {
//        startX = x;
//        startY = y;
        this.setX(x);
        this.setY(y);
    }

    @Override
    public double getAnchorX() {
        return this.getX();
    }

    @Override
    public double getAnchorY() {
        return this.getY();
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
