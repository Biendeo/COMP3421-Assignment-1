package ass1.asteroids;

import javax.swing.JFrame;

import ass1.math.Vector3;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import ass1.Camera;
import ass1.GameEngine;
import ass1.GameObject;

public class AsteroidsGame {
	
	private GLProfile glprofile;
	private GLCapabilities glcapabilities;
	private GLJPanel panel;
	private GameEngine engine;
	private Camera camera;
	private FPSAnimator animator;
	private JFrame jFrame;

	private static final String windowTitle = "Asteroids";
	private static final int windowFramerate = 144;
	private static final int windowWidth = 800;
	private static final int windowHeight = 800;
	
	public static void main(String[] args) {
		AsteroidsGame g = new AsteroidsGame();
        g.initialiseOpenGL();
        g.createEngine();
        g.initialiseObjects();
	}
	
	public void initialiseOpenGL() {
		glprofile = GLProfile.getDefault();
		glcapabilities = new GLCapabilities(glprofile);
		panel = new GLJPanel(glcapabilities);
	}
	
	public void createEngine() {
		camera = new Camera(GameObject.ROOT);
		camera.enableDepthBuffer();
		camera.setBackground(new float[]{0.0f, 0.0f, 0.0f, 1.0f});
		
		engine = new GameEngine(camera);
		panel.addGLEventListener(engine);
		
		animator = new FPSAnimator(windowFramerate);
		animator.add(panel);
		animator.start();
		
		jFrame = new JFrame(windowTitle);
		jFrame.add(panel);
		jFrame.setSize(windowWidth, windowHeight);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initialiseObjects() {
		Camera camera = new Camera(GameObject.ROOT);
		camera.setScale(new Vector3(100.0, 100.0, 100.0));
		GameObject rules = new AsteroidsRules(GameObject.ROOT);
	}
}
