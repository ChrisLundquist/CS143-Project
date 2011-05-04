package math;

import java.io.Serializable;

public class Quaternion implements Serializable {
    private static final long serialVersionUID = 5284423308557214091L;
    public static final Quaternion IDENTITY = new Quaternion();

    public Quaternion() {
        w_ = 1.0f; // real
        x_ = 0.0f; // i
        y_ = 0.0f; // j
        z_ = 0.0f; // k
    }

    public Quaternion(float w, float x, float y, float z) {
        w_ = w;
        x_ = x;
        y_ = y;
        z_ = z;
    }

    /**
     * Create a quaternion that rotates around a vector specified
     * @param axis the vector about which to rotate
     * @param degress the angle to rotate through
     */
    public Quaternion(Vector3 axis, float degrees) {
        float radians = degrees / 180.0f * (float)Math.PI;

        float angle = (float)Math.sin(radians / 2.0f);
        w_ = (float)Math.cos(radians / 2.0f);

        x_ = axis.x * angle;
        y_ = axis.y * angle;
        z_ = axis.z * angle;
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

    public boolean equals(Quaternion rhs) {
        return (w_ == rhs.w_ && x_ == rhs.x_ && y_ == rhs.y_ && z_ == rhs.z_);
    }

    public String toString() {
        return String.format("%g + %gi + %gj + %gk", w_, x_, y_, z_);
    }

    public String toMatrixString() {
        float[] m = toGlMatrix();
        return String.format(
                "| %06.3f %06.3f %06.3f %06.3f |\n" +
                "| %06.3f %06.3f %06.3f %06.3f |\n" +
                "| %06.3f %06.3f %06.3f %06.3f |\n" +
                "| %06.3f %06.3f %06.3f %06.3f |",
                m[0], m[4], m[8], m[12],
                m[1], m[5], m[9], m[13],
                m[2], m[6], m[10], m[14],
                m[3], m[7], m[11], m[15]);
    }

    public Quaternion times(Quaternion q) {
        return new Quaternion(
                w_ * q.w_ - x_ * q.x_ - y_ * q.y_ - z_ * q.z_,
                w_ * q.x_ + x_ * q.w_ + y_ * q.z_ - z_ * q.y_,
                w_ * q.y_ - x_ * q.z_ + y_ * q.w_ + z_ * q.x_,
                w_ * q.z_ + x_ * q.y_ - y_ * q.x_ + z_ * q.w_
        );
    }


    /**
     *  Optimization to avoid creating new quaternion objects when rotating
     * @param q the right hand side
     * @return
     */
    public Quaternion timesEquals(Quaternion q) {
        float tw = w_; // Temporary variables to hold components changed during rotation
        float tx = x_;
        float ty = y_;

        w_ = w_ * q.w_ - x_ * q.x_ - y_ * q.y_ - z_ * q.z_;
        x_ = tw * q.x_ + x_ * q.w_ + y_ * q.z_ - z_ * q.y_;
        y_ = tw * q.y_ - tx * q.z_ + y_ * q.w_ + z_ * q.x_;
        z_ = tw * q.z_ + tx * q.y_ - ty * q.x_ + z_ * q.w_;

        return this;
    }

    public float magnitude(){
        return (float) Math.sqrt( w_ * w_ + x_ * x_ + y_ * y_ + z_ * z_);
    }

    /**
     * Returns the square of the magnitude
     */ 
    public float magnitude2(){
        return  w_ * w_ + x_ * x_ + y_ * y_ + z_ * z_;
    }

    // Makes the magnitude one
    public Quaternion normalize() {
        float magnitude = magnitude();
        w_ /= magnitude;
        x_ /= magnitude;
        y_ /= magnitude;
        z_ /= magnitude;

        return this;
    }

    public Quaternion inverse(){
        float magnitude2 = magnitude2();
        return new Quaternion(w_ / magnitude2, -x_ / magnitude2, -y_ / magnitude2, -z_ / magnitude2 );
    }

    public Quaternion dampen(float strength){
        w_ = (1.0f - strength) * w_ + strength * IDENTITY.w_;
        x_ = (1.0f - strength) * x_ + strength * IDENTITY.x_;
        y_ = (1.0f - strength) * y_ + strength * IDENTITY.y_;
        z_ = (1.0f - strength) * z_ + strength * IDENTITY.z_;

        // CL - We have to normalize after our weighted average above
        normalize();

        return this;
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

    public Vector3 pitchAxis() {
        return new Vector3(
                -1.0f + 2.0f * ( y_ * y_ + z_ * z_ ),
                -2.0f * ( x_ * y_ - z_ * w_ ),
                -2.0f * ( x_ * z_ + y_ * w_ )
        );
    }

    public Vector3 yawAxis() {
        return new Vector3(
                -2.0f * (x_ * y_ + z_ * w_),
                -1.0f + 2.0f * ( x_ * x_ + z_ * z_ ),
                -2.0f * ( y_ * z_ - x_ * w_ )
        );
    }

    public Vector3 rollAxis() {
        return new Vector3 (
                -2.0f * (x_ * z_ - y_ * w_),
                -2.0f * (z_ * y_ + x_ * w_ ),
                -1.0f + 2.0f * ( x_ * x_ + y_ * y_ )
        );
    }

    float getPitch() {
        return (float) (Math.atan2(2*(y_*z_ + w_*x_), w_*w_ - x_*x_ - y_*y_ + z_*z_) * 180.0 / Math.PI);
    }

    float getYaw() {
        return (float) Math.asin(-2*(x_*z_ - w_*y_) * 180.0 / Math.PI); 
    }

    float getRoll() {
        return (float) ((float) Math.atan2(2*(x_*y_ + w_*z_), w_*w_ + x_*x_ - y_*y_ - z_*z_) * 180.0 / Math.PI);
    }

    float w_; // real
    float x_; // i
    float y_; // j
    float z_; // k
}
