package mcpecommander.theOvercasted.entity.models;

import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.client.util.MathHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;

public class CSModelRendererOvercasted extends CSModelRenderer {
	
	float currentTick = 0;
    private boolean compiled;
    private int displayList;

	public CSModelRendererOvercasted(ModelBase modelbase, String partName, int xTextureOffset, int yTextureOffset) {
		super(modelbase, partName, xTextureOffset, yTextureOffset);
	}
	
	@Override
    public void render(float scale) {
        if (!this.isHidden)
            if (this.showModel) {
                if (!this.compiled)
                    this.compileDisplayList(scale);

                GlStateManager.pushMatrix();

                GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                FloatBuffer buf = MathHelper.makeFloatBuffer(this.getRotationMatrix());
                GlStateManager.multMatrix(buf);
                GlStateManager.translate(this.offsetX * scale, this.offsetY * scale, this.offsetZ * scale);

                GlStateManager.pushMatrix();
                GlStateManager.scale(this.getStretchAsVector().x, this.getStretchAsVector().y, this.getStretchAsVector().z);
                GlStateManager.callList(this.displayList);
                GlStateManager.popMatrix();

                if (this.childModels != null)
                    for (int i = 0; i < this.childModels.size(); ++i)
                        this.childModels.get(i).render(scale);

                GlStateManager.popMatrix();

            }
    }
	
	@Override
	public void postRender(float scale) {
		if (!this.isHidden)
            if (this.showModel) {
                if (!this.compiled)
                    this.compileDisplayList(scale);

                GlStateManager.pushMatrix();

                GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                FloatBuffer buf = MathHelper.makeFloatBuffer(this.getRotationMatrix());
                GlStateManager.multMatrix(buf);
                GlStateManager.translate(this.offsetX * scale, this.offsetY * scale, this.offsetZ * scale);
                GlStateManager.callList(this.displayList);

                GlStateManager.popMatrix();

            }
	}
	
	/**
	 * 
	 * @param angleToRotate The angle to rotate
	 * @param tick Living age tick is recommended.
	 * @param invert Invert the wave (sin to -sin).
	 * @param flip Flip the slowness to the other half of the wave.
	 * @return The rotated angle in floats.
	 */
	public static float unbalancedWave (float angleToRotate, float tick, boolean invert, boolean flip, float frequnecy) {
		if(invert) {
			if(flip ? Math.sin((double)tick/frequnecy) > 0 : Math.sin((double)tick/frequnecy) < 0) {
				return angleToRotate = -(float) Math.toDegrees(Math.sin((double)tick/frequnecy))/(frequnecy/1.53f);
	        }else {
	        	return angleToRotate = -(float) Math.toDegrees(Math.sin((double)tick/frequnecy))/(frequnecy*1.53f);
	        }
		}else {
			if(flip ? Math.sin((double)tick/frequnecy) < 0 : Math.sin((double)tick/frequnecy) > 0) {
				return angleToRotate = (float) Math.toDegrees(Math.sin((double)tick/frequnecy))/(frequnecy/1.53f);
	        }else {
	        	return angleToRotate = (float) Math.toDegrees(Math.sin((double)tick/frequnecy))/(frequnecy*1.53f);
	        }
		}
	}
	
	/**
	 * 
	 * @param originalAngle Original angle to add or subtract from.
	 * @param tick Living age tick is recommended.
	 * @param invert Invert the wave (sin to -sin).
	 * @param frequnecy How intensive is the change.
	 * @param range Multiplier to change it beyond the (-1, 1) range.
	 * @return A sin wave
	 */
	public static float balancedWave (float originalAngle, float tick, boolean invert, float frequnecy, float range) {
		if(invert) {
			return (float) (originalAngle - Math.sin(tick/frequnecy) * range);
		}else {
			return (float) (originalAngle + Math.sin(tick/frequnecy) * range);
		}
	}
	
	/**
	 * A helpful method to rotate one or more boxes at the same time.
	 * @param x The X rotation wished to be applied.
	 * @param y The Y rotation wished to be applied.
	 * @param z The Z rotation wished to be applied.
	 * @param boxes The ModelRender boxes to affected.
	 */
	public static void rotateBoxes(float x, float y, float z, CSModelRendererOvercasted... boxes) {
		for(CSModelRendererOvercasted box : boxes) {
			box.getRotationMatrix().set(new Vector3f(x, y, z));
		}
	}
	
	//Old animation test that works on one box at time.
//	public void playAnimation(Animation animation, float tick) {
//		if(currentTick == 0) {
//			currentTick = tick;
//		}
//		int tickInt = (int) (tick - currentTick);
//		for(SortedMap map : animation.getMaps()) {
//			KeyFrame currentFrame = (KeyFrame) map.get(tickInt);
//			if(currentFrame == null) {
//				currentTick = 0;
//			}else {
//				addTranslate(currentFrame.getTranslate());
//				setStretch(currentFrame.getScale().x, currentFrame.getScale().y, currentFrame.getScale().z);
//				this.getRotationMatrix().set(MathHelper.quatFromEuler(currentFrame.getRotation()));
//				
//			}
//		}
//		
//	}
	
	/**
	 * Used to change the position quickly instead of changing each axis on its own.
	 * @param translate The translation vector wished to be added to the current position.
	 */
	public void addTranslate(Vector3f translate) {
		this.rotationPointX = this.getDefaultRotationPointX() + translate.x;
		this.rotationPointY = this.getDefaultRotationPointY() + translate.y;
		this.rotationPointZ = this.getDefaultRotationPointZ() + translate.z;
	}
	
	public void compileDisplayList(float scale) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GlStateManager.glNewList(this.displayList, GL11.GL_COMPILE);
        BufferBuilder vertexbuffer = Tessellator.getInstance().getBuffer();

        for (int i = 0; i < this.cubeCSList.size(); ++i)
            this.cubeCSList.get(i).render(vertexbuffer, scale);

        GlStateManager.glEndList();
        this.compiled = true;
    }


}
