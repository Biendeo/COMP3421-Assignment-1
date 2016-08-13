package ass1.tests;

import ass1.CircularGameObject;
import ass1.GameObject;
import ass1.LineGameObject;
import ass1.PolygonalGameObject;
import ass1.math.Vector3;
import junit.framework.TestCase;
import org.junit.Test;

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

	@Test
	public void testLine0() {
		// This tests a line from (0.0, 0.0) to (1.0, 0.0).
		GameObject obj = new LineGameObject(GameObject.ROOT, new Vector3(0.0, 0.0), new Vector3(1.0, 0.0), null);

		Vector3 p = new Vector3(0.5, 0.0);

		assertTrue(obj.collides(p));

		p = new Vector3(0.5, 0.5);

		assertFalse(obj.collides(p));
	}

	@Test
	public void testLine1() {
		// This tests a line from (1.0, 1.0) to (2.0, 3.0).
		GameObject obj = new LineGameObject(GameObject.ROOT, new Vector3(1.0, 1.0), new Vector3(2.0, 3.0), null);

		Vector3 p = new Vector3(1.5, 2.0);

		assertTrue(obj.collides(p));

		p = new Vector3(3.0, 5.0);

		assertFalse(obj.collides(p));
	}

	@Test
	public void testPolygon0() {
		// This tests a square.
		GameObject obj = new PolygonalGameObject(GameObject.ROOT, new double[]{-1.0, -1.0, 1.0, -1.0, 1.0, 1.0, -1.0, 1.0}, null, null);

		Vector3 p = new Vector3(0.0, 0.0);

		assertTrue(obj.collides(p));

		p = new Vector3(2.0, 0.0);

		assertFalse(obj.collides(p));
	}

	@Test
	public void testPolygon1() {
		// This tests a triangle.
		GameObject obj = new PolygonalGameObject(GameObject.ROOT, new double[]{-1.0, -1.0, 1.0, -1.0, 0.0, 1.0}, null, null);

		Vector3 p = new Vector3(0.0, 0.0);

		assertTrue(obj.collides(p));

		p = new Vector3(1.0, 1.0);

		assertFalse(obj.collides(p));
	}
}
