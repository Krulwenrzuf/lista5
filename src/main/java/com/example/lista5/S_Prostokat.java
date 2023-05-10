package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.Serial;

public class S_Prostokat extends Rectangle implements Shaper {
    @Serial
    private static final long serialVersionUID = 3L;

    ShapeData shapeData = new ShapeData(this);

    /**
     * Minimalna długość i szerokość generowanego prostokąta
     */
    public static final int min = 14;

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

    public S_Prostokat(){
        shapeData.color = Color.GREENYELLOW;
    }
    @Override
    public void drawStart( double x, double y) {
        shapeData.setStart(x,y);

        this.setFill(shapeData.color);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(5/shapeData.scale.getX());

        this.setX(x);
        this.setY(y);
        this.getTransforms().addAll(shapeData.translate, shapeData.rotate, shapeData.scale);

        drawDraw(x,y);
    }

    @Override
    public void drawDraw( double x, double y) {
        shapeData.setEnd(x,y);

        //↓ metody pozwalające rysować prostokąt w dowolną stronę, a nie tylko w kierunku +x i +y
        if (x < shapeData.startX && shapeData.startX - x < min) {
            this.setX(shapeData.startX - min);
        } else {
            this.setX(Math.min(x, shapeData.startX));
        }
        if (y < shapeData.startY && shapeData.startY - y < min) {
            this.setY(shapeData.startY - min);
        } else {
            this.setY(Math.min(y, shapeData.startY));
        }

        double width = Math.abs(x - shapeData.startX);
        double height = Math.abs(y - shapeData.startY);

        if (width < min) {
            width = min;
        }
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
    public boolean drawEnd() {
        return true;
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
