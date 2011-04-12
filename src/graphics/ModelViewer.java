package graphics;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
 
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.swing.JFileChooser;
 
import com.jogamp.opengl.util.Animator;
 
public class ModelViewer implements GLEventListener, KeyListener {
    float rotateT = 0.0f;
    Model model;
 
    static GLU glu = new GLU();
 
    static GLCanvas canvas = new GLCanvas();
 
    static Frame frame = new Frame("Model Viewer");
 
    static Animator animator = new Animator(canvas);
 
    public ModelViewer(File objFile) {
        model = WavefrontObjLoader.load(objFile);
    }

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -20.0f);
 
        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);

        model.render(gl);
 
        rotateT += 0.2f;
    }
 
    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }
 
    public void init(GLAutoDrawable gLDrawable) {
        GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        ((Component) gLDrawable).addKeyListener(this);        
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
 
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                exit();
                break;
            case KeyEvent.VK_L:
                loadModel();
                break;
        }
    }
 
    private void loadModel() {
        model = WavefrontObjLoader.load(guiPromptForFilename());
    }

    public void keyReleased(KeyEvent e) {
    }
 
    public void keyTyped(KeyEvent e) {
    }
 
    public static void exit() {
        animator.stop();
        frame.dispose();
        System.exit(0);
    }
 
    public static void main(String[] args) {
        File objFile = guiPromptForFilename();
        
        canvas.addGLEventListener(new ModelViewer(objFile));
        frame.add(canvas);
        frame.setSize(640, 480);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }
 
    public void dispose(GLAutoDrawable gLDrawable) {
        // do nothing
    }
    
    private static File guiPromptForFilename() {
        JFileChooser jfc = new JFileChooser("assets");
        
        if (jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.err.println("User did not specify an input file.");
            System.exit(1);
        }

        return jfc.getSelectedFile();
    }

}