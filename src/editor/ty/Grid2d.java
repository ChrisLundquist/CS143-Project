package editor.ty;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseMotionAdapter;
import javax.swing.SwingConstants;
import javax.swing.JLabel;


public class Grid2d extends JPanel {
	private final Color VERTEX = Color.BLACK;
	private final Color TEXT = Color.BLUE;
	
	private double scale = 1;
	private JLabel posLbl;

	public Grid2d() {
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				try{
					mouseCoords(e.getX(),e.getY(),getGraphics());
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});		
		this.setSize(500, 500);	
		setLayout(null);
		
		JButton button = new JButton("+");
		button.setBounds(12, 30, 44, 25);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		add(button);
		
		JButton button_1 = new JButton("-");
		button_1.setBounds(12, 67, 44, 25);
		button_1.setHorizontalAlignment(SwingConstants.LEFT);
		add(button_1);
		
		posLbl = new JLabel("(x,y)");
		posLbl.setBounds(12, 3, 70, 15);
		add(posLbl);
	}
	
	public void paintComponent(Graphics g){
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		super.paintComponent(g);
		this.drawAxis(g);
		this.drawItems(g);
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
		
		return new Point((int)(-(w/2-x)/scale),(int)((h/2-y)/scale));
	}
	private Point gtl(Point p){
		return gtl(p.x,p.y);
	}
	
	private void drawAxis(Graphics g){
		int w = this.getWidth();
		int h = this.getHeight();
		
		int hL = (int) (10/scale); //stands for hash Length
		int spacing = (int) (10/scale);
		
		//draw the vertical axis
		g.setColor(VERTEX);
		g.drawLine(w/2, 0, w/2, h);
		g.drawLine(0, h/2, w, h/2);
		
		Point center= ltg(20,20);
		g.drawOval(center.x, center.y, 10, 10);
		
		//set hashes on horizontal
		for(int i=0;i<(w/2)/scale;i+=spacing){
			Point pos1 = ltg(i,-hL/2);
			Point pos2 = ltg(i,hL/2);
			
			Point neg1 = ltg(-i,-hL/2);
			Point neg2 = ltg(-i,hL/2);
			
			g.setColor(VERTEX);
			g.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
			g.drawLine(neg1.x, neg1.y, neg2.x, neg2.y);
			
		}
		//vertical
		for(int i=0;i<(h/2)/scale;i+=spacing){
			Point pos1 = ltg(-hL/2,i);
			Point pos2 = ltg(hL/2,i);
			
			Point neg1 = ltg(-hL/2,-i);
			Point neg2 = ltg(hL/2,-i);
			
			g.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
			g.drawLine(neg1.x, neg1.y, neg2.x, neg2.y);
		}
	}
	
	private void drawItems(Graphics g){
		int defSize = 5;
		int size = (int) (defSize * this.scale);
		
		
	}
	
	public void mouseCoords(int x, int y,Graphics g){
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		//draw vertical line
		g.drawLine(x, 0, x, this.getHeight());
		//draw horizontal line
		g.drawLine(0, y, this.getWidth(), y);
		Point pos = gtl(x,y);
		
		g.drawString("("+pos.x+","+pos.y+")",10,10);
		this.drawAxis(g);
		this.drawItems(g);
	}
	
	private static class Point{
		public int x;
		public int y;
		
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
}
