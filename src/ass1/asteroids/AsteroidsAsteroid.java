package ass1.asteroids;

import ass1.CircularGameObject;
import ass1.GameObject;
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

	private List<Double> vertexRadii;

	public AsteroidsAsteroid(GameObject parent, double radius) {
		super(parent, radius, fillColor, lineColor);

		vertexRadii = new ArrayList<Double>();

		Random r = new Random();

		for (int i = 0; i < sides; ++i) {
			double calculatedRadius = radius * (1 - r.nextDouble() * radiusJitter);
			vertexRadii.add(calculatedRadius);
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
