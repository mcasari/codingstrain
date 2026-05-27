package com.codingstrain.sealed;

public final class Oblong extends Rectangle {

    public Oblong(double width, double height) {
        super(width, height);
    }

    @Override
    public String label() {
        return "Oblong(" + width() + "x" + height() + ")";
    }
}
