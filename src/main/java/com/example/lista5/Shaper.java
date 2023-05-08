package com.example.lista5;

import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public interface Shaper {
    String toString(); //zwraca nazwę figury, nadpisuje metodę z Object używaną do wyświetlania obiektu

    Shape getShape(); //zwraca figurę jako Shape, pozwala na dostęp do metod klasy Shape

    Shaper newShape(); //zwraca nową figurę tej samej klasy (oraz ilości boków w wypadku wielokąta)

    Translate getTranslation(); //zwraca obiekt Translate przypisany do figury, pozwala na przesunięcia

    Scale getScalation(); //zwraca obiekt Scale przypisany do figury, pozwala na powiększenie/pomniejszenie

    Rotate getRotation(); //zwraca obiekt Rotate przypisany do figury, pozwala na obrócenie

    void setStart(double x, double y); //ustawia początek generowania figury

    void setEnd(double x, double y); //ustawia koniec generowania figury

    //↓ stare metody do manualnego przesuwania figur (bez użycia translate)
    double getAnchorX(); //zwraca punkt zaczepienia figury

    double getAnchorY(); //zwraca punkt zaczepienia figury

    void moveShape(double x, double y); //przesuwa punkt zaczepienia do punktu (x,y)


}
