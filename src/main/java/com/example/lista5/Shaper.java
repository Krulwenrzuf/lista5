package com.example.lista5;

import javafx.animation.PauseTransition;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import java.lang.Math;

public interface Shaper{
    String toString();
    Shape getShape();
    void setStart(double x, double y);
    void setEnd(double x, double y);

}
