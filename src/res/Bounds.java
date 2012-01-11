 package res;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public enum Bounds
{
    FRAME_INIT      ( 0.300, 0.150, 0.400, 0.600 ),
    FRAME_MIN       ( 0.440, 0.420, 0.120, 0.160 ),
    DISPLAY         ( 0.005, 0.005, 0.990, 0.990 );

    private final double x, y, w, h;

    private Bounds( final double x, final double y, final double w, final double h )
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rectangle2D getRect()
    {
        return new Rectangle2D.Double( x, y, w, h );
    }

    public static Rectangle convert( final Rectangle2D rct, final Dimension dim )
    {
        final int cX = round( rct.getX() * dim.width );
        final int cY = round( rct.getY() * dim.height );

        final int cW = round( rct.getWidth() * dim.width );
        final int cH = round( rct.getHeight() * dim.height );

        return new Rectangle( cX, cY, cW, cH );
    }

    public static Point convert( final Point2D pnt, final Dimension dim )
    {
        return new Point( round( pnt.getX() * dim.width ), round( pnt.getY() * dim.height ) );
    }

    public static Point round( final Point2D pnt )
    {
        return new Point( round( pnt.getX() ), round( pnt.getY() ) );
    }

    public static Point2D convert2D( final Point2D pnt, final Dimension dim )
    {
        return new Point2D.Double( pnt.getX() * dim.width, pnt.getY() * dim.height );
    }

    public static Line2D convert2D( final Line2D line, final Dimension dim )
    {
        return new Line2D.Double( convert2D( line.getP1(), dim ),
                                  convert2D( line.getP2(), dim ) );
    }

    public static Deque<Line2D> convert2D( final Queue<Line2D> lines, final Dimension dim )
    {
        final Deque<Line2D> lQ = new ArrayDeque<Line2D>( lines.size() );
        for ( final Line2D cL : lines ) lQ.add( convert2D( cL, dim ) );
        return lQ;
    }

    public Rectangle convert( final Dimension dim )
    {
        return convert( getRect(), dim );
    }

    public static final int round( final double d )
    {
        return (int) ( 0.5 + d );
    }
}
