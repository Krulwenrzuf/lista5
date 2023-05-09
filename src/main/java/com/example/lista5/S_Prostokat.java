package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.Serial;

public class S_Prostokat extends Rectangle implements Shaper {
    @Serial
    private static final long serialVersionUID = 3L;

    ShapeData shapeData = new ShapeData();
    public static final int min = 14; //minimalny rozmiar

    @Override
    public String toString() {
        return "Prostokąt";
    }

    @Override
    public ShapeData getData() {
        return shapeData;
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
    public void setStart() {
        this.setFill(Color.GREENYELLOW);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(5);

        this.setX(shapeData.startX);
        this.setY(shapeData.startY);
        this.getTransforms().addAll(shapeData.translate, shapeData.rotate, shapeData.scale);

//        this.setWidth(min);
//        this.setHeight(min);
        setEnd();
    }

    @Override
    public void setEnd() {
        //↓ metody pozwalające rysować prostokąt w dowolną stronę, a nie tylko w kierunku +x i +y
        if (shapeData.endX < shapeData.startX && shapeData.startX - shapeData.endX < min) {
            this.setX(shapeData.startX - min);
        } else {
            this.setX(Math.min(shapeData.endX, shapeData.startX));
        }
        if (shapeData.endY < shapeData.startY && shapeData.startY - shapeData.endY < min) {
            this.setY(shapeData.startY - min);
        } else {
            this.setY(Math.min(shapeData.endY, shapeData.startY));
        }

        double width = Math.abs(shapeData.endX - shapeData.startX);
        if (width < min) {
            width = min;
        }
        double height = Math.abs(shapeData.endY - shapeData.startY);
        if (height < min) {
            height = min;
        }

        this.setWidth(width);
        this.setHeight(height);

        //↓ ustawienie punktu obrotu i skalowania na środek prostokąta
        shapeData.rotate.setPivotX(this.getX() + (width / 2));
        shapeData.rotate.setPivotY(this.getY() + (height / 2));
        shapeData.scale.setPivotX(this.getX() + (width / 2));
        shapeData.scale.setPivotY(this.getY() + (height / 2));
    }

    @Override
    public void moveShape(double x, double y) {
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
}
