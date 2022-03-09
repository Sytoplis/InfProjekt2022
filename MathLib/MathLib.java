package MathLib;

public class MathLib {
    public static double rnd(double max) { return Math.random() * max; }
    public static double rnd(double min, double max){ return Math.random() * (max - min) + min; }

    public static double lerp(double a, double b, double t){ return a*(1.0d-t) + t*b; }
    public static double clamp(double min, double max, double t){ return Math.min(max, Math.max(min, t)); }

    public static double FastInvSqrt(double number){//Quake III Algorithm
        long i;
        double x2, y;
        final double threehalfs = 1.5d;

        x2 = number * 0.5d;
        y = number;
        i = Double.doubleToLongBits(y);         //evil floating point bit hack
        i = 0x43E6EB3BE0004A48L - ( i >> 1);    //what the fuck?           
        y = Double.longBitsToDouble(i);
        y = y * ( threehalfs - (x2 * y * y) );  //1st iteration
        y = y * ( threehalfs - (x2 * y * y) );  //2nd iteration, can be removed

        return y;
    }

    public static float FastInvSqrt(float number){//Quake III Algorithm
        int i;
        float x2, y;
        final float threehalfs = 1.5f;

        x2 = number * 0.5f;
        y = number;
        i = Float.floatToIntBits(y);            //evil floating point bit hack
        i = 0x5f3759df - ( i >> 1);             //what the fuck?
        y = Float.intBitsToFloat(i);
        y = y * ( threehalfs - (x2 * y * y) );  //1st iteration
        y = y * ( threehalfs - (x2 * y * y) );  //2nd iteration, can be removed 
    
        return y;
    }

    //notes to quake III: i ~ log2(y);      log2(1/sqrt(y)) = -0.5 * log2(y) = - (i << 1)
    //the weird float number: 3/2 * 2²³ * (127 - mu)        (mu = ~ 0.0430 in log2(x + 1) ~ x + mu)     (0x5f3759df -> 1.3211836173e+19)
    //the weird double number:  3/2 * 2^52 * (1023 - mu)

    //float bit representation 23 bit Mantisse M; 8 bit Exponent E
    //=> int representation = 2^23*E + M
    //=> float representation = (1 + M/(2^23)) * 2^(E-127)

    /* Solution s = 1/sqrt(y)
    => log2(s) = log2(1/sqrt(y)) = -0.5 * log2(y)
    
    */
}
