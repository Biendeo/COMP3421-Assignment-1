package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * The mastermind to the game; controls all the objects, and manages the game's states and variables.
 *
 * @author Thomas Moffet, z5061905
 */
public class AsteroidsRules extends GameObject {

	public static final Vector3 playerStartingPosition = new Vector3(0.0, -20.0);
	public static final double cameraZoom = 25;
	public static final double playerDeadTime = 3.0;
	public static final double asteroidDelay = 2.0;

	private int lives;
	private int score;

	private boolean playerDead;
	private double playerDeadTimeLeft;

	private double timeToNextAsteroid;

	private AsteroidsPlayer player;
	private List<AsteroidsAsteroid> asteroids;
	private List<AsteroidsLaser> laserShots;
	private List<GameObject> otherObjects;

	private AsteroidsString scoreString;
	private AsteroidsString livesString;

	/**
	 * Creates a new rules object.
	 * @param parent The parent object.
	 */
	public AsteroidsRules(GameObject parent) {
		super(parent);
		asteroids = new ArrayList<AsteroidsAsteroid>();
		laserShots = new ArrayList<AsteroidsLaser>();
		otherObjects = new ArrayList<GameObject>();
		newGame();
	}

	/**
	 * Creates a new game, creating all the objects for the game.
	 */
	private void newGame() {
		// Firstly, we spawn the player.
		player = new AsteroidsPlayer(GameObject.ROOT, this);
		spawnPlayer();

		// Make sure that no other objects exist.
		asteroids.clear();
		laserShots.clear();
		otherObjects.clear();

		// Then we create the score text using two strings.
		AsteroidsString scoreText = new AsteroidsString(GameObject.ROOT, "SCORE", true, false);
		scoreText.translate(new Vector3(-cameraZoom, cameraZoom - 1));
		otherObjects.add(scoreText);
		scoreString = new AsteroidsString(GameObject.ROOT, Integer.toString(score), true, false);
		scoreString.translate(new Vector3(-cameraZoom, cameraZoom - 3));
		otherObjects.add(scoreString);

		// We set our starting lives to 3.
		lives = 3;

		//And we also create the lives text.
		AsteroidsString livesText = new AsteroidsString(GameObject.ROOT, "LIVES", false, true);
		livesText.translate(new Vector3(cameraZoom, cameraZoom - 1));
		otherObjects.add(livesText);
		livesString = new AsteroidsString(GameObject.ROOT, Integer.toString(lives), false, true);
		livesString.translate(new Vector3(cameraZoom, cameraZoom - 3));
		otherObjects.add(livesString);

		// Then we just set the delay to the first asteroid.
		timeToNextAsteroid = asteroidDelay;
	}

	@Override
	public void update(double dt) {
		// We make sure we handle all collisions first.
		processLaserCollisions();

		// Then we spawn a new asteroid if we need to.
		timeToNextAsteroid -= dt;
		if (timeToNextAsteroid <= 0) {
			timeToNextAsteroid += asteroidDelay;
			spawnRandomAsteroid();
		}

		// If the player is dead, we just process the time left. If they need to respawn, we call
		// that.
		if (playerDead) {
			playerDeadTimeLeft -= dt;
			if (playerDeadTimeLeft < 0) {
				// TODO: This might be a problem if an asteroid is on top of the player when they spawn.
				spawnPlayer();
			}
		} else {
			// Otherwise, they need to collide with things.
			processPlayerCollision();
		}
	}

	/**
	 * Resets the game and starts a new one.
	 * If you call this, make sure you also call {@link #getPlayerKeyListener()} to get the input
	 * working.
	 */
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

	/**
	 * Returns the KeyListener associated with the player. Use this to attach the controls to the
	 * panel that this game is using.
	 * @return The player's KeyListener.
	 */
	public KeyListener getPlayerKeyListener() {
		return (KeyListener)player;
	}

	/**
	 * Returns the camera's zoom level (this is constant).
	 * @return The camera zoom.
	 */
	public double getCameraZoom() {
		return cameraZoom;
	}

	/**
	 * Fires a shot from the player.
	 */
	public void fireShot() {
		Vector3 position = player.getPositionVector();
		Vector3 rotation = player.getRotationVector();
		Vector3 scale = player.getScaleVector();
		AsteroidsLaser laser = new AsteroidsLaser(GameObject.ROOT, this, rotation.z);
		laser.translate(position);
		laserShots.add(laser);
	}

	/**
	 * Deletes a given asteroid.
	 * @param asteroid The asteroid.
	 */
	public void deleteAsteroid(AsteroidsAsteroid asteroid) {
		asteroids.remove(asteroid);
		asteroid.destroy();
	}

	/**
	 * Deletes the given laser.
	 * @param laser The laser.
	 */
	public void deleteLaser(AsteroidsLaser laser) {
		laserShots.remove(laser);
		laser.destroy();
	}

	/**
	 * Deletes the given object.
	 * This object should be either an explosion or a text element that is contained within the
	 * game.
	 * @param object The given object.
	 */
	public void deleteObject(GameObject object) {
		otherObjects.remove(object);
		object.destroy();
	}

	/**
	 * Checks every laser against every asteroid and determines whether the asteroid is shot.
	 */
	private void processLaserCollisions() {
		List<AsteroidsAsteroid> asteroidList = new ArrayList<AsteroidsAsteroid>(asteroids);
		for (AsteroidsAsteroid a : asteroidList) {
			List<AsteroidsLaser> laserList = new ArrayList<AsteroidsLaser>(laserShots);
			for (AsteroidsLaser l : laserList) {
				// If the laser is colliding with the asteroid.
				if (a.collides(l.getGlobalPositionVector())) {
					// We add to the score.
					score += a.getRadius() * a.getRadius() * 100;
					// Update the score text.
					updateScore();
					// Create an explosion at the asteroid.
					createAsteroidExplosion(a);
					// And delete the two objects.
					deleteAsteroid(a);
					deleteLaser(l);
					// We also stop checking this asteroid.
					break;
				}
			}
		}
	}

	/**
	 * Checks the player's vertices against all the asteroids to see if it collides.
	 */
	private void processPlayerCollision() {
		// This starts by converting the player vertices to global positions.
		Vector3 playerGlobalPosition = player.getGlobalPositionVector();
		Vector3 playerGlobalRotation = player.getGlobalRotationVector();

		Vector3 playerVertex1 = new Vector3(player.hitboxPoints[0], player.hitboxPoints[1]);
		Vector3 playerVertex2 = new Vector3(player.hitboxPoints[2], player.hitboxPoints[3]);
		Vector3 playerVertex3 = new Vector3(player.hitboxPoints[4], player.hitboxPoints[5]);
		// TODO: Confirm this is correct.
		Vector3 playerVertex1Global = new Vector3(playerVertex1.x * -Math.sin(Math.toRadians(playerGlobalRotation.z)), playerVertex1.y * Math.cos(Math.toRadians(playerGlobalRotation.z))).add(playerGlobalPosition);
		Vector3 playerVertex2Global = new Vector3(playerVertex2.x * -Math.sin(Math.toRadians(playerGlobalRotation.z)), playerVertex2.y * Math.cos(Math.toRadians(playerGlobalRotation.z))).add(playerGlobalPosition);
		Vector3 playerVertex3Global = new Vector3(playerVertex3.x * -Math.sin(Math.toRadians(playerGlobalRotation.z)), playerVertex3.y * Math.cos(Math.toRadians(playerGlobalRotation.z))).add(playerGlobalPosition);

		for (AsteroidsAsteroid a : asteroids) {
			// If any of the vertices collide and the player is alive, lose a life.
			if ((a.collides(playerVertex1Global) || a.collides(playerVertex2Global) || a.collides(playerVertex3Global)) && player.isShowing()) {
				loseLife();
			}
		}
	}

	/**
	 * Destroys the player and loses a life, or game overs if there's no lives left.
	 */
	private void loseLife() {
		--lives;
		updateLives();
		createPlayerExplosion();
		player.show(false);
		if (lives <= 0) {
			gameOver();
		} else {
			playerDead = true;
			playerDeadTimeLeft = playerDeadTime;
		}
	}

	/**
	 * Spawns the player at the default position.
	 */
	private void spawnPlayer() {
		player.show(true);
		player.resetPosition();
		playerDead = false;
		playerDeadTimeLeft = 0.0;
	}

	/**
	 * Displays a game over message.
	 */
	private void gameOver() {
		AsteroidsGameOverString gameOverString = new AsteroidsGameOverString(GameObject.ROOT);
		gameOverString.setScale(new Vector3(5.0, 5.0, 5.0));
		gameOverString.translate(new Vector3(0.0, 8.0));
	}

	/**
	 * Spawns a random asteroid off the side of the game, and moves it towards the game field.
	 */
	private void spawnRandomAsteroid() {
		Random r = new Random();

		// The spawn distance is a fair distance away from the action.
		final double spawnDistance = cameraZoom * 2 + 5;

		// The size of the asteroid is randomised.
		double size = r. nextDouble() * (AsteroidsAsteroid.maximumSize - AsteroidsAsteroid.minimumSize) + AsteroidsAsteroid.minimumSize;

		// Same with its velocity magnitude.
		double decidedVelocity = r.nextDouble() * (AsteroidsAsteroid.maximumVelocity - AsteroidsAsteroid.minimumVelocity) + AsteroidsAsteroid.minimumVelocity;

		// And the angle.
		double decidedAngle = r.nextDouble() * 360 - 180;

		// The velocity is then converted to a Vector3.
		Vector3 velocity = new Vector3(decidedVelocity * -Math.sin(Math.toRadians(decidedAngle)), decidedVelocity * Math.cos(Math.toRadians(decidedAngle)));

		// Then we pick a random point on the screen.
		Vector3 randomPoint = new Vector3(r.nextDouble() * 2 * cameraZoom - cameraZoom, r.nextDouble() * 2 * cameraZoom - cameraZoom);

		// We then set the asteroid's starting position as that spawn distance away from the point
		// in the reverse direction to its velocity.
		Vector3 startingPosition = randomPoint.add(new Vector3(spawnDistance * Math.sin(Math.toDegrees(-decidedAngle)), spawnDistance * -Math.cos(Math.toDegrees(-decidedAngle))));

		// Then we just create the asteroid.
		AsteroidsAsteroid asteroid = new AsteroidsAsteroid(GameObject.ROOT, this, size, velocity);
		asteroid.translate(startingPosition);
		asteroids.add(asteroid);

	}

	/**
	 * Creates an explosion relative to the given asteroid size and position.
	 * @param asteroid The asteroid.
	 */
	private void createAsteroidExplosion(AsteroidsAsteroid asteroid) {
		AsteroidsExplosion explosion = new AsteroidsExplosion(GameObject.ROOT, this, 25, asteroid.getRadius() * 3.0, 1.0);
		explosion.translate(asteroid.getPositionVector());
		otherObjects.add(asteroid);
	}

	/**
	 * Creates an explosion at the player.
	 */
	private void createPlayerExplosion() {
		AsteroidsExplosion explosion = new AsteroidsExplosion(GameObject.ROOT, this, 50, 20.0, 3.0);
		explosion.translate(player.getPositionVector());
		otherObjects.add(explosion);
	}

	/**
	 * Updates the on-screen score text.
	 */
	private void updateScore() {
		scoreString.updateString(Integer.toString(score));
	}

	/**
	 * Updates the on-screen lives text.
	 */
	private void updateLives() {
		livesString.updateString(Integer.toString(lives));
	}
}
