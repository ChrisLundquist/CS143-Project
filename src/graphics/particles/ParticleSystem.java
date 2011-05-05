package graphics.particles;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
//import com.sun.opengl.util.*;
import javax.media.opengl.*;

import com.jogamp.common.nio.Buffers;

import graphics.Texture;
import java.nio.ByteBuffer;

public class ParticleSystem {

    ///////////////// Constants /////////////////////////

    private final int MAX_PARTICLES = 500;
    // We may want more than one texture for particle evolution.
    final int nbTexture = 2;

    //////////////// Variables /////////////////////////

    private ParticleFire p[] = new ParticleFire[MAX_PARTICLES];
    // Array for texture ids.
    // Texturs are share by particles 
    // private int[] textures = new int[ nbTexture ] ;
    Texture texture[] = new Texture[4];
    String RED = "assets/images/particles/redParticle.jpg";
    String ORANGE = "assets/images/particles/orangeParticle.jpg";
    String YELLOW ="assets/images/particles/yellowParticle.jpg";
    String WHITE= "assets/images/particles/whiteParticle.jpg";
    ///////////////// Functions /////////////////////////
    public ParticleSystem() {
        texture[0] = Texture.findOrCreateByName(YELLOW);
        texture[1] = Texture.findOrCreateByName(ORANGE);
        texture[2] = Texture.findOrCreateByName(RED);
        texture[3] = Texture.findOrCreateByName(WHITE);
    }
    public void init( GL2 gl)
    {

        // Particles are tranparent.
        gl.glEnable( GL.GL_BLEND );    
        gl.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE );

        // Prepare textures for paricles.
        gl.glEnable( GL.GL_TEXTURE_2D );
        //gl.glGenTextures( nbTexture, textures, 0 );
        // Square textures size.
        byte b[]; // Array for texture data.
        final int size = 256;
        // Texture 0.
        b = calcTextureData( size, 4, 250, 50, 5, 2f );
        initTexture( gl, b, 0, size );
        // Texture 1.
        b = calcTextureData( size, 4, 50, 50, 55, 1f );
        initTexture( gl, b, 1, size );
    }

    private void initTexture( GL2 gl, byte b[], int index, int size )
    {
        //gl.glBindTexture( GL.GL_TEXTURE_2D,textures[ index ] );
        texture[0].bind(gl);
        texture[1].bind(gl);
        texture[2].bind(gl);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        // Set texture data.
        ByteBuffer texture = Buffers.newDirectByteBuffer(b.length);
        texture.put( b, 0, b.length );
        gl.glTexImage2D( GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, size, size, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, texture.rewind() ) ;


        /* gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
      gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
      // Set texture data.
      ByteBuffer texture = Buffers.newDirectByteBuffer(b.length);
      texture.put( b, 0, b.length );
      gl.glTexImage2D( GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, size, size, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, texture.rewind() ) ;*/

    }

    //Create particle Texture.
    private byte[] calcTextureData( int size, int bytesPerPixel,
            int r, int g, int b, 
            float alphaScale )
    {      
        final int halfSize = size/2;
        final int nbBytes = size*size*bytesPerPixel;
        final int nbBytesRow = size*bytesPerPixel;
        // Byte-Array for Image.
        byte[] data = new byte[nbBytes];

        //RGB-Werte 150,150,255 (some blue)
        for(int i=0; i < nbBytes; i+=bytesPerPixel){
            data[i]   = (byte)r;
            data[i+1] = (byte)g;
            data[i+2] = (byte)b;
            //data[i+3] = (byte)255; // Calc below.
        }      
        // Re-calc Alpha with max in center and radia reduction.
        final int maxAlpha = 100;
        for(int y=0; y < size; y++) {
            for(int x=0; x < size; x++) {
                int dx = x - halfSize;
                int dy = y - halfSize;
                int a = maxAlpha - 
                (int) ( Math.sqrt((double)(dx*dx+dy*dy)) 
                        * alphaScale );
                if (a < 0) { a = 0; }
                data[ y * nbBytesRow + x * bytesPerPixel + 3 ] = (byte)a;
            }
        }   
        return data;
    }


    private ParticleFire createParticle()
    {
        ParticleFire p = new ParticleFire( 500f, 0f, 0f );
        p.setSpeed( // Init random Speed.
                0.001f - (float)Math.random() / 500.0f,
                0.008f - (float)Math.random() / 1000.0f,
                0.001f - (float)Math.random() / 500.0f 
        );
        return p;
    }

    ////////////////// draw ////////////////////////////////

    public void draw( GL2 gl )
    {

        gl.glDepthMask( false );

        // Loop over particles.
        for( int i=0; i < MAX_PARTICLES; i++ )
        {
            // Create new particles for continuous effect.
            if ( p[i] == null ) 
            {
                p[i] = createParticle();
                break; // Create one particle per time step.
            }

            // Kill particle if it hits the ground or died.
            if ( p[i].getPosY() < 0.0f || ! p[i].isAlive() ) 
            {
                p[i] = createParticle();
            }

            // Apply gravity.
            p[i].incSpeedY( -0.00004f );
            p[i].evolve();

           // Select texture and draw.
            if( p[i].getLifetime() > 200 ) {
                gl.glEnable(GL2.GL_TEXTURE_2D);
                texture[0].bind(gl);
            } 
            else if(p[i].getLifetime() > 50) {
                gl.glEnable(GL2.GL_TEXTURE_2D);
                texture[1].bind(gl);
            }
            else {
                gl.glEnable(GL2.GL_TEXTURE_2D);
                texture[2].bind(gl);
            }
            p[i].draw( gl );

        } // end particle loop.

        gl.glDepthMask( true );
    }

}