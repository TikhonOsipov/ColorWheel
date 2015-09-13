package com.tixon.colorwheel;

public class Drawer {
    public Drawer() {}

    public float[] calculateCoordinates(double angle, float[] coordinates, float centerX, float centerY) {
        float[] result = new float[2];
        result[0] = (int) (centerX + coordinates[0] * Math.cos(Math.toRadians(angle)) + coordinates[1] * Math.sin(Math.toRadians(angle)));
        result[1] = (int) (centerY - (coordinates[0] * Math.sin(Math.toRadians(angle)) + coordinates[1] * Math.cos(Math.toRadians(angle))));
        return result;
    }
}
