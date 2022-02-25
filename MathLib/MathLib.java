package MathLib;

public class MathLib {
    public static double rnd(double max) { return Math.random() * max; }
    public static double rnd(double min, double max){ return Math.random() * (max - min) + min; }

    public static double lerp(double a, double b, double t){ return a*(1.0d-t) + t*b; }
    public static double clamp(double min, double max, double t){ return Math.min(max, Math.max(min, t)); }
}
