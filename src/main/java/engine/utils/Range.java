package engine.utils;

public final class Range {

    public static float Clip(float low, float high, float value) {
        return Math.min(Math.max(value, low), high);
    }

}
