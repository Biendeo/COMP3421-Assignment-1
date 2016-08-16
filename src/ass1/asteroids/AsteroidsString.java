package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 16/08/2016.
 */
public class AsteroidsString extends GameObject {
	private String string;
	private boolean leftAlign;
	private boolean rightAlign;

	private List<AsteroidsChar> characters;

	public AsteroidsString(GameObject parent, String string, boolean leftAlign, boolean rightAlign) {
		super(parent);
		this.string = string;
		this.leftAlign = leftAlign;
		this.rightAlign = rightAlign;
		characters = new ArrayList<AsteroidsChar>();

		Vector3 translateOffset = new Vector3();

		if (!leftAlign && !rightAlign) {
			translateOffset.subtractSelf(new Vector3(1.0 * (string.length() - 1) / 2, 0.0));
		} else if (rightAlign) {
			translateOffset.subtractSelf(new Vector3(1.0 * (string.length() - 1), 0.0));
		}

		for (int i = 0; i < string.length(); ++i) {
			char c = string.charAt(i);
			AsteroidsChar newChar = new AsteroidsChar(this, c);
			newChar.translate(translateOffset);
			translateOffset.addSelf(new Vector3(1.0, 0.0));
		}
	}
}
