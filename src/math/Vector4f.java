package math;

public class Vector4f extends Vector3f {
    private static final long serialVersionUID = 6357784296699011970L;
    float t;
    public Vector4f(float x, float y, float z, float t){
        super(x,y,z);
        this.t = t;
    }
    
    public Vector4f(float x, float y, float z){
        t = 1.0f;
    }
    
    public Vector4f(Vector3f xyz,float t){
        super(xyz);
        this.t = t;
    }
    public Vector4f() {
        super();
        t = 0.0f;
    }
    
    public Vector4f(Vector4f other) {
        x = other.x;
        y = other.y;
        z = other.z;
        t = other.t;
    }

    /**
     * This version ignores the 4th vector component
     */
    public Vector4f times(Quaternion q){
        super.times(q);
        return this;
    }
    
    public boolean equals(Vector4f other){
       return super.equals(other) && t == other.t;
    }

    public float[] toFloatArray(){
        float[] toRet = {x,y,z,t};
        return toRet;
    }
}
