package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Map;

import javax.swing.JPanel;

import actor.Actor;
/**
 * The gridPacker does what it's name suggests: it packs 2d Grids into a usable JPanel.
 * Now you just pass the Grid Packer a Map of <Actor,Color> and it will draw that to the screen.
 * @author ty
 *
 */
public class GridPacker extends JPanel {
    private static final long serialVersionUID = -2727178296952482512L;
    Grid2d xyGrid;
    Grid2d xzGrid;
    Grid2d yzGrid;
    
    Grid2d[] grids=new Grid2d[3];
    
    public GridPacker() {
        this.setLayout(null);
        
        /*
         * Sets up the XY Grid
         */
        this.xyGrid =  new Grid2d(Grid2d.Context.XY);
        this.xyGrid.setBounds(0,0,300,300);
        this.xyGrid.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                updateMouseLines(xyGrid,e.getX(),e.getY());
            }
        });     
        this.add(xyGrid,BorderLayout.CENTER);
        
        /*
         * Sets up the XZ Grid 
         */
        this.xzGrid =  new Grid2d(Grid2d.Context.XZ);
        this.xzGrid.setBounds(300,0,300,300);
        this.xzGrid.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                updateMouseLines(xzGrid,e.getX(),e.getY());
            }
        }); 
        this.add(xzGrid,BorderLayout.CENTER);
        
        /*
         * Sets up the YZ Grid
         */
        this.yzGrid =  new Grid2d(Grid2d.Context.YZ);
        this.yzGrid.setBounds(600,0,300,300);
        this.yzGrid.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                updateMouseLines(yzGrid,e.getX(),e.getY());
            }
        }); 
        this.add(yzGrid,BorderLayout.CENTER);
      
        //Adds all of the grids to the array of grids
        grids[0]=xyGrid;
        grids[1]=xzGrid;
        grids[2]=yzGrid;
       
        this.setSize(900, 300); 
    }
    
    /**
     * Takes the grid that the mouse is currently on and gets its x,y coordinates
     * and passes them to the other grids
     * @param grid The grid that the mouse is currently on
     * @param x The X coordinate (globally) of the current screen
     * @param y The Y coordinate (globally) of the current screen
     */
    private void updateMouseLines(Grid2d grid,int x,int y){
        for(Grid2d g:grids){
            if(!g.equals(grid)){
                g.mouseChange(grid,x,y);
            }
        }
    }
    
    /**
     * Zooms in all of the grids to a certain offset
     * @param offset The difference applied to the zoom level of the grid.
     */
    public void zoom(double offset){
        for(Grid2d grid:grids){
            grid.zoom(offset);
        }
    }
    
    /**
     * Takes an Map of <Actor,Color> and updates all of the grids with those Actor, Color pairs
     * @param actors The actors to update
     */ 
    public void updateActors(Map<Actor,Color> actors){
        for(Grid2d grid:grids){
            grid.updateActors(actors);
        }
    }
}
