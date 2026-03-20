package com.codingstrain.tweetexamples;

public class RectangleExample {

    // Define records
    record Point(int x, int y) {}
    record Rectangle(Point topLeft, Point bottomRight) {}

    public static void main(String[] args) {

        Object shape = new Rectangle(
                new Point(2, 10),
                new Point(8, 4)
        );

        if (shape instanceof Rectangle(Point(int x1, int y1),
                                       Point(int x2, int y2))) {

            int width = x2 - x1;
            int height = y1 - y2;

            System.out.println("Width: " + width);
            System.out.println("Height: " + height);
        } else {
            System.out.println("Not a rectangle");
        }
    }
}