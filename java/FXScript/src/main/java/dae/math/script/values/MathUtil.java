/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.values;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class MathUtil {

    private static Double3 work1 = new Double3();

    private static final Double3 v1Work = new Double3();
    private static final Double3 v2Work = new Double3();

    public static void linePlaneIntersection(Double3 planeOrigin, Double3 planeNormal, Double3 p1, Double3 p2, Double3 result) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        double dz = p2.z - p1.z;
        double l = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double ndx = dx / l;
        double ndy = dy / l;
        double ndz = dz / l;
        double t = (planeNormal.dot(planeOrigin) - planeNormal.dot(p1)) / (planeNormal.x * ndx + planeNormal.y * ndy + planeNormal.z * ndz);
        result.set(p1.x + ndx * t, p1.y + ndy * t, p1.z + ndz * t, 1);
    }

    public static void lineCPA(Double3 P1, Double3 P2, Double3 Q1, Double3 Q2, Double3 result1, Double3 result2) {
        double wx = P1.x - Q1.x;
        double wy = P1.y - Q1.y;
        double wz = P1.z - Q1.z;

        double ux = P2.x - P1.x;
        double uy = P2.y - P1.y;
        double uz = P2.z - P1.z;

        double vx = Q2.x - Q1.x;
        double vy = Q2.y - Q1.y;
        double vz = Q2.z - Q1.z;

        double a = Double3.dot(ux, uy, uz, ux, uy, uz);
        double b = Double3.dot(ux, uy, uz, vx, vy, vz);
        double c = Double3.dot(vx, vy, vz, vx, vy, vz);
        double d = Double3.dot(ux, uy, uz, wx, wy, wz);
        double e = Double3.dot(vx, vy, vz, wx, wy, wz);

        double denom = (a * c - b * b);
        double s = (b * e - c * d) / denom;
        double t = (a * e - b * d) / denom;

        result1.set(ux, uy, uz, 0);
        result1.scale(s);
        result1.add(P1);

        result2.set(vx, vy, vz, 0);
        result2.scale(t);
        result2.add(Q1);
    }

    public static boolean isHalfPi(double value) {
        return Math.abs(value - Math.PI / 2.0) < 0.0001;
    }

    public static void createOrthogonalVector(Double3 vector, Double3 orthogonal) {
        createOrthogonalVector(vector.x, vector.y, vector.z, orthogonal);
    }

    public static void createOrthogonalVector(double x, double y, double z, Double3 orthogonal) {
        // choose (1,1,z) as start vector
        // v.x + v.y + o.z*v.z = 0
        double ax = Math.abs(x);
        double ay = Math.abs(y);
        double az = Math.abs(z);
        double hypot = 1;
        if (ax > ay && ax > az) {
            orthogonal.set(-z, 0, x, 0);
            hypot = Math.hypot(z, x);
        } else if (ay > az) {
            orthogonal.set(y, -x, 0, 0);
            hypot = Math.hypot(y, x);
        } else {
            orthogonal.set(0, z, -y, 0);
            hypot = Math.hypot(z, y);
        }
        orthogonal.scale(1 / hypot);
    }

    public static void project(Double3 toProject, Double3 axis, Double3 result) {
        double d = toProject.dot(axis);
        toProject.subtract(axis.x * d, axis.y * d, axis.z * d, result);
    }

    public static double angle(Double3 axis, Double3 v1, Double3 v2) {

        double nv1 = v1.norm();
        double nv2 = v2.norm();
        double dot = v1.dot(v2) / (nv1 * nv2);
        double clamped = Math.max(Math.min(dot, 1), -1);
        double angle = Math.acos(clamped);
        if (Double.isNaN(angle)) {
            return 0.0;
        } else {
            v1.cross(v2, work1);
            double dir = axis.dot(work1);
            if (dir < 0) {
                angle = -angle;
            }
            return angle;
        }
    }

    public static double projectedAngle(Double3 axis, Double3 v1, Double3 v2) {
        MathUtil.project(v1, axis, v1Work);
        MathUtil.project(v2, axis, v2Work);

        double nv1 = v1Work.norm();
        double nv2 = v2Work.norm();
        double dot = v1Work.dot(v2Work) / (nv1 * nv2);
        double clamped = Math.max(Math.min(dot, 1), -1);
        double angle = Math.acos(clamped);
        if (Double.isNaN(angle)) {
            return 0.0;
        } else {
            v1Work.cross(v2Work, work1);
            double dir = axis.dot(work1);
            if (dir < 0) {
                angle = -angle;
            }
            return angle;
        }
    }

    public static double toRadians(double value) {
        return value * Math.PI / 180.0;
    }

    public static double toDegrees(double value) {
        return value * 180.0 / Math.PI;
    }
}
