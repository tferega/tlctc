package gui;

public class TuggablePendulum extends Pendulum
{
  // ##### CONSTRUCTOR / ACCESSORS #####
  public TuggablePendulum(double d, double f)
  {
    super(d, f);
  }

  // ##### METHODS #####
  public void tug(double v)
  {
    this.v = this.v + v;
  }
}
