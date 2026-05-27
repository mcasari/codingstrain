package com.codingstrain.sealed;

public final class Square extends Rectangle {

    public Square(double side) {
        super(side, side);
    }

    @Override
    public String label() {
        return "Square(" + width() + ")";
    }
}
