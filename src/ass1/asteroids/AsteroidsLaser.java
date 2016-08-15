package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;
import com.jogamp.opengl.GL2;

public class AsteroidsLaser extends GameObject {
	private static final double projectileSpeed = 20.0;
	private static final double[] dotColor = new double[]{1.0, 1.0, 1.0, 1.0};
	private static final double dotSize = 3.0;

	private AsteroidsRules rules;

	private double angle;

	public AsteroidsLaser(GameObject parent, AsteroidsRules rules, double angle) {
		super(parent);

		this.rules = rules;
		this.angle = angle;
	}

	@Override
	public void update(double dt) {
		translate(new Vector3(-Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle))));

		double cameraZoom = rules.getCameraZoom() + 2;

		if (getPositionVector().x > cameraZoom || getPositionVector().x < -cameraZoom || getPositionVector().y > cameraZoom || getPositionVector().y < -cameraZoom) {
			rules.deleteLaser(this);
		}
	}


	@Override
	public void drawSelf(GL2 gl) {

		gl.glColor4d(dotColor[0], dotColor[1], dotColor[2], dotColor[3]);

		gl.glPointSize((float)dotSize);

		gl.glBegin(GL2.GL_POINTS);

		gl.glVertex3d(0.0, 0.0, 0.0);

		gl.glEnd();

	}
}
