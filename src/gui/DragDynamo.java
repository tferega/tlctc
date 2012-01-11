package gui;

public class DragDynamo extends AngularDynamo
{
  // ##### FIELDS #####
  private double d;    // Drag coefficient
  private double f;    // Friction coefficient

  // ##### CONSTRUCTOR / ACCESSORS #####
  public DragDynamo(double d, double f)
  {
    if (d < 0) throw new IllegalArgumentException("Drag coefficient cannot be less than 0");
    if (f < 0) throw new IllegalArgumentException("Friction coefficient cannot be less than 0");
    this.d = d;
    this.f = f;
  }
  public double getd() { return d; }
  public double getf() { return f; }



  // ##### METHODS #####
  public void move(double F, double dt)
  {
    double s = Math.signum(v);

    double Fd = s * d * Math.pow(v, 2);
    double Ff = f * v;
    super.move(F-Fd-Ff, dt);
  }
}
