package com.codingstrain.sealed;

/**
 * Nested sealed type: {@code Rectangle} is permitted by {@code Shape} and itself
 * declares a smaller closed set via {@code permits Square, Oblong}.
 */
public sealed class Rectangle extends Shape permits Square, Oblong {

    private final double width;
    private final double height;

    protected Rectangle(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive");
        }
        this.width = width;
        this.height = height;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public String label() {
        return "Rectangle(" + width + "x" + height + ")";
    }
}
