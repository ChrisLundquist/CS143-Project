package math;

/* Based on the Nehe Tutorial by  Vic Hollis */
public class Quaternion {
    public Quaternion() {
        w_ = 1.0f; // real
        x_ = 0.0f; // i
        y_ = 0.0f; // j
        z_ = 0.0f; // k
    }

    /*
     * What does degrees represent? is it yaw?
     */
    public Quaternion(float x, float y, float z, float degrees) {
        float radians = degrees / 180.0f * (float)Math.PI;
        // BOO java doubles
        float angle = (float)Math.sin(radians / 2.0f);
        w_ = (float)Math.cos(angle / 2.0f);

        x_ = x * angle;
        y_ = y * angle;
        z_ = z * angle;
    }

    /*
     * Copy constructor
     */
    public Quaternion(Quaternion original) {
        w_ = original.w_;
        x_ = original.x_;
        y_ = original.y_;
        z_ = original.z_;
    }

    public Quaternion times(Quaternion q) {
        Quaternion r =  new Quaternion();

        r.w_ = w_ * q.w_ - x_ * q.x_ - y_ * q.y_ - z_ * q.z_;
        r.x_ = w_ * q.x_ + x_ * q.w_ + y_ * q.z_ - z_ * q.y_;
        r.y_ = w_ * q.y_ - x_ * q.z_ + y_ * q.w_ + z_ * q.x_;
        r.z_ = w_ * q.z_ + x_ * q.y_ - y_ * q.x_ + z_ * q.w_;

        return r;
    }

    /*
     * Returns a rotation matrix 
     * glLoadMatrix expects the matrix in column major form:
     *  | m[0]  m[4]  m[8]  m[12] |
     *  | m[1]  m[5]  m[9]  m[13] |
     *  | m[2]  m[6]  m[10] m[14] |
     *  | m[3]  m[7]  m[11] m[15] |
     */
    public float[] toGlMatrix(){
        float[] matrix = new float[16];

        matrix[0]  = 1.0f - 2.0f * ( y_ * y_ + z_ * z_ );
        matrix[1]  = 2.0f * ( x_ * y_ - z_ * w_ );
        matrix[2]  = 2.0f * ( x_ * z_ + y_ * w_ );
        matrix[3]  = 0.0f;

        matrix[4]  = 2.0f * (x_ * y_ + z_ * w_);
        matrix[5]  = 1.0f - 2.0f * ( x_ * x_ + z_ * z_ );
        matrix[6]  = 2.0f * ( y_ * z_ - x_ * w_ );
        matrix[7]  = 0.0f;

        matrix[8]  = 2.0f * (x_ * z_ - y_ * w_);
        matrix[9]  = 2.0f * (z_ * y_ + x_ * w_ );
        matrix[10] = 1.0f - 2.0f * ( x_ * x_ + y_ * y_ );
        matrix[11] = 0.0f;

        matrix[12] = 0.0f;
        matrix[13] = 0.0f;
        matrix[14] = 0.0f;
        matrix[15] = 1.0f;

        return matrix;
    }

    public static void main(String[] args) {
        Quaternion q = new Quaternion();
    }

    private float w_; // real
    private float x_; // i
    private float y_; // j
    private float z_; // k
}
