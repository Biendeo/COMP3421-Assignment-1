package ass1;

import ass1.math.Vector3;
import com.jogamp.opengl.GL2;

/**
 * A game object with a circular shape.
 * 
 * @author Thomas Moffet
 */
public class CircularGameObject extends GameObject {
	
	// This value is written in the spec.
	protected final static int sides = 32;

	private double myRadius;
	private double[] myFillColour;
	private double[] myLineColour;

	public CircularGameObject(GameObject parent, double[] fillColour, double[] lineColour) {
		this(parent, 1.0, fillColour, lineColour);
	}

	public CircularGameObject(GameObject parent, double radius, double[] fillColour, double[] lineColour) {
		super(parent);

		myRadius = radius;
		myFillColour = fillColour;
		myLineColour = lineColour;

	}

	public double getRadius() {
		return myRadius;
	}

	public void setRadius(double radius) {
		myRadius = radius;
	}

	/**
	 * Get the fill colour
	 *
	 * @return
	 */
	public double[] getFillColour() {
		return myFillColour;
	}

	/**
	 * Set the fill colour.
	 *
	 * Setting the colour to null means the object should not be filled.
	 *
	 * @param fillColour The fill colour in [r, g, b, a] form
	 */
	public void setFillColour(double[] fillColour) {
		myFillColour = fillColour;
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

		if (myFillColour != null) {
			gl.glColor4d(myFillColour[0], myFillColour[1], myFillColour[2], myFillColour[3]);

			gl.glBegin(GL2.GL_POLYGON);

			for (int i = 0; i < sides; ++i) {
				gl.glVertex2d(Math.sin(i * 2 * Math.PI / sides) * myRadius, Math.cos(i * 2 * Math.PI / sides) * myRadius);
			}

			gl.glEnd();
		}

		if (myLineColour != null) {
			gl.glColor4d(myLineColour[0], myLineColour[1], myLineColour[2], myLineColour[3]);

			gl.glBegin(GL2.GL_LINE_LOOP);

			for (int i = 0; i < sides; ++i) {
				gl.glVertex2d(Math.sin(i * 2 * Math.PI / sides) * myRadius, Math.cos(i * 2 * Math.PI / sides) * myRadius);
			}

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

		// Then, we just determine whether the point lies within the radius of the circle.
		if (localPointPosition.modulus() <= myRadius) {
			return true;
		} else {
			return false;
		}

	}


}
