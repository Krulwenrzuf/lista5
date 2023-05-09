package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.io.*;

public class ShapeData implements Serializable {
    @Serial
    private static final long serialVersionUID = 144L;

    public Shaper shapeType;
    public double startX = 0;
    public double startY = 0;
    public double endX = 0;
    public double endY = 0;
    public transient Translate translate = new Translate();
    public transient Rotate rotate = new Rotate();
    public transient Scale scale = new Scale();
    public transient Color color;


    public Double[] translateData;
    public Double[] rotateData;
    public Double[] scaleData;
    public Double[] colorData;

    public ShapeData() {

    }

    public ShapeData(double sx, double sy, double ex, double ey) {
        startX = sx;
        startY = sy;
        endX = ex;
        endY = ey;

    }

    public void setStart(double x, double y) {
        startX = x;
        startY = y;
        endX = x;
        endY = y;
    }

    public void setEnd(double x, double y) {
        endX = x;
        endY = y;
    }

    public double getDist() {
        return Math.sqrt(Math.pow(startX - endX, 2) + Math.pow(startY - endY, 2));
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        translateData = new Double[]{
                translate.getX(),
                translate.getY()
        };
        rotateData = new Double[]{
                rotate.getAngle(),
                rotate.getPivotX(),
                rotate.getPivotY()
        };
        scaleData = new Double[]{
                scale.getX(),
                scale.getY(),
                scale.getPivotX(),
                scale.getPivotY()
        };

        colorData = new Double[]{
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        };

        oos.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

        translate = new Translate(translateData[0], translateData[1]);
        rotate = new Rotate(rotateData[0],rotateData[1],rotateData[2]);
        scale = new Scale(scaleData[0], scaleData[1], scaleData[2], scaleData[3]);
        color = new Color(colorData[0],colorData[1],colorData[2],1);
    }
}
