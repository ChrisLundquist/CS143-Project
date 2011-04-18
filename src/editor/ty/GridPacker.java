package editor.ty;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

import actor.Actor;

public class GridPacker extends JPanel {
    Grid2d xyGrid;
    Grid2d xzGrid;
    Grid2d yzGrid;
    
    Grid2d[] grids=new Grid2d[3];
    
    public GridPacker() {
        this.setLayout(null);
        
        this.xyGrid =  new Grid2d(Grid2d.Context.XY);
        this.xyGrid.setBounds(0,0,300,300);
        this.xyGrid.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                updateMouseLines(xyGrid,e.getX(),e.getY());
            }
        });     
        this.add(xyGrid,BorderLayout.CENTER);
        
        this.xzGrid =  new Grid2d(Grid2d.Context.XZ);
        this.xzGrid.setBounds(300,0,300,300);
        this.xzGrid.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                updateMouseLines(xzGrid,e.getX(),e.getY());
            }
        }); 
        this.add(xzGrid,BorderLayout.CENTER);
        
        this.yzGrid =  new Grid2d(Grid2d.Context.YZ);
        this.yzGrid.setBounds(600,0,300,300);
        this.yzGrid.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                updateMouseLines(yzGrid,e.getX(),e.getY());
            }
        }); 
        this.add(yzGrid,BorderLayout.CENTER);
       
        grids[0]=xyGrid;
        grids[1]=xzGrid;
        grids[2]=yzGrid;
       
        this.setSize(900, 300); 
    }
    
    private void updateMouseLines(Grid2d grid,int x,int y){
        for(Grid2d g:grids){
            if(!g.equals(grid)){
                g.mouseChange(grid,x,y);
            }
        }
    }
    
    public void zoom(double offset){
        for(Grid2d grid:grids){
            grid.zoom(offset);
        }
    }
    
    public void updateActors(ArrayList<Actor> actors){
        for(Grid2d grid:grids){
            grid.updateActors(actors);
        }
    }
}
