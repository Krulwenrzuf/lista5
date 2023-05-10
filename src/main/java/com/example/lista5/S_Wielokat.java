package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.util.ArrayList;

public class S_Wielokat extends Polygon implements Shaper {
    @Serial
    private static final long serialVersionUID = 4L;

    ShapeData shapeData = new ShapeData(this);

    ArrayList<Double> vertexes = new ArrayList<>();

    double centerX;

    double centerY;

    public boolean finished = false;

    public S_Wielokat() {
        shapeData.color = Color.CORAL;

    }

    @Override
    public String toString() {
        return "dowolny wielokąt";
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
        finished = true;
        this.setOpacity(1);
        return new S_Wielokat();
    }

    @Override
    public void drawStart(double x, double y) {
        if (this.getPoints().isEmpty()) {
            shapeData.setStart(x, y);
            shapeData.setEnd(x, y);

            shapeData.rotate.setPivotX(x);
            shapeData.rotate.setPivotY(y);
            shapeData.scale.setPivotX(x);
            shapeData.scale.setPivotY(y);

            this.getTransforms().addAll(shapeData.translate, shapeData.rotate, shapeData.scale);

            this.setFill(shapeData.color);
            this.setStroke(Color.BLACK);
            this.setStrokeWidth(5 / shapeData.scale.getX());

            vertexes.add(x);
            vertexes.add(y);
            this.getPoints().addAll(x, y, x, y);

        } else if (shapeData.getDist(x, y) < 5) {
            if(vertexes.size() > 4){
                finished = true;
            }
        } else if (!finished) {
            this.getPoints().add(x);
            this.getPoints().add(y);
            shapeData.setEnd(x, y);
        }
//        else if (!finished) {
//            vertexes.add(x);
//            vertexes.add(y);
//            this.getPoints().add(x);
//            this.getPoints().add(y);
//            System.out.println(vertexes.size());
//        }
    }

    @Override
    public void drawDraw(double x, double y) {
//        this.getPoints().clear();
//
//        for (Double vertex : vertexes) {
//            this.getPoints().add(vertex);
//        }

        if (shapeData.getDist(x, y) > 5) {
            //this.getPoints().remove(this.getPoints().size() - 1);
            //this.getPoints().remove(this.getPoints().size() - 2);

            this.getPoints().set(this.getPoints().size() - 2, x);
            this.getPoints().set(this.getPoints().size() - 1, y);

            //this.getPoints().addAll(x, y);
            shapeData.setEnd(x, y);
        }
    }

    @Override
    public boolean drawEnd() {
        if (shapeData.startX != shapeData.endX && shapeData.startY != shapeData.endY) {
            if (!finished) {
                vertexes.add(shapeData.endX);
                vertexes.add(shapeData.endY);
//                this.getPoints().add(shapeData.endX);
//                this.getPoints().add(shapeData.endY);
            }
        }
        calculateMiddlePoint();
        return finished;
    }

    @Override
    public void moveShape(double x, double y) {
    }

    @Override
    public double getAnchorX() {
        return shapeData.startX;
    }

    @Override
    public double getAnchorY() {
        return shapeData.startY;
    }

    /**
     * Liczy punkt środkowy punktów w vertexes
     */
    public void calculateMiddlePoint() {
        Double SumX = 0.0;
        for (int i = 0; i < vertexes.size(); i += 2) {
            SumX += vertexes.get(i);
        }

        Double SumY = 0.0;
        for (int i = 1; i < vertexes.size(); i += 2) {
            SumY += vertexes.get(i);
        }

        centerX = SumX / (vertexes.size() / 2.0);
        centerY = SumY / (vertexes.size() / 2.0);

        shapeData.rotate.setPivotX(centerX);
        shapeData.rotate.setPivotY(centerY);
        shapeData.scale.setPivotX(centerX);
        shapeData.scale.setPivotY(centerY);
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

        drawStart(shapeData.startX,shapeData.startY);
        drawDraw(shapeData.endX,shapeData.endY);
        drawEnd();

        this.getPoints().clear();

        for (Double vertex: vertexes) {
            this.getPoints().add(vertex);
        }
    }
}
