package mcpecommander.theOvercasted.entity.animationTest;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Nullable;
import javax.vecmath.Vector3d;

import mcpecommander.theOvercasted.entity.models.ModelOvercastedRenderer;

public class Animation {
	
	private final boolean looped;
	private final int length;
	private final SortedMap[] maps;
	public boolean hasLooped;
	
	private Animation(boolean looped, int length, SortedMap... maps) {
		this.looped = looped;
		this.length = length;
		this.maps = maps;
	}
	
	public static Animation BuildAnimation(boolean looped, int length, SortedMap... animations) {
		return new Animation(looped, length, animations);
	}
	
	@Nullable
	public static SortedMap createAnimation(int length ,KeyFrame... frames ) {
		SortedMap<Integer, KeyFrame> animation = new TreeMap<>();
		for(KeyFrame frame : frames) {
			if(frame.getNum() >= length || frame.getNum() < 0) {
				return null;
			}
			animation.putIfAbsent(frame.getNum(), frame);
		}
		ListIterator<Integer> it = new ArrayList<>(animation.keySet()).listIterator();
		SortedMap<Integer, KeyFrame> temp = new TreeMap<>();
		while(it.hasNext()) {
			int currentFrame = (int) it.next();
			for(int i = 0; i < length; ++i) {
				if(i <= currentFrame) {
					continue;
				}else {
					if(it.hasNext()) {
						int nextFrame = (int) it.next();
						for(int j = currentFrame + 1; j < nextFrame; j ++) {
							double t  = ((double)j - (double)currentFrame)/((double)nextFrame - (double)currentFrame);
							Vector3d interpolatedScale = new Vector3d();
							interpolatedScale.x = linearLerp(animation.get(currentFrame).getScale().x, animation.get(nextFrame).getScale().x, t);
							interpolatedScale.y = linearLerp(animation.get(currentFrame).getScale().y, animation.get(nextFrame).getScale().y, t);
							interpolatedScale.z = linearLerp(animation.get(currentFrame).getScale().z, animation.get(nextFrame).getScale().z, t);
							Vector3d interpolatedTranslate = new Vector3d();
							interpolatedTranslate.x = linearLerp(animation.get(currentFrame).getTranslate().x, animation.get(nextFrame).getTranslate().x, t);
							interpolatedTranslate.y = linearLerp(animation.get(currentFrame).getTranslate().y, animation.get(nextFrame).getTranslate().y, t);
							interpolatedTranslate.z = linearLerp(animation.get(currentFrame).getTranslate().z, animation.get(nextFrame).getTranslate().z, t);
							Vector3d interpolatedRotation = new Vector3d();
							interpolatedRotation.x = rotationalInterpolation(animation.get(currentFrame).getRotation().x, animation.get(nextFrame).getRotation().x, t);
							interpolatedRotation.y = rotationalInterpolation(animation.get(currentFrame).getRotation().y, animation.get(nextFrame).getRotation().y, t);
							interpolatedRotation.z = rotationalInterpolation(animation.get(currentFrame).getRotation().z, animation.get(nextFrame).getRotation().z, t);
							KeyFrame interpolatedFrame = new KeyFrame(interpolatedScale, interpolatedRotation, interpolatedTranslate, j);
							temp.put(j, interpolatedFrame);
						}
						it.previous();
					}else {
						if(length == currentFrame) {
							break;
						}
						for(int j = currentFrame + 1; j < length; j ++) {
							double t  = ((double)j - (double)currentFrame)/((double)length - (double)currentFrame);
							Vector3d interpolatedScale = new Vector3d();
							interpolatedScale.x = linearLerp(animation.get(currentFrame).getScale().x, animation.get(length).getScale().x, t);
							interpolatedScale.y = linearLerp(animation.get(currentFrame).getScale().y, animation.get(length).getScale().y, t);
							interpolatedScale.z = linearLerp(animation.get(currentFrame).getScale().z, animation.get(length).getScale().z, t);
							Vector3d interpolatedTranslate = new Vector3d();
							interpolatedTranslate.x = linearLerp(animation.get(currentFrame).getTranslate().x, animation.get(length).getTranslate().x, t);
							interpolatedTranslate.y = linearLerp(animation.get(currentFrame).getTranslate().y, animation.get(length).getTranslate().y, t);
							interpolatedTranslate.z = linearLerp(animation.get(currentFrame).getTranslate().z, animation.get(length).getTranslate().z, t);
							Vector3d interpolatedRotation = new Vector3d();
							interpolatedRotation.x = rotationalInterpolation(animation.get(currentFrame).getRotation().x, animation.get(length).getRotation().x, t);
							interpolatedRotation.y = rotationalInterpolation(animation.get(currentFrame).getRotation().y, animation.get(length).getRotation().y, t);
							interpolatedRotation.z = rotationalInterpolation(animation.get(currentFrame).getRotation().z, animation.get(length).getRotation().z, t);
							KeyFrame interpolatedFrame = new KeyFrame(interpolatedScale, interpolatedRotation, interpolatedTranslate, j);
							temp.put(j, interpolatedFrame);
						}
					}
				}
				
			}
		}
		animation.putAll(temp);
		
		return animation;
	}
	
	public static double linearLerp(double v0, double v1, double t) {
		return v0 + t * (v1 - v0);
	}
	
	public static double rotationalInterpolation(double angle0, double angle1, double t) {
		return angle0 + ((((((angle1 - angle0) % 360) + 540) % 360) - 180) * t);
	}

	public boolean isLooped() {
		return looped;
	}

	public int getLength() {
		return length;
	}

	public SortedMap[] getMaps() {
		return maps;
	}

}
