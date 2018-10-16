package mcpecommander.theOvercasted.entity.animationTest;

import javax.vecmath.Vector3d;

import mcpecommander.theOvercasted.entity.models.ModelOvercastedRenderer;

public class KeyFrame {

	private final Vector3d scale, rotation, translate;
	private final int num;

	public KeyFrame(Vector3d scale, Vector3d rotation, Vector3d translate, int num) {
		this.scale = scale;
		this.rotation = rotation;
		this.translate = translate;
		this.num = num;
	}

	public KeyFrame(Vector3d rotation, int num) {
		this.rotation = rotation;
		this.scale = new Vector3d(1, 1, 1);
		this.translate = new Vector3d();
		this.num = num;
	}

	public Vector3d getScale() {
		return scale;
	}

	public Vector3d getRotation() {
		return rotation;
	}

	public Vector3d getTranslate() {
		return translate;
	}

	public int getNum() {
		return num;
	}

	@Override
	public String toString() {
		return "Scale: " + scale.toString() + ", Rotation: " + rotation.toString() + ", Translate: "
				+ translate.toString() + ", Frame Number is " + num;
	}

}
