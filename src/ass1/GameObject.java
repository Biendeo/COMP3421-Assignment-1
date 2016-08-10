package ass1;

import java.util.ArrayList;
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
 * TODO: The methods you need to complete are at the bottom of the class
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
    //myRotation should be normalised to the range (-180..180)
    private Vector3 myRotation;
    private Vector3 myScale;
    private Vector3 myTranslation;
    
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
        for (GameObject child : myChildren) {
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
    	return myRotation.clone();
    }

    /**
     * Set the local rotation (in degrees)
     * 
     * @return
     */
    public void setRotation(double rotation) {
        myRotation.z = MathUtil.normaliseAngle(rotation);
    }
    
    /**
     * Set the local rotation (in degrees)
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
     */
    public void rotate(double angle) {
        myRotation.z += angle;
        myRotation.z = MathUtil.normaliseAngle(myRotation.z);
    }

    /**
     * Get the local scale
     * 
     * @return
     * @deprecated Use {@link #getScaleVector()}.
     */
    public double getScale() {
        return myScale.x;
    }
    
    /**
     * Get the local scale.
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
     * Set the local scale
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
        myScale.x *= factor;
        myScale.y *= factor;
    }
    
    /**
     * Multiply the scale of the object by the given factor
     * 
     * @param factor
     */
    public void scale(Vector3 factor) {
    	myScale.x *= factor.x;
    	myScale.y *= factor.y;
    	myScale.z *= factor.z;
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
     * Get the local position of the object
     * 
     * @return
     */
    public Vector3 getPositionVector() {
    	return myTranslation.clone();
    }

    /**
     * Set the local position of the object
     * 
     * @param x
     * @param y
     */
    public void setPosition(double x, double y) {
        myTranslation.x = x;
        myTranslation.y = y;
    }
    
    /**
     * Set the local position of the object
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
     * Move the object by the specified offset in local coordinates
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

    
    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    
    /**
     * Draw the object and all of its descendants recursively.
     * 
     * TODO: Complete this method
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
     * TODO: Write this method
     * 
     * @return a point in world coordinats in [x,y] form
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
     * Compute the object's position
     * @return
     */
    public Vector3 getGlobalPositionVector3() {
    	// TODO: This.
    	return null;
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
     * 
     * @return The global rotation of the object (in an Euler angle Vector3) and
     * normalized to the range given by Java's math libraries.
     */
    public Vector3 getGlobalRotationVector() {
    	if (this == GameObject.ROOT) {
    		return myRotation.clone();
    	} else {
    		double[][] globalRotationMatrix = MathUtil.rotationMatrixXYZ(myParent.getRotationVector());
    		double[][] rotationMatrix = MathUtil.rotationMatrixXYZ(getRotationVector());
    		
    		double[][] multipliedMatrix = MathUtil.multiply4D(rotationMatrix, globalRotationMatrix);
    		
    		// These will be in radians.
    		if (multipliedMatrix[0][0] == 1.0 || multipliedMatrix[0][0] == -1.0) {
    			double x = 0;
    			double y = 0;
    			double z = Math.atan2(multipliedMatrix[0][2], multipliedMatrix[2][3]);
    			return new Vector3(x, y, z);
    		} else {
    			double x = Math.atan2(-multipliedMatrix[1][2], multipliedMatrix[1][1]);
    			double y = Math.asin(multipliedMatrix[1][0]);
    			double z = Math.atan2(-multipliedMatrix[2][0], multipliedMatrix[0][0]);
    			return new Vector3(x, y, z);
    		}
    	}
    }

    /**
     * Compute the object's scale in global terms
     * 
     * TODO: Write this method
     * 
     * @return the global scale of the object 
     */
    public double getGlobalScale() {
    	// We start by grabbing the parent's scale. If the parent doesn't exist, we set it as 1.
        double globalScale = ((this != GameObject.ROOT) ? myParent.getGlobalScale() : 1.0);
        
        // Then we multiply this object's scale.
        globalScale *= myScale.x;
        
        return globalScale;
    }
    
    public Vector3 getGlobalScaleVector() {
    	// TODO: This.
    	return null;
    }
    
    

    /**
     * Change the parent of a game object.
     * 
     * @param parent
     */
    public void setParent(GameObject parent) {
    	
    	// TODO: Change this to Vector3.
    	
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
        
    }
    

}
