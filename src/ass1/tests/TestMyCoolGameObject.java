package ass1.tests;


import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import ass1.*;
import ass1.math.Vector3;

/**
 * A simple class to view MyCoolGameObject
 *
 * @author angf
 */
public class TestMyCoolGameObject {

	public static void createTestObjects(){
		
		//Should look good when we create using the default constructor
		MyCoolGameObject cgo = new MyCoolGameObject(GameObject.ROOT, new double[]{1.0, 1.0, 1.0, 1.0}, new double[]{0.9, 0.9, 0.9, 1.0});
		
		// Should not break if we apply transformations for example
		// If we uncommented these lines (or wrote other transformations) 
		// it should not break your object

		cgo.translate(-0.2,0.2);
		cgo.rotate(new Vector3(0.0, 45.0, 0.0));
		cgo.scale(0.25);
	}
   
    /**
     * A simple test for MyCoolGameObject
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Initialise OpenGL
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        
        // create a GLJPanel to draw on
        GLJPanel panel = new GLJPanel(glcapabilities);

        // Create a camera
        Camera camera = new Camera(GameObject.ROOT);
        
        createTestObjects();
        
        // Add the game engine
        GameEngine engine = new GameEngine(camera);
        panel.addGLEventListener(engine);

        // Add an animator to call 'display' at 60fps        
        FPSAnimator animator = new FPSAnimator(60);
        animator.add(panel);
        animator.start();

        // Put it in a window
        JFrame jFrame = new JFrame("Test Shapes");
        jFrame.add(panel);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
