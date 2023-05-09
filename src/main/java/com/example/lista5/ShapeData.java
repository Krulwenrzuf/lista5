package com.example.lista5;

import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.io.*;

/**
 * Wszystkie potrzebne informacje o figurze
 */
public class ShapeData implements Serializable {
    @Serial
    private static final long serialVersionUID = 144L;

    public Shaper shapeType;

    /**
     * Wartość x punktu startu generowania figury
     */
    public double startX = 0;

    /**
     * Wartość y punktu startu generowania figury
     */
    public double startY = 0;

    /**
     * Wartość x punktu końca generowania figury
     */
    public double endX = 0;

    /**
     * Wartość y punktu startu generowania figury
     */
    public double endY = 0;
    /**
     * Wartość Translate przesunięcia figury
     */
    public transient Translate translate = new Translate();

    /**
     * Wartość Rotate obrócenia figury
     */
    public transient Rotate rotate = new Rotate();

    /**
     * Wartość Scale przeskalowania figury
     */
    public transient Scale scale = new Scale();

    /**
     * Kolor figury
     */
    public transient Color color;

    /**
     * {@link com.example.lista5.ShapeData#translate} Jako tablica double
     */
    public Double[] translateData;

    /**
     * {@link com.example.lista5.ShapeData#rotate} Jako tablica double
     */
    public Double[] rotateData;

    /**
     * {@link com.example.lista5.ShapeData#scale} Jako tablica double
     */
    public Double[] scaleData;

    /**
     * {@link com.example.lista5.ShapeData#color} Jako tablica double
     */
    public Double[] colorData;

    /**
     * Ustawia punkt startu generowania figury
     * @param x x
     * @param y y
     */
    public void setStart(double x, double y) {
        startX = x;
        startY = y;
        endX = x;
        endY = y;
    }

    /**
     * Ustawia punkt końca generowania figury
     * @param x x
     * @param y y
     */
    public void setEnd(double x, double y) {
        endX = x;
        endY = y;
    }

    /**
     * Liczy dystans pomiędzy punktem startu a punktem końca
     * @return Dystans pomiędzy punktem startu a punktem końca
     */
    public double getDist() {
        return Math.sqrt(Math.pow(startX - endX, 2) + Math.pow(startY - endY, 2));
    }

    /**
     * Przygotowuje obiekt do zapisu zamieniając niezapisywalne pola jako tablice
     * @param oos a
     * @throws IOException a
     */
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

    /**
     * Dekoduje zapisane tablice na prawidłowe obiekty
     * @param ois a
     * @throws ClassNotFoundException  a
     * @throws IOException  a
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

        translate = new Translate(translateData[0], translateData[1]);
        rotate = new Rotate(rotateData[0],rotateData[1],rotateData[2]);
        scale = new Scale(scaleData[0], scaleData[1], scaleData[2], scaleData[3]);
        color = new Color(colorData[0],colorData[1],colorData[2],1);
    }
}
