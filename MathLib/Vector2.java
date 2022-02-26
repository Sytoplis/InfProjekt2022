package MathLib;

public class Vector2
{
    public static final Vector2 zero() { return new Vector2(0 ,0); }
    public static final Vector2 one() { return new Vector2(1 ,1); }

    public double x = 0;
    public double y = 0;

    public Vector2() { }
    public Vector2(double x, double y) { this.x = x; this.y = y; }
    public Vector2(Vector2 vec) { this.x = vec.x; this.y = vec.y; }

    //IMPORTANT NOTE: DO NOT CREATE FOR EVERYTHING NEW VECTOR OBJECTS!! YOU ARE KILLING THE MEMORY!! STOP THAT!!


    /*
    public Vector2 add(Vector2 b){
        return new Vector2(x + b.x, y + b.y);
    }
    public Vector2 sub(Vector2 b){
        return new Vector2(x - b.x, y - b.y);
    }

    public Vector2 mul(double mul){
        return new Vector2(x * mul, y * mul);
    }*/
    public Vector2 add(Vector2 b){
        x += b.x;
        y += b.y;
        return this;
    }
    public Vector2 sub(Vector2 b){
        x -= b.x;
        y -= b.y;
        return this;
    }
    public Vector2 mul(double mul){
        x *= mul;
        y *= mul;
        return this;
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

    
    public Vector2 normalize(){
        double l = len();
        x /= l;
        y /= l;
        return this;
    }
    /*
    public Vector2 normalized(){
        double l = len();
        return new Vector2(x / l, y / l);
    }*/

    /*
    public Vector2 clamp(double min, double max){
        return new Vector2(MathLib.clamp(min, max, x), MathLib.clamp(min, max, y));
    }
    public Vector2 clamp(Vector2 min, Vector2 max){
        return new Vector2(MathLib.clamp(min.x, max.x, x), MathLib.clamp(min.y, max.y, y));
    }*/

    public Vector2 clamp(double min, double max){
        x = MathLib.clamp(min, max, x);
        y = MathLib.clamp(min, max, y);
        return this;
    }
    public Vector2 clamp(Vector2 min, Vector2 max){
        x = MathLib.clamp(min.x, max.x, x);
        y = MathLib.clamp(min.y, max.y, y);
        return this;
    }


    @Override
    public String toString(){
        return String.valueOf(x) + " ; " + String.valueOf(y);
    }
}