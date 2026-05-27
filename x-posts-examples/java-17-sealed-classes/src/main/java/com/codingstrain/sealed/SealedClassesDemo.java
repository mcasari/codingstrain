package com.codingstrain.sealed;

import java.util.List;

public final class SealedClassesDemo {

    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Circle(3),
                new Square(4),
                new Oblong(2, 6),
                new FancyRectangle(5, 3, 0.5),
                new RoundedRectangle(8, 2, 1.0)
        );

        shapes.forEach(shape ->
                System.out.printf("%s → area=%.2f | %s%n", shape.label(), shape.area(), describe(shape)));
    }

    /**
     * With a sealed root type, the set of concrete {@code Shape} implementations is
     * known at compile time — useful for modeling and for exhaustiveness checks in
     * newer {@code switch} pattern matching (standard in Java 21+).
     */
    static String describe(Shape shape) {
        if (shape instanceof Circle) {
            return "leaf (final)";
        }
        if (shape instanceof Square) {
            return "leaf under sealed Rectangle";
        }
        if (shape instanceof Oblong) {
            return "leaf under sealed Rectangle";
        }
        if (shape instanceof RoundedRectangle) {
            return "open extension via non-sealed parent";
        }
        if (shape instanceof FancyRectangle) {
            return "non-sealed branch (open below)";
        }
        if (shape instanceof Rectangle) {
            return "sealed intermediate";
        }
        throw new IllegalStateException("unexpected Shape: " + shape.getClass());
    }
}
