package ass1;

import ass1.math.Vector3;
import com.jogamp.opengl.GL2;

/**
 * A game object that has a polygonal shape.
 * 
 * This class extend GameObject to draw polygonal shapes.
 *
 * @author malcolmr
 */
public class PolygonalGameObject extends GameObject {

	private double[] myPoints;
	private double[] myFillColour;
	private double[] myLineColour;

	/**
	 * Create a polygonal game object and add it to the scene tree
	 *
	 * The polygon is specified as a list of doubles in the form:
	 *
	 * [ x0, y0, x1, y1, x2, y2, ... ]
	 *
	 * The line and fill colours can possibly be null, in which case that part of the object
	 * should not be drawn.
	 *
	 * @param parent The parent in the scene tree
	 * @param points A list of points defining the polygon
	 * @param fillColour The fill colour in [r, g, b, a] form
	 * @param lineColour The outlien colour in [r, g, b, a] form
	 */
	public PolygonalGameObject(GameObject parent, double points[],
			double[] fillColour, double[] lineColour) {
		super(parent);

		myPoints = points;
		myFillColour = fillColour;
		myLineColour = lineColour;
	}

	/**
	 * Get the polygon
	 *
	 * @return The polygon's points.
	 */
	public double[] getPoints() {
		return myPoints;
	}

	/**
	 * Set the polygon
	 *
	 * @param points The polygon's points.
	 */
	public void setPoints(double[] points) {
		myPoints = points;
	}

	/**
	 * Get the fill colour
	 *
	 * @return The fill colour.
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
	 * @return The outline colour.
	 */
	public double[] getLineColour() {
		return myLineColour;
	}

	/**
	 * Set the outline colour.
	 *
	 * Setting the colour to null means the outline should not be drawn
	 *
	 * @param lineColour The polygon's line colour.
	 */
	public void setLineColour(double[] lineColour) {
		myLineColour = lineColour;
	}

	/**
	 * if the fill colour is non-null, fill the polygon with this colour
	 * if the line colour is non-null, draw the outline with this colour
	 */
	@Override
	public void drawSelf(GL2 gl) {

		if (myFillColour != null) {
			gl.glColor4d(myFillColour[0], myFillColour[1], myFillColour[2], myFillColour[3]);

			gl.glBegin(GL2.GL_POLYGON);

			for (int i = 0; i < myPoints.length; i += 2) {
				gl.glVertex2d(myPoints[i], myPoints[i + 1]);
			}

			gl.glEnd();
		}

		if (myLineColour != null) {
			gl.glColor4d(myLineColour[0], myLineColour[1], myLineColour[2], myLineColour[3]);

			gl.glBegin(GL2.GL_LINE_LOOP);

			for (int i = 0; i < myPoints.length; i += 2) {
				gl.glVertex2d(myPoints[i], myPoints[i + 1]);
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

		// TODO: Iron out the logic on this.

		// We start by just checking the rectangle formed by this polygon's further vertices.
		Vector3 rectangleTopLeft = new Vector3(100000000000.0, 100000000000.0);
		Vector3 rectangleBottomRight = new Vector3(-100000000000.0, -100000000000.0);

		for (int i = 0; i < myPoints.length; i += 2) {
			if (myPoints[i] < rectangleTopLeft.x) {
				rectangleTopLeft.x = myPoints[i];
			}
			if (myPoints[i] > rectangleBottomRight.x) {
				rectangleBottomRight.x = myPoints[i];
			}
			if (myPoints[i + 1] < rectangleTopLeft.y) {
				rectangleTopLeft.y = myPoints[i + 1];
			}
			if (myPoints[i + 1] > rectangleBottomRight.y) {
				rectangleBottomRight.y = myPoints[i + 1];
			}
		}

		// If the point isn't in this rectangle, then we can skip it.
		if (localPointPosition.x < rectangleTopLeft.x || localPointPosition.x > rectangleBottomRight.x || localPointPosition.y < rectangleTopLeft.y || localPointPosition.y > rectangleBottomRight.y) {
			return false;
		}

		// If it is in the rectangle, we take a ray to the right of this point, and see how many
		// lines we cross.
		int linesCrossed = 0;

		for (int i = 0; i < myPoints.length; i += 2) {
			// We two points in this polygon to make our line.
			Vector3 currentPoint = new Vector3(myPoints[i], myPoints[i + 1]);
			Vector3 nextPoint;
			// The last point will loop back to the first point.
			if (i + 2 == myPoints.length) {
				nextPoint = new Vector3(myPoints[0], myPoints[1]);
			} else {
				nextPoint = new Vector3(myPoints[i + 2], myPoints[i + 3]);
			}

			// If the line is completely to the left of the point, we can ignore it.
			if (currentPoint.x < localPointPosition.x && nextPoint.x < localPointPosition.x) {
				continue;
			} else if (currentPoint.x > localPointPosition.x && nextPoint.x > localPointPosition.x) {
				// If the line is completely to the right, then if the ray goes between the two
				// points, we count it.
				if (Math.abs(localPointPosition.y - currentPoint.y) + Math.abs(localPointPosition.y - nextPoint.y) == Math.abs(nextPoint.y - currentPoint.y)) {
					++linesCrossed;
				}
			} else {
				// Otherwise, we have some edge cases.
				// If the line is either completely above or below the point, we skip.
				if (Math.abs(localPointPosition.y - currentPoint.y) + Math.abs(localPointPosition.y - nextPoint.y) == Math.abs(nextPoint.y - currentPoint.y)) {
					double pointGradient;
					// If it is, then we calculate the gradient of the line, and the gradient from
					// this point to the line.
					if (nextPoint.x > currentPoint.x) {
						pointGradient = (localPointPosition.y - currentPoint.y) / (localPointPosition.x - nextPoint.x);
					} else {
						pointGradient = (localPointPosition.y - nextPoint.y) / (localPointPosition.x - currentPoint.x);
					}
					double lineGradient = (nextPoint.y - currentPoint.y) / (nextPoint.x - currentPoint.x);
					if (lineGradient == 0) {
						// TODO: Handle this better.
						// If the line is horizontal, we just say it passes the line.
						++linesCrossed;
					} else if (lineGradient < 0) {
						// If the line goes down-right, then our line just needs to be less steep.
						if (pointGradient > lineGradient) {
							++linesCrossed;
						}
					} else if (lineGradient > 0) {
						// Ssame thing if it's up-right.
						if (pointGradient < lineGradient) {
							++linesCrossed;
						}
					} else {
						// If the line is vertical, then it has to pass it.
						++linesCrossed;
					}
				}
			}
		}

		// If we passed an even amount of lines, we are not in the polygon.
		if (linesCrossed % 2 == 0) {
			return false;
		} else {
			return true;
		}
	}


}
