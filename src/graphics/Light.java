package graphics;

import java.util.Random;

import math.Vector4;

public class Light implements java.io.Serializable{
    private static final long serialVersionUID = -7133937204421075824L;
    protected static Random gen = new Random();
    float constantAttenuation, linearAttenuation, quadraticAttenuation;
    math.Vector4 position, ambient, diffuse, specular;

    public static Light newRandom(float rangeMax){
        Light light = new Light();
        light.setPosition(new Vector4(gen.nextFloat()* rangeMax,
                gen.nextFloat() * rangeMax,
                gen.nextFloat() * rangeMax,
                gen.nextFloat() * rangeMax));
        light.setAmbient(new Vector4(gen.nextFloat(),
                gen.nextFloat(),
                gen.nextFloat(),
                gen.nextFloat()));
        light.setDiffuse(new Vector4(gen.nextFloat(),
                gen.nextFloat(),
                gen.nextFloat(),
                gen.nextFloat()));
        light.setSpecular(new Vector4(gen.nextFloat(),
                gen.nextFloat(),
                gen.nextFloat(),
                gen.nextFloat()));
        
        return light;
    }
    public Light(){
        position = new Vector4();
        ambient = new Vector4();
        diffuse = new Vector4();
        specular = new Vector4();
        constantAttenuation = 100.0f;
        linearAttenuation = 500.0f;
        quadraticAttenuation = 10.0f;
    }

    public math.Vector4 getAmbient() {
        return ambient;
    }

    public float getConstantAttenuation() {
        return constantAttenuation;
    }

    public math.Vector4 getDiffuse() {
        return diffuse;
    }

    public float getLinearAttenuation() {
        return linearAttenuation;
    }

    public math.Vector4 getPosition() {
        return position;
    }

    public float getQuadraticAttenuation() {
        return quadraticAttenuation;
    }

    public math.Vector4 getSpecular() {
        return specular;
    }

    public Light setAmbient(math.Vector4 ambient) {
        this.ambient = ambient;
        return this;
    }

    public Light setConstantAttenuation(float constantAttenuation) {
        this.constantAttenuation = constantAttenuation;
        return this;
        }

    public Light setDiffuse(math.Vector4 diffuse) {
        this.diffuse = diffuse;
        return this;
        }

    public Light setLinearAttenuation(float linearAttenuation) {
        this.linearAttenuation = linearAttenuation;
        return this;
        }
    
    public Light setPosition(math.Vector4 position) {
        this.position = position;
        return this;
        }

    public Light setQuadraticAttenuation(float quadraticAttenuation) {
        this.quadraticAttenuation = quadraticAttenuation;
        return this;
        }

    public Light setSpecular(math.Vector4 specular) {
        this.specular = specular;
        return this;
        }


}
