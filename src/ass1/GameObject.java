package ass1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jogamp.opengl.GL2;

import ass1.math.Vector3;




/**
 * A GameObject is an object that can move around in the game world.
 * 
 * GameObjects form a scene tree. The root of the tree is the special ROOT object.
 * 
 * Each GameObject is offset from its parent by a rotation, a translation and a scale factor. 
 *
 * @author malcolmr
 */
public class GameObject {

	// the list of all GameObjects in the scene tree
	public final static List<GameObject> ALL_OBJECTS = new ArrayList<GameObject>();

	// the root of the scene tree
	public final static GameObject ROOT = new GameObject();

	// the links in the scene tree
	private GameObject myParent;
	private List<GameObject> myChildren;

	// the local transformation
	// These have been changed into Vector3 objects to allow 3D coordinates and transformations.
	// The Z property of myTranslation now allows for depth rendering, which lets you control
	// what objects get drawn on top of other objects, which is especially useful during runtime
	// instancing.
	private Vector3 myTranslation;
	// Rotation around the X and Y axes is mostly for visual effects.
	private Vector3 myRotation;
	// Scaling on the X and Y axes are independent now, allowing for better control of shapes.
	// Scaling on the Z axis serves no purpose since everything is flat and collision is uniform
	// across the world's Z axis.
	private Vector3 myScale;

	// is this part of the tree showing?
	private boolean amShowing;

	/**
	 * Special private constructor for creating the root node. Do not use otherwise.
	 */
	private GameObject() {
		myParent = null;
		myChildren = new ArrayList<GameObject>();

		myRotation = new Vector3();
		myScale = new Vector3(1.0, 1.0, 1.0);
		myTranslation = new Vector3();

		amShowing = true;

		ALL_OBJECTS.add(this);
	}

	/**
	 * Public constructor for creating GameObjects, connected to a parent (possibly the ROOT).
	 *
	 * New objects are created at the same location, orientation and scale as the parent.
	 *
	 * @param parent
	 */
	public GameObject(GameObject parent) {
		this();
		myParent = parent;
		parent.myChildren.add(this);
	}

	/**
	 * Remove an object and all its children from the scene tree.
	 */
	public void destroy() {
		List<GameObject> childrenList = new ArrayList<GameObject>(myChildren);

		for (GameObject child : childrenList) {
			child.destroy();
		}

		myParent.myChildren.remove(this);
		ALL_OBJECTS.remove(this);
	}

	/**
	 * Get the parent of this game object
	 *
	 * @return
	 */
	public GameObject getParent() {
		return myParent;
	}

	/**
	 * Get the children of this object
	 *
	 * @return
	 */
	public List<GameObject> getChildren() {
		return myChildren;
	}

	/**
	 * Get the local rotation (in degrees)
	 *
	 * @return
	 * @deprecated Use {@link #getRotationVector()} instead.
	 */
	public double getRotation() {
		return myRotation.z;
	}

	/**
	 * Get the local rotation (in degrees).
	 * @return
	 */
	public Vector3 getRotationVector() {
		// TODO: Remove this and try and get this normalised in memory.
		Vector3 normalisedRotation = myRotation.clone();
		normalisedRotation.x = MathUtil.normaliseAngle(normalisedRotation.x);
		normalisedRotation.y = MathUtil.normaliseAngle(normalisedRotation.y);
		normalisedRotation.z = MathUtil.normaliseAngle(normalisedRotation.z);
		return normalisedRotation;
	}

	/**
	 * Set the local rotation (in degrees)
	 *
	 * @return
	 * @deprecated Use {@link #setRotationVector(Vector3)}.
	 */
	public void setRotation(double rotation) {
		myRotation.z = MathUtil.normaliseAngle(rotation);
	}

	/**
	 * Sets the local rotation to the given vector.
	 * To use it similarly to the old {@link #setRotation(double)}, create the Vector3 object with
	 * the same x and y properties as the current rotation, and change only the z axis.
	 *
	 * @param rotation
	 */
	public void setRotationVector(Vector3 rotation) {
		myRotation = rotation.clone();
	}

	/**
	 * Rotate the object by the given angle (in degrees)
	 *
	 * @param angle
	 * @deprecated Use {@link #rotate(Vector3)} instead.
	 */
	public void rotate(double angle) {
		myRotation.z += angle;
		myRotation.z = MathUtil.normaliseAngle(myRotation.z);
	}

	/**
	 * Adds the given rotation to the current rotation.
	 * To use it similarly to the old {@link #rotate(double)}, create the Vector3 object with 0
	 * in the x and y properties, and the rotation in the z property.
	 *
	 * @param rotation
	 */
	public void rotate(Vector3 rotation) {
		myRotation.addSelf(rotation);
	}

	/**
	 * Get the local scale.
	 *
	 * @return
	 * @deprecated Use {@link #getScaleVector()}. If you've been changing the x and y scale
	 * independently, then this will only return the x scale.
	 */
	public double getScale() {
		return myScale.x;
	}

	/**
	 * Returns the local scale vector.
	 * To use it similarly to the old {@link #getScale()}, grab the x property afterwards. This
	 * relies on you scaling everything uniformly across the x and y axes.
	 *
	 * @return
	 */
	public Vector3 getScaleVector() {
		return myScale.clone();
	}

	/**
	 * Set the local scale
	 *
	 * @param scale
	 * @deprecated Use {@link #setScale(Vector3)}.
	 */
	public void setScale(double scale) {
		myScale.x = scale;
		myScale.y = scale;
	}

	/**
	 * Sets the  local scale to the given Vector3.
	 * To use it similarly to the old {@link #setScale(double)}, just make sure that the x and y
	 * properties are the same.
	 *
	 * @param scale
	 */
	public void setScale(Vector3 scale) {
		myScale = scale.clone();
	}

	/**
	 * Multiply the scale of the object by the given factor
	 *
	 * @param factor
	 */
	public void scale(double factor) {
		myScale.multiplySelf(factor);
	}

	/**
	 * Multiply the scale of the object by the given factor
	 *
	 * @param factor
	 */
	public void scale(Vector3 factor) {
		myScale.multiplySelf(factor);
	}

	/**
	 * Get the local position of the object
	 *
	 * @return
	 * @deprecated Use {@link #getPositionVector()}.
	 */
	public double[] getPosition() {
		double[] t = new double[2];
		t[0] = myTranslation.x;
		t[1] = myTranslation.y;

		return t;
	}

	/**
	 * Returns the local position of the object.
	 * To use this similarly to the old {@link #getPosition()}, just grab the x and y properties
	 * from the Vector3 object.
	 *
	 * @return
	 */
	public Vector3 getPositionVector() {
		return myTranslation.clone();
	}

	/**
	 * Set the local position of the object
	 * This does not change its z property.
	 *
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		myTranslation.x = x;
		myTranslation.y = y;
	}

	/**
	 * Set the local position of the object.
	 * This changes its z property (unless you keep it the same).
	 *
	 * @param v
	 */
	public void setPosition(Vector3 v) {
		myTranslation = v.clone();
	}

	/**
	 * Move the object by the specified offset in local coordinates
	 *
	 * @param dx
	 * @param dy
	 */
	public void translate(double dx, double dy) {
		myTranslation.x += dx;
		myTranslation.y += dy;
	}

	/**
	 * Moves the object by the specified vector.
	 * Remember to set the z property to 0 if you don't want to change the depth.
	 *
	 * @param v
	 */
	public void translate(Vector3 v) {
		myTranslation.addSelf(v);
	}

	/**
	 * Test if the object is visible
	 *
	 * @return
	 */
	public boolean isShowing() {
		return amShowing;
	}

	/**
	 * Set the showing flag to make the object visible (true) or invisible (false).
	 * This flag should also apply to all descendents of this object.
	 *
	 * @param showing
	 */
	public void show(boolean showing) {
		amShowing = showing;
	}

	/**
	 * Update the object. This method is called once per frame.
	 *
	 * This does nothing in the base GameObject class. Override this in subclasses.
	 *
	 * @param dt The amount of time since the last update (in seconds)
	 */
	public void update(double dt) {
		// do nothing
	}

	/**
	 * Draw the object (but not any descendants)
	 *
	 * This does nothing in the base GameObject class. Override this in subclasses.
	 *
	 * @param gl
	 */
	public void drawSelf(GL2 gl) {
		// do nothing
	}

	/**
	 * Draw the object and all of its descendants recursively.
	 *
	 * @param gl
	 */
	public void draw(GL2 gl) {

		// don't draw if it is not showing
		if (!amShowing) {
			return;
		}

		gl.glPushMatrix();

		gl.glTranslated(myTranslation.x, myTranslation.y, myTranslation.z);
		gl.glRotated(myRotation.x, 1.0, 0.0, 0.0);
		gl.glRotated(myRotation.y, 0.0, 1.0, 0.0);
		gl.glRotated(myRotation.z, 0.0, 0.0, 1.0);
		gl.glScaled(myScale.x, myScale.y, myScale.z);

		drawSelf(gl);

		for (GameObject child : myChildren) {
			child.draw(gl);
		}

		gl.glPopMatrix();


	}

	/**
	 * Compute the object's position in world coordinates
	 *
	 * @return a point in world coordinats in [x,y] form
	 *
	 * @deprecated Use {@link #getGlobalPositionVector()} and grab the x and y values.
	 */
	public double[] getGlobalPosition() {
		double[] p = new double[2];

		double[] parentGlobalPosition = ((this != GameObject.ROOT) ? myParent.getGlobalPosition() : new double[]{0, 0});
		double parentGlobalRotation = ((this != GameObject.ROOT) ? myParent.getGlobalRotation() : 0.0);
		double parentGlobalScale = ((this != GameObject.ROOT) ? myParent.getGlobalScale() : 1.0);

		double[][] rotationMatrix = MathUtil.rotationMatrix(parentGlobalRotation);

		double[] newGlobalTranslation = MathUtil.multiply(rotationMatrix, new double[]{myTranslation.x, myTranslation.y, 0.0});

		p[0] = newGlobalTranslation[0] * parentGlobalScale + parentGlobalPosition[0];
		p[1] = newGlobalTranslation[1] * parentGlobalScale + parentGlobalPosition[1];

		return p;
	}

	/**
	 * Compute the object's position in world coordinates's and returns it as a Vector3.
	 * To use this similarly to the old {@link #getGlobalPosition()}, just grab the x and y
	 * properties from the returned Vector3.
	 *
	 * @return The world coordinates as a Vector3 (x,y,z)
	 */
	public Vector3 getGlobalPositionVector() {
		Vector3 parentGlobalPosition = ((this != GameObject.ROOT) ? myParent.getGlobalPositionVector() : new Vector3());
		Vector3 parentGlobalRotation = ((this != GameObject.ROOT) ? myParent.getGlobalRotationVector() : new Vector3());
		Vector3 parentGlobalScale = ((this != GameObject.ROOT) ? myParent.getGlobalScaleVector() : new Vector3(1.0, 1.0, 1.0));

		double[][] localPositionMatrix = MathUtil.translationMatrix(getPositionVector());

		// I didn't really know how to use these matrices effectively.
		double[][] parentGlobalRotationMatrix = MathUtil.rotationMatrixXYZ(parentGlobalRotation);

		double[][] rotatedTranslationMatrix = MathUtil.multiply4D(parentGlobalRotationMatrix, localPositionMatrix);

		// TODO: Use a matrix for this.
		Vector3 intermediateVector = MathUtil.translationMatrixToVector(rotatedTranslationMatrix);
		intermediateVector.multiplySelf(parentGlobalScale);

		Vector3 finalVector = intermediateVector.add(parentGlobalPosition);

		return finalVector;
	}

	/**
	 * Compute the object's rotation in the global coordinate frame
	 *
	 * @return the global rotation of the object (in degrees) and
	 * normalized to the range (-180, 180) degrees.
	 * @deprecated Use {@link #getGlobalRotationVector()}.z instead.
	 */
	public double getGlobalRotation() {
		// We start by grabbing the parent's rotation. If the parent doesn't exist, we set it as 0.
		double globalRotation = ((this != GameObject.ROOT) ? myParent.getGlobalRotation() : 0.0);

		// Then we add this object's rotation.
		globalRotation = MathUtil.normaliseAngle(globalRotation + myRotation.z);

		return globalRotation;
	}

	/**
	 * Compute the object's rotation in the global coordinate frame.
	 * To use this similarly to the old {@link #getGlobalRotation()}, just grab the z property.
	 *
	 * @return The global rotation of the object in a Vector3
	 */
	public Vector3 getGlobalRotationVector() {
		if (this == GameObject.ROOT) {
			return myRotation.clone();
		} else {
			double[][] globalRotationMatrix = MathUtil.rotationMatrixXYZ(myParent.getGlobalRotationVector());
			double[][] rotationMatrix = MathUtil.rotationMatrixXYZ(getRotationVector());

			double[][] multipliedMatrix = MathUtil.multiply4D(rotationMatrix, globalRotationMatrix);

			return MathUtil.rotationMatrixToVector(multipliedMatrix);
		}
	}

	/**
	 * Compute the object's scale in global terms
	 *
	 * @return the global scale of the object
	 *
	 * @deprecated Use {@link #getGlobalScaleVector()}.
	 */
	public double getGlobalScale() {
		// We start by grabbing the parent's scale. If the parent doesn't exist, we set it as 1.
		double globalScale = ((this != GameObject.ROOT) ? myParent.getGlobalScale() : 1.0);

		// Then we multiply this object's scale.
		globalScale *= myScale.x;

		return globalScale;
	}

	/**
	 * Computes the object's global scale.
	 * To use this similarly to the old {@link #getGlobalScale()}, grab the x property of the
	 * return. This only works the same if you've been scaling uniformly across the x and y axes.
	 *
	 * @return The global scale of the object in a Vector3.
	 */
	public Vector3 getGlobalScaleVector() {
		if (this == GameObject.ROOT) {
			return myScale.clone();
		} else {
			double[][] globalScaleMatrix = MathUtil.scaleMatrix(myParent.getGlobalScaleVector());
			double[][] scaleMatrix = MathUtil.scaleMatrix(getScaleVector());

			double[][] multipliedMatrix = MathUtil.multiply4D(scaleMatrix, globalScaleMatrix);

			return MathUtil.scaleMatrixToVector(multipliedMatrix);
		}
	}



	/**
	 * Change the parent of a game object.
	 *
	 * @param parent
	 */
	public void setParent(GameObject parent) {

		// This is the legacy code from before I moved to 3D.
		/*
		double[] globalPosition = getGlobalPosition();
		double globalRotation = getGlobalRotation();
		double globalScale = getGlobalScale();

		myParent.myChildren.remove(this);
		myParent = parent;
		myParent.myChildren.add(this);

		double[] parentGlobalPosition = ((this != GameObject.ROOT) ? myParent.getGlobalPosition() : new double[]{0, 0});
		double parentGlobalRotation = ((this != GameObject.ROOT) ? myParent.getGlobalRotation() : 0.0);
		double parentGlobalScale = ((this != GameObject.ROOT) ? myParent.getGlobalScale() : 1.0);

		myTranslation.x = ((globalPosition[0] - parentGlobalPosition[0]) * Math.cos(Math.toRadians(-parentGlobalRotation)) - (globalPosition[1] - parentGlobalPosition[1]) * Math.sin(Math.toRadians(-parentGlobalRotation))) / parentGlobalScale;
		myTranslation.y = ((globalPosition[0] - parentGlobalPosition[0]) * Math.sin(Math.toRadians(-parentGlobalRotation)) + (globalPosition[1] - parentGlobalPosition[1]) * Math.cos(Math.toRadians(-parentGlobalRotation))) / parentGlobalScale;

		myRotation.z = MathUtil.normaliseAngle(globalRotation - ((this != GameObject.ROOT) ? myParent.getGlobalRotation() : 0.0));
		myScale.y = myScale.x = globalScale / ((this != GameObject.ROOT) ? myParent.getGlobalScale() : 1.0);
		*/


		// This now uses 3D matrices.
		Vector3 globalPosition = getGlobalPositionVector();
		Vector3 globalRotation = getGlobalRotationVector();
		Vector3 globalScale = getGlobalScaleVector();

		myParent.myChildren.remove(this);
		myParent = parent;
		myParent.myChildren.add(this);

		Vector3 parentGlobalPosition = myParent.getGlobalPositionVector();
		Vector3 parentGlobalRotation = myParent.getGlobalRotationVector();
		Vector3 parentGlobalScale = myParent.getGlobalScaleVector();

		Vector3 parentGlobalRotationInverted = parentGlobalRotation.multiply(-1);

		Vector3 globalPositionDifference = globalPosition.subtract(parentGlobalPosition);

		double[][] parentGlobalRotationMatrix = MathUtil.rotationMatrixXYZ(parentGlobalRotationInverted);

		// TODO: Use a matrix for this.
		Vector3 globalPositionDifferenceScaled = globalPositionDifference.clone();
		globalPositionDifferenceScaled.multiplySelf(parentGlobalScale.invert());

		double[][] globalPositionDifferenceScaledMatrix = MathUtil.translationMatrix(globalPositionDifferenceScaled);

		double[][] globalRotatedMatrix = MathUtil.multiply4D(parentGlobalRotationMatrix, globalPositionDifferenceScaledMatrix);

		myTranslation = MathUtil.translationMatrixToVector(globalRotatedMatrix);

		myRotation = globalRotation.subtract(parentGlobalRotation);
		myScale = globalScale.divide(parentGlobalScale);
	}

	/**
	 * Returns whether the given point collides with this object.
	 * The z axis is ignored in this collision.
	 *
	 * @param p The global coordinate of the point.
	 * @return
	 */
	public boolean collides(double[] p) {
		return collides(new Vector3(p[0], p[1], 0.0));
	}

	/**
	 * Returns whether the given point collides with this object.
	 * The z axis is ignored in this collision.
	 *
	 * This should be overridden by any class that extends this and wants collisions.
	 * @param v The global coordinate of the point.
	 * @return
	 */
	public boolean collides(Vector3 v) {
		return false;
	}

}
