package ass1.asteroids;

import ass1.GameObject;
import ass1.LineGameObject;
import ass1.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * A character that uses LineGameObjects.
 * It's not very useful by itself, it's better with the AsteroidsString class.
 *
 * @author Thomas Moffet, z5061905
 */
public class AsteroidsChar extends GameObject {
	private static final double[] lineColor = new double[]{1.0, 1.0, 1.0, 1.0};

	private char letter;
	private List<LineGameObject> lines;

	/**
	 * Creates a new character.
	 * @param parent The parent object.
	 * @param letter The letter to be made.
	 */
	public AsteroidsChar(GameObject parent, char letter) {
		super(parent);
		this.letter = letter;
		lines = new ArrayList<LineGameObject>();
		createLetter(letter);
	}

	/**
	 * Chooses the letter and creates the LineGameObjects to create it.
	 * @param letter The letter to create.
	 */
	private void createLetter(char letter) {
		switch (letter) {
			case 'A':
				createA();
				break;
			case 'B':
				createB();
				break;
			case 'C':
				createC();
				break;
			case 'D':
				createD();
				break;
			case 'E':
				createE();
				break;
			case 'F':
				createF();
				break;
			case 'G':
				createG();
				break;
			case 'H':
				createH();
				break;
			case 'I':
				createI();
				break;
			case 'J':
				createJ();
				break;
			case 'K':
				createK();
				break;
			case 'L':
				createL();
				break;
			case 'M':
				createM();
				break;
			case 'N':
				createN();
				break;
			case 'O':
				createO();
				break;
			case 'P':
				createP();
				break;
			case 'Q':
				createQ();
				break;
			case 'R':
				createR();
				break;
			case 'S':
				createS();
				break;
			case 'T':
				createT();
				break;
			case 'U':
				createU();
				break;
			case 'V':
				createV();
				break;
			case 'W':
				createW();
				break;
			case 'X':
				createX();
				break;
			case 'Y':
				createY();
				break;
			case 'Z':
				createZ();
				break;
			case '0':
				create0();
				break;
			case '1':
				create1();
				break;
			case '2':
				create2();
				break;
			case '3':
				create3();
				break;
			case '4':
				create4();
				break;
			case '5':
				create5();
				break;
			case '6':
				create6();
				break;
			case '7':
				create7();
				break;
			case '8':
				create8();
				break;
			case '9':
				create9();
				break;
			default:
				break;
		}
	}

	private void createA() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.0), new Vector3(0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, -0.7), lineColor));
	}

	private void createB() {

	}

	private void createC() {
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(-0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(0.4, -0.7), lineColor));
	}

	private void createD() {

	}

	private void createE() {
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(-0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.0), new Vector3(0.4, 0.0), lineColor));
	}

	private void createF() {

	}

	private void createG() {
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(-0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, -0.7), new Vector3(0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.0, 0.0), new Vector3(0.4, 0.0), lineColor));
	}

	private void createH() {

	}

	private void createI() {
		lines.add(new LineGameObject(this, new Vector3(0.0, 0.7), new Vector3(0.0, -0.7), lineColor));
	}

	private void createJ() {

	}

	private void createK() {

	}

	private void createL() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(0.4, -0.7), lineColor));
	}

	private void createM() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.0, 0.7), new Vector3(0.0, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, -0.7), lineColor));
	}

	private void createN() {

	}

	private void createO() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, -0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(-0.4, 0.7), lineColor));
	}

	private void createP() {

	}

	private void createQ() {

	}

	private void createR() {
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.0), new Vector3(-0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(-0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.3, 0.0), new Vector3(0.3, -0.7), lineColor));
	}

	private void createS() {
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(-0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.0), new Vector3(0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.0), new Vector3(0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(0.4, -0.7), lineColor));
	}

	private void createT() {

	}

	private void createU() {

	}

	private void createV() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(0.4, 0.7), lineColor));
	}

	private void createW() {

	}

	private void createX() {

	}

	private void createY() {

	}

	private void createZ() {

	}

	private void create0() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, -0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(-0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(0.4, 0.7), lineColor));
	}

	private void create1() {
		lines.add(new LineGameObject(this, new Vector3(0.0, 0.7), new Vector3(0.0, -0.7), lineColor));
	}

	private void create2() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.0), new Vector3(-0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.0), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, -0.7), new Vector3(-0.4, -0.7), lineColor));
	}

	private void create3() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, -0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.0), new Vector3(-0.4, 0.0), lineColor));
	}

	private void create4() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.0), new Vector3(0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, -0.7), lineColor));
	}

	private void create5() {
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(-0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.0), new Vector3(0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.0), new Vector3(0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(0.4, -0.7), lineColor));
	}

	private void create6() {
		lines.add(new LineGameObject(this, new Vector3(0.4, -0.7), new Vector3(0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.0), new Vector3(-0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, -0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(-0.4, 0.7), lineColor));
	}

	private void create7() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, -0.7), lineColor));
	}

	private void create8() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, -0.7), new Vector3(-0.4, -0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, -0.7), new Vector3(-0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.0), new Vector3(0.4, 0.0), lineColor));
	}

	private void create9() {
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(-0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.0), new Vector3(0.4, 0.0), lineColor));
		lines.add(new LineGameObject(this, new Vector3(-0.4, 0.7), new Vector3(0.4, 0.7), lineColor));
		lines.add(new LineGameObject(this, new Vector3(0.4, 0.7), new Vector3(0.4, -0.7), lineColor));
	}
}
