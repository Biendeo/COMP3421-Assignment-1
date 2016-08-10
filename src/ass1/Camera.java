package ass1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
 * The camera is a GameObject that can be moved, rotated and scaled like any
 * other.
 *
 * @author malcolmr
 */
public class Camera extends GameObject {

	private float[] myBackground;

	private boolean depthBufferEnabled;

	public Camera(GameObject parent) {
		super(parent);

		myBackground = new float[4];
		depthBufferEnabled = false;
	}

	public Camera() {
		this(GameObject.ROOT);
	}

	public float[] getBackground() {
		return myBackground;
	}

	public void setBackground(float[] background) {
		myBackground = background;
	}

	public void setView(GL2 gl) {
		gl.glClearColor(myBackground[0], myBackground[1], myBackground[2], myBackground[3]);

		// I wanted to implement a depth buffer. This changes the image
		// slightly, so I've disabled
		// it by default. In my custom game, this is enabled.
		if (depthBufferEnabled) {
			gl.glEnable(GL2.GL_DEPTH_TEST);
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		} else {
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		}

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		double[] globalTranslate = getGlobalPosition();
		double globalRotate = getGlobalRotation();
		double globalScale = getGlobalScale();

		gl.glScaled(1.0 / globalScale, 1.0 / globalScale, 1.0);
		gl.glRotated(-globalRotate, 0.0, 0.0, 1.0);
		gl.glTranslated(-globalTranslate[0], -globalTranslate[1], 0.0);
	}

	public void reshape(GL2 gl, int x, int y, int width, int height) {

		// match the projection aspect ratio to the viewport
		// to avoid stretching

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		double top, bottom, left, right;

		if (width > height) {
			double aspect = (1.0 * width) / height;
			top = 1.0;
			bottom = -1.0;
			left = -aspect;
			right = aspect;
		} else {
			double aspect = (1.0 * height) / width;
			top = aspect;
			bottom = -aspect;
			left = -1;
			right = 1;
		}
		GLU myGLU = new GLU();
		// coordinate system (left, right, bottom, top)
		myGLU.gluOrtho2D(left, right, bottom, top);
	}

	public void enableDepthBuffer() {
		depthBufferEnabled = true;
	}

	public void disableDepthBuffer() {
		depthBufferEnabled = false;
	}
}
