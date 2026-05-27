package com.codingstrain.sealed;

/** Leaf: {@code final} closes inheritance for this permitted subtype. */
public final class Circle extends Shape {

    private final double radius;

    public Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be positive");
        }
        this.radius = radius;
    }

    public double radius() {
        return radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public String label() {
        return "Circle(r=" + radius + ")";
    }
}
