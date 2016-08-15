package ass1.asteroids;

import ass1.GameObject;
import ass1.LineGameObject;
import ass1.PolygonalGameObject;
import ass1.math.Vector3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The player object in the game.
 * It handles input, and collisions with asteroids.
 */
public class AsteroidsPlayer extends PolygonalGameObject implements KeyListener {

	public static final double maximumSpeed = 200.0;
	public static final double turnSpeed = 180.0;
	public static final double thrustSpeed = 20.0;
	public static final double[] hitboxPoints = new double[]{-0.5, -1.0, 0.5, -1.0, 0.0, 1.0};
	public static final double[] line1 = new double[]{-0.4, -0.6, 0.4, -0.6};
	public static final double[] line2 = new double[]{0.0, 1.0, 0.5, -1.0};
	public static final double[] line3 = new double[]{0.0, 1.0, -0.5, -1.0};
	public static final double[] lineColor = new double[]{1.0, 1.0, 1.0, 1.0};

	private AsteroidsRules rules;

	private Vector3 velocity;

	private boolean movingForward;
	private boolean movingLeft;
	private boolean movingRight;

	public AsteroidsPlayer(GameObject parent, AsteroidsRules rules) {
		super(parent, hitboxPoints, null, null);
		this.rules = rules;
		GameObject lineObj1 = new LineGameObject(this, line1[0], line1[1], line1[2], line1[3], lineColor);
		GameObject lineObj2 = new LineGameObject(this, line2[0], line2[1], line2[2], line2[3], lineColor);
		GameObject lineObj3 = new LineGameObject(this, line3[0], line3[1], line3[2], line3[3], lineColor);

		velocity = new Vector3();

		movingForward = false;
		movingLeft = false;
		movingRight = false;
	}

	@Override
	public void update(double dt) {
		if (isShowing()) {
			if (movingForward) {
				double rotation = getRotationVector().z;
				velocity.addSelf(new Vector3(-Math.sin(Math.toRadians(rotation)) * thrustSpeed * dt, Math.cos(Math.toRadians(rotation)) * thrustSpeed * dt));
				if (velocity.modulus() > maximumSpeed) {
					velocity.divideSelf(velocity.modulus() / maximumSpeed);
				}
			}
			if (movingLeft) {
				rotate(new Vector3(0.0, 0.0, turnSpeed * dt));
			}
			if (movingRight) {
				rotate(new Vector3(0.0, 0.0, -turnSpeed * dt));
			}

			translate(velocity.multiply(dt));

			Vector3 position = getPositionVector();

			double cameraZoom = rules.getCameraZoom() + 2;

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
		}
	}

	private void fireShot() {
		if (isShowing()) {
			rules.fireShot();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				movingForward = true;
				break;
			case KeyEvent.VK_LEFT:
				movingLeft = true;
				break;
			case KeyEvent.VK_RIGHT:
				movingRight = true;
				break;
			case KeyEvent.VK_SPACE:
				fireShot();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				movingForward = false;
				break;
			case KeyEvent.VK_LEFT:
				movingLeft = false;
				break;
			case KeyEvent.VK_RIGHT:
				movingRight = false;
				break;
		}
	}

	public void resetPosition() {
		velocity = new Vector3();
		setPosition(rules.playerStartingPosition);
		setRotationVector(new Vector3());
	}
}
