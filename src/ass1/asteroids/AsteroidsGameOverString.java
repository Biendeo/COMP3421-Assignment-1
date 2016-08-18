package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;

/**
 * A nice fancy version of the AsteroidsString that shows a game over message.
 *
 * @author Thomas Moffet, z5061905
 */
public class AsteroidsGameOverString extends AsteroidsString {
	private double rotation;

	/**
	 * Creates a new game over string.
	 * @param parent The parent object.
	 */
	public AsteroidsGameOverString(GameObject parent) {
		super(parent, "GAME OVER", false, false);
		rotation = 0;
	}

	@Override
	public void update(double dt) {

		rotation += dt / 50;
		this.setRotationVector(new Vector3(Math.sin(Math.toDegrees(rotation)) * 10, Math.cos(Math.toDegrees(rotation)) * 10, 0.0));
	}
}
