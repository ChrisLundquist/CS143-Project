package math;

/**
 * 
 * @author Durandal
 *
 *  Description: 
 *  | m[0]  m[3]  m[6] |
 *  | m[1]  m[4]  m[7] |
 *  | m[2]  m[5]  m[8] |
 *  A column major 3x3 float matrix
 *  */
public class Matrix3f {
    public static final Matrix3f IDENTITY = new Matrix3f();
    float[] entries;

    public static Matrix3f newFromScale(Vector3 scale){
        return new Matrix3f(scale.x,0,0,
                0,scale.y,0,
                0,0,scale.z);
    }

    public static Matrix3f newFromQuaternion(Quaternion q){
        float[] entries = {
                1.0f - 2.0f * ( q.y_ * q.y_ + q.z_ * q.z_ ),
                2.0f * ( q.x_ * q.y_ - q.z_ * q.w_ ),
                2.0f * ( q.x_ * q.z_ + q.y_ * q.w_ ),

                2.0f * (q.x_ * q.y_ + q.z_ * q.w_),
                1.0f - 2.0f * ( q.x_ * q.x_ + q.z_ * q.z_ ),
                2.0f * ( q.y_ * q.z_ - q.x_ * q.w_ ),

                2.0f * (q.x_ * q.z_ - q.y_ * q.w_),
                2.0f * (q.z_ * q.y_ + q.x_ * q.w_ ),
                1.0f - 2.0f * ( q.x_ * q.x_ + q.y_ * q.y_ ),
        };
        return new Matrix3f(entries);
    }

    public Matrix3f(){
        entries = new float[9];
        // Initialize to identity
        for(int i = 0; i < this.entries.length; ++i)
            entries[i] = i % 4 == 0 ? 1.0f : 0.0f;
    }

    public Matrix3f(float m0, float m1, float m2, float m3, float m4, float m5,
            float m6, float m7, float m8) {
        entries = new float[9];
        entries[0] = m0;
        entries[1] = m1;
        entries[2] = m2;
        entries[3] = m3;
        entries[4] = m4;
        entries[5] = m5;
        entries[6] = m6;
        entries[7] = m7;
        entries[8] = m8;
    }

    public Matrix3f(float[] entries){
        this.entries = new float[9];
        for(int i = 0; i < this.entries.length; ++i)
            this.entries[i] = entries[i];
    }

    public Matrix3f times(Matrix3f other){
        float m0 = entries[0] * other.entries[0] + entries[1] * other.entries[3] + entries[2] * other.entries[6];
        float m1 = entries[0] * other.entries[1] + entries[1] * other.entries[4] + entries[2] * other.entries[7];
        float m2 = entries[0] * other.entries[2] + entries[1] * other.entries[5] + entries[2] * other.entries[8];

        float m3 = entries[3] * other.entries[0] + entries[4] * other.entries[3] + entries[5] * other.entries[6];
        float m4 = entries[3] * other.entries[1] + entries[4] * other.entries[4] + entries[5] * other.entries[7];
        float m5 = entries[3] * other.entries[2] + entries[4] * other.entries[5] + entries[5] * other.entries[8];

        float m6 = entries[6] * other.entries[0] + entries[7] * other.entries[3] + entries[8] * other.entries[6];
        float m7 = entries[6] * other.entries[1] + entries[7] * other.entries[4] + entries[8] * other.entries[7];
        float m8 = entries[6] * other.entries[2] + entries[7] * other.entries[5] + entries[8] * other.entries[8];

        return new Matrix3f(m0, m1, m2, m3, m4, m5, m6, m7, m8);
    }

    public Vector3 times(Vector3 vec){
        return new Vector3(entries[0] * vec.x + entries[3] * vec.y + entries[6] * vec.z,
                entries[1] * vec.x + entries[4] * vec.y + entries[7] * vec.z,
                entries[2] * vec.x + entries[5] * vec.y + entries[8] * vec.z
        );
    }

    public Matrix3f times(float scalar){
        return new Matrix3f(entries[0] * scalar, entries[1] * scalar, entries[2] * scalar,
                entries[3] * scalar, entries[4] * scalar, entries[5] * scalar,
                entries[6] * scalar, entries[7] * scalar, entries[8] * scalar);
    }

    public Matrix3f transpose(){
        return new Matrix3f(entries[0],entries[3], entries[6],
                entries[1], entries[4],entries[7],
                entries[2], entries[5], entries[8]);
    }

    public boolean equals(Matrix3f other){
        return other != null &&
        entries[0] == other.entries[0] &&
        entries[1] == other.entries[1] &&
        entries[2] == other.entries[2] &&
        entries[3] == other.entries[3] &&
        entries[4] == other.entries[4] &&
        entries[5] == other.entries[5] &&
        entries[6] == other.entries[6] &&
        entries[7] == other.entries[7] &&
        entries[8] == other.entries[8];
    }

    public boolean equals(Object other){
        if(other instanceof Matrix3f)
            return equals((Matrix3f)other);
        else
            return false;
    }

    public String toString(){
        return new String("| " + entries[0] + "," + entries[3] + "," + entries[6] + " |\n" + 
                "| " + entries[1] + "," + entries[4] + "," + entries[7] + " |\n" +
                "| " + entries[2] + "," + entries[5] + "," + entries[8] + " |" );
    }

    public float[] toFloatArray(){
        return entries;
    }
}
