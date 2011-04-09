package game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * The Main menu of the game
 * @author tim
 *
 */
public class MainMenu extends JPanel implements MouseListener {
    BufferedImage image;
    String path = "resources/background.jpg";
    JFrame frame = new JFrame();
    public MainMenu() {
        createMainMenu();
    }
    /**
     * Loads image and and creates gui elements
     */
    private void createMainMenu() {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Can't find resources\background.jpg");
            e.printStackTrace();
        }
        
        //sets properties for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.setVisible(true);
    }
    
    /**
     * Paints the background image to the screen, it scales the image in relation to screen size
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        int w = getWidth();
        int h = getHeight();
        int iw = image.getWidth();
        int ih = image.getHeight();
        double xScale = (double)w/iw;
        double yScale = (double)h/ih;
        double scale = //Math.min(xScale, yScale);    // scale to fit
                       Math.max(xScale, yScale);  // scale to fill
        int width = (int)(scale*iw);
        int height = (int)(scale*ih);
        int x = (w - width)/2;
        int y = (h - height)/2;
        
        g2.drawImage(image, x, y, width, height, this);
    }

    
    /**
     * Checks for if an element of the Main Menu was clicked, if so changes to that element
     */
    public void mouseClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        
        //reminder for myself to remember that I'm doing
        
        /*rectangle with two points in the opposite corners
        (not on the same side of rectangle!), (x1, y1) and (x2, y2), and you
        also have a point to test, (Xp, Yp) then this will return true if the
        point is on the border or inside of the rectangle:

        return (Xp >= x1 && Xp <= x2) && (Yp >= y1 && Yp <= y2);
        */
        
    }
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    //for debugging purposes
    public static void main(String[] args) throws IOException {
        MainMenu test = new MainMenu();
    }
}