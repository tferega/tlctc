package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import logic.Heart;
import res.Colors;

import java.awt.MouseInfo;

public class DisplayPanel extends JPanel implements MouseListener, MouseMotionListener
{
    private final Heart heart;
    private final EpicFail fail;

    private double mouseAngle = Pendulum.DOWN;
    private double mouseForce = 10;

    private double pPosOld = 0;
    private double sPosOld = 0;

    double tug = 0;

    public DisplayPanel()
    {
        final Color col = Colors.MAIN_BACKGROUND.col;
        setBackground( col );

        setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED,
                Color.white, Colors.MAIN_TOP_BORDER.col,
                col.darker().darker(), col.darker() ) );

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        heart = new Heart( this );
        fail = new EpicFail();
    }

    @Override
    public void paint( final Graphics g )
    {
        super.paint( g );

        final int w = getWidth();
        final int h = getHeight();

        final int s = Math.min( w, h );
        final double c = s / 2.0;

        final Graphics2D g2D = (Graphics2D) g;

        g2D.translate( w / 2.0, h / 2.0 );
        g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        final Stroke minStroke = new BasicStroke( s / 150f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
        final Stroke hourStroke = new BasicStroke( s / 75f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
        final Stroke secStroke = new BasicStroke( s / 200f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
        final Stroke failStroke = new BasicStroke( s / 1000f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
        final Stroke gravStroke = new BasicStroke( s / 1000f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );

        g2D.setColor( Color.black );

        for ( int i = 0; i < 60; i ++ )
        {
            final boolean fullHour = ( 0 == i % 5 );

            g2D.setStroke( fullHour ? hourStroke : minStroke );

            final double min = fullHour ? 0.965 : 0.950;
            final double max = fullHour ? 0.885 : 0.900;

            final double cos = Math.cos( i * Math.PI / 30 ) * c;
            final double sin = Math.sin( i * Math.PI / 30 ) * c;

            final Point2D.Double pMin = new Point2D.Double( cos * min, sin * min );
            final Point2D.Double pMax = new Point2D.Double( cos * max, sin * max );

            final Line2D tick = new Line2D.Double( pMin, pMax );
            g2D.draw( tick );
        }

        final Calendar cal = heart.getTime();
        final int hh = cal.get( Calendar.HOUR );
        final int mm = cal.get( Calendar.MINUTE );
        final int ss = cal.get( Calendar.SECOND );
        final int uu = cal.get( Calendar.MILLISECOND );

        final Point2D.Double center = new Point2D.Double();


        final double hMax = 0.6;
        final double hDst = ( hh + ( mm + ss / 60.0 ) / 60.0 ) / 12;
        final double hhCos = Math.cos( ( hDst * 2 - 0.5 ) * Math.PI ) * c;
        final double hhSin = Math.sin( ( hDst * 2 - 0.5 ) * Math.PI ) * c;
        final Point2D.Double hpMax = new Point2D.Double( hhCos * hMax, hhSin * hMax );
        final Line2D hLine = new Line2D.Double( center, hpMax );
        g2D.setStroke( hourStroke );
        g2D.translate( 1, 1 );
        g2D.setColor( Color.black );
        g2D.draw( hLine );
        g2D.translate( -1, -1 );
        g2D.setColor( Color.green );
        g2D.draw( hLine );

        final double mMax = 0.95;
        final double mDst = ( mm + ss / 60.0 ) / 60.0;
        final double mmCos = Math.cos( ( mDst * 2 - 0.5 ) * Math.PI ) * c;
        final double mmSin = Math.sin( ( mDst * 2 - 0.5 ) * Math.PI ) * c;
        final Point2D.Double mpMax = new Point2D.Double( mmCos * mMax, mmSin * mMax );
        final Line2D mLine = new Line2D.Double( center, mpMax );
        g2D.setStroke( minStroke );
        g2D.setColor( Color.black );
        g2D.translate( 1, 1 );
        g2D.draw( mLine );
        g2D.setColor( Color.green.darker() );
        g2D.translate( -1, -1 );
        g2D.draw( mLine );

        final double sMax = 0.5;
        final double sDst = ss / 60.0;
        final double ssCos = Math.cos( ( sDst * 2 - 0.5 ) * Math.PI ) * c;
        final double ssSin = Math.sin( ( sDst * 2 - 0.5 ) * Math.PI ) * c;
        final Point2D.Double spPoint = new Point2D.Double( ssCos * sMax, ssSin * sMax );
        g2D.setColor( Color.black );
        g2D.fillOval((int)spPoint.x-3, (int)spPoint.y-3, 6, 6);

//        final double pPos = fail.getPos();
//        final double sPos = (1-ss/60.0) * (2.0*Math.PI);
////        System.out.println(String.format("%3.3f, %3.3f, %2d", pPos, sPos, ss));
//        double tug = 0;
//        if (isCollision(pPos, sPos)) {
//          tug = 10;
//        }

        final double fMax = 0.95;
        final double fRaw = fail.process(cal.getTimeInMillis(), -mouseAngle - Math.PI/2, mouseForce*10, -tug);
        tug = 0;
        final double fDst = -fRaw - Math.PI/2;
        final double ffCos = Math.cos(fDst) * c;
        final double ffSin = Math.sin(fDst) * c;
        final Point2D.Double fpMax = new Point2D.Double( ffCos * fMax, ffSin * fMax );
        final Line2D fLine = new Line2D.Double( center, fpMax );
        g2D.setStroke( failStroke );
        g2D.setColor( Color.black );
        g2D.translate( 1, 1 );
        g2D.draw( fLine );

        final double gMax = mouseForce;
        final double gDst = (mouseAngle + Math.PI/2) / (Math.PI*2);
        final double gCos = Math.cos( ( gDst * 2 - 0.5 ) * Math.PI ) * c;
        final double gSin = Math.sin( ( gDst * 2 - 0.5 ) * Math.PI ) * c;
        final Point2D.Double gpMax = new Point2D.Double( gCos * gMax, gSin * gMax );
        final Line2D gLine = new Line2D.Double( center, gpMax );
        g2D.setStroke( gravStroke );
        g2D.setColor( Color.red );
        g2D.draw( gLine );

        g2D.setColor( Color.black );
        g2D.drawString( String.format( "%02d:%02d:%02d.%03d (%2.2f))", hh, mm, ss, uu, (fRaw/(2*Math.PI)) ), 0, 50 );
    }

    private void setGravity(MouseEvent e)
    {
      final double w = getWidth();
      final double h = getHeight();

      // x and y relative to the center.
      final double x = e.getX() - (w / 2.0);
      final double y = e.getY() - (h / 2.0);
      final double c = Math.min(w, h) / 2.0;

      if (x > 0) mouseAngle = Math.atan(y/x);
      if ((x < 0) && (y >= 0)) mouseAngle = Math.atan(y/x) + Math.PI;
      if ((x < 0) && (y < 0)) mouseAngle = Math.atan(y/x) - Math.PI;
      if ((x == 0) && (y > 0)) mouseAngle = Math.PI/2;
      if ((x == 0) && (y < 0)) mouseAngle = -Math.PI/2;
      if ((x == 0) && (y == 0)) mouseAngle = 0;

      mouseForce = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / c;
      if (mouseForce > 1)
        mouseForce = 1;
    }

    private boolean isCollision(double pPos, double sPos)
    {
      pPosOld = pPos;
      sPosOld = sPos;

      return false;
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { tug = 5; }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) { setGravity(e); }
    public void mouseDragged(MouseEvent e) {  }

    private static final long serialVersionUID = 0xA785E8C6E53D298EL;
}
