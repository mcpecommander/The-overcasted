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
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leviathanstudio.craftstudio.client.util.MathHelper;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.TheOvercasted;
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
			Set<ImmutablePair<String, SortedMap<Integer, KeyFrame>>> boxAnimations) {
		this.looped = looped;
		this.length = length;
		this.animations = boxAnimations;
	}

	public static Animation BuildAnimation(boolean looped, int length,
			Set<ImmutablePair<String, SortedMap<Integer, KeyFrame>>> boxAnimations) {
		return new Animation(looped, length, boxAnimations);
	}

	/**
	 * 
	 * @param fps FPS to have this run at.
	 * @param startingFrame	In case the animation start from a different frame other than 0.
	 * @param model	The model wished to apply the animation to.
	 */
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
	        //The animation loop
	        for(ImmutablePair<String, SortedMap<Integer, KeyFrame>> animation : animations) {
	        	CSModelRendererOvercasted box = model.getModelRendererFromName(animation.getLeft());
	        	if(box != null) {
	        		KeyFrame frame = animation.right.get(currentFrame);
	        		if(frame == null) {
	        			return;
	        		}else {
	        			//Does not really support multiple rotations on the same box from different animations.
	        			box.addTranslate(frame.getTranslate());
	        			//Do not have access to the default stretch.
	        			box.resetStretch();
	        			box.setStretch(box.getStretchAsVector().x + frame.getScale().x, box.getStretchAsVector().y + frame.getScale().y, box.getStretchAsVector().z + frame.getScale().z);
	        			if(!frame.getRotation().equals(Reference.EMPTY_VECTOR)) {
		        			Quat4f quat = box.getDefaultRotationAsQuaternion();
		        			quat.mul(MathHelper.quatFromEuler(frame.getRotation()));
		        			quat.inverse();
		        			box.getRotationMatrix().set(quat);
	        			}
	        		}
	        	}
	        }
		}
	}

	/**
	 * A method to return a fully interpolated sub-animation from the given key frames.
	 * 
	 * @param length The animation length, note that it should be 1 frame larger than the last frame to insure a smooth lerp.
	 * @param box The name of the modelRenderer that this sub-animation belongs to.
	 * @param frames The frames 
	 * @return An {@link ImmutablePair} with a the box name in the left and the interpolated animation in the right.
	 */
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
	
	/**
	 * Oh god why.
	 * @param location The animation file name with the extension {@code .csjsmodelanim} and the full location.
	 * @return A fully interpolated animation ready to be used.
	 */
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
        	TheOvercasted.logger.fatal(location.toString() + " could not be found, check the spelling.");
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
            	TheOvercasted.logger.error("Naming is missing but naming is not yet supported, continuing");
            }
            TheOvercasted.logger.info("Naming is not yet supported");
            
            jsEl = json.get("duration");
            if (jsEl == null) {
            	TheOvercasted.logger.fatal("json file syntax is missing a length, add length.");
            	return null;
            }
            length = jsEl.getAsInt() + 1;
            
            jsEl = json.get("holdLastKeyframe");
            if (jsEl == null) {
            	TheOvercasted.logger.error("Holding is missing but holding is not yet supported, continuing");
            }
            TheOvercasted.logger.info("holding is not yet supported");

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
        	TheOvercasted.logger.fatal("json file is broken, check the syntax and structure!");
        	return null;
        }

	}
	
	/**
	 * 
	 * @param entry The json object entries.
	 * @param animLength The animation length and not just the length of this sun-animation.
	 * @return A {@link ImmutablePair} that includes the full interpolated sub-animation for one block.
	 */
	private static ImmutablePair<String, SortedMap<Integer, KeyFrame>> readAnimBlock(Entry<String, JsonElement> entry, int animLength) {
		
        JsonObject objBlock = entry.getValue().getAsJsonObject(), objFieldP, objFieldS, objFieldR;

        objFieldP = objBlock.get("position").getAsJsonObject();

        objFieldR = objBlock.get("rotation").getAsJsonObject();

        objFieldS = objBlock.get("stretch").getAsJsonObject();
        
        KeyFrame[] frames = AddFrames(objFieldR, objFieldP, objFieldS);
        
        return createAnimation(animLength , strNormalize(entry.getKey()), frames);

    }
	
	/**
	 * Deserialize the json arrays into usable key frames.
	 * @param objR The json rotation array.
	 * @param objP The json translate array.
	 * @param objS The json scale array.
	 * @return An array of key frames including no repeated key frames where a two key frames have the same number.
	 */
	private static KeyFrame[] AddFrames(JsonObject objR, JsonObject objP, JsonObject objS) {
        
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
	
	@Override
	public String toString() {
		return this.animations.toString() + " Looped: " + this.looped + " Length: " + this.length;
	}

}
