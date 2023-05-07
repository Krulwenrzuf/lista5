package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class S_Kolo extends Circle implements Shaper {
    public Circle shape;
    public Color color = Color.ORANGERED;
    public double startx;
    public double starty;
    
    @Override
    public String toString() {
        return "Koło";
    }

    @Override
    public Shape getShape() {
        return this;
    }

    @Override
    public Shaper newShape(){
        return new S_Kolo();
    }

    @Override
    public void setStart(double x, double y) {
        shape = new Circle();
       this.setFill(color);
       this.setStroke(Color.BLACK);
       this.setStrokeWidth(5);


        startx = x;
        starty = y;
       this.setCenterX(x);
       this.setCenterY(y);
       this.setRadius(5);
    }

    @Override
    public void setEnd(double x, double y) {
        double radius = Math.sqrt(Math.pow(x -this.getCenterX(), 2) + Math.pow(y -this.getCenterY(), 2));
        if (radius < 5){
            radius = 5;
        }
       this.setRadius(radius);

    }

    public void resetShape(){
       this.setFill(color);
       this.setStroke(Color.BLACK);
       this.setStrokeWidth(5);
    }

    @Override
    public void moveShape(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
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
