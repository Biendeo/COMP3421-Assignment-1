package ass1.tests;

import ass1.CircularGameObject;
import ass1.GameObject;
import ass1.math.Vector3;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Thomas on 13/08/2016.
 */
public class HitboxTest extends TestCase {

	@Test
	public void testCircular0() {
		// This tests the point (0.0, 0.0) in a circle at origin with radius 1.0.
		GameObject obj = new CircularGameObject(GameObject.ROOT, 1.0, null, null);

		Vector3 p = new Vector3(0.0, 0.0);

		assertTrue(obj.collides(p));
	}

	@Test
	public void testCircular1() {
		// This tests the point (0.0, 0.0) in a circle at (2.0, 0.0) with radius 1.0.
		GameObject obj = new CircularGameObject(GameObject.ROOT, 1.0, null, null);
		obj.translate(new Vector3(2.0, 0.0));

		Vector3 p = new Vector3(0.0, 0.0);

		assertFalse(obj.collides(p));

		p = new Vector3(2.0, 0.0);

		assertTrue(obj.collides(p));
	}

	@Test
	public void testCircular2() {
		// This tests the point (0.0, 0.0) in an offset parent object.
		GameObject parent = new GameObject(GameObject.ROOT);
		GameObject obj = new CircularGameObject(parent, 1.0, null, null);
		parent.translate(new Vector3(1.0, 1.0));
		obj.translate(new Vector3(2.0, 0.0));

		Vector3 p = new Vector3(2.5, 1.0);

		assertTrue(obj.collides(p));

		p = new Vector3(2.5, 2.0);
		assertFalse(obj.collides(p));
	}
}
