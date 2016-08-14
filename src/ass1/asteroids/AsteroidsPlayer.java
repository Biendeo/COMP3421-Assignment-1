package ass1.asteroids;

import ass1.GameObject;
import ass1.LineGameObject;
import ass1.PolygonalGameObject;

/**
 * The player object in the game.
 * It handles input, and collisions with asteroids.
 */
public class AsteroidsPlayer extends PolygonalGameObject {

	public static final double maximumSpeed = 2000.0;
	public static final double[] hitboxPoints = new double[]{-0.5, -1.0, 0.5, -1.0, 0.0, 1.0};
	public static final double[] line1 = new double[]{-0.4, -0.6, 0.4, -0.6};
	public static final double[] line2 = new double[]{0.0, 1.0, 0.5, -1.0};
	public static final double[] line3 = new double[]{0.0, 1.0, -0.5, -1.0};
	public static final double[] lineColor = new double[]{1.0, 1.0, 1.0, 1.0};

	public AsteroidsPlayer(GameObject parent) {
		super(parent, hitboxPoints, null, null);
		GameObject lineObj1 = new LineGameObject(this, line1[0], line1[1], line1[2], line1[3], lineColor);
		GameObject lineObj2 = new LineGameObject(this, line2[0], line2[1], line2[2], line2[3], lineColor);
		GameObject lineObj3 = new LineGameObject(this, line3[0], line3[1], line3[2], line3[3], lineColor);
	}
}
