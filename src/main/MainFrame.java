package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;

import res.Bounds;
import res.FaceIcon;

public class MainFrame extends JFrame
{
  public static final String TITLE = "TLCTC Applet v0.0";

  public MainFrame()
  {
    super( TITLE );
    setDefaultCloseOperation( DISPOSE_ON_CLOSE );

    setIconImage( new FaceIcon( true ) );

    final MainApplet mA = new MainApplet();
    add( mA );
    mA.init();
  }

  public static void centerOnScreen( final Window window, final Bounds bounds, final Bounds min )
  {
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    window.setBounds( bounds.convert( screenSize ) );
    window.setMinimumSize( min.convert( screenSize ).getSize() );
    window.setVisible( true );
  }

  public static void setDecoratedLookAndFeel( final boolean lf )
  {
    Toolkit.getDefaultToolkit().setDynamicLayout( true );
    setDefaultLookAndFeelDecorated( lf );
  }

  private static final long serialVersionUID = 0xD6BC9DD027C11A3EL;
}
