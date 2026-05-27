package com.codingstrain.sealed;

/**
 * Root of a closed hierarchy: only types listed in {@code permits} may extend {@code Shape}.
 */
public abstract sealed class Shape permits Circle, Rectangle, FancyRectangle {

    public abstract double area();

    public abstract String label();
}
