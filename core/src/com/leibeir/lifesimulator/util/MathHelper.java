package com.leibeir.lifesimulator.util;

public class MathHelper {
    public static float minmax(float min, float max, float value){
        return Math.min(max, Math.max(min, value));
    }
}
