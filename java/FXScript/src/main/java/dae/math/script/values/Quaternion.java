package dae.math.script.values;

import dae.math.script.specops.I3DValue;
import dae.math.script.specops.IDouble1Value;
import dae.math.script.specops.IQuaternionValue;

/**
 *
 * @author Koen Samyn (samyn.koen@gmail.com)
 */
public class Quaternion implements IQuaternionValue {

    private static Quaternion tmp1 = new Quaternion(0, 0, 0, 0);
    private static Quaternion tmp2 = new Quaternion(0, 0, 0, 0);

    public double x;
    public double y;
    public double z;
    public double w;

    /**
     * Constructor, sets the four components of the quaternion.
     *
     * @param x The x-component
     * @param y The y-component
     * @param z The z-component
     * @param w The w-component
     */
    public Quaternion(double x, double y, double z, double w) {
        this.set(x, y, z, w);
    }

    public Quaternion() {
        idt();
    }

    /**
     * Constructor, sets the quaternion components from the given quaternion.
     *
     * @param quaternion The quaternion to copy.
     */
    public Quaternion(Quaternion quaternion) {
        this.set(quaternion);
    }

    /**
     * Constructor, sets the quaternion from the given axis vector and the angle
     * around that axis in degrees.
     *
     * @param axis The axis
     * @param angle The angle in degrees.
     */
    public Quaternion(Double3 axis, double angle) {
        this.set(axis, angle);
    }

    /**
     * Sets the components of the quaternion
     *
     * @param x The x-component
     * @param y The y-component
     * @param z The z-component
     * @param w The w-component
     * @return This quaternion for chaining
     */
    public final void set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Sets the quaternion components from the given quaternion.
     *
     * @param quaternion The quaternion.
     * @return This quaternion for chaining.
     */
    public final void set(Quaternion quaternion) {
        this.set(quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    /**
     * Sets the quaternion components from the given axis and angle around that
     * axis.
     *
     * @param axis The axis
     * @param angle The angle in degrees
     * @return This quaternion for chaining.
     */
    public final Quaternion set(Double3 axis, double angle) {
        return setFromAxis(axis.x, axis.y, axis.z, angle);
    }

    /**
     * @return a copy of this quaternion
     */
    public Quaternion cpy() {
        return new Quaternion(this);
    }

    /**
     * @return the euclidean length of the specified quaternion
     */
    public final static double len(final double x, final double y, final double z, final double w) {
        return Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * @return the euclidean length of this quaternion
     */
    public double len() {
        return Math.sqrt(x * x + y * y + z * z + w * w);
    }

    @Override
    public String toString() {
        return String.format("\\begin{pmatrix} %.3f \\\\ %.3f \\\\ %.3f \\\\ %.3f \\end{pmatrix}", x, y, z, w);
        //return "[" + x + "|" + y + "|" + z + "|" + w + "]";
    }

    /**
     * Get the pole of the gimbal lock, if any.
     *
     * @return positive (+1) for north pole, negative (-1) for south pole, zero
     * (0) when no gimbal lock
     */
    public int getGimbalPole() {
        final double t = y * x + z * w;
        return t > 0.499f ? 1 : (t < -0.499f ? -1 : 0);
    }

    public final static double len2(final double x, final double y, final double z, final double w) {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * @return the length of this quaternion without square root
     */
    public double len2() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * Normalizes this quaternion to unit length
     *
     * @return the quaternion for chaining
     */
    public Quaternion nor() {
        double len = len2();
        if (len != 0.f) {
            len = (float) Math.sqrt(len);
            w /= len;
            x /= len;
            y /= len;
            z /= len;
        }
        return this;
    }

    /**
     * Conjugate the quaternion.
     *
     * @return This quaternion for chaining
     */
    public Quaternion conjugate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    // TODO : this would better fit into the vector3 class
    /**
     * Transforms the given vector using this quaternion
     *
     * @param v Vector to transform
     */
    public Double3 transform(Double3 v) {
        tmp2.set(this);
        tmp2.conjugate();
        tmp1.set(v.x, v.y, v.z, 0);
        tmp2.mulLeft(tmp1).mulLeft(this);

        v.x = tmp2.x;
        v.y = tmp2.y;
        v.z = tmp2.z;
        return v;
    }

    /**
     * Transforms the given vector using this quaternion
     *
     * @param v Vector to transform
     * @param result the result to store
     */
    public void transform(Double3 v, Double3 result) {
        tmp2.set(this);
        tmp2.conjugate();
        tmp1.set(v.x, v.y, v.z, 0);
        tmp2.mulLeft(tmp1).mulLeft(this);

        result.x = tmp2.x;
        result.y = tmp2.y;
        result.z = tmp2.z;
    }

    /**
     * Transforms the given vector using this quaternion
     *
     * @param x the x component of the point to transform
     * @param y the y component of the point to transform
     * @param z the z component of the point to transform
     * @param result the result to store
     */
    @Override
    public void transform(double x, double y, double z, I3DValue result) {
        tmp2.set(this);
        tmp2.conjugate();
        tmp1.set(x, y, z, 0);
        tmp2.mulLeft(tmp1).mulLeft(this);

        result.set(x, y, z, 0);
    }

    /**
     * Multiplies this quaternion with another one in the form of this = this *
     * other
     *
     * @param other Quaternion to multiply with
     * @return This quaternion for chaining
     */
    public Quaternion mul(final Quaternion other) {
        final double newX = this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y;
        final double newY = this.w * other.y + this.y * other.w + this.z * other.x - this.x * other.z;
        final double newZ = this.w * other.z + this.z * other.w + this.x * other.y - this.y * other.x;
        final double newW = this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
        return this;
    }

    /**
     * Multiplies this quaternion with another one in the form of this = this *
     * other
     *
     * @param other Quaternion to multiply with
     * @return This quaternion for chaining
     */
    public void mul(final IQuaternionValue other, final IQuaternionValue result) {
        final double newX = this.w * other.x() + this.x * other.w() + this.y * other.z() - this.z * other.y();
        final double newY = this.w * other.y() + this.y * other.w() + this.z * other.x() - this.x * other.z();
        final double newZ = this.w * other.z() + this.z * other.w() + this.x * other.y() - this.y * other.x();
        final double newW = this.w * other.w() - this.x * other.x() - this.y * other.y() - this.z * other.z();
        result.set(newX, newY, newZ, newW);
    }

    /**
     * Multiplies this quaternion with another one in the form of this = this *
     * other
     *
     * @param x the x component of the other quaternion to multiply with
     * @param y the y component of the other quaternion to multiply with
     * @param z the z component of the other quaternion to multiply with
     * @param w the w component of the other quaternion to multiply with
     * @return This quaternion for chaining
     */
    public Quaternion mul(final double x, final double y, final double z, final double w) {
        final double newX = this.w * x + this.x * w + this.y * z - this.z * y;
        final double newY = this.w * y + this.y * w + this.z * x - this.x * z;
        final double newZ = this.w * z + this.z * w + this.x * y - this.y * x;
        final double newW = this.w * w - this.x * x - this.y * y - this.z * z;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
        return this;
    }

    /**
     * Multiplies this quaternion with another one in the form of this = other *
     * this
     *
     * @param other Quaternion to multiply with
     * @return This quaternion for chaining
     */
    public Quaternion mulLeft(Quaternion other) {
        final double newX = other.w * this.x + other.x * this.w + other.y * this.z - other.z * this.y;
        final double newY = other.w * this.y + other.y * this.w + other.z * this.x - other.x * this.z;
        final double newZ = other.w * this.z + other.z * this.w + other.x * this.y - other.y * this.x;
        final double newW = other.w * this.w - other.x * this.x - other.y * this.y - other.z * this.z;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
        return this;
    }

    /**
     * Multiplies this quaternion with another one in the form of this = other *
     * this
     *
     * @param x the x component of the other quaternion to multiply with
     * @param y the y component of the other quaternion to multiply with
     * @param z the z component of the other quaternion to multiply with
     * @param w the w component of the other quaternion to multiply with
     * @return This quaternion for chaining
     */
    public Quaternion mulLeft(final double x, final double y, final double z, final double w) {
        final double newX = w * this.x + x * this.w + y * this.z - z * this.y;
        final double newY = w * this.y + y * this.w + z * this.x - x * this.z;
        final double newZ = w * this.z + z * this.w + x * this.y - y * this.x;
        final double newW = w * this.w - x * this.x - y * this.y - z * this.z;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
        return this;
    }

    /**
     * Add the x,y,z,w components of the passed in quaternion to the ones of
     * this quaternion
     */
    public Quaternion add(Quaternion quaternion) {
        this.x += quaternion.x;
        this.y += quaternion.y;
        this.z += quaternion.z;
        this.w += quaternion.w;
        return this;
    }

    /**
     * Add the x,y,z,w components of the passed in quaternion to the ones of
     * this quaternion
     */
    public Quaternion add(double qx, double qy, double qz, double qw) {
        this.x += qx;
        this.y += qy;
        this.z += qz;
        this.w += qw;
        return this;
    }

    // TODO : the matrix4 set(quaternion) doesnt set the last row+col of the matrix to 0,0,0,1 so... that's why there is this
// method
    /**
     * Fills a 4x4 matrix with the rotation matrix represented by this
     * quaternion.
     *
     * @param matrix Matrix to fill
     */
    /*
    public void toMatrix(double float[] matrix) {
        final double xx = x * x;
        final double xy = x * y;
        final double xz = x * z;
        final double xw = x * w;
        final double yy = y * y;
        final double yz = y * z;
        final double yw = y * w;
        final double zz = z * z;
        final double zw = z * w;
        // Set matrix from quaternion
        matrix[Matrix4.M00] = 1 - 2 * (yy + zz);
        matrix[Matrix4.M01] = 2 * (xy - zw);
        matrix[Matrix4.M02] = 2 * (xz + yw);
        matrix[Matrix4.M03] = 0;
        matrix[Matrix4.M10] = 2 * (xy + zw);
        matrix[Matrix4.M11] = 1 - 2 * (xx + zz);
        matrix[Matrix4.M12] = 2 * (yz - xw);
        matrix[Matrix4.M13] = 0;
        matrix[Matrix4.M20] = 2 * (xz - yw);
        matrix[Matrix4.M21] = 2 * (yz + xw);
        matrix[Matrix4.M22] = 1 - 2 * (xx + yy);
        matrix[Matrix4.M23] = 0;
        matrix[Matrix4.M30] = 0;
        matrix[Matrix4.M31] = 0;
        matrix[Matrix4.M32] = 0;
        matrix[Matrix4.M33] = 1;
    }*/
    /**
     * Sets the quaternion to an identity Quaternion
     *
     * @return this quaternion for chaining
     */
    public final void idt() {
        this.set(0, 0, 0, 1);
    }

    // todo : the setFromAxis(v3,float) method should replace the set(v3,float) method
    /**
     * Sets the quaternion components from the given axis and angle around that
     * axis.
     *
     * @param axis The axis
     * @param degrees The angle in degrees
     * @return This quaternion for chaining.
     */
    public Quaternion setFromAxis(final Double3 axis, final double degrees) {
        return setFromAxis(axis.x, axis.y, axis.z, degrees);
    }

    /**
     * Sets the quaternion components from the given axis and angle around that
     * axis.
     *
     * @param axis The axis
     * @param radians The angle in radians
     * @return This quaternion for chaining.
     */
    public Quaternion setFromAxisRad(final Double3 axis, final double radians) {
        return setFromAxisRad(axis.x, axis.y, axis.z, radians);
    }

    /**
     * Sets the quaternion components from the given axis and angle around that
     * axis.
     *
     * @param x X direction of the axis
     * @param y Y direction of the axis
     * @param z Z direction of the axis
     * @param degrees The angle in degrees
     * @return This quaternion for chaining.
     */
    public Quaternion setFromAxis(final double x, final double y, final double z, final double degrees) {
        return setFromAxisRad(x, y, z, degrees * Math.PI / 180.0);
    }

    /**
     * Sets the quaternion components from the given axis and angle around that
     * axis.
     *
     * @param x X direction of the axis
     * @param y Y direction of the axis
     * @param z Z direction of the axis
     * @param radians The angle in radians
     * @return This quaternion for chaining.
     */
    public Quaternion setFromAxisRad(final double x, final double y, final double z, final double radians) {
        double d = Math.sqrt(x * x + y * y + z * z);
        if (d == 0f) {
            idt();
            return this;
        }
        d = 1.0 / d;
        double l_sin = Math.sin(radians / 2);
        double l_cos = Math.cos(radians / 2);
        set(d * x * l_sin, d * y * l_sin, d * z * l_sin, l_cos);
        return this.nor();
    }

    public Quaternion setFromDualAxisRadZX(final double alpha, final double beta) {
        double sa = Math.sin(alpha / 2);
        double ca = Math.cos(alpha / 2);

        double sb = Math.sin(beta / 2);
        double cb = Math.cos(beta / 2);

        this.set(ca * sb, sa * sb, sa * cb, ca * cb);
        return this;
    }

    public Quaternion setFromDualAxisRadXY(final double alpha, final double beta) {
        double sa = Math.sin(alpha / 2);
        double ca = Math.cos(alpha / 2);

        double sb = Math.sin(beta / 2);
        double cb = Math.cos(beta / 2);

        this.set(sa * cb, ca * sb, -sa * sb, ca * cb);
        return this;
    }

    public Quaternion setFromTripleAxisRadZXY(final double alpha, final double beta, final double gamma) {
        //System.out.println(alpha +","+beta+","+gamma);
        double sa = Math.sin(alpha / 2);
        double ca = Math.cos(alpha / 2);

        double sb = Math.sin(beta / 2);
        double cb = Math.cos(beta / 2);

        double sg = Math.sin(gamma / 2);
        double cg = Math.cos(gamma / 2);

        this.set(
                ca * sb * sg + sa * cb * cg,
                -sa * cb * sg + ca * sb * cg,
                ca * cb * sg - sa * sb * cg,
                sa * sb * sg + ca * cb * cg
        );
        return this;
    }

    public Quaternion setFromDualAxisRadZY(double alpha, double beta) {
        double sa = Math.sin(alpha / 2);
        double ca = Math.cos(alpha / 2);

        double sb = Math.sin(beta / 2);
        double cb = Math.cos(beta / 2);

        this.set(-sa * sb, ca * sb, sa * cb, ca * cb);
        return this;
    }

    /**
     * Spherical linear interpolation between this quaternion and the other
     * quaternion, based on the alpha value in the range [0,1]. Taken from Bones
     * framework for JPCT, see http://www.aptalkarga.com/bones/
     *
     * @param end the end quaternion
     * @param alpha alpha in the range [0,1]
     * @return this quaternion for chaining
     */
    public Quaternion slerp(Quaternion end, float alpha) {
        final double d = this.x * end.x + this.y * end.y + this.z * end.z + this.w * end.w;
        double absDot = d < 0.f ? -d : d;

        // Set the first and second scale for the interpolation
        double scale0 = 1f - alpha;
        double scale1 = alpha;

        // Check if the angle between the 2 quaternions was big enough to
        // warrant such calculations
        if ((1 - absDot) > 0.1) {// Get the angle between the 2 quaternions,
            // and then store the sin() of that angle
            final double angle = (double) Math.acos(absDot);
            final double invSinTheta = 1f / (double) Math.sin(angle);

            // Calculate the scale for q1 and q2, according to the angle and
            // it's sine value
            scale0 = ((float) Math.sin((1f - alpha) * angle) * invSinTheta);
            scale1 = ((float) Math.sin((alpha * angle)) * invSinTheta);
        }

        if (d < 0.f) {
            scale1 = -scale1;
        }

        // Calculate the x, y, z and w values for the quaternion by using a
        // special form of linear interpolation for quaternions.
        x = (scale0 * x) + (scale1 * end.x);
        y = (scale0 * y) + (scale1 * end.y);
        z = (scale0 * z) + (scale1 * end.z);
        w = (scale0 * w) + (scale1 * end.w);

        // Return the interpolated quaternion
        return this;
    }

    /**
     * Spherical linearly interpolates multiple quaternions and stores the
     * result in this Quaternion. Will not destroy the data previously inside
     * the elements of q. result = (q_1^w_1)*(q_2^w_2)* ... *(q_n^w_n) where
     * w_i=1/n.
     *
     * @param q List of quaternions
     * @return This quaternion for chaining
     */
    public Quaternion slerp(Quaternion[] q) {

        // Calculate exponents and multiply everything from left to right
        final float w = 1.0f / q.length;
        set(q[0]);
        exp(w);
        for (int i = 1; i < q.length; i++) {
            tmp1.set(q[i]);
            mul(tmp1).exp(w);
        }
        nor();
        return this;
    }

    /**
     * Spherical linearly interpolates multiple quaternions by the given weights
     * and stores the result in this Quaternion. Will not destroy the data
     * previously inside the elements of q or w. result = (q_1^w_1)*(q_2^w_2)*
     * ... *(q_n^w_n) where the sum of w_i is 1. Lists must be equal in length.
     *
     * @param q List of quaternions
     * @param w List of weights
     * @return This quaternion for chaining
     */
    public Quaternion slerp(Quaternion[] q, float[] w) {

        // Calculate exponents and multiply everything from left to right
        set(q[0]);
        exp(w[0]);
        for (int i = 1; i < q.length; i++) {
            tmp1.set(q[i]);
            mul(tmp1.exp(w[i]));
        }
        nor();
        return this;
    }

    /**
     * Calculates (this quaternion)^alpha where alpha is a real number and
     * stores the result in this quaternion. See
     * http://en.wikipedia.org/wiki/Quaternion#Exponential.2C_logarithm.2C_and_power
     *
     * @param alpha Exponent
     * @return This quaternion for chaining
     */
    public Quaternion exp(float alpha) {

        // Calculate |q|^alpha
        double norm = len();
        double normExp = Math.pow(norm, alpha);

        // Calculate theta
        double theta = Math.acos(w / norm);

        // Calculate coefficient of basis elements
        double coeff = 0;
        if (Math.abs(theta) < 0.001) // If theta is small enough, use the limit of sin(alpha*theta) / sin(theta) instead of actual
        // value
        {
            coeff = normExp * alpha / norm;
        } else {
            coeff = (float) (normExp * Math.sin(alpha * theta) / (norm * Math.sin(theta)));
        }

        // Write results
        w = (float) (normExp * Math.cos(alpha * theta));
        x *= coeff;
        y *= coeff;
        z *= coeff;

        // Fix any possible discrepancies
        nor();

        return this;
    }

    /**
     * Get the dot product between the two quaternions (commutative).
     *
     * @param x1 the x component of the first quaternion
     * @param y1 the y component of the first quaternion
     * @param z1 the z component of the first quaternion
     * @param w1 the w component of the first quaternion
     * @param x2 the x component of the second quaternion
     * @param y2 the y component of the second quaternion
     * @param z2 the z component of the second quaternion
     * @param w2 the w component of the second quaternion
     * @return the dot product between the first and second quaternion.
     */
    public final static double dot(final double x1, final double y1, final double z1, final double w1, final double x2, final double y2,
            final double z2, final double w2) {
        return x1 * x2 + y1 * y2 + z1 * z2 + w1 * w2;
    }

    /**
     * Get the dot product between this and the other quaternion (commutative).
     *
     * @param other the other quaternion.
     * @return the dot product of this and the other quaternion.
     */
    public double dot(final Quaternion other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    /**
     * Get the dot product between this and the other quaternion (commutative).
     *
     * @param x the x component of the other quaternion
     * @param y the y component of the other quaternion
     * @param z the z component of the other quaternion
     * @param w the w component of the other quaternion
     * @return the dot product of this and the other quaternion.
     */
    public double dot(final float x, final float y, final float z, final float w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    /**
     * Multiplies the components of this quaternion with the given scalar.
     *
     * @param scalar the scalar.
     * @return this quaternion for chaining.
     */
    public Quaternion mul(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
        return this;
    }

    public void getAngleAxis(Double1 ar, Double3 axis) {
        if (w > 1) {
            nor();
        }
        ar.set(2 * Math.acos(w));
        double s = Math.sqrt(1 - w * w); // assuming quaternion normalised then w is less than 1, so term always positive.
        if (s < 0.001) { // test to avoid divide by zero, s is always positive due to sqrt
            axis.set(x, y, z, 0);
        } else {
            axis.set(x / s, y / s, z / s, 0);
        }
    }

    @Override
    public IQuaternionValue getValue() {
        return this;
    }

    @Override
    public I3DValue getAxis() {
        Double3 axis = new Double3();
        double s = Math.sqrt(1 - w * w); // assuming quaternion normalised then w is less than 1, so term always positive.
        if (s < 0.001) { // test to avoid divide by zero, s is always positive due to sqrt
            axis.set(x, y, z, 0);
        } else {
            axis.set(x / s, y / s, z / s, 0);
        }
        return axis;
    }

    @Override
    public IDouble1Value getAngle() {
        if (w > 1) {
            nor();
        }
        return new Double1(2 * Math.acos(w));
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double w() {
        return w;
    }

    @Override
    public void update() {
        // nothing to do.
    }

}
