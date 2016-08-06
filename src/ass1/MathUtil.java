package ass1;

/**
 * A collection of useful math methods 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class MathUtil {

    /**
     * Normalise an angle to the range [-180, 180)
     * 
     * @param angle 
     * @return
     */
    static public double normaliseAngle(double angle) {
        return ((angle + 180.0) % 360.0 + 360.0) % 360.0 - 180.0;
    }

    /**
     * Clamp a value to the given range
     * 
     * @param value
     * @param min
     * @param max
     * @return
     */

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    /**
     * Multiply two matrices
     * 
     * @param p A 3x3 matrix
     * @param q A 3x3 matrix
     * @return
     */
    public static double[][] multiply(double[][] p, double[][] q) {

        double[][] m = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                   m[i][j] += p[i][k] * q[k][j]; 
                }
            }
        }

        return m;
    }

    /**
     * Multiply a vector by a matrix
     * 
     * @param m A 3x3 matrix
     * @param v A 3x1 vector
     * @return
     */
    public static double[] multiply(double[][] m, double[] v) {

        double[] u = new double[3];

        for (int i = 0; i < 3; i++) {
            u[i] = 0;
            for (int j = 0; j < 3; j++) {
                u[i] += m[i][j] * v[j];
            }
        }

        return u;
    }

    /**
     * A 2D translation matrix for the given offset vector
     * 
     * @param v The x and y translation coordinates {x, y}.
     * @return
     * The translation matrix in the following form.
     * [[1,0,x]
     *  [0,1,y]
     *  [0,0,1]]
     */
    public static double[][] translationMatrix(double[] v) {
    	double[][] returnMatrix = new double[3][3];

    	returnMatrix[0][0] = 1;
    	returnMatrix[0][1] = 0;
    	returnMatrix[0][2] = v[0];
    	
    	returnMatrix[1][0] = 0;
    	returnMatrix[1][1] = 1;
    	returnMatrix[1][2] = v[1];
    	
    	returnMatrix[2][0] = 0;
    	returnMatrix[2][1] = 0;
    	returnMatrix[2][2] = 1;
    	
    	return returnMatrix;
    }

    /**
     * A 2D rotation matrix for the given angle
     * 
     * @param angle in degrees
     * @return
     * The rotation matrix in the following form.
     * [[cos(a),-sin(a),0]
     *  [sin(a), cos(a),0]
     *  [     0,      0,1]]
     */
    public static double[][] rotationMatrix(double angle) {
    	double[][] returnMatrix = new double[3][3];

    	returnMatrix[0][0] = Math.cos(Math.toRadians(angle));
    	returnMatrix[0][1] = -Math.sin(Math.toRadians(angle));
    	returnMatrix[0][2] = 0;
    	
    	returnMatrix[1][0] = Math.sin(Math.toRadians(angle));
    	returnMatrix[1][1] = Math.cos(Math.toRadians(angle));
    	returnMatrix[1][2] = 0;
    	
    	returnMatrix[2][0] = 0;
    	returnMatrix[2][1] = 0;
    	returnMatrix[2][2] = 1;
    	
    	return returnMatrix;
    }

    /**
     * TODO: A 2D scale matrix that scales both axes by the same factor
     * 
     * @param scale
     * @return
     * The scale matrix in the following form.
     * [[scale,    0,0]
     *  [    0,scale,0]
     *  [    0,    0,1]]
     */
    public static double[][] scaleMatrix(double scale) {
    	double[][] returnMatrix = new double[3][3];

    	returnMatrix[0][0] = scale;
    	returnMatrix[0][1] = 0;
    	returnMatrix[0][2] = 0;
    	
    	returnMatrix[1][0] = 0;
    	returnMatrix[1][1] = scale;
    	returnMatrix[1][2] = 0;
    	
    	returnMatrix[2][0] = 0;
    	returnMatrix[2][1] = 0;
    	returnMatrix[2][2] = 1;
    	
    	return returnMatrix;
    }

    
}
