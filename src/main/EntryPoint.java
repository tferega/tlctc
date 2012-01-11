package main;

import javax.swing.SwingUtilities;

import res.Bounds;

public class EntryPoint implements Runnable
{
  public static void main( final String[] args )
  {
    MainFrame.setDecoratedLookAndFeel( true );

    SwingUtilities.invokeLater( new EntryPoint() );
  }

  @Override
  public void run()
  {
    MainFrame.centerOnScreen( new MainFrame(), Bounds.FRAME_INIT, Bounds.FRAME_MIN );
  }
}
