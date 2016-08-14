package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;
import com.jogamp.opengl.GL2;

public class AsteroidsLaser extends GameObject {
	private static final double projectileSpeed = 50.0;
	private static final double[] dotColor = new double[]{1.0, 1.0, 1.0, 1.0};
	private static final double dotSize = 3.0;

	private double angle;

	public AsteroidsLaser(GameObject parent, double angle) {
		super(parent);

		this.angle = angle;
	}

	@Override
	public void update(double dt) {
		translate(new Vector3(Math.cos(Math.toRadians(angle)), -Math.sin(Math.toRadians(angle))));
	}


	@Override
	public void drawSelf(GL2 gl) {

		gl.glColor4d(dotColor[0], dotColor[1], dotColor[2], dotColor[3]);

		gl.glBegin(GL2.GL_POINTS);

		gl.glPointSize((float)dotSize);

		gl.glVertex3d(0.0, 0.0, 0.0);

		gl.glEnd();

	}
}