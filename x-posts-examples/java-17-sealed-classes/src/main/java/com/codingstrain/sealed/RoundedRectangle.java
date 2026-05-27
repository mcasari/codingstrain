package com.codingstrain.sealed;

/**
 * Not listed in any {@code permits} clause — legal because {@code FancyRectangle}
 * is {@code non-sealed}, so the closed-hierarchy contract ends at that branch.
 */
public class RoundedRectangle extends FancyRectangle {

    public RoundedRectangle(double width, double height, double cornerRadius) {
        super(width, height, cornerRadius);
    }

    @Override
    public String label() {
        return "RoundedRectangle(" + super.label() + ")";
    }
}
