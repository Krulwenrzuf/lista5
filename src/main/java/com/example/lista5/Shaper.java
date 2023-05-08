package com.example.lista5;

import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public interface Shaper {
    String toString();

    Shape getShape();

    Shaper newShape();

    Translate getTranslation();

    Scale getScalation();

    Rotate getRotation();

    void setStart(double x, double y);

    void setEnd(double x, double y);

    double getAnchorX();

    double getAnchorY();

    void moveShape(double x, double y);


}
