package ass1.asteroids;

import ass1.GameObject;
import ass1.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to draw strings on the screen.
 *
 * @author Thomas Moffet, z5061905
 */
public class AsteroidsString extends GameObject {
	private String string;
	private boolean leftAlign;
	private boolean rightAlign;

	private List<AsteroidsChar> characters;

	/**
	 * Constructs a new string.
	 * @param parent The parent object.
	 * @param string The string to show.
	 * @param leftAlign Whether the string is left-aligned.
	 * @param rightAlign Whether the string is right-aligned (if it's not left-aligned).
	 */
	public AsteroidsString(GameObject parent, String string, boolean leftAlign, boolean rightAlign) {
		super(parent);
		this.string = string;
		this.leftAlign = leftAlign;
		this.rightAlign = rightAlign;
		characters = new ArrayList<AsteroidsChar>();

		createString();
	}

	/**
	 * Generates the letters for the string.
	 */
	private void createString() {
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
			characters.add(newChar);
		}
	}

	/**
	 * Changes the text in this string.
	 * @param string
	 */
	public void updateString(String string) {
		for (AsteroidsChar c : characters) {
			c.destroy();
		}
		characters.clear();
		this.string = string;
		createString();
	}
}
