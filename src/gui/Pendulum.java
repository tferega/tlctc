package gui;

public class Pendulum extends DragDynamo
{
  // ##### CONSTRUCTOR / ACCESSORS #####
  public Pendulum(double d, double f)
  {
    super(d, f);
  }

  // ##### METHODS #####
  public void move(double θg, double Fg, double dt)
  {
    double F = -Fg*Math.sin(θ - θg);
    super.move(F, dt);
  }
}
