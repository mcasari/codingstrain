package com.codingstrain.sealed;

/**
 * Escape hatch: {@code non-sealed} re-opens the hierarchy — any class may extend
 * {@code FancyRectangle} without being listed in a {@code permits} clause.
 */
public non-sealed class FancyRectangle extends Shape {

    private final double width;
    private final double height;
    private final double cornerRadius;

    public FancyRectangle(double width, double height, double cornerRadius) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive");
        }
        if (cornerRadius < 0) {
            throw new IllegalArgumentException("cornerRadius cannot be negative");
        }
        this.width = width;
        this.height = height;
        this.cornerRadius = cornerRadius;
    }

    public double cornerRadius() {
        return cornerRadius;
    }

    @Override
    public double area() {
        // Approximation for demo purposes; not a precise rounded-rect formula.
        return width * height;
    }

    @Override
    public String label() {
        return "FancyRectangle(" + width + "x" + height + ", r=" + cornerRadius + ")";
    }
}
