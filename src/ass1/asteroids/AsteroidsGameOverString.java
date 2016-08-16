package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;

public class AsteroidsGameOverString extends AsteroidsString {
	private double rotation;

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
