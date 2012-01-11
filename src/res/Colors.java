package res;

import java.awt.Color;

public enum Colors
{
    MAIN_BACKGROUND ( 230, 223, 255 ),
    MAIN_BCK_FILLER ( 238, 238, 238 ),
    MAIN_TOP_BORDER ( 241, 233, 255 );

    public final Color col;

    private Colors( final int r, final int g, final int b )
    {
        col = new Color( r, g, b );
    }
}
