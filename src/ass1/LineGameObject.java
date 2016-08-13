package ass1;

import ass1.math.Vector3;
import com.jogamp.opengl.GL2;

/**
 * A game object with a line shape.
 *
 * @author Thomas Moffet
 */
public class LineGameObject extends GameObject {

	private Vector3 point1;
	private Vector3 point2;
	private double[] myLineColour;

	public LineGameObject(GameObject parent, double[] lineColour) {
		this(parent, 0.0, 0.0, 1.0, 0.0, lineColour);
	}

	public LineGameObject(GameObject parent, double x1, double y1, double x2, double y2, double[] lineColour) {
		super(parent);

		this.point1 = new Vector3(x1, y1);
		this.point2 = new Vector3(x2, y2);

		myLineColour = lineColour;

	}

	public LineGameObject(GameObject parent, Vector3 point1, Vector3 point2, double[] lineColour) {
		super(parent);

		this.point1 = point1.clone();
		this.point2 = point2.clone();

		myLineColour = lineColour;

	}

	/**
	 * @deprecated Use {@link #getPoint1()}.x.
	 */
	public double getX1() {
		return point1.x;
	}

	/**
	 * @deprecated Use {@link #setPoint1(Vector3)}.
	 */
	public void setX1(double x1) {
		point1.x = x1;
	}

	/**
	 * @deprecated Use {@link #getPoint1()}.y.
	 */
	public double getY1() {
		return point1.y;
	}

	/**
	 * @deprecated Use {@link #setPoint1(Vector3)}.
	 */
	public void setY1(double y1) {
		point1.y = y1;
	}

	/**
	 * Gets the position vector of the first point of this line.
	 * @return
	 */
	public Vector3 getPoint1() {
		return point1.clone();
	}

	/**
	 * Sets the position vector of the first point of this line.
	 * @param v
	 */
	public void setPoint1(Vector3 v) {
		point1 = v.clone();
	}

	/**
	 * @deprecated Use {@link #getPoint2()}.x.
	 */
	public double getX2() {
		return point2.x;
	}

	/**
	 * @deprecated Use {@link #setPoint2(Vector3)}.
	 */
	public void setX2(double x2) {
		point2.x = x2;
	}

	/**
	 * @deprecated Use {@link #getPoint2()}.y.
	 */
	public double getY2() {
		return point2.y;
	}

	/**
	 * @deprecated Use {@link #setPoint2(Vector3)}.
	 */
	public void setY2(double y2) {
		point2.y = y2;
	}

	/**
	 * Gets the position vector of the second point of this line.
	 * @return
	 */
	public Vector3 getPoint2() {
		return point2.clone();
	}

	/**
	 * Sets the position vector of the second point of this line.
	 * @param v
	 */
	public void setPoint2(Vector3 v) {
		point2 = v.clone();
	}

	/**
	 * Get the outline colour.
	 *
	 * @return
	 */
	public double[] getLineColour() {
		return myLineColour;
	}

	/**
	 * Set the outline colour.
	 *
	 * Setting the colour to null means the outline should not be drawn
	 *
	 * @param lineColour
	 */
	public void setLineColour(double[] lineColour) {
		myLineColour = lineColour;
	}

	/**
	 * if the fill colour is non-null, fill the polygon with this colour
	 * if the line colour is non-null, draw the outline with this colour
	 *
	 * @see ass1.spec.GameObject#drawSelf(javax.media.opengl.GL2)
	 */
	@Override
	public void drawSelf(GL2 gl) {

		if (myLineColour != null) {
			gl.glColor4d(myLineColour[0], myLineColour[1], myLineColour[2], myLineColour[3]);

			gl.glBegin(GL2.GL_LINES);

			gl.glVertex3d(point1.x, point1.y, point1.z);
			gl.glVertex3d(point2.x, point2.y, point2.z);

			gl.glEnd();
		}

	}

	@Override
	public boolean collides(Vector3 v) {
		// Firstly, the point is converted from the global to the local co-ordinate space.
		// TODO: Turn this into its own function.
		Vector3 globalPosition = getGlobalPositionVector();
		Vector3 globalRotation = getGlobalRotationVector();
		Vector3 globalScale = getGlobalScaleVector();

		Vector3 positionDifference = v.subtract(globalPosition);

		Vector3 positionDifferenceScaled = positionDifference.multiply(globalScale.invert());

		double[][] globalRotationMatrix = MathUtil.rotationMatrixXYZ(globalRotation);

		double[][] positionDifferenceScaledMatrix = MathUtil.translationMatrix(positionDifferenceScaled);

		double[][] positionRotatedMatrix = MathUtil.multiply4D(globalRotationMatrix, positionDifferenceScaledMatrix);

		Vector3 localPointPosition = MathUtil.translationMatrixToVector(positionRotatedMatrix);

		localPointPosition = new Vector3(localPointPosition.x, localPointPosition.y, 0.0);

		// To determine whether the point collides with the line, we check that the point is inside
		// the rectangle formed when this line acts as its diagonal. If it is, then we check whether
		// a line formed from the given point to this line's point1 has the same gradient as the
		// actual line itself. If it does, then it is colliding.

		if (Math.abs(localPointPosition.x - point1.x) + Math.abs(localPointPosition.x - point2.x) != Math.abs(point2.x - point1.x)) {
			return false;
		} else if (Math.abs(localPointPosition.y - point1.y) + Math.abs(localPointPosition.y - point2.y) != Math.abs(point2.y - point1.y)) {
			return false;
		} else {
			double pointGradient = (localPointPosition.y - point1.y) / (localPointPosition.x - point1.x);
			double lineGradient = (point2.y - point1.y) / (point2.x - point1.x);

			if (pointGradient == lineGradient) {
				return true;
			} else {
				return false;
			}
		}
	}
}
