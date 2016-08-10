package ass1.math;

public class Vector3 {
	public double x;
	public double y;
	public double z;
	
	public Vector3() {
		this(0.0, 0.0, 0.0);
	}
	
	public Vector3(double x, double y) {
		this(x, y, 0.0);
	}
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3 clone() {
		Vector3 returnVector = new Vector3();
		returnVector.x = x;
		returnVector.y = y;
		returnVector.z = z;
		
		return returnVector;
	}
	
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		} else {
			Vector3 v = (Vector3)o;
			return (v.x == x && v.y == y && v.z == z);
		}
	}
	
	public Vector3 add(Vector3 v) {
		return add(this, v);
	}

	public Vector3 addSelf(Vector3 v) {
		Vector3 a = add(this, v);
		x = a.x;
		y = a.y;
		z = a.z;
		
		return this;
	}
	
	public static Vector3 add(Vector3 left, Vector3 right) {
		Vector3 returnVector = new Vector3();
		returnVector.x = left.x + right.x;
		returnVector.y = left.y + right.y;
		returnVector.z = left.z + right.z;
		
		return returnVector;
	}
	
	public Vector3 subtract(Vector3 v) {
		return subtract(this, v);
	}
	
	public Vector3 subtractSelf(Vector3 v) {
		Vector3 a = subtract(this, v);
		x = a.x;
		y = a.y;
		z = a.z;
		
		return this;
	}
	
	public static Vector3 subtract(Vector3 left, Vector3 right) {
		Vector3 returnVector = new Vector3();
		returnVector.x = left.x - right.x;
		returnVector.y = left.y - right.y;
		returnVector.z = left.z - right.z;
		
		return returnVector;
	}
	
	public Vector3 multiply(double s) {
		return multiply(this, s);
	}
	
	public Vector3 multiplySelf(double s) {
		Vector3 a = multiply(this, s);
		x = a.x;
		y = a.y;
		z = a.z;
		
		return this;
	}
	
	public static Vector3 multiply(Vector3 left, double right) {
		Vector3 returnVector = new Vector3();
		returnVector.x = left.x * right;
		returnVector.y = left.y * right;
		returnVector.z = left.z * right;
		
		return returnVector;
	}
	
	public Vector3 divide(double s) {
		return divide(this, s);
	}
	
	public Vector3 divideSelf(double s) {
		Vector3 a = divide(this, s);
		x = a.x;
		y = a.y;
		z = a.z;
		
		return this;
	}
	
	public static Vector3 divide(Vector3 left, double right) {
		Vector3 returnVector = new Vector3();
		returnVector.x = left.x / right;
		returnVector.y = left.y / right;
		returnVector.z = left.z / right;
		
		return returnVector;
	}
	
	public double modulus() {
		return Math.sqrt(x * x + y * y + z * z);
	}
}
