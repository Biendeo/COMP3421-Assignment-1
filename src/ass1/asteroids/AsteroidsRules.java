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

		// This is just for testing collisions.
		AsteroidsAsteroid asteroid1 = new AsteroidsAsteroid(GameObject.ROOT, this, 6.0);
		asteroids.add(asteroid1);
	}

	@Override
	public void update(double dt) {
		processLaserCollisions();
		processPlayerCollision();
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

	public void processLaserCollisions() {
		for (AsteroidsAsteroid a : asteroids) {
			for (AsteroidsLaser l : laserShots) {
				if (a.collides(l.getGlobalPositionVector())) {
					// TODO: Track score and make a nice particle effect later.
					a.destroy();
					asteroids.remove(a);
					l.destroy();
					laserShots.remove(l);
					break;
				}
			}
		}
	}

	public void processPlayerCollision() {
		for (AsteroidsAsteroid a : asteroids) {
			Vector3 playerGlobalPosition = player.getGlobalPositionVector();
			Vector3 playerGlobalRotation = player.getGlobalRotationVector();

			Vector3 playerVertex1 = new Vector3(player.hitboxPoints[0], player.hitboxPoints[1]);
			Vector3 playerVertex2 = new Vector3(player.hitboxPoints[2], player.hitboxPoints[3]);
			Vector3 playerVertex3 = new Vector3(player.hitboxPoints[4], player.hitboxPoints[5]);
			// TODO: Confirm this is correct.
			Vector3 playerVertex1Global = new Vector3(playerVertex1.x * -Math.sin(Math.toRadians(playerGlobalRotation.z)), playerVertex1.y * Math.cos(Math.toRadians(playerGlobalRotation.z))).add(playerGlobalPosition);
			Vector3 playerVertex2Global = new Vector3(playerVertex2.x * -Math.sin(Math.toRadians(playerGlobalRotation.z)), playerVertex2.y * Math.cos(Math.toRadians(playerGlobalRotation.z))).add(playerGlobalPosition);
			Vector3 playerVertex3Global = new Vector3(playerVertex3.x * -Math.sin(Math.toRadians(playerGlobalRotation.z)), playerVertex3.y * Math.cos(Math.toRadians(playerGlobalRotation.z))).add(playerGlobalPosition);

			if (a.collides(playerVertex1Global) || a.collides(playerVertex2Global) || a.collides(playerVertex3Global)) {
				// TODO: Add a game over function.
				player.destroy();
			}
		}
	}

}
