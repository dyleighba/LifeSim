package com.leibeir.lifesimulator.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public class RandomColour {
    private static final Random rand = new Random();
    public static Color get(){
        Color color = new Color();
        return color.fromHsv(rand.nextInt(360), 0.5f, 1);
    }

    public static Color noisyColour(Color colour) {
        float[] hsv = new float[3];
        colour.toHsv(hsv);
        float b =  0.03f*rand.nextFloat();
        return colour.fromHsv(hsv[0], hsv[1], 0.3f+b);
    }

    public static Color setS(Color colour, float s) {
        float[] hsv = new float[3];
        colour.toHsv(hsv);
        return colour.fromHsv(hsv[0], s, hsv[2]);
    }

    public static float[] getHSV(Color colour) {
        float[] hsv = new float[3];
        colour.toHsv(hsv);
        return hsv;
    }
}
