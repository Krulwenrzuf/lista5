package com.example.lista5;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class ShapeEdit extends ShapeDraw {
    @FXML
    public ToggleButton edit;
    protected boolean editMode = false;
    protected Shaper selected;
    protected Color defColor;
//    protected double xdif;
//    protected double ydif;

    protected double xclick;
    protected double yclick;

    @Override
    public void delayedConstructor() {
        super.delayedConstructor();
    }

    @Override
    public void setShape() {
        super.setShape();
        disableEditMode();
    }

    @FXML
    protected void setEditMode() {
        if (!editMode) {
            enableEditMode();
        } else {
            disableEditMode();
        }
    }

    protected void enableEditMode() {
        canvas.setOnMousePressed(this::selectShape);
        canvas.setOnMouseDragged(this::moveShape);
        canvas.setOnMouseReleased(null);
        canvas.setOnScroll(this::scaleShape);
        canvas.getScene().setOnKeyPressed(this::setControlMode);
        canvas.getScene().setOnKeyReleased(this::setControlMode);
        editMode = true;
    }

    protected void disableEditMode() {
        canvas.setOnMousePressed(this::drawStart);
        canvas.setOnMouseDragged(this::drawDraw);
        canvas.setOnMouseReleased(this::drawEnd);
        if (selected != null) {
            selected.getShape().setFill(defColor);
        }
        selected = null;
        defColor = null;
        editMode = false;
        edit.setSelected(false);
    }

    protected void setControlMode(KeyEvent keyEvent) {
        if (keyEvent.isControlDown()) {
            canvas.setOnScroll(this::rotateShape);
        } else {
            canvas.setOnScroll(this::scaleShape);
        }
    }

    public void selectShape(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget() instanceof Shaper) {
            if (selected != null) {
                selected.getShape().setFill(defColor);
            }
            selected = (Shaper) mouseEvent.getTarget();
            defColor = (Color) selected.getShape().getFill();
            selected.getShape().setFill(Color.BLUE);

//            xdif = mouseEvent.getX() - selected.getAnchorX();
//            ydif = mouseEvent.getY() - selected.getAnchorY();

            xclick = mouseEvent.getX();
            yclick = mouseEvent.getY();
        }
    }

    public void moveShape(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget() instanceof Shaper) {
            Translate translation = selected.getTranslation();
            translation.setX(translation.getX() + mouseEvent.getX() - xclick);
            translation.setY(translation.getY() + mouseEvent.getY() - yclick);

            xclick = mouseEvent.getX();
            yclick = mouseEvent.getY();
        }
    }

    public void scaleShape(ScrollEvent scrollEvent) {
        if (scrollEvent.getTarget() instanceof Shaper) {
            Scale scalation = selected.getScalation();
            scalation.setX(scalation.getX() + scrollEvent.getDeltaY() / 1000);
            scalation.setY(scalation.getY() + scrollEvent.getDeltaY() / 1000);
        }
    }

    public void rotateShape(ScrollEvent scrollEvent) {
        if (scrollEvent.getTarget() instanceof Shaper) {
            Rotate rotation = selected.getRotation();
            rotation.setAngle(rotation.getAngle() + scrollEvent.getDeltaY() / 16);
        }
    }
}
