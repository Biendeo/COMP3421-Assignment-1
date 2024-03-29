package ass1.asteroids;

import ass1.CircularGameObject;
import ass1.GameObject;
import ass1.math.Vector3;
import com.jogamp.opengl.GL2;

/**
 * An explosion effect used by asteroids and the player.
 *
 * @author Thomas Moffet, z5061905
 */
public class AsteroidsExplosion extends CircularGameObject {
	private static final double[] lineColor = new double[]{1.0, 1.0, 1.0, 1.0};
	private static final double dotSize = 2.0;

	private AsteroidsRules rules;

	private int explosionPoints;
	private double explosionFinalSize;
	private double explosionCurrentSize;
	private double survivalTime;
	private double currentSurvivalTime = 0;

	/**
	 * Constructs a new explosion with some given parameter.s
	 * @param parent The parent GameObject.
	 * @param rules A reference to the rules object.
	 * @param explosionPoints How many points are on the explosion.
	 * @param explosionFinalSize How big the explosion will be when it dies.
	 * @param survivalTime How long will the explosion survive for.
	 */
	public AsteroidsExplosion(GameObject parent, AsteroidsRules rules, int explosionPoints, double explosionFinalSize, double survivalTime) {
		super(parent, 0.0, null, null);
		this.rules = rules;
		this.explosionPoints = explosionPoints;
		this.explosionFinalSize = explosionFinalSize;
		this.survivalTime = survivalTime;
		this.explosionCurrentSize = 0.0;
		this.currentSurvivalTime = 0.0;
	}

	@Override
	public void update(double dt) {
		// Every update, the explosion gets bigger until it's past the survival time.
		currentSurvivalTime += dt;
		explosionCurrentSize = currentSurvivalTime * explosionFinalSize / survivalTime;
		if (explosionCurrentSize > explosionFinalSize) {
			rules.deleteObject(this);
		}
	}

	@Override
	public void drawSelf(GL2 gl) {
		// This draws similarly to the circle, except it uses points instead.
		gl.glColor4d(lineColor[0], lineColor[1], lineColor[2], lineColor[3]);
		gl.glPointSize((float)dotSize);

		gl.glBegin(GL2.GL_POINTS);

		for (int i = 0; i < explosionPoints; ++i) {
			gl.glVertex2d(Math.sin(i * 2 * Math.PI / explosionPoints) * explosionCurrentSize, Math.cos(i * 2 * Math.PI / explosionPoints) * explosionCurrentSize);
		}

		gl.glEnd();
	}

	@Override
	public boolean collides(Vector3 v) {
		// Unlike the circle, this should not collide with anything.
		return false;
	}
}
