package ass1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import ass1.math.Vector3;

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
			gl.glClearDepth(1000.0);
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		} else {
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		}

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		Vector3 globalTranslate = getGlobalPositionVector();
		Vector3 globalRotate = getGlobalRotationVector();
		Vector3 globalScale = getGlobalScaleVector();

		gl.glScaled(1.0 / globalScale.x, 1.0 / globalScale.y, 1.0 / globalScale.z);
		gl.glRotated(-globalRotate.z, 0.0, 0.0, 1.0);
		gl.glRotated(-globalRotate.y, 0.0, 1.0, 0.0);
		gl.glRotated(-globalRotate.x, 1.0, 0.0, 0.0);
		gl.glTranslated(-globalTranslate.x, -globalTranslate.y, -globalTranslate.z);
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
		
		// If 3D rendering is wanted, then we set up the orthographic projection to the same as
		// before, but with a near-far plane of [0.01, 1000.0]. Otherwise, it's the given code.
		if (depthBufferEnabled) {
			gl.glOrtho(left, right, bottom, top, 0.01, 1000.0);
		} else {
			GLU myGLU = new GLU();
			// coordinate system (left, right, bottom, top)
			myGLU.gluOrtho2D(left, right, bottom, top);
		}
	}

	/**
	 * Turns on the depth buffer flag and changes how the camera draws.
	 * This is intended for drawing objects on top of other objects, but it changes how regular
	 * scenes are drawn slightly, so don't use this if you want exact output.
	 */
	public void enableDepthBuffer() {
		depthBufferEnabled = true;
	}

	/**
	 * Turns off the depth buffer flag and changes how the camera draws.
	 *
	 */
	public void disableDepthBuffer() {
		depthBufferEnabled = false;
	}
}
