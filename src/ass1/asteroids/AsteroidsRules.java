package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains player data about the game.
 */
public class AsteroidsRules extends GameObject {

	private static final Vector3 playerStartingPosition = new Vector3(0.0, -90.0);

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
		player = new AsteroidsPlayer(GameObject.ROOT);
		player.scale(new Vector3(10.0, 10.0, 10.0));
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

}
