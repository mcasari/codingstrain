// java-17-sealed-classes — closed hierarchy

public abstract sealed class Shape permits Circle, Rectangle, FancyRectangle {
    public abstract double area();
}

public final class Circle extends Shape { /* leaf — no further subclasses */ }

public sealed class Rectangle extends Shape permits Square, Oblong { }

public final class Square extends Rectangle { }

public non-sealed class FancyRectangle extends Shape { }
// RoundedRectangle extends FancyRectangle — not in Shape.permits (open branch)
