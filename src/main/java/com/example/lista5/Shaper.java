package com.example.lista5;

import javafx.animation.PauseTransition;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import java.lang.Math;

public interface Shaper{
    public String toString();
    public Shape getShape();
    public void setStart(double x, double y);
    public void setEnd(double x, double y);
    public void setOpacity(double n);

}
