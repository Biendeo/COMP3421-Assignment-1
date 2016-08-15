package ass1.asteroids;

import ass1.CircularGameObject;
import ass1.GameObject;
import ass1.math.Vector3;
import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class AsteroidsAsteroid extends CircularGameObject {

	private static final double radiusJitter = 0.25;
	public static final double[] fillColor = null;
	public static final double[] lineColor = new double[]{1.0, 1.0, 1.0, 1.0};
	public static final double maximumRotationSpeed = 10.0;

	private List<Double> vertexRadii;
	private AsteroidsRules rules;

	private double rotationSpeed;

	public Vector3 velocity;

	public AsteroidsAsteroid(GameObject parent, AsteroidsRules rules, double radius) {
		super(parent, radius, fillColor, lineColor);

		this.rules = rules;

		vertexRadii = new ArrayList<Double>();

		Random r = new Random();

		for (int i = 0; i < sides; ++i) {
			double calculatedRadius = radius * (1 - r.nextDouble() * radiusJitter);
			vertexRadii.add(calculatedRadius);
		}

		rotationSpeed = (r.nextDouble() - 0.5) * maximumRotationSpeed;

		// TODO: Set a velocity;
		velocity = new Vector3();
	}

	@Override
	public void update(double dt) {
		rotate(new Vector3(0.0, 0.0, rotationSpeed * dt));
		translate(velocity.multiply(dt));

		double cameraZoom = rules.getCameraZoom();
		Vector3 position = getPositionVector();

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
