package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains player data about the game.
 */
public class AsteroidsRules extends GameObject {

	private static final Vector3 playerStartingPosition = new Vector3(0.0, -20.0);

	public static final double cameraZoom = 25;

	private int lives;
	private int score;

	private AsteroidsPlayer player;
	private List<AsteroidsAsteroid> asteroids;
	private List<AsteroidsLaser> laserShots;
	private List<GameObject> otherObjects;

	public AsteroidsRules(GameObject parent) {
		super(parent);
		asteroids = new ArrayList<AsteroidsAsteroid>();
		laserShots = new ArrayList<AsteroidsLaser>();
		otherObjects = new ArrayList<GameObject>();
		newGame();
	}

	public void newGame() {
		player = new AsteroidsPlayer(GameObject.ROOT, this);
		player.translate(playerStartingPosition);
		asteroids.clear();
		laserShots.clear();
		otherObjects.clear();
	}

	public void resetGame() {
		player.destroy();
		for (AsteroidsAsteroid a : asteroids) {
			a.destroy();
		}
		for (AsteroidsLaser l : laserShots) {
			l.destroy();
		}
		for (GameObject o : otherObjects) {
			o.destroy();
		}

		newGame();
	}

	public KeyListener getPlayerKeyListener() {
		return (KeyListener)player;
	}

	public double getCameraZoom() {
		return cameraZoom;
	}

	public void fireShot() {
		Vector3 position = player.getPositionVector();
		Vector3 rotation = player.getRotationVector();
		Vector3 scale = player.getScaleVector();
		AsteroidsLaser laser = new AsteroidsLaser(GameObject.ROOT, this, rotation.z);
		laser.translate(position);
		laserShots.add(laser);
	}

	public void deleteLaser(AsteroidsLaser laser) {
		laserShots.remove(laser);
		laser.destroy();
	}

}
