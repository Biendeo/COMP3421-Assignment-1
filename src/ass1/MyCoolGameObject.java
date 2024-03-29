package ass1;

import com.jogamp.opengl.GL2;

import ass1.math.Vector3;

public class MyCoolGameObject extends CircularGameObject {

	private static final double[] leftEarVertices = {-0.2, 0.0, 0.2, 0.0, 0.0, 0.5};
	private static final double[] rightEarVertices = {-0.2, 0.0, 0.2, 0.0, 0.0, 0.5};
	private static final double[] eyeColor = {0.0, 0.0, 0.0, 1.0};
	
	private PolygonalGameObject leftEar;
	private PolygonalGameObject rightEar;
	private CircularGameObject leftEye;
	private CircularGameObject rightEye;

	/**
	 * MyCoolGameObject is a little monster face. It has two pointy ears and eyes. With the
	 * z-buffer implemented, it can spin around to hide its face.
	 * @param parent The parent GameObject.
	 * @param fillColor The filled colour used.
	 * @param outlineColor The outline colour used.
	 */
	public MyCoolGameObject(GameObject parent, double[] fillColor, double[] outlineColor) {
		super(parent, 0.7, fillColor, outlineColor);

		leftEar = new PolygonalGameObject(this, leftEarVertices, fillColor, outlineColor);
		leftEar.translate(new Vector3(-0.4, 0.4, 0.05));

		rightEar = new PolygonalGameObject(this, rightEarVertices, fillColor, outlineColor);
		rightEar.translate(new Vector3(0.4, 0.4, 0.05));

		leftEye = new CircularGameObject(this, eyeColor, null);
		leftEye.translate(new Vector3(-0.25, 0.2, 0.05));
		leftEye.scale(0.2);

		rightEye = new CircularGameObject(this, eyeColor, null);
		rightEye.translate(new Vector3(0.25, 0.2, 0.05));
		rightEye.scale(0.2);
	}

	@Override
	public void update(double dt) {
		// TODO: I noticed this seems to rotate the X, Y and Z axes separately. Fix this.
		// This just makes this spin along the y-axis.
		rotate(new Vector3(0.0, dt * 50.0, 0.0));
	}


}
