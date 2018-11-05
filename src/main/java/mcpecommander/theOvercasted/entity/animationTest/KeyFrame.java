package mcpecommander.theOvercasted.entity.animationTest;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class KeyFrame {

	public Vector3f scale, rotation, translate;
	public final int num;

	public KeyFrame(int num) {
		this.num = num;
		this.scale = new Vector3f();
		this.translate = new Vector3f();
		this.rotation = new Vector3f();
	}

	public KeyFrame(Vector3f scale, Vector3f rotation, Vector3f translate, int num) {
		this.scale = scale;
		this.rotation = rotation;
		this.translate = translate;
		this.num = num;
	}

	public KeyFrame(Vector3f rotation, int num) {
		this.rotation = rotation;
		this.scale = new Vector3f();
		this.translate = new Vector3f();
		this.num = num;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getTranslate() {
		return translate;
	}

	public int getNum() {
		return num;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof KeyFrame && ((KeyFrame) obj).num == this.num;
	}

	@Override
	public String toString() {
		String str;
		str = "Scale:";
		str = str + scale.toString();

		str = str + ", Rotation:";
		str = str + rotation.toString();
		str = str + ", Translate:";
		str = str + translate.toString();
		str = str + ", Frame Number is " + num;
		return str;
	}

}
