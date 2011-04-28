package editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import actor.Actor;

/**
 * This 2d grid is used to display actors on a coordinate plane in either XY, XZ, or YZ space.
 * @author ty
 *
 */
public class Grid2d extends JPanel {
    private static final long serialVersionUID = -6582427625015220206L;
    
    private final Color X_COLOR = Color.RED;
    private final Color Y_COLOR = Color.GREEN;
    private final Color Z_COLOR = Color.BLUE;

    private final Color hVertexColor;
    private final Color vVertexColor;

    public final Context context;
    private Map<Actor,Color> actorMap = new HashMap<Actor,Color>();

    private double scale = 1;

    /**
     * Sets the context ie. XY, XZ, YZ
     * @param context The context of this particular grid
     */
    public Grid2d(Context context) {
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                try{
                    mouseCoords(e.getX(),e.getY());
                }
                //Because the grid isn't initialized
                catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        });		
        this.context = context;

        //depending on the context, color the axes differently
        switch(context){
            case XY:
                hVertexColor = X_COLOR;
                vVertexColor = Y_COLOR;
                break;
            case XZ:
                hVertexColor = X_COLOR;
                vVertexColor = Z_COLOR;
                break;
            case YZ:
                hVertexColor = Z_COLOR;
                vVertexColor = Y_COLOR;
                break;
            default:
                hVertexColor = Color.GRAY;
                vVertexColor = Color.GRAY;
        }

        setLayout(null);
    }

    /**
     * override for JPanels method
     * @param g the graphics component
     */
    public void paintComponent(Graphics g){
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        super.paintComponent(g);
        refresh();
    }

    /**
     * Takes a local point and transforms it to a global point
     * @param x The X coordinate of the local point
     * @param y The Y coordinate of the local point
     * @return The global coordinate
     */
    private Point ltg(int x,int y){
        int w = this.getWidth();
        int h = this.getHeight();

        return new Point((int)((w/2+scale*x)),(int) ((h/2-scale*y)));
    }
    /**
     * Takes a local point and transforms it to a global point
     * @param p The local Point
     * @return The global Point
     */
    private Point ltg(Point p){
        return ltg(p.x,p.y);
    }

    /**
     * Takes a global point and transforms it to a local point
     * @param x The X coordinate of the global point
     * @param y The Y coordinate of the global point
     * @return The local Point
     */
    private Point gtl(int x, int y){
        int w = this.getWidth();
        int h = this.getHeight();

        return new Point((int)(-(w/2-x)/scale),(int)((h/2-y)/scale));
    }
    /**
     * Takes a global point and transforms it to a local point
     * @param p The global point
     * @return The local point
     */
    @SuppressWarnings("unused")
    private Point gtl(Point p){
        return gtl(p.x,p.y);
    }

    /**
     * Draws the axis.
     */
    private void drawAxis(){
        Graphics g = this.getGraphics();

        int w = this.getWidth();
        int h = this.getHeight();

        int hL = (int) (10/scale); //stands for hash Length
        int spacing = (int) (10/scale);

        //horizontal
        g.setColor(hVertexColor);
        g.drawLine(0, h/2, w, h/2);
        for(int i=0;i<(w/2)/scale;i+=spacing){
            Point pos1 = ltg(i,-hL/2);
            Point pos2 = ltg(i,hL/2);

            Point neg1 = ltg(-i,-hL/2);
            Point neg2 = ltg(-i,hL/2);

            g.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
            g.drawLine(neg1.x, neg1.y, neg2.x, neg2.y);

        }
        //vertical
        g.setColor(vVertexColor);
        g.drawLine(w/2, 0, w/2, h);
        for(int i=0;i<(h/2)/scale;i+=spacing){
            Point pos1 = ltg(-hL/2,i);
            Point pos2 = ltg(hL/2,i);

            Point neg1 = ltg(-hL/2,-i);
            Point neg2 = ltg(hL/2,-i);

            g.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
            g.drawLine(neg1.x, neg1.y, neg2.x, neg2.y);
        }
    }

    /**
     * Draws the actors to the screen
     */
    private void drawActors(){
        Graphics g = this.getGraphics();
        //Goes through all of the actors
        for(Actor a: this.actorMap.keySet()){
            Point p;
            int defSize = (int)a.getSize().magnitude();
            int size = (int) (defSize* this.scale);

            switch(this.context){
                case XY:
                    p = new Point((int)a.getPosition().x,(int)a.getPosition().y);
                    break;
                case XZ:
                    p = new Point((int)a.getPosition().x,(int)a.getPosition().z);
                    break;
                case YZ:
                    p = new Point((int)a.getPosition().z,(int)a.getPosition().y);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            //convert the local point to global
            Point glo = ltg(p);
            g.setColor(this.actorMap.get(a));
            g.drawOval(glo.x-size/2, glo.y-size/2, size, size);
        }
    }
    
    /**
     * Updates the actors Map and then repaints the screen
     * @param actors The map of actors to colors
     */
    public void updateActors(Map<Actor,Color> actors){
        this.actorMap = actors;
        this.refresh();
    }
    
    /**
     * Gets called when the mouse moves on another grid 
     * @param exGrid The external grid that was moved
     * @param x The global X position on the external Grid
     * @param y The global Y position on the external Grid
     */
    public void mouseChange(Grid2d exGrid,int x,int y){
        int lx =1110,ly=1110;

        switch(exGrid.context){
            case XY:
                switch(this.context){                   
                    case XZ:
                        lx=x;
                        break;
                    case YZ:
                        ly=y;
                        break;
                }
                break;
            case XZ:
                switch(this.context){                   
                    case XY:
                        lx=x;
                        break;
                    case YZ:
                        lx=exGrid.getWidth()-y;
                        break;
                }
                break;
            case YZ:
                switch(this.context){
                    case XY:
                        ly=y;
                        break;
                    case XZ:
                        ly=exGrid.getHeight() - x;
                        break;
                }
                break;

        }
        
        this.mouseCoords(lx, ly);
    }

    /**
     * Called whenever the mouse moves on this grid
     * @param x The global X position of the mouse on this grid
     * @param y The global Y position of the mouse on this grid
     */
    private void mouseCoords(int x, int y){
        Graphics g = this.getGraphics();

        refresh();
        //draw vertical line
        g.setColor(hVertexColor);
        g.drawLine(x, 0, x, this.getHeight());
        //draw horizontal line
        g.setColor(vVertexColor);
        g.drawLine(0, y, this.getWidth(), y);
        Point pos = gtl(x,y);

        g.setColor(Color.BLACK);
        g.drawString("("+pos.x+","+pos.y+")",x+5,y-5);
    }

    /**
     * Zooms in the grid by a scale of diff.
     * Ie, 2 would zoom in by a factor of 2 while 0.5 would zoom out.
     * @param diff The scale by which to zoom
     */
    public void zoom(double diff){
        this.scale*=diff;
        this.refresh();
    }

    /**
     * Repaints everything
     */
    public void refresh(){
        try{
            Graphics g = this.getGraphics();
            g.clearRect(0, 0, this.getWidth(), this.getHeight());
            this.drawAxis();
            this.drawActors();
        }
        catch(NullPointerException e){
            //not ready yet.
        }
    }

    /**
     * Just a stupid Point class.  Not really sure why I even made this
     * @author ty
     */
    private static class Point{
        public int x;
        public int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    /**
     * The contexts for the different types of grids
     * @author ty
     */
    enum Context{
        XY,
        XZ,
        YZ
    }
}
