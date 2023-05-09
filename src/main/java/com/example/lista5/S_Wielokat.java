package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.io.Serial;

public class S_Wielokat extends Polygon implements Shaper {
    @Serial
    private static final long serialVersionUID = 4L;

    ShapeData shapeData = new ShapeData();
    protected int xgon; // ilość boków x-ścianu foremnego
    protected double angle; //kąt środkowy wielokąta
    protected double min = 7; //minimalny promień

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
    public ShapeData getData() {
        return shapeData;
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
    public void setStart() {

        this.setFill(generateColor());
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(5);

        this.getTransforms().addAll(shapeData.translate, shapeData.rotate, shapeData.scale);
        shapeData.rotate.setPivotX(shapeData.startX);
        shapeData.rotate.setPivotY(shapeData.startY);
        shapeData.scale.setPivotX(shapeData.startX);
        shapeData.scale.setPivotY(shapeData.startY);

        //this.getPoints().addAll(generateXgon(shapeData.startX + min, shapeData.startY + min));
        setEnd();
    }

    @Override
    public void setEnd() {
        //↓ pilnuje by kształt nie był za mały (promień < min)
        double radius = shapeData.getDist();
        if (radius == 0) {
            shapeData.endX = shapeData.startX + 5;
            shapeData.endY = shapeData.startY + 5;
        } else if (radius < min) {
            double scalar = min / radius;
            shapeData.endX = (shapeData.startX - shapeData.endX) * scalar + shapeData.startX;
            shapeData.endY = (shapeData.startY - shapeData.endY) * scalar + shapeData.startY;
        }

        this.getPoints().clear();
        this.getPoints().addAll(generateXgon(shapeData.endX, shapeData.endY));
    }

    @Override
    public void moveShape(double x, double y) {
        shapeData.endX = shapeData.endX - (shapeData.startX - x);
        shapeData.endY = shapeData.endY - (shapeData.startY - y);
        shapeData.startX = x;
        shapeData.startY = y;
        this.getPoints().clear();
        this.getPoints().addAll(generateXgon(shapeData.endX, shapeData.endY));
    }

    @Override
    public double getAnchorX() {
        return shapeData.startX;
    }

    @Override
    public double getAnchorY() {
        return shapeData.startY;
    }

    public Double[] generateXgon(double x, double y) { //generuje wierzchołki wielokąta wykorzystując obrót liczby zespolonej (pierwszego wierzchołka)
        Double[] vertexes = new Double[xgon * 2];
        vertexes[0] = x;
        vertexes[1] = y;

        for (int i = 1; i < xgon; i++) {
            vertexes[i * 2] = shapeData.startX + (x - shapeData.startX) * Math.cos(angle * i) - (y - shapeData.startY) * Math.sin(angle * i);
            vertexes[i * 2 + 1] = shapeData.startY + (x - shapeData.startX) * Math.sin(angle * i) + (y - shapeData.startY) * Math.cos(angle * i);
        }
        return vertexes;
    }

    public Color generateColor() { //generuje unikalny kolor dla wielokąta foremnego o ilości boków xgon
        int colorNum = (xgon + "kabanos").hashCode();                           //tworzy hash liczby xgon
        colorNum = Math.abs(colorNum) % 16777215;                               //liczy modulo aby liczba nie była większa niż 0xFFFFFF
        String colorStr = Integer.toHexString(colorNum);                        //zamienia int na str
        String colorEnd = ("000000" + colorStr).substring(colorStr.length());   //dodaje wiodące zera
        return Color.web(colorEnd);
    }
}
