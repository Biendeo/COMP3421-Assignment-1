package ass1;

import com.jogamp.opengl.GL2;

/**
 * A game object with a line shape.
 * 
 * @author Thomas Moffet
 */
public class LineGameObject extends GameObject {

    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double[] myLineColour;

    public LineGameObject(GameObject parent, double[] lineColour) {
        this(parent, 0.0, 0.0, 1.0, 0.0, lineColour);
    }
    
    public LineGameObject(GameObject parent, double x1, double y1, double x2, double y2, double[] lineColour) {
    	super(parent);
    	
    	this.x1 = x1;
    	this.y1 = y1;
    	this.x2 = x2;
    	this.y2 = y2;
    	
    	myLineColour = lineColour;
    	
    }

    public double getX1() {        
        return x1;
    }
    
    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {        
        return y1;
    }
    
    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {        
        return x2;
    }
    
    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {        
        return y2;
    }
    
    public void setY2(double y2) {
        this.y2 = y2;
    }

    /**
     * Get the outline colour.
     * 
     * @return
     */
    public double[] getLineColour() {
        return myLineColour;
    }

    /**
     * Set the outline colour.
     * 
     * Setting the colour to null means the outline should not be drawn
     * 
     * @param lineColour
     */
    public void setLineColour(double[] lineColour) {
        myLineColour = lineColour;
    }

    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    

    /**
     * TODO: Draw the polygon
     * 
     * if the fill colour is non-null, fill the polygon with this colour
     * if the line colour is non-null, draw the outline with this colour
     * 
     * @see ass1.spec.GameObject#drawSelf(javax.media.opengl.GL2)
     */
    @Override
    public void drawSelf(GL2 gl) {
        
    	if (myLineColour != null) {
	        gl.glColor4d(myLineColour[0], myLineColour[1], myLineColour[2], myLineColour[3]);
	        
	        gl.glBegin(GL2.GL_LINES);

        	gl.glVertex2d(x1, y1);
        	gl.glVertex2d(x2, y2);
	        
	        gl.glEnd();
    	}

    }


}
