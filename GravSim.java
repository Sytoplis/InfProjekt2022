import MathLib.Vector2;

public class GravSim extends Simulation{

    private final double gravitationalConstant = 10;
    private double gravRad = 80;

    public class GravityOJ extends SimOJ{
        public double mass = 1;

        public GravityOJ(AnimationObject animOJ, int id) {
            super(animOJ, id, 5);// initSpeed 5
        }

        @Override
        public void step(double dt) {
           double tempmass = AnimationSurface.instance.Slider1.getValue();
           mass = tempmass/ 10; 
           double tempgravRad = AnimationSurface.instance.Slider2.getValue();
           gravRad = tempgravRad/10;
            CompGravity(dt);
            super.step(dt);
        }

        private void CompGravity(double dt){
            double sqrDist = 0;// allocate memory for sqr distance for faster usage
            Vector2 pos = getPos();

            Vector2 gravForce = Vector2.zero();// F = G*m1*m2*r.norm / |r|² => F_ges = G*m1*(m_i*r_i.norm / |r_i|² + ... + m_j*r_j.norm / |r_j|²)

            Vector2 tempForce = Vector2.zero();
            for(int i = 0; i < simOJs.length; i++){//the O(n²) approach
                if (i == id)
                    continue;// skip self

                sqrDist = simOJs[i].getPos().sqrDist(pos);
                if (sqrDist < gravRad * gravRad) {// if gravOj is in range
                    tempForce.clone(simOJs[i].getPos());
                    tempForce.sub(pos);
                    tempForce.normalize();
                    tempForce.mul(((GravityOJ)simOJs[i]).mass / sqrDist);

                    gravForce.add(tempForce);
                }
            }

                gravForce.mul(gravitationalConstant * mass * dt);
                getVel().add(gravForce);
        }
    }

    @Override
    public Simulation.SimOJ createSimOJ(AnimationObject animOJ, int id) {
        return new GravityOJ(animOJ, id);
    }// use GravityOJ as the standard simoj

    public GravSim(AnimationObject[] initAnim, double width, double height, int threadCount) {
        super(initAnim, width, height, threadCount);
    }
    
}
