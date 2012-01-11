package gui;

public class EpicFail
{
  private double tOld;
  private TuggablePendulum pendulum;

  public EpicFail()
  {
    pendulum = new TuggablePendulum(0, 1);
    pendulum.init((3d/4d)*Math.PI, 0);
  }

  public double process(long tNew, double θg, double Fg, double tug)
  {
    if (tOld == 0)
      tOld = tNew;
    double dt = (tNew - tOld) / 1000d;
    tOld = tNew;

    pendulum.tug(tug);
    pendulum.move(θg, Fg, dt);
    return pendulum.getθ();
  }

  public double getPos() { return pendulum.getθ(); }
}
