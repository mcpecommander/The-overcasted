package mcpecommander.theOvercasted.entity.animationTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Nullable;
import javax.vecmath.Vector3f;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leviathanstudio.craftstudio.client.util.MathHelper;

import mcpecommander.theOvercasted.entity.CraftStudioModelSon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class Animation {

	private final boolean looped;
	private final int length;
	private final Set<ImmutablePair<String, SortedMap<Integer, KeyFrame>>> animations;
	public boolean hasFinished, firstFrame = true;
	public long currentTime, previousTime;
	public int currentFrame;

	private Animation(boolean looped, int length,
			Set<ImmutablePair<String, SortedMap<Integer, KeyFrame>>> animations) {
		this.looped = looped;
		this.length = length;
		this.animations = animations;
	}

	public static Animation BuildAnimation(boolean looped, int length,
			Set<ImmutablePair<String, SortedMap<Integer, KeyFrame>>> animations) {
		return new Animation(looped, length, animations);
	}

	public void playAnimation(int fps, int startingFrame, CraftStudioModelSon model) {
		if(!hasFinished) {
			if(firstFrame) {
				this.previousTime = System.nanoTime();
				currentFrame = startingFrame;
				firstFrame = false;
			}else {
				currentFrame ++;
			}
			//Fps
			currentTime = System.nanoTime();
			
			double deltaTime = (currentTime - previousTime) / 100000000000.0;
			
	        float numberOfSkippedFrames = (float) (deltaTime * fps);
			
	        currentFrame = (int) (currentFrame + numberOfSkippedFrames);

	        if (currentFrame < length - 1) {
	        	
	        }else if(currentFrame > length - 1){
	        	if(!looped) {
	        		hasFinished = true;
	        	}else {
	        		currentFrame = 0;
	        		previousTime = currentTime;
	        	}
	        }
	        for(ImmutablePair<String, SortedMap<Integer, KeyFrame>> animation : animations) {
	        	CSModelRendererOvercasted box = model.getModelRendererFromName(animation.getLeft());
	        	if(box != null) {
	        		KeyFrame frame = animation.right.get(currentFrame);
	        		if(frame == null) {
	        			return;
	        		}else {

	        			box.addTranslate(frame.getTranslate());
	        			box.setStretch(frame.getScale().x, frame.getScale().y, frame.getScale().z);
	        			box.getRotationMatrix().set(MathHelper.quatFromEuler(frame.getRotation()));
	        		}
	        	}
	        }
		}
	}

	@Nullable
	public static ImmutablePair createAnimation(int length, String box, KeyFrame... frames) {
		SortedMap<Integer, KeyFrame> animation = new TreeMap<>();
		for (KeyFrame frame : frames) {
			if (frame.getNum() >= length || frame.getNum() < 0) {
				return null;
			}
			animation.putIfAbsent(frame.getNum(), frame);
		}
		ListIterator<Integer> it = new ArrayList<>(animation.keySet()).listIterator();
		SortedMap<Integer, KeyFrame> temp = new TreeMap<>();
		while (it.hasNext()) {
			int currentFrame = (int) it.next();
			for (int i = 0; i < length; ++i) {
				if (i <= currentFrame) {
					continue;
				} else {
					if (it.hasNext()) {
						int nextFrame = (int) it.next();
						for (int j = currentFrame + 1; j < nextFrame; j++) {
							
							double t = ((double) j - (double) currentFrame)
									/ ((double) nextFrame - (double) currentFrame);
							
							Vector3f interpolatedScale = new Vector3f();

							interpolatedScale.x = lerp(animation.get(currentFrame).getScale().x,
									animation.get(nextFrame).getScale().x, t);
							
							interpolatedScale.y = lerp(animation.get(currentFrame).getScale().y,
									animation.get(nextFrame).getScale().y, t);
							
							interpolatedScale.z = lerp(animation.get(currentFrame).getScale().z,
									animation.get(nextFrame).getScale().z, t);
							
							Vector3f interpolatedTranslate = new Vector3f();
							
							interpolatedTranslate.x = lerp(animation.get(currentFrame).getTranslate().x,
									animation.get(nextFrame).getTranslate().x, t);
							
							interpolatedTranslate.y = lerp(animation.get(currentFrame).getTranslate().y,
									animation.get(nextFrame).getTranslate().y, t);
							
							interpolatedTranslate.z = lerp(animation.get(currentFrame).getTranslate().z,
									animation.get(nextFrame).getTranslate().z, t);
							
							Vector3f interpolatedRotation = new Vector3f();
							
							interpolatedRotation.x = rotationalInterpolation(
									animation.get(currentFrame).getRotation().x,
									animation.get(nextFrame).getRotation().x, t);
							
							interpolatedRotation.y = rotationalInterpolation(
									animation.get(currentFrame).getRotation().y,
									animation.get(nextFrame).getRotation().y, t);
							
							interpolatedRotation.z = rotationalInterpolation(
									animation.get(currentFrame).getRotation().z,
									animation.get(nextFrame).getRotation().z, t);
							
							KeyFrame interpolatedFrame = new KeyFrame(interpolatedScale, interpolatedRotation,
									interpolatedTranslate, j);
							
							temp.put(j, interpolatedFrame);
						}
						it.previous();
					} else {
						if (length == currentFrame) {
							break;
						}
						for (int j = currentFrame + 1; j < length; j++) {
							KeyFrame lastFrame = animation.get(length);
							if(lastFrame == null) {
								lastFrame = new KeyFrame(length - 1);
							}
							double t = ((double) j - (double) currentFrame) / ((double) length - (double) currentFrame);
							Vector3f interpolatedScale = new Vector3f();
							interpolatedScale.x = lerp(animation.get(currentFrame).getScale().x,
									lastFrame.getScale().x, t);
							interpolatedScale.y = lerp(animation.get(currentFrame).getScale().y,
									lastFrame.getScale().y, t);
							interpolatedScale.z = lerp(animation.get(currentFrame).getScale().z,
									lastFrame.getScale().z, t);
							Vector3f interpolatedTranslate = new Vector3f();
							interpolatedTranslate.x = lerp(animation.get(currentFrame).getTranslate().x,
									lastFrame.getTranslate().x, t);
							interpolatedTranslate.y = lerp(animation.get(currentFrame).getTranslate().y,
									lastFrame.getTranslate().y, t);
							interpolatedTranslate.z = lerp(animation.get(currentFrame).getTranslate().z,
									lastFrame.getTranslate().z, t);
							Vector3f interpolatedRotation = new Vector3f();
							interpolatedRotation.x = rotationalInterpolation(
									animation.get(currentFrame).getRotation().x, lastFrame.getRotation().x,
									t);
							interpolatedRotation.y = rotationalInterpolation(
									animation.get(currentFrame).getRotation().y, lastFrame.getRotation().y,
									t);
							interpolatedRotation.z = rotationalInterpolation(
									animation.get(currentFrame).getRotation().z, lastFrame.getRotation().z,
									t);
							KeyFrame interpolatedFrame = new KeyFrame(interpolatedScale, interpolatedRotation,
									interpolatedTranslate, j);
							temp.put(j, interpolatedFrame);
						}
					}
				}

			}
		}
		animation.putAll(temp);
		return new ImmutablePair<String, SortedMap<Integer, KeyFrame>>(box, animation);
	}
	
	public static Animation readAnim(ResourceLocation location) {
		
		int length;
		
		JsonParser jsonParser = new JsonParser();
        BufferedReader reader = null;
        IResource iResource = null;
        StringBuilder strBuilder = new StringBuilder();
        JsonObject json = null;
        try {
            iResource = Minecraft.getMinecraft().getResourceManager().getResource(location);
            reader = new BufferedReader(new InputStreamReader(iResource.getInputStream(), Charsets.UTF_8));
            String s;
            while ((s = reader.readLine()) != null)
                strBuilder.append(s);
            Object object = jsonParser.parse(strBuilder.toString());
            json = (JsonObject) object;
        } catch (FileNotFoundException fnfe) {
        	fnfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (iResource != null)
                    iResource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(json != null) {

            JsonElement jsEl;
            Set list = new HashSet<ImmutablePair<String, SortedMap<Integer, KeyFrame>>>();

            jsEl = json.get("title");
            if (jsEl == null) {
            	System.out.println("whatever, naming is not yet supported");
            }
            System.out.println("Naming is not yet supported");
            
            jsEl = json.get("duration");
            if (jsEl == null) {
            	System.out.println("need to have a length");
            	return null;
            }
            length = jsEl.getAsInt() + 1;
            
            jsEl = json.get("holdLastKeyframe");
            if (jsEl == null) {
            	System.out.println("not yet supported");
            }
            System.out.println("holding is not yet supported");

            jsEl = json.get("nodeAnimations");
            if (jsEl == null) {
            	System.out.println("I don't know why you bothered making an animation if there are no animations");
            }
            
            JsonObject nodeAnims = jsEl.getAsJsonObject();
            for (Entry<String, JsonElement> entry : nodeAnims.entrySet()) {

                try {
                	ImmutablePair<String, SortedMap<Integer, KeyFrame>> frame = readAnimBlock(entry, length);
                	list.add(frame);
                } catch (Exception e) {
                	e.printStackTrace();
                }
                
            }
            
            return BuildAnimation(true, length, list);
        	
        }else {
        	return null;
        }

	}
	
	private static ImmutablePair<String, SortedMap<Integer, KeyFrame>> readAnimBlock(Entry<String, JsonElement> entry, int animLength) {
		
        JsonObject objBlock = entry.getValue().getAsJsonObject(), objFieldP, objFieldS, objFieldR;

        objFieldP = objBlock.get("position").getAsJsonObject();

        objFieldR = objBlock.get("rotation").getAsJsonObject();

        objFieldS = objBlock.get("stretch").getAsJsonObject();
        
        KeyFrame[] frames = AddFrames(objFieldR, objFieldP, objFieldS, animLength);
        
        return createAnimation(animLength , strNormalize(entry.getKey()), frames);

    }
	
	private static KeyFrame[] AddFrames(JsonObject objR, JsonObject objP, JsonObject objS, int length) {
        
		List<KeyFrame> frames = new ArrayList<>();
		JsonArray array;

        for (Entry<String, JsonElement> entry : objR.entrySet()) {
        	
        	KeyFrame frame = new KeyFrame(Integer.parseInt(entry.getKey()));
            array = entry.getValue().getAsJsonArray();
            frame.rotation = new Vector3f(array.get(0).getAsFloat(), -array.get(1).getAsFloat(), -array.get(2).getAsFloat());
            frames.add(frame);
        }
        for (Entry<String, JsonElement> entry : objP.entrySet()) {
        	KeyFrame frame = new KeyFrame(Integer.parseInt(entry.getKey()));
            array = entry.getValue().getAsJsonArray();
            if(frames.contains(frame)) {
            	frames.get(frames.indexOf(frame)).translate = new Vector3f(array.get(0).getAsFloat(), -array.get(1).getAsFloat(), -array.get(2).getAsFloat());
            }else {
            	frame.translate = new Vector3f(array.get(0).getAsFloat(), -array.get(1).getAsFloat(), -array.get(2).getAsFloat());
            	frames.add(frame);
            }
            
        }
        for (Entry<String, JsonElement> entry : objS.entrySet()) {
        	KeyFrame frame = new KeyFrame(Integer.parseInt(entry.getKey()));
            array = entry.getValue().getAsJsonArray();
            if(frames.contains(frame)) {
            	frames.get(frames.indexOf(frame)).scale = new Vector3f(array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat());
            }else {
            	frame.scale = new Vector3f(array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat());
            	frames.add(frame);
            }
            
        }
//        System.out.println(frames);
        return frames.stream().toArray(KeyFrame[]::new);
    }
	
	private static String strNormalize(String str) {
        return str.replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_");
    }

	public static float lerp(double v0, double v1, double t) {
		return (float) (v0 + t * (v1 - v0));
	}

	public static float rotationalInterpolation(double angle0, double angle1, double t) {
		return (float) (angle0 + ((((((angle1 - angle0) % 360) + 540) % 360) - 180) * t));
	}

	public boolean isLooped() {
		return looped;
	}

	public int getLength() {
		return length;
	}

}
