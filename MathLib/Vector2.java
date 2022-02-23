package MathLib;

public class Vector2
{
    public static final Vector2 zero = new Vector2(0 ,0);
    public static final Vector2 one = new Vector2(1 ,1);

    public double x = 0;
    public double y = 0;

    public Vector2() { }
    public Vector2(double x, double y) { this.x = x; this.y = y; }

    public Vector2 add(Vector2 b)
    {
        return new Vector2(x + b.x, y + b.y);
    }
    public Vector2 sub(Vector2 b){
        return new Vector2(x - b.x, y - b.y);
    }

    public Vector2 mul(double mul)
    {
        return new Vector2(x * mul, y * mul);
    }

    public double len()
    {   
        return Math.sqrt(x*x + y*y);
    }

    public double dist(Vector2 b)
    {
        return Math.sqrt((x-b.x)*(x-b.x) + (y-b.y)*(y-b.y));
    }
    public double sqrDist(Vector2 b)
    {
        return (x-b.x)*(x-b.x) + (y-b.y)*(y-b.y);
    }

    
    public void normalize(){
        double l = len();
        x /= l;
        y /= l;
    }
    public Vector2 normalized(){
        double l = len();
        return new Vector2(x / l, y / l);
    }


    public void ClipDiagLength(double max){
        if(x > max) x = max;
        if(y > max) y = max;
    }
}