package editor.ty;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JLabel;

import actor.Actor;


public class Grid2d extends JPanel {
    private final Color X_COLOR = Color.RED;
    private final Color Y_COLOR = Color.GREEN;
    private final Color Z_COLOR = Color.BLUE;

    private final Color hVertexColor;
    private final Color vVertexColor;
    private final Color TEXT = Color.BLUE;

    public final Context context;
    private ArrayList<Actor> actors = new ArrayList<Actor>();

    private double scale = 1;

    public Grid2d(Context context) {
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                try{
                    moveCrosshair(gtl(e.getX(),e.getY()));
                }
                catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        });		
        this.context = context;

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

    public void paintComponent(Graphics g){
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        super.paintComponent(g);
        refresh();
    }

    private void moveCrosshair(Point p){
        Graphics g = this.getGraphics();
        
        Point global = ltg(p);

        refresh();
        //draw vertical line
        g.setColor(hVertexColor);
        g.drawLine(global.x, 0, global.x, this.getHeight());
        //draw horizontal line
        g.setColor(vVertexColor);
        g.drawLine(0, global.y, this.getWidth(), global.y);
        Point pos = gtl(global.x,global.y);

        g.setColor(Color.BLACK);
        g.drawString("("+pos.x+","+pos.y+")",10,10);
    }
    
    private Point ltg(int x,int y){
        int w = this.getWidth();
        int h = this.getHeight();

        return new Point((int)((w/2+scale*x)),(int) ((h/2-scale*y)));
    }
    private Point ltg(Point p){
        return ltg(p.x,p.y);
    }

    private Point gtl(int x, int y){
        int w = this.getWidth();
        int h = this.getHeight();

        return new Point((int)(-(w/2-x-w/2)/scale),(int)((h/2-y)/scale));
    }
    private Point gtl(Point p){
        return gtl(p.x,p.y);
    }

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

    private void drawActors(){
        Graphics g = this.getGraphics();
        for(Actor a: this.actors){
            Point p;
            int defSize = (int)a.getSize();
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

            Point glo = ltg(p);
            g.drawOval(glo.x-size/2, glo.y-size/2, size, size);
        }
    }
    public void updateActors(ArrayList<Actor> actors){
        this.actors = actors;
        this.refresh();
    }

    public void zoom(double diff){
        this.scale*=diff;
        this.refresh();
    }

    public void refresh(){
        try{
            Graphics g = this.getGraphics();
            g.clearRect(0, 0, this.getWidth(), this.getHeight());
            this.drawAxis();
            g.drawString(String.valueOf(this.scale), 10, 25);
            this.drawActors();
            g.drawString(this.context.toString(), 100, 100);
        }
        catch(NullPointerException e){
            //not ready yet.
        }
    }

    public static class Point{
        public int x;
        public int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    enum Context{
        XY,
        XZ,
        YZ
    }
}
