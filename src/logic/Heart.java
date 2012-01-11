package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Heart implements ActionListener
{
    private static final int SLEEP_CYCLE = 1;
    private final Timer timer;

    private final Calendar cal;
    private final JPanel jPanel;

    public Heart( final JPanel jPanel )
    {
        cal = Calendar.getInstance();
        timer = new Timer( SLEEP_CYCLE, this );
        timer.start();

        this.jPanel = jPanel;
    }

    public Calendar getTime()
    {
        return cal;
    }

    @Override
    public void actionPerformed( final ActionEvent aE )
    {
        cal.setTimeInMillis( aE.getWhen() );
        jPanel.repaint();
    }
}
