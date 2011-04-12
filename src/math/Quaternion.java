package math;

import java.io.Serializable;

public class Quaternion implements Serializable {
    private static final long serialVersionUID = 5284423308557214091L;

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
        Quaternion r =  new Quaternion();

        r.w_ = w_ * q.w_ - x_ * q.x_ - y_ * q.y_ - z_ * q.z_;
        r.x_ = w_ * q.x_ + x_ * q.w_ + y_ * q.z_ - z_ * q.y_;
        r.y_ = w_ * q.y_ - x_ * q.z_ + y_ * q.w_ + z_ * q.x_;
        r.z_ = w_ * q.z_ + x_ * q.y_ - y_ * q.x_ + z_ * q.w_;

        return r;
    }


    public float magnitude(){
        return (float) Math.sqrt( w_ * w_ + x_ * x_ + y_ * y_ + z_ * z_);
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

    public Quaternion dampen(float strength){
        Quaternion q = new Quaternion(); // Identity Quaternion
        w_ = (1.0f - strength) * w_ + strength * q.w_;
        x_ = (1.0f - strength) * x_ + strength * q.x_;
        y_ = (1.0f - strength) * y_ + strength * q.y_;
        z_ = (1.0f - strength) * z_ + strength * q.z_;

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

    public Vector3 rollAxis() {
        return Vector3.UNIT_Z.times(this);
    }

    public Vector3 pitchAxis() {
        return Vector3.UNIT_X.times(this);
    }

    public Vector3 yawAxis() {
        return Vector3.UNIT_Y.times(this);
    }

    public static void main(String[] args) {
        Quaternion negativeone = new Quaternion(-1, 0, 0, 0);
        Quaternion i = new Quaternion(0, 1, 0, 0);
        Quaternion j = new Quaternion(0, 0, 1, 0);
        Quaternion k = new Quaternion(0, 0, 0, 1);

        // i^2 = j^2 = k^2 = ijk = -1
        assert(negativeone.equals(negativeone));
        assert(i.times(i).equals(negativeone));
        assert(j.times(j).equals(negativeone));
        assert(k.times(k).equals(negativeone));
        assert(i.times(j).times(k).equals(negativeone));

        // This transformation matrix should rotate 90 degrees about the x axis
        assert(new Quaternion(Vector3.UNIT_X, 90).toMatrixString().equals(
                "| 01.000 00.000 00.000 00.000 |\n" +
                "| 00.000 00.000 01.000 00.000 |\n" +
                "| 00.000 -1.000 00.000 00.000 |\n" +
        "| 00.000 00.000 00.000 01.000 |"));

        // Test for gimble lock
        Quaternion rotation = new Quaternion();
        rotation = rotation.times(new Quaternion(Vector3.UNIT_X, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Z, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Y, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Z, -90));
        // There should be a better way to compare within epsilon
        assert(rotation.toString().equals(new Quaternion().toString()));

        // Test that rotating through 360 degrees on all axies brings us back to normal
        rotation = rotation.times(new Quaternion(Vector3.UNIT_X, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_X, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_X, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_X, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Y, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Y, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Y, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Y, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Z, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Z, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Z, 90));
        rotation = rotation.times(new Quaternion(Vector3.UNIT_Z, 90));
        // Here we get a negative real component by the transformation matrix is the same
        assert(rotation.toMatrixString().equals(new Quaternion().toMatrixString()));


        rotation = new Quaternion();
        assert rotation.magnitude() == 1.0f;
        assert rotation.normalize().equals(new Quaternion());

        for( int it = 0; it < (4 * 4096); it++){
            // Rotate a lot to denormalize our vector
            rotation = rotation.times(new Quaternion(Vector3.UNIT_X, it));
            rotation = rotation.times(new Quaternion(Vector3.UNIT_Y, it));
            rotation = rotation.times(new Quaternion(Vector3.UNIT_Z, it));
            rotation = rotation.times(new Quaternion(Vector3.UNIT_X, it));
        }
        rotation.normalize();

        //Quaternion r = new Quaternion(Vector3.UNIT_Z, 30);
        //System.out.println(r.pitchAxis());
    }

    private float w_; // real
    private float x_; // i
    private float y_; // j
    private float z_; // k
}
