package ass1.tests;

import org.junit.Test;

import ass1.GameObject;
import ass1.math.Vector3;
import junit.framework.TestCase;

/**
 * COMMENT: Comment GameObjectTest 
 *
 * @author malcolmr
 * 
 * 
 */
public class GameObjectTest extends TestCase {

    // to account for rounding errors on doubles, we will
    // test to within epsilon of the correct answer:
 
    private static final double EPSILON = 0.001;
    
    @Test
    public void testGlobal0() {
        GameObject obj = new GameObject(GameObject.ROOT);
        
        double[] p = obj.getGlobalPosition();
        double r = obj.getGlobalRotation();
        double s = obj.getGlobalScale();
        
        assertEquals(0, p[0], EPSILON);
        assertEquals(0, p[1], EPSILON);
        assertEquals(0, r, EPSILON);
        assertEquals(1, s, EPSILON);
    }
    
    @Test
    public void testGlobal0Vector() {
        GameObject obj = new GameObject(GameObject.ROOT);
        
        Vector3 p = obj.getGlobalPositionVector();
        Vector3 r = obj.getGlobalRotationVector();
        Vector3 s = obj.getGlobalScaleVector();
        
        assertEquals(0, p.x, EPSILON);
        assertEquals(0, p.y, EPSILON);
        assertEquals(0, r.z, EPSILON);
        assertEquals(1, s.x, EPSILON);
    }

    @Test
    public void testGlobal1() {
        GameObject obj = new GameObject(GameObject.ROOT);
        
        obj.translate(-2, 3);
        obj.rotate(90);
        obj.scale(2);
        
        double[] p = obj.getGlobalPosition();
        double r = obj.getGlobalRotation();
        double s = obj.getGlobalScale();
        
        assertEquals(-2, p[0], EPSILON);
        assertEquals(3, p[1], EPSILON);
        assertEquals(90, r, EPSILON);
        assertEquals(2, s, EPSILON);
    }

    @Test
    public void testGlobal1Vector() {
        GameObject obj = new GameObject(GameObject.ROOT);
        
        obj.translate(-2, 3);
        obj.rotate(90);
        obj.scale(2);
        
        Vector3 p = obj.getGlobalPositionVector();
        Vector3 r = obj.getGlobalRotationVector();
        Vector3 s = obj.getGlobalScaleVector();
        
        assertEquals(-2, p.x, EPSILON);
        assertEquals(3, p.y, EPSILON);
        assertEquals(90, r.z, EPSILON);
        assertEquals(2, s.x, EPSILON);
    }

    /**
     * Computing position is not simple a matter of adding up the origins. 
     * If the parent frame is rotated or scaled then the answer you get for 
     * the child will be wrong. 
     * 
     * You need to multiply the child's origin by the parent's model matrix 
     * to get the right answer.
     * 
     *  In this example the parent frame is rotated 90 and scaled by 2. 
     *  So the model matrix is:
     *  
     *  [ 0  -2  -2 ]
     *  [ 2   0   3 ]  
     *  [ 0   0   1 ]
     *  
     *  The i-axis is given by the 1st column: (0,2)
     *  The j-axis is given by the 2nd column: (-2,0)
     *  The origin is given by the 3rd column: (-2,3)
     *  
     *  The child is at (1,0) in the parent's frame, which is:
     *  
     *     phi + 1 * i + 0 * j = (-2,3) + 1 * (0,2) + 0 * (-2,0)
     *                         = (-2,5) in world coordinates
     *
     *        |(1,0)
     *      C +--
     *        |
     *    ----+ (-2, 3)
     *        P
     *            |
     *            +--
     *            W
     *            
     * W = world coordinate frame: origin = (0,0)   i = (1,0)     j = (0,1)
     * P = parent coordinate frame: origin = (-2,3) i = (0,2)     j = (-2, 0)  [in world coordinates]
     * C = child coordinate frame: origin = (1,0),  i = (0, -0.5) j = (0.5, 0) [in parent coordinates]
     *                             origin = (-2,5)  i = (1,0)     j = (0, 1)   [in world coordinates]
     */
    @Test
    public void testGlobal2() {
        GameObject parent = new GameObject(GameObject.ROOT);
        GameObject child = new GameObject(parent);
        
        parent.translate(-2, 3);
        parent.rotate(90);
        parent.scale(2);
        
        // the child is also moved:
        
        double[] p = child.getGlobalPosition();
        double r = child.getGlobalRotation();
        double s = child.getGlobalScale();
        
        assertEquals(-2, p[0], EPSILON);
        assertEquals(3, p[1], EPSILON);
        assertEquals(90, r, EPSILON);
        assertEquals(2, s, EPSILON);        
        
        // now move the child in its new coordinate frame
        
        child.translate(1, 0);
        child.rotate(-90);
        child.scale(0.5);

        p = child.getGlobalPosition();
        r = child.getGlobalRotation();
        s = child.getGlobalScale();
        
        assertEquals(-2, p[0], EPSILON);
        assertEquals(5, p[1], EPSILON);
        assertEquals(0, r, EPSILON);
        assertEquals(1, s, EPSILON);

        // the parent is not affected
        
        p = parent.getGlobalPosition();
        r = parent.getGlobalRotation();
        s = parent.getGlobalScale();
        
        assertEquals(-2, p[0], EPSILON);
        assertEquals(3, p[1], EPSILON);
        assertEquals(90, r, EPSILON);
        assertEquals(2, s, EPSILON);

    }
    
    @Test
    public void testGlobal2Vector() {
        GameObject parent = new GameObject(GameObject.ROOT);
        GameObject child = new GameObject(parent);
        
        parent.translate(-2, 3);
        parent.rotate(90);
        parent.scale(2);
        
        // the child is also moved:
        
        Vector3 p = child.getGlobalPositionVector();
        Vector3 r = child.getGlobalRotationVector();
        Vector3 s = child.getGlobalScaleVector();
        
        assertEquals(-2, p.x, EPSILON);
        assertEquals(3, p.y, EPSILON);
        assertEquals(90, r.z, EPSILON);
        assertEquals(2, s.x, EPSILON);        
        
        // now move the child in its new coordinate frame
        
        child.translate(1, 0);
        child.rotate(-90);
        child.scale(0.5);

        p = child.getGlobalPositionVector();
        r = child.getGlobalRotationVector();
        s = child.getGlobalScaleVector();
        
        assertEquals(-2, p.x, EPSILON);
        assertEquals(5, p.y, EPSILON);
        assertEquals(0, r.z, EPSILON);
        assertEquals(1, s.x, EPSILON);

        // the parent is not affected
        
        p = parent.getGlobalPositionVector();
        r = parent.getGlobalRotationVector();
        s = parent.getGlobalScaleVector();
        
        assertEquals(-2, p.x, EPSILON);
        assertEquals(3, p.y, EPSILON);
        assertEquals(90, r.z, EPSILON);
        assertEquals(2, s.x, EPSILON);

    }

    @Test
    public void testSetParent0() {
        GameObject obj1 = new GameObject(GameObject.ROOT);
        GameObject obj2 = new GameObject(GameObject.ROOT);
        
        assertSame(GameObject.ROOT, obj1.getParent());
        assertTrue(obj1.getChildren().isEmpty());
        
        assertSame(GameObject.ROOT, obj2.getParent());
        assertTrue(obj2.getChildren().isEmpty());
        
        obj1.translate(1, 1);
        obj1.rotate(90);
        obj1.scale(2);
        
        obj2.setParent(obj1);

        // obj2's global coordinate frame should not be changed
        
        double[] p = obj2.getGlobalPosition();
        double r = obj2.getGlobalRotation();
        double s = obj2.getGlobalScale();
        
        assertEquals(0, p[0], EPSILON);
        assertEquals(0, p[1], EPSILON);
        assertEquals(0, r, EPSILON);
        assertEquals(1, s, EPSILON);        

        // obj2's local coordinate frame is adjusted to suit
        
        p = obj2.getPosition();
        r = obj2.getRotation();
        s = obj2.getScale();
        
        assertEquals(-0.5, p[0], EPSILON);
        assertEquals(0.5, p[1], EPSILON);
        assertEquals(-90, r, EPSILON);
        assertEquals(0.5, s, EPSILON);        

        // obj1's local coordinate frame is not affected
        
        p = obj1.getPosition();
        r = obj1.getRotation();
        s = obj1.getScale();
        
        assertEquals(1, p[0], EPSILON);
        assertEquals(1, p[1], EPSILON);
        assertEquals(90, r, EPSILON);
        assertEquals(2, s, EPSILON);        

    }
    
    @Test
    public void testSetParent0Vector() {
        GameObject obj1 = new GameObject(GameObject.ROOT);
        GameObject obj2 = new GameObject(GameObject.ROOT);
        
        assertSame(GameObject.ROOT, obj1.getParent());
        assertTrue(obj1.getChildren().isEmpty());
        
        assertSame(GameObject.ROOT, obj2.getParent());
        assertTrue(obj2.getChildren().isEmpty());
        
        obj1.translate(1, 1);
        obj1.rotate(90);
        obj1.scale(2);
        
        obj2.setParent(obj1);

        // obj2's global coordinate frame should not be changed
        
        Vector3 p = obj2.getGlobalPositionVector();
        Vector3 r = obj2.getGlobalRotationVector();
        Vector3 s = obj2.getGlobalScaleVector();
        
        assertEquals(0, p.x, EPSILON);
        assertEquals(0, p.y, EPSILON);
        assertEquals(0, r.z, EPSILON);
        assertEquals(1, s.x, EPSILON);        

        // obj2's local coordinate frame is adjusted to suit
        
        p = obj2.getPositionVector();
        r = obj2.getRotationVector();
        s = obj2.getScaleVector();
        
        assertEquals(-0.5, p.x, EPSILON);
        assertEquals(0.5, p.y, EPSILON);
        assertEquals(-90, r.z, EPSILON);
        assertEquals(0.5, s.x, EPSILON);        

        // obj1's local coordinate frame is not affected
        
        p = obj1.getPositionVector();
        r = obj1.getRotationVector();
        s = obj1.getScaleVector();
        
        assertEquals(1, p.x, EPSILON);
        assertEquals(1, p.y, EPSILON);
        assertEquals(90, r.z, EPSILON);
        assertEquals(2, s.x, EPSILON);        

    }
    
    @Test
    public void testSetParent1() {
    	GameObject obj1 = new GameObject(GameObject.ROOT);
        GameObject obj2 = new GameObject(GameObject.ROOT);
        GameObject obj3 = new GameObject(obj1);
        
        assertSame(GameObject.ROOT, obj1.getParent());
        assertTrue(!obj1.getChildren().isEmpty());
        
        assertSame(GameObject.ROOT, obj2.getParent());
        assertTrue(obj2.getChildren().isEmpty());
        
        obj1.translate(1, 0);
        obj1.rotate(90);
        obj1.scale(1);
        
        obj2.translate(-1, -1);
        obj2.rotate(-45);
        obj2.scale(2);
       
        obj3.translate(1, 0);
        obj3.rotate(60);
        obj3.scale(4);

        //Test obj1
        double [] gp = obj1.getGlobalPosition();
        double gr = obj1.getGlobalRotation();
        double gs = obj1.getGlobalScale();
                
        Vector3 p = obj1.getPositionVector();
        Vector3 r = obj1.getRotationVector();
        Vector3 s = obj1.getScaleVector();
        
        assertEquals(1, gp[0], EPSILON);
        assertEquals(0, gp[1], EPSILON);
        assertEquals(90, gr, EPSILON);
        assertEquals(1, gs, EPSILON);     
        
        assertEquals(1, p.x, EPSILON);
        assertEquals(0, p.y, EPSILON);
        assertEquals(90, r.z, EPSILON);
        assertEquals(1, s.x, EPSILON);     
        
        //Test obj2
        gp = obj2.getGlobalPosition();
        gr = obj2.getGlobalRotation();
        gs = obj2.getGlobalScale();
                
        p = obj2.getPositionVector();
        r = obj2.getRotationVector();
        s = obj2.getScaleVector();
        
        assertEquals(-1, gp[0], EPSILON);
        assertEquals(-1, gp[1], EPSILON);
        assertEquals(-45, gr, EPSILON);
        assertEquals(2, gs, EPSILON);     
        
        assertEquals(-1, p.x, EPSILON);
        assertEquals(-1, p.y, EPSILON);
        assertEquals(-45, r.z, EPSILON);
        assertEquals(2, s.x, EPSILON);     
        
       
        //Test obj3
        gp = obj3.getGlobalPosition();
        gr = obj3.getGlobalRotation();
        gs = obj3.getGlobalScale();
                
        p = obj3.getPositionVector();
        r = obj3.getRotationVector();
        s = obj3.getScaleVector();
        
        assertEquals(1, gp[0], EPSILON);
        assertEquals(1, gp[1], EPSILON);
        assertEquals(150, gr, EPSILON);
        assertEquals(4, gs, EPSILON);     
        
        assertEquals(1, p.x, EPSILON);
        assertEquals(0, p.y, EPSILON);
        assertEquals(60, r.z, EPSILON);
        assertEquals(4, s.x, EPSILON);     
       
        //Change obj3
        obj3.setParent(obj2);
        
        // obj3's global coordinate frame should not be changed
        gp = obj3.getGlobalPosition();
        gr = obj3.getGlobalRotation();
        gs = obj3.getGlobalScale();
        
        assertEquals(1, gp[0], EPSILON);
        assertEquals(1, gp[1], EPSILON);
        assertEquals(150, gr, EPSILON);
        assertEquals(4, gs, EPSILON);        

        // obj2's local coordinate frame is adjusted to suit
        
        p = obj3.getPositionVector();
        r = obj3.getRotationVector();
        s = obj3.getScaleVector();
        
        assertEquals(0, p.x, EPSILON);
        assertEquals(1.41421, p.y, EPSILON);
        //195 normalized is -165
        assertEquals(-165, r.z, EPSILON);
        assertEquals(2, s.x, EPSILON);        

        // obj1's local coordinate frame is not affected
        
        p = obj1.getPositionVector();
        r = obj1.getRotationVector();
        s = obj1.getScaleVector();
        
        assertEquals(1, p.x, EPSILON);
        assertEquals(0, p.y, EPSILON);
        assertEquals(90, r.z, EPSILON);
        assertEquals(1, s.x, EPSILON);      
        
    }
    
    @Test
    public void testSetParent1Vector() {
    	GameObject obj1 = new GameObject(GameObject.ROOT);
        GameObject obj2 = new GameObject(GameObject.ROOT);
        GameObject obj3 = new GameObject(obj1);
        
        assertSame(GameObject.ROOT, obj1.getParent());
        assertTrue(!obj1.getChildren().isEmpty());
        
        assertSame(GameObject.ROOT, obj2.getParent());
        assertTrue(obj2.getChildren().isEmpty());
        
        obj1.translate(1, 0);
        obj1.rotate(90);
        obj1.scale(1);
        
        obj2.translate(-1, -1);
        obj2.rotate(-45);
        obj2.scale(2);
       
        obj3.translate(1, 0);
        obj3.rotate(60);
        obj3.scale(4);

        //Test obj1
        Vector3 gp = obj1.getGlobalPositionVector();
        Vector3 gr = obj1.getGlobalRotationVector();
        Vector3 gs = obj1.getGlobalScaleVector();
                
        Vector3 p = obj1.getPositionVector();
        Vector3 r = obj1.getRotationVector();
        Vector3 s = obj1.getScaleVector();
        
        assertEquals(1, gp.x, EPSILON);
        assertEquals(0, gp.y, EPSILON);
        assertEquals(90, gr.z, EPSILON);
        assertEquals(1, gs.x, EPSILON);     
        
        assertEquals(1, p.x, EPSILON);
        assertEquals(0, p.y, EPSILON);
        assertEquals(90, r.z, EPSILON);
        assertEquals(1, s.x, EPSILON);     
        
        //Test obj2
        gp = obj2.getGlobalPositionVector();
        gr = obj2.getGlobalRotationVector();
        gs = obj2.getGlobalScaleVector();
                
        p = obj2.getPositionVector();
        r = obj2.getRotationVector();
        s = obj2.getScaleVector();
        
        assertEquals(-1, gp.x, EPSILON);
        assertEquals(-1, gp.y, EPSILON);
        assertEquals(-45, gr.z, EPSILON);
        assertEquals(2, gs.x, EPSILON);     
        
        assertEquals(-1, p.x, EPSILON);
        assertEquals(-1, p.y, EPSILON);
        assertEquals(-45, r.z, EPSILON);
        assertEquals(2, s.x, EPSILON);     
        
       
        //Test obj3
        gp = obj3.getGlobalPositionVector();
        gr = obj3.getGlobalRotationVector();
        gs = obj3.getGlobalScaleVector();
                
        p = obj3.getPositionVector();
        r = obj3.getRotationVector();
        s = obj3.getScaleVector();
        
        assertEquals(1, gp.x, EPSILON);
        assertEquals(1, gp.y, EPSILON);
        assertEquals(150, gr.z, EPSILON);
        assertEquals(4, gs.x, EPSILON);     
        
        assertEquals(1, p.x, EPSILON);
        assertEquals(0, p.y, EPSILON);
        assertEquals(60, r.z, EPSILON);
        assertEquals(4, s.x, EPSILON);     
       
        //Change obj3
        obj3.setParent(obj2);
        
        // obj3's global coordinate frame should not be changed
        gp = obj3.getGlobalPositionVector();
        gr = obj3.getGlobalRotationVector();
        gs = obj3.getGlobalScaleVector();
        
        assertEquals(1, gp.x, EPSILON);
        assertEquals(1, gp.y, EPSILON);
        assertEquals(150, gr.z, EPSILON);
        assertEquals(4, gs.x, EPSILON);        

        // obj2's local coordinate frame is adjusted to suit
        
        p = obj3.getPositionVector();
        r = obj3.getRotationVector();
        s = obj3.getScaleVector();
        
        assertEquals(0, p.x, EPSILON);
        assertEquals(1.41421, p.y, EPSILON);
        //195 normalized is -165
        assertEquals(-165, r.z, EPSILON);
        assertEquals(2, s.x, EPSILON);        

        // obj1's local coordinate frame is not affected
        
        p = obj1.getPositionVector();
        r = obj1.getRotationVector();
        s = obj1.getScaleVector();
        
        assertEquals(1, p.x, EPSILON);
        assertEquals(0, p.y, EPSILON);
        assertEquals(90, r.z, EPSILON);
        assertEquals(1, s.x, EPSILON);      
        
    }
    
}
