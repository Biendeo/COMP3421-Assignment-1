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
     * @deprecated Use {@link #rotate(Vector3)} instead.
     */
    public void rotate(double angle) {
        myRotation.z += angle;
        myRotation.z = MathUtil.normaliseAngle(myRotation.z);
    }
    
    /**
     * Rotate the object by the given angle (in degrees)
     * 
     * @param angle
     */
    public void rotate(Vector3 angle) {
    	myRotation.addSelf(angle);
    	//myRotation = MathUtil.rotationMatrixToVector(MathUtil.multiply4D(MathUtil.rotationMatrixXYZ(angle), MathUtil.rotationMatrixXYZ(myRotation)));
    }

    /**
     * Get the local scale
     * 
     * @return
     * @deprecated Use {@link #getScaleVector()} instead.
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
        myScale.multiplySelf(factor);
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
     * Compute the object's position in world coordinates
     * 
     * @return The world coordinates as a Vector3 (x,y,z)
     */
    public Vector3 getGlobalPositionVector() {
    	Vector3 parentGlobalPosition = ((this != GameObject.ROOT) ? myParent.getGlobalPositionVector() : new Vector3());
    	Vector3 parentGlobalRotation = ((this != GameObject.ROOT) ? myParent.getGlobalRotationVector() : new Vector3());
    	Vector3 parentGlobalScale = ((this != GameObject.ROOT) ? myParent.getGlobalScaleVector() : new Vector3(1.0, 1.0, 1.0));
    	
    	double[][] localPositionMatrix = MathUtil.translationMatrix(getPositionVector());
    	
    	// I didn't really know how to use these matrices effectively.
    	double[][] parentGlobalPositionMatrix = MathUtil.translationMatrix(parentGlobalPosition);
    	double[][] parentGlobalRotationMatrix = MathUtil.rotationMatrixXYZ(parentGlobalRotation);
    	double[][] parentGlobalScaleMatrix = MathUtil.scaleMatrix(parentGlobalScale);
    	
    	double[][] rotatedTranslationMatrix = MathUtil.multiply4D(parentGlobalRotationMatrix, localPositionMatrix);
    	//double[][] scaledMatrix = MathUtil.multiply4D(rotatedTranslationMatrix, parentGlobalScaleMatrix);
    	
    	Vector3 intermediateVector = MathUtil.translationMatrixToVector(rotatedTranslationMatrix);
    	intermediateVector.x *= parentGlobalScale.x;
    	intermediateVector.y *= parentGlobalScale.y;
    	intermediateVector.z *= parentGlobalScale.z;
    	
    	Vector3 finalVector = intermediateVector.add(parentGlobalPosition);
    	
    	
    	
    	/*
    	double[][] parentGlobalRotationMatrix = MathUtil.rotationMatrixXYZ(parentGlobalRotation);
    	
    	double[][] newGlobalTranslationScaled = MathUtil.multiply4D(MathUtil.translationMatrix(getPositionVector()), MathUtil.scaleMatrix(parentGlobalScale));
    	
    	Vector3 referenceScale = MathUtil.translationMatrixToVector(newGlobalTranslationScaled);
    	
    	double[][] newGlobalTranslationRotated = MathUtil.multiply4D(parentGlobalRotationMatrix, newGlobalTranslationScaled);
    	
    	Vector3 newGlobalTranslationScaledVector = MathUtil.translationMatrixToVector(newGlobalTranslationRotated);
    	
    	Vector3 finalTranslation = newGlobalTranslationScaledVector.add(parentGlobalPosition);
    	*/
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
     * 
     * @return The global rotation of the object (in an Euler angle Vector3) and
     * normalized to the range given by Java's math libraries.
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
    	
    	// This is legacy from before I moved to 3D.
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
    	
    	// This uses 3D matrices.
        Vector3 globalPosition = getGlobalPositionVector();
        Vector3 globalRotation = getGlobalRotationVector();
        Vector3 globalScale = getGlobalScaleVector();
        
        myParent.myChildren.remove(this);
        myParent = parent;
        myParent.myChildren.add(this);
        
        Vector3 parentGlobalPosition = myParent.getGlobalPositionVector();
        Vector3 parentGlobalRotation = myParent.getGlobalRotationVector();
        Vector3 parentGlobalScale = myParent.getGlobalScaleVector();
        
        Vector3 globalPositionDifference = globalPosition.subtract(parentGlobalPosition);
        
        double[][] globalPositionDifferenceMatrix = MathUtil.translationMatrix(globalPositionDifference);

        double[][] parentGlobalRotationMatrix = MathUtil.rotationMatrixXYZ(parentGlobalRotation);
        double[][] parentGlobalScaleMatrix = MathUtil.scaleMatrix(parentGlobalScale);
        
        double[][] globalScaledMatrix = MathUtil.multiply4D(parentGlobalScaleMatrix, globalPositionDifferenceMatrix);
        
        double[][] globalScaledRotatedMatrix = MathUtil.multiply4D(parentGlobalRotationMatrix, globalScaledMatrix);
        
        myTranslation = MathUtil.translationMatrixToVector(globalScaledRotatedMatrix);
        
        myRotation = globalRotation.subtract(parentGlobalRotation);
        myScale.x = globalScale.x / parentGlobalScale.x;
        myScale.y = globalScale.y / parentGlobalScale.y;
        myScale.z = globalScale.z / parentGlobalScale.z;
    }
    

}
