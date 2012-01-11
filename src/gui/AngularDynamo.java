package gui;

public class AngularDynamo
{
  // ##### CONSTANTS #####
  public static final double UP    = (0d/2d) * Math.PI;
  public static final double LEFT  = (1d/2d) * Math.PI;
  public static final double DOWN  = (2d/2d) * Math.PI;
  public static final double RIGHT = (3d/2d) * Math.PI;

  // ##### FIELDS #####
  protected double θ;   // Current angular position [rad]. θ = 0 is upwards.
  protected double v;   // Current angular velocity [rad/s]. v > 0 is counter-clockwise.

  // ##### CONSTRUCTOR / ACCESSORS #####
  public AngularDynamo()
  {
    this.θ = 0;
    this.v = 0;
  }

  public double getθ() { return θ; }
  public double getv() { return v; }

  // ##### METHODS #####
  public void init(double θ, double v)
  {
    this.θ = normalize(θ);
    this.v = normalize(v);
  }

  public void move(double F, double dt)
  {
    v = v + F*dt;
    θ = normalize(θ + v*dt);
  }

  private double normalize(double arg)
  {
    arg = arg % (2*Math.PI);
    if (arg < 0)
      arg = 2*Math.PI + arg;
    return arg;
  }
}
