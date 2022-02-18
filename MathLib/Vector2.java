package MathLib;

public class Vector2
{
    public static final Vector2 zero = new Vector2(0 ,0);
    public static final Vector2 one = new Vector2(1 ,1);

    public double x = 0;
    public double y = 0;

    public Vector2() { }
    public Vector2(double x, double y) { this.x = x; this.y = y; }

    public Vector2 Add(Vector2 b)
    {
        return new Vector2(x + b.x, y + b.y);
    }

    public Vector2 Mul(double mul)
    {
        return new Vector2(x * mul, y * mul);
    }

    public double len()
    {
        return Math.pow(x*x + y*y, 0.5);
    }

    public double Dist(Vector2 b)
    {
        return Math.pow((x-b.x)*(x-b.x) + (y-b.y)*(y-b.y), 0.5);
    }
}