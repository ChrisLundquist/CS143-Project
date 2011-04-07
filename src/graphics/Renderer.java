package graphics;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.Animator;

public class Renderer implements GLEventListener {
     GLU glu;
     GLCanvas canvas;
     Frame frame;
     Animator animator;
     
     public Renderer(){
          glu = new GLU();
          canvas = new GLCanvas();
          frame = new Frame("Jogl Quad drawing");
          animator = new Animator(canvas);
     }
     private static float rotateT;
     public void display(GLAutoDrawable gLDrawable) {
         //TODO render something we care about
         final GL2 gl = gLDrawable.getGL().getGL2();
         gl.glClear(GL.GL_COLOR_BUFFER_BIT);
         gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
         gl.glLoadIdentity();
         gl.glTranslatef(0.0f, 0.0f, -5.0f);
  
         // rotate on the three axis
         gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
         gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
         gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
  
         // Draw A Quad
         gl.glBegin(GL2.GL_QUADS);               
             gl.glColor3f(0.0f, 1.0f, 1.0f);   // set the color of the quad
             gl.glVertex3f(-1.0f, 1.0f, 0.0f);      // Top Left
             gl.glVertex3f( 1.0f, 1.0f, 0.0f);       // Top Right
             gl.glVertex3f( 1.0f,-1.0f, 0.0f);      // Bottom Right
             gl.glVertex3f(-1.0f,-1.0f, 0.0f);     // Bottom Left
         // Done Drawing The Quad
         gl.glEnd();                                                     
  
         // increasing rotation for the next iteration                                 
         rotateT += 0.2f; 
     }
  
     public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
     }
  
     public void init(GLAutoDrawable gLDrawable) {
         GL2 gl = gLDrawable.getGL().getGL2();
         gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
         gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
         gl.glClearDepth(1.0f);
         gl.glEnable(GL.GL_DEPTH_TEST);
         gl.glDepthFunc(GL.GL_LEQUAL);
         gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
         ((Component) gLDrawable).addKeyListener(new input.InputHandler());
     }
  
     public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
         GL2 gl = gLDrawable.getGL().getGL2();
         if (height <= 0) {
             height = 1;
         }
         float h = (float) width / (float) height;
         gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
         gl.glLoadIdentity();
         glu.gluPerspective(50.0f, h, 1.0, 1000.0);
         gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
         gl.glLoadIdentity();
     }
  
     public void exit() {
         animator.stop();
         frame.dispose();
         System.exit(0);
     }
     
     public void dispose(GLAutoDrawable gLDrawable) {
         // do nothing
     }
    public void start() {
        canvas.addGLEventListener(this);
        frame.add(canvas);
        frame.setSize(640, 480);
        frame.setUndecorated(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();        
    }
}
