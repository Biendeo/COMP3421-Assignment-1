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
     */
    public double getRotation() {
        return myRotation.z;
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
     * @deprecated Use {@link getScaleX} or something.
     */
    public double getScale() {
        return myScale.x;
    }

    /**
     * Set the local scale
     * 
     * @param scale
     * @deprecated Use {@link setScaleX} or something.
     */
    public void setScale(double scale) {
    	// TODO: Handle all scaling on separate axes.
        myScale.x = scale;
        myScale.y = scale;
    }

    /**
     * Multiply the scale of the object by the given factor
     * 
     * @param factor
     * @deprecated Use {@link scaleX} or something.
     */
    public void scale(double factor) {
    	// TODO: Handle all scaling on separate axes.
        myScale.x *= factor;
        myScale.y *= factor;
    }

    /**
     * Get the local position of the object 
     * 
     * @return
     * @deprecated Use {@link getPosition3} or something.
     */
    public double[] getPosition() {
    	// TODO: Make a Vector3 get.
        double[] t = new double[2];
        t[0] = myTranslation.x;
        t[1] = myTranslation.y;

        return t;
    }

    /**
     * Set the local position of the object
     * 
     * @param x
     * @param y
     */
    public void setPosition(double x, double y) {
    	// TODO: Make a Vector3 setPosition.
        myTranslation.x = x;
        myTranslation.y = y;
    }

    /**
     * Move the object by the specified offset in local coordinates
     * 
     * @param dx
     * @param dy
     */
    public void translate(double dx, double dy) {
    	// TODO: Make a Vector3 translation.
        myTranslation.x += dx;
        myTranslation.y += dy;
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

        // TODO: This.
        gl.glTranslated(myTranslation.x, myTranslation.y, 0.0);
        gl.glRotated(myRotation.z, 0.0, 0.0, 1.0);
        gl.glScaled(myScale.x, myScale.y, 0.0);
        
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
     * Compute the object's rotation in the global coordinate frame
     * 
     * TODO: Write this method
     * 
     * @return the global rotation of the object (in degrees) and 
     * normalized to the range (-180, 180) degrees. 
     */
    public double getGlobalRotation() {
    	// We start by grabbing the parent's rotation. If the parent doesn't exist, we set it as 0.
        double globalRotation = ((this != GameObject.ROOT) ? myParent.getGlobalRotation() : 0.0);
        
        // Then we add this object's rotation.
        globalRotation = MathUtil.normaliseAngle(globalRotation + myRotation.z);
        
        return globalRotation;
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

    /**
     * Change the parent of a game object.
     * 
     * TODO: add code so that the object does not change its global position, rotation or scale
     * when it is reparented. You may need to add code before and/or after 
     * the fragment of code that has been provided - depending on your approach
     * 
     * @param parent
     */
    public void setParent(GameObject parent) {
    	
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
