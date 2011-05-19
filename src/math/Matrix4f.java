package math;
/**
 * 
 * @author Durandal
 * Description
 *  | m[0]  m[4]  m[8]  m[12] |
 *  | m[1]  m[5]  m[9]  m[13] |
 *  | m[2]  m[6]  m[10] m[14] |
 *  | m[3]  m[7]  m[11] m[15] |
 *  A column major 4x4 float matrix
 *  */
public class Matrix4f {
    public static final Matrix4f IDENTITY = new Matrix4f();
    float[] entries;

    public static Matrix4f newFromScale(Vector3 scale){
        float[] entries = {scale.x,0,0,0,
                0,scale.y,0,0,
                0,0,scale.z,0,
                0,0,0,1
        };
        return new Matrix4f(entries);
    }

    public static Matrix4f newFromQuaternion(Quaternion q){
        float[] entries = {
                1.0f - 2.0f * ( q.y_ * q.y_ + q.z_ * q.z_ ),
                2.0f * ( q.x_ * q.y_ - q.z_ * q.w_ ),
                2.0f * ( q.x_ * q.z_ + q.y_ * q.w_ ),
                0.0f,

                2.0f * (q.x_ * q.y_ + q.z_ * q.w_),
                1.0f - 2.0f * ( q.x_ * q.x_ + q.z_ * q.z_ ),
                2.0f * ( q.y_ * q.z_ - q.x_ * q.w_ ),
                0.0f,

                2.0f * (q.x_ * q.z_ - q.y_ * q.w_),
                2.0f * (q.z_ * q.y_ + q.x_ * q.w_ ),
                1.0f - 2.0f * ( q.x_ * q.x_ + q.y_ * q.y_ ),
                0.0f,

                0.0f,
                0.0f,
                0.0f,
                1.0f
        };
        return new Matrix4f(entries);
    }

    public Matrix4f(){
        entries = new float[16];
        // Initialize to identity
        for(int i = 0; i < this.entries.length; ++i)
            entries[i] = i % 5 == 0 ? 1.0f : 0.0f;
    }

    public Matrix4f(float[] entries){
        this.entries = new float[16];
        for(int i = 0; i < this.entries.length; ++i)
            this.entries[i] = entries[i];
    }

    public Matrix4f times(Matrix4f other){
        float[] result = new float[16];
        result[0] = entries[0] * other.entries[0] + entries[1] * other.entries[4] + entries[2] * other.entries[8] + entries[3] * other.entries[12];
        result[1] = entries[0] * other.entries[1] + entries[1] * other.entries[5] + entries[2] * other.entries[9] + entries[3] * other.entries[13];
        result[2] = entries[0] * other.entries[2] + entries[1] * other.entries[6] + entries[2] * other.entries[10] + entries[3] * other.entries[14];
        result[3] = entries[0] * other.entries[3] + entries[1] * other.entries[7] + entries[2] * other.entries[11] + entries[3] * other.entries[15];

        result[4] = entries[4] * other.entries[0] + entries[5] * other.entries[4] + entries[6] * other.entries[8] + entries[7] * other.entries[12];
        result[5] = entries[4] * other.entries[1] + entries[5] * other.entries[5] + entries[6] * other.entries[9] + entries[7] * other.entries[13];
        result[6] = entries[4] * other.entries[2] + entries[5] * other.entries[6] + entries[6] * other.entries[10] + entries[7] * other.entries[14];
        result[7] = entries[4] * other.entries[3] + entries[5] * other.entries[7] + entries[6] * other.entries[11] + entries[7] * other.entries[15];

        result[8] = entries[8] * other.entries[0] + entries[9] * other.entries[4] + entries[10] * other.entries[8] + entries[11] * other.entries[12];
        result[9] = entries[8] * other.entries[1] + entries[9] * other.entries[5] + entries[10] * other.entries[9] + entries[11] * other.entries[13];
        result[10] = entries[8] * other.entries[2] + entries[9] * other.entries[6] + entries[10] * other.entries[10] + entries[11] * other.entries[14];
        result[11] = entries[8] * other.entries[3] + entries[9] * other.entries[7] + entries[10] * other.entries[11] + entries[11] * other.entries[15];

        result[12] = entries[12] * other.entries[0] + entries[13] * other.entries[4] + entries[14] * other.entries[8] + entries[15] * other.entries[12];
        result[13] = entries[12] * other.entries[1] + entries[13] * other.entries[5] + entries[14] * other.entries[9] + entries[15] * other.entries[13];
        result[14] = entries[12] * other.entries[2] + entries[13] * other.entries[6] + entries[14] * other.entries[10] + entries[15] * other.entries[14];
        result[15] = entries[12] * other.entries[3] + entries[13] * other.entries[7] + entries[14] * other.entries[11] + entries[15] * other.entries[15];

        return new Matrix4f(result);
    }
    
    public Matrix4f timesEquals(Matrix4f other){
        float[] result = new float[16];
        result[0] = entries[0] * other.entries[0] + entries[1] * other.entries[4] + entries[2] * other.entries[8] + entries[3] * other.entries[12];
        result[1] = entries[0] * other.entries[1] + entries[1] * other.entries[5] + entries[2] * other.entries[9] + entries[3] * other.entries[13];
        result[2] = entries[0] * other.entries[2] + entries[1] * other.entries[6] + entries[2] * other.entries[10] + entries[3] * other.entries[14];
        result[3] = entries[0] * other.entries[3] + entries[1] * other.entries[7] + entries[2] * other.entries[11] + entries[3] * other.entries[15];

        result[4] = entries[4] * other.entries[0] + entries[5] * other.entries[4] + entries[6] * other.entries[8] + entries[7] * other.entries[12];
        result[5] = entries[4] * other.entries[1] + entries[5] * other.entries[5] + entries[6] * other.entries[9] + entries[7] * other.entries[13];
        result[6] = entries[4] * other.entries[2] + entries[5] * other.entries[6] + entries[6] * other.entries[10] + entries[7] * other.entries[14];
        result[7] = entries[4] * other.entries[3] + entries[5] * other.entries[7] + entries[6] * other.entries[11] + entries[7] * other.entries[15];

        result[8] = entries[8] * other.entries[0] + entries[9] * other.entries[4] + entries[10] * other.entries[8] + entries[11] * other.entries[12];
        result[9] = entries[8] * other.entries[1] + entries[9] * other.entries[5] + entries[10] * other.entries[9] + entries[11] * other.entries[13];
        result[10] = entries[8] * other.entries[2] + entries[9] * other.entries[6] + entries[10] * other.entries[10] + entries[11] * other.entries[14];
        result[11] = entries[8] * other.entries[3] + entries[9] * other.entries[7] + entries[10] * other.entries[11] + entries[11] * other.entries[15];

        result[12] = entries[12] * other.entries[0] + entries[13] * other.entries[4] + entries[14] * other.entries[8] + entries[15] * other.entries[12];
        result[13] = entries[12] * other.entries[1] + entries[13] * other.entries[5] + entries[14] * other.entries[9] + entries[15] * other.entries[13];
        result[14] = entries[12] * other.entries[2] + entries[13] * other.entries[6] + entries[14] * other.entries[10] + entries[15] * other.entries[14];
        result[15] = entries[12] * other.entries[3] + entries[13] * other.entries[7] + entries[14] * other.entries[11] + entries[15] * other.entries[15];
        entries = result;
        return this;
    }

    public Vector3 times(Vector3 vec){
        return new Vector3(
                entries[0] * vec.x + entries[4] * vec.y + entries[8] * vec.z,
                entries[1] * vec.x + entries[5] * vec.y + entries[9] * vec.z,
                entries[2] * vec.x + entries[6] * vec.y + entries[10] * vec.z
        );
    }
    
    public Vector4 times(Vector4 vec){
        return new Vector4(
                entries[0] * vec.x + entries[4] * vec.y + entries[8] * vec.z + entries[12] * vec.t,
                entries[1] * vec.x + entries[5] * vec.y + entries[9] * vec.z + entries[13] * vec.t,
                entries[2] * vec.x + entries[6] * vec.y + entries[10] * vec.z + entries[14] * vec.t,
                entries[3] * vec.x + entries[7] * vec.y + entries[11] * vec.z + entries[15] * vec.t
        );
    }

    public Matrix4f times(float scalar){
        float[] result = new float[entries.length];
        for(int i = 0; i < result.length; i++)
            result[i] =  entries[i] * scalar;

        return new Matrix4f(result);
    }

    public Matrix4f transpose(){
        float[] result = new float[entries.length];
        //result[0] = entries[0];
        result[1] = entries[4];
        result[2] = entries[8];
        result[3] = entries[12];
        result[4] = entries[1];
        //result[5] = entries[5];
        result[6] = entries[9];
        result[7] = entries[13];
        result[8] = entries[2];
        result[9] = entries[6];
        //result[10] = entries[10];
        result[11] = entries[14];
        result[12] = entries[3];
        result[13] = entries[7];
        result[14] = entries[11];
        //result[15] = entries[15];
        return new Matrix4f(result);
    }

    public boolean equals(Matrix4f other){
        return other != null &&
        entries[0] == other.entries[0] &&
        entries[1] == other.entries[1] &&
        entries[2] == other.entries[2] &&
        entries[3] == other.entries[3] &&
        entries[4] == other.entries[4] &&
        entries[5] == other.entries[5] &&
        entries[6] == other.entries[6] &&
        entries[7] == other.entries[7] &&
        entries[8] == other.entries[8] &&
        entries[9] == other.entries[9] &&
        entries[10] == other.entries[10] &&
        entries[11] == other.entries[11] &&
        entries[12] == other.entries[12] &&
        entries[13] == other.entries[13] &&
        entries[14] == other.entries[14] &&
        entries[15] == other.entries[15];
    }


    public boolean equals(Object other){
        if(other instanceof Matrix4f)
            return equals((Matrix4f)other);
        else
            return false;
    }

    public String toString(){
        return new String(
                "| " + entries[0] + "," + entries[4] + "," + entries[8] + "," + entries[12]+ " |\n" + 
                "| " + entries[1] + "," + entries[5] + "," + entries[9] + "," + entries[13]+ " |\n" +
                "| " + entries[2] + "," + entries[6] + "," + entries[10] + "," + entries[14]+ " |" +
                "| " + entries[3] + "," + entries[7] + "," + entries[11] + "," + entries[15]+ " |\n");
    }

    public float[] toFloatArray(){
        return entries;
    }
}