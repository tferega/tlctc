package main;

import gui.DisplayPanel;

import java.awt.Cursor;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class MainApplet extends JApplet implements Runnable
{
    @Override
    public void run()
    {
      setContentPane( new DisplayPanel() );
      setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );

        start();
    }

    @Override
    public void start() {}

    @Override
    public void stop()
    {
    }

    @Override
    public void init()
    {
        if ( SwingUtilities.isEventDispatchThread() )
        {
            run();
            return;
        }

        try
        {
            SwingUtilities.invokeAndWait( this );
        }
        catch ( final Throwable t )
        {
            throw new RuntimeException( "Could not initialize applet!", t );
        }
    }

    private static final long serialVersionUID = 0xF00096351CCA6F57L;
}
