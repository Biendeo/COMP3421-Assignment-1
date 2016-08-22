package ass1.asteroids;

import javax.swing.JFrame;

import ass1.*;
import ass1.math.Vector3;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class AsteroidsGame {

	private static final String windowTitle = "Asteroids";
	private static final int windowFramerate = 60;
	private static final int windowWidth = 800;
	private static final int windowHeight = 800;

	/**
	 * The main entry point to the Asteroids game.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Initialise OpenGL
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities(glprofile);

		// create a GLJPanel to draw on
		GLJPanel panel = new GLJPanel(glcapabilities);

		// Add my own game objects and set the depth buffer.
		AsteroidsRules rules = new AsteroidsRules(GameObject.ROOT);
		Camera camera = new Camera(GameObject.ROOT);
		camera.setScale(new Vector3(rules.getCameraZoom(), rules.getCameraZoom(), rules.getCameraZoom()));
		camera.translate(new Vector3(0.0, 0.0, 5.0));
		camera.enableDepthBuffer();

		// Add the game engine
		GameEngine engine = new GameEngine(camera);
		panel.addGLEventListener(engine);
		panel.addKeyListener(rules.getPlayerKeyListener());

		FPSAnimator animator = new FPSAnimator(windowFramerate);
		animator.add(panel);
		animator.start();

		// Put it in a window
		JFrame jFrame = new JFrame(windowTitle);
		jFrame.add(panel);
		jFrame.setSize(windowWidth, windowHeight);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
