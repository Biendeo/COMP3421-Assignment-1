package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that contains player data about the game, and handles functions relating to the game's
 * state.
 */
public class AsteroidsRules extends GameObject {

	public static final Vector3 playerStartingPosition = new Vector3(0.0, -20.0);

	public static final double cameraZoom = 25;

	public static final double playerDeadTime = 3.0;

	public static final double asteroidDelay = 2.0;

	private int lives;
	private int score;

	public boolean playerDead;
	public double playerDeadTimeLeft;

	public double timeToNextAsteroid;

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
		spawnPlayer();
		asteroids.clear();
		laserShots.clear();
		otherObjects.clear();

		lives = 3;
		timeToNextAsteroid = asteroidDelay;
	}

	@Override
	public void update(double dt) {
		processLaserCollisions();
		timeToNextAsteroid -= dt;
		if (timeToNextAsteroid <= 0) {
			timeToNextAsteroid += asteroidDelay;
			spawnRandomAsteroid();
		}

		if (playerDead) {
			playerDeadTimeLeft -= dt;
			if (playerDeadTimeLeft < 0) {
				// TODO: This might be a problem if an asteroid is on top of the player when they spawn.
				spawnPlayer();
			}
		} else {
			processPlayerCollision();
		}
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

	public void deleteAsteroid(AsteroidsAsteroid asteroid) {
		asteroids.remove(asteroid);
		asteroid.destroy();
	}

	public void deleteLaser(AsteroidsLaser laser) {
		laserShots.remove(laser);
		laser.destroy();
	}

	public void deleteObject(GameObject object) {
		otherObjects.remove(object);
		object.destroy();
	}

	public void processLaserCollisions() {
		for (AsteroidsAsteroid a : asteroids) {
			for (AsteroidsLaser l : laserShots) {
				if (a.collides(l.getGlobalPositionVector())) {
					// TODO: Track score and make a nice particle effect later.
					deleteAsteroid(a);
					deleteLaser(l);
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
				loseLife();
			}
		}
	}

	public void loseLife() {
		--lives;
		createPlayerExplosion();
		player.show(false);
		if (lives <= 0) {
			gameOver();
		} else {
			playerDead = true;
			playerDeadTimeLeft = playerDeadTime;
		}
	}

	public void spawnPlayer() {
		player.show(true);
		player.resetPosition();
		playerDead = false;
		playerDeadTimeLeft = 0.0;
	}

	public void gameOver() {
		// TODO: Show the score, or something like that.
	}

	public void spawnRandomAsteroid() {
		Random r = new Random();

		// The spawn distance is a fair distance away from the action.
		final double spawnDistance = cameraZoom * 4;

		// The size of the asteroid is randomised.
		double size = r. nextDouble() * (AsteroidsAsteroid.maximumSize - AsteroidsAsteroid.minimumSize) + AsteroidsAsteroid.minimumSize;

		// Same with its velocity magnitude.
		double decidedVelocity = r.nextDouble() * (AsteroidsAsteroid.maximumVelocity - AsteroidsAsteroid.minimumVelocity) + AsteroidsAsteroid.minimumVelocity;

		// And the angle.
		double decidedAngle = r.nextDouble() * 360 - 180;

		// The velocity is then converted to a Vector3.
		Vector3 velocity = new Vector3(decidedVelocity * -Math.sin(Math.toRadians(decidedAngle)), decidedVelocity * Math.cos(Math.toRadians(decidedAngle)));

		// Then we pick a random point on the screen.
		// TODO: Not all asteroids go towards the screen, sort that out.
		Vector3 randomPoint = new Vector3(r.nextDouble() * 2 * cameraZoom - cameraZoom, r.nextDouble() * 2 * cameraZoom - cameraZoom);

		// We then set the asteroid's starting position as that spawn distance away from the point
		// in the reverse direction to its velocity.
		Vector3 startingPosition = randomPoint.add(new Vector3(spawnDistance * Math.sin(Math.toDegrees(-decidedAngle)), spawnDistance * -Math.cos(Math.toDegrees(-decidedAngle))));

		// Then we just create the asteroid.
		AsteroidsAsteroid asteroid = new AsteroidsAsteroid(GameObject.ROOT, this, size, velocity);
		asteroid.translate(startingPosition);
		asteroids.add(asteroid);

	}

	public void createPlayerExplosion() {
		AsteroidsExplosion explosion = new AsteroidsExplosion(GameObject.ROOT, this, 50, 20.0, 3.0);
		explosion.translate(player.getPositionVector());
		otherObjects.add(explosion);
	}

}
