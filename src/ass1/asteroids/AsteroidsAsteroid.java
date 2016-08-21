package ass1.asteroids;

import ass1.CircularGameObject;
import ass1.GameObject;
import ass1.math.Vector3;
import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An asteroid in the game space.
 *
 * @author Thomas Moffet, z5061905
 */
public class AsteroidsAsteroid extends CircularGameObject {

	public static final double radiusJitter = 0.25;
	public static final double[] fillColor = null;
	public static final double[] lineColor = new double[]{1.0, 1.0, 1.0, 1.0};
	public static final double maximumRotationSpeed = 100.0;

	public static final double minimumVelocity = 5.0;
	public static final double maximumVelocity = 30.0;

	public static final double minimumSize = 1.5;
	public static final double maximumSize = 6.0;

	private List<Double> vertexRadii;
	private AsteroidsRules rules;

	private double rotationSpeed;

	private boolean beenOnScreenBefore;

	private Vector3 velocity;

	/**
	 * Creates a new asteroid with the given parameters.
	 * @param parent The parent object.
	 * @param rules A reference to the rules.
	 * @param radius The size of the asteroid.
	 * @param velocity How fast the asteroid is moving in the x and y directions.
	 */
	public AsteroidsAsteroid(GameObject parent, AsteroidsRules rules, double radius, Vector3 velocity) {
		super(parent, radius, fillColor, lineColor);

		this.rules = rules;

		vertexRadii = new ArrayList<Double>();

		Random r = new Random();
		// The shape of the asteroid is randomly generated.
		for (int i = 0; i < sides; ++i) {
			double calculatedRadius = radius * (1 - r.nextDouble() * radiusJitter);
			vertexRadii.add(calculatedRadius);
		}

		// Same thing with the asteroid's rotation.
		rotationSpeed = (r.nextDouble() - 0.5) * maximumRotationSpeed;

		this.velocity = velocity;

		beenOnScreenBefore = false;
	}

	@Override
	public void update(double dt) {
		rotate(new Vector3(0.0, 0.0, rotationSpeed * dt));
		translate(velocity.multiply(dt));

		double cameraZoom = rules.getCameraZoom() + getRadius();
		Vector3 position = getPositionVector();

		// The asteroid will only loop if it's been on screen before.
		if (beenOnScreenBefore) {
			if (position.x > cameraZoom) {
				position.subtractSelf(new Vector3(2 * cameraZoom, 0.0, 0.0));
			} else if (position.x < -cameraZoom) {
				position.addSelf(new Vector3(2 * cameraZoom, 0.0, 0.0));
			}

			if (position.y > cameraZoom) {
				position.subtractSelf(new Vector3(0.0, 2 * cameraZoom, 0.0));
			} else if (position.y < -cameraZoom) {
				position.addSelf(new Vector3(0.0, 2 * cameraZoom, 0.0));
			}
			setPosition(position);
		} else {
			if (position.x < cameraZoom && position.x > -cameraZoom && position.y < cameraZoom && position.y > -cameraZoom) {
				beenOnScreenBefore = true;
			}
		}

	}

	@Override
	public void drawSelf(GL2 gl) {

		gl.glColor4d(lineColor[0], lineColor[1], lineColor[2], lineColor[3]);

		gl.glBegin(GL2.GL_LINE_LOOP);

		for (int i = 0; i < sides; ++i) {
			gl.glVertex2d(Math.sin(i * 2 * Math.PI / sides) * vertexRadii.get(i).doubleValue(), Math.cos(i * 2 * Math.PI / sides) * vertexRadii.get(i).doubleValue());
		}

		gl.glEnd();

	}
}
