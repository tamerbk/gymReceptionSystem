package com.example.midterm;

import javafx.scene.control.CheckBox;
import javafx.scene.shape.Circle;

public class CircularCheckBox extends CheckBox {

    private final Circle circle;

    public CircularCheckBox() {
        circle = new Circle(8); // adjust the radius as needed
        setGraphic(circle);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        circle.setCenterX(getWidth() / 2);
        circle.setCenterY(getHeight() / 2);
    }
}