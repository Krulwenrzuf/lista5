package com.example.lista5;

import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public interface Shaper {
    /**
     * Nadpisuje metodę z Object używaną do wyświetlania obiektu
     * @return Nazwa figury
     */
    String toString();

    /**
     * Pozwala na dostęp do metod klasy Shape
     * @return figurę jako obiekt Shape
     */
    Shape getShape();

    /**
     * @return Nową figurę tej samej klasy,
     * Zachowuje ilość boków wielokąta
     */
    Shaper newShape();

    /**
     * Pozwala na przesunięcie figury
     * @return obiekt Translate przypisany do figury
     */
    Translate getTranslation();

    /**
     * Pozwala na powiększenie/pomniejszenie figury
     * @return obiekt Scale przypisany do figury
     */
    Scale getScalation(); //zwraca ,

    /**
     * Pozwala na obrócenie figury
     * @return obiekt Rotate przypisany do figury
     */
    Rotate getRotation();

    /**
     * Ustawia początek generowania figury w punkcie (x,y)
     * @param x wartość x
     * @param y wartość y
     */
    void setStart(double x, double y);

    /**
     * Ustawia koniec generowania figury w punkcie (x,y)
     * @param x wartość x
     * @param y wartość y
     */
    void setEnd(double x, double y); //

    /**
     * @deprecated
     * Zwraca punkt zaczepienia figury
     * @return wartość x punktu zaczepienia
     */
    double getAnchorX(); //

    /**
     * @deprecated
     * Zwraca punkt zaczepienia figury
     * @return wartość y punktu zaczepienia
     */
    double getAnchorY(); //zwraca punkt zaczepienia figury

    /**
     * @deprecated
     * Przesuwa punkt zaczepienia do punktu (x,y).
     * Robi to manualnie bez użycia translate
     * @param x wartość x
     * @param y wartość y
     */
    void moveShape(double x, double y); //


}
