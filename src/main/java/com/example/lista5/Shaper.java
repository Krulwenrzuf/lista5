package com.example.lista5;

import javafx.scene.shape.Shape;

import java.io.Serializable;

public interface Shaper extends Serializable {
    /**
     * Nadpisuje metodę z Object używaną do wyświetlania obiektu
     *
     * @return Nazwa figury
     */
    String toString();

    /**
     * Pozwala na dostęp do pola shapeData figury
     *
     * @return pole shapeData figury
     */
    ShapeData getData();

    /**
     * Pozwala na dostęp do metod klasy Shape
     *
     * @return figurę jako obiekt Shape
     */
    Shape getShape();

    /**
     * @return Nową figurę tej samej klasy,
     * Zachowuje ilość boków wielokąta
     */
    Shaper newShape();

    /**
     * Ustawia początek generowania figury w punkcie (shapeData.startX,shapeData.startY)
     */
    void generateStart();

    /**
     * Ustawia koniec generowania figury w punkcie (shapeData.endX,shapeData.endY)
     */
    void generateEnd(); //

    /**
     * @return wartość x punktu zaczepienia
     * @deprecated Zwraca punkt zaczepienia figury
     */
    double getAnchorX(); //

    /**
     * @return wartość y punktu zaczepienia
     * @deprecated Zwraca punkt zaczepienia figury
     */
    double getAnchorY(); //zwraca punkt zaczepienia figury

    /**
     * @param x wartość x
     * @param y wartość y
     * @deprecated Przesuwa punkt zaczepienia do punktu (x,y).
     * Robi to manualnie bez użycia translate
     */
    void moveShape(double x, double y); //


}
