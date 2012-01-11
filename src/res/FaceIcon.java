package res;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.UIManager;

public class FaceIcon extends BufferedImage
{
  public FaceIcon( final boolean active )
  {
    super( 16, 16, TYPE_INT_RGB );

    final BufferedImage bI = new BufferedImage( 18, 18, TYPE_INT_ARGB );

    final Graphics2D g = bI.createGraphics();
    g.setColor( Color.black );
    g.fillOval(  2,  2, 15, 15 );
    g.setColor( Color.white );
    g.fillOval(  1,  1, 15, 15 );
    g.setColor( Color.black );
    g.drawLine(  8,  8, 13,  8 );
    g.drawLine(  8,  9,  8, 14 );
    g.setColor( Color.gray );
    g.drawLine(  9,  5,  8,  6 );
    g.drawLine( 12,  5, 11,  6 );
    g.setColor( Color.blue );
    g.drawLine(  9,  6,  9,  6 );
    g.drawLine( 12,  6, 12,  6 );
    g.dispose();

    final float bK[] = { 0, .1f, 0, .1f, 1, .1f, 0, .1f, 0 };
    ConvolveOp c = new ConvolveOp( new Kernel( 3, 3, bK ) );
    final BufferedImage nI = c.filter( bI, null );

    final Graphics2D t = createGraphics();
    t.setColor( (Color) UIManager.get( active ? "activeCaption" : "inactiveCaption" ) );
    t.fillRect( 0, 0, 16, 16 );
    t.drawImage( nI, -1, -1, null );
    t.dispose();
  }
}
