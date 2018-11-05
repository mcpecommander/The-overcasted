package mcpecommander.theOvercasted.entity.models;

import java.util.SortedMap;

import javax.vecmath.Vector3f;

import mcpecommander.theOvercasted.entity.animationTest.Animation;
import mcpecommander.theOvercasted.entity.animationTest.KeyFrame;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelOvercastedRenderer extends ModelRenderer{
	
	public double scaleX , scaleY , scaleZ ;
	public float originalRotateX , originalRotateY, originalRotateZ;
	private boolean compiled;
	private int displayList;
	private float currentTick = 0;
	private String boxName;
	
	public ModelOvercastedRenderer(ModelBase model, int texOffX, int texOffY) {
		super(model, texOffX, texOffY);
	}
	
	public ModelOvercastedRenderer(ModelBase model, String boxName) {
		super(model, boxName);
	}
	
	/**
	 * 
	 * @param scaleX The scale on X axis.
	 * @param scaleY The scale on Y axis.
	 * @param scaleZ The scale on Z axis.
	 */
	public void setScale (double scaleX, double scaleY, double scaleZ) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}
	
	/**
	 * 
	 * @param x Original X angle.
	 * @param y Original Y angle.
	 * @param z Original Z angle.
	 */
	public void setOriginalRotation(float x, float y, float z) {
		this.originalRotateX = x;
		this.originalRotateY = y;
		this.originalRotateZ = z;
	}
	
	public String getBoxName() {
		return boxName;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	/**
	 * Resets the rotation to the original state (if original angles are not specified, it will default to 0, 0, 0).
	 */
	public void resetRotation () {
		this.rotateAngleX = this.originalRotateX;
		this.rotateAngleY = this.originalRotateY;
		this.rotateAngleZ = this.originalRotateZ;
	}
	
	/**
	 * A more complete version of renderWithRotation that does the children too.
	 */
	@SideOnly(Side.CLIENT)
    public void render(float scale)
    {
        if (!this.isHidden)
        {
            if (this.showModel)
            {
                if (!this.compiled)
                {
                    this.compileDisplayList(scale);
                }

                GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);

                if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F)
                {
                    if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F)
                    {
                    	
                    	GlStateManager.pushMatrix();
                        if(scaleX != 0 && scaleY != 0 && scaleZ != 0) {
                        	GlStateManager.scale(scaleX, scaleY, scaleZ);
                        }
                        GlStateManager.callList(this.displayList);
                        GlStateManager.popMatrix();

                        if (this.childModels != null)
                        {
                            for (int k = 0; k < this.childModels.size(); ++k)
                            {
                                ((ModelRenderer)this.childModels.get(k)).render(scale);
                            }
                        }
                    }
                    else
                    {

                        GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                        if(this.boxName == "Chest") {
                        	//System.out.println(this.rotationPointX * scale + " " + this.rotationPointY * scale + " " +  this.rotationPointZ * scale);
                        }
                        GlStateManager.pushMatrix();
                        if(scaleX != 0 && scaleY != 0 && scaleZ != 0) {
                        	GlStateManager.scale(scaleX, scaleY, scaleZ);
                        }
                        GlStateManager.callList(this.displayList);
                        GlStateManager.popMatrix();

                        if (this.childModels != null)
                        {
                            for (int j = 0; j < this.childModels.size(); ++j)
                            {
                                ((ModelRenderer)this.childModels.get(j)).render(scale);
                            }
                        }

                        GlStateManager.translate(-this.rotationPointX * scale, -this.rotationPointY * scale, -this.rotationPointZ * scale);
                    }
                }
                else
                {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

                    if (this.rotateAngleZ != 0.0F)
                    {
                        GlStateManager.rotate(this.rotateAngleZ, 0.0F, 0.0F, 1.0F);
                    }

                    if (this.rotateAngleY != 0.0F)
                    {
                        GlStateManager.rotate(this.rotateAngleY, 0.0F, 1.0F, 0.0F);
                    }

                    if (this.rotateAngleX != 0.0F)
                    {
                        GlStateManager.rotate(this.rotateAngleX, 1.0F, 0.0F, 0.0F);
                    }

                    GlStateManager.pushMatrix();
                    if(scaleX != 0 && scaleY != 0 && scaleZ != 0) {
                    	GlStateManager.scale(scaleX, scaleY, scaleZ);
                    }
                    GlStateManager.callList(this.displayList);
                    GlStateManager.popMatrix();

                    if (this.childModels != null)
                    {
                        for (int i = 0; i < this.childModels.size(); ++i)
                        {
                            ((ModelRenderer)this.childModels.get(i)).render(scale);
                        }
                    }

                    GlStateManager.popMatrix();
                }

                GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
            }
        }
    }
	
	//Added scaling and change the angles back to degrees (as it always should be).
	//Do not call this with children boxes as it will mess their rotation point. 
	@SideOnly(Side.CLIENT)
    public void renderWithRotation(float scale)
    {
		
        if (!this.isHidden)
        {
            if (this.showModel)
            {
                if (!this.compiled)
                {
                    this.compileDisplayList(scale);
                }
                GlStateManager.translate(this.offsetX * scale, this.offsetY * scale, this.offsetZ * scale);
                GlStateManager.pushMatrix();
                GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                GlStateManager.rotate(this.rotateAngleZ, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(this.rotateAngleY , 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(this.rotateAngleX, 1.0F, 0.0F, 0.0F);
                
                

                GlStateManager.pushMatrix();
                if(scaleX != 0 && scaleY != 0 && scaleZ != 0) {
                	GlStateManager.scale(scaleX, scaleY, scaleZ);
                }
                GlStateManager.callList(this.displayList);
                GlStateManager.popMatrix();

                GlStateManager.popMatrix();
                if (this.childModels != null)
                {
                    for (int i = 0; i < this.childModels.size(); ++i)
                    {
                        ((ModelRenderer)this.childModels.get(i)).render(scale);
                    }
                }
                GlStateManager.translate(-this.offsetX * scale, -this.offsetY * scale, -this.offsetZ * scale);
//                GlStateManager.disableTexture2D();
//        		Tessellator tessellator = Tessellator.getInstance();
//        		BufferBuilder buf = tessellator.getBuffer();
//        		buf.begin(7, DefaultVertexFormats.POSITION_COLOR);
//        		buf.pos(-0.2 + this.rotationPointX * scale, -0.2 + this.rotationPointY * scale, 0 ).color(1f, 0f, 0f, 1f).endVertex();
//        		buf.pos( 0.2 + this.rotationPointX * scale, -0.2 + this.rotationPointY * scale, 0).color(1f, 0f, 0f, 1f).endVertex();
//        		buf.pos(0.2 + this.rotationPointX * scale, 0.2 + this.rotationPointY * scale, 0).color(1f, 0f, 0f, 1f).endVertex();
//        		buf.pos(-0.2 + this.rotationPointX * scale, 0.2 + this.rotationPointY * scale, 0).color(1f, 0f, 0f, 1f).endVertex();
//        		tessellator.draw();
//        		GlStateManager.enableTexture2D();
            }
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
	public static void rotateBoxes(float x, float y, float z, ModelOvercastedRenderer... boxes) {
		for(ModelOvercastedRenderer box : boxes) {
			box.rotateAngleX = x;
			box.rotateAngleY = y;
			box.rotateAngleZ = z;
		}
	}
	
	/**
	 * Used to change the position quickly instead of changing each axis on its own.
	 * @param translate The translation vector wished to be added to the current position.
	 */
	public void addTranslate(Vector3f translate) {
		this.rotationPointX += translate.x;
		this.rotationPointY += translate.y;
		this.rotationPointZ += translate.z;
	}
	
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
//				setScale(currentFrame.getScale().x, currentFrame.getScale().y, currentFrame.getScale().z);
//				rotateBoxes((float)currentFrame.getRotation().x, (float)currentFrame.getRotation().y, (float)currentFrame.getRotation().z, this);
//			}
//		}
//		
//	}
	
	//Changed to degrees
	@Override
	@SideOnly(Side.CLIENT)
    public void postRender(float scale)
    {
		if (!this.isHidden)
        {
            if (this.showModel)
            {
                if (!this.compiled)
                {
                    this.compileDisplayList(scale);
                }

                if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F)
                {
                    if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F)
                    {
                    	
                        GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                    }
                }
                else
                {
                    GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

                    if (this.rotateAngleZ != 0.0F)
                    {
                        GlStateManager.rotate(this.rotateAngleZ , 0.0F, 0.0F, 1.0F);
                    }

                    if (this.rotateAngleY != 0.0F)
                    {
                        GlStateManager.rotate(this.rotateAngleY , 0.0F, 1.0F, 0.0F);
                    }

                    if (this.rotateAngleX != 0.0F)
                    {
                        GlStateManager.rotate(this.rotateAngleX , 1.0F, 0.0F, 0.0F);
                    }
                }
            }
        }
    }
	
	@SideOnly(Side.CLIENT)
	public void postRerender(float scale, ModelOvercastedRenderer... boxes) {
		if (!this.isHidden)
        {
            if (this.showModel)
            {
                if (!this.compiled)
                {
                    this.compileDisplayList(scale);
                }
                
                if(boxes.length != 0) {
                	
                	for(ModelOvercastedRenderer box : boxes) {
                    	
                    	if (box.rotateAngleX == 0.0F && box.rotateAngleY == 0.0F && box.rotateAngleZ == 0.0F)
                        {
                            if (box.rotationPointX != 0.0F || box.rotationPointY != 0.0F || box.rotationPointZ != 0.0F)
                            {
                                GlStateManager.translate(box.rotationPointX * scale, box.rotationPointY * scale, box.rotationPointZ * scale);
                            }
                        }
                        else
                        {
                            
                        	GlStateManager.translate(box.rotationPointX * scale, box.rotationPointY * scale, box.rotationPointZ * scale);
                        	
                            if (box.rotateAngleZ != 0.0F)
                            {
                                GlStateManager.rotate(box.rotateAngleZ , 0.0F, 0.0F, 1.0F);
                            }

                            if (box.rotateAngleY != 0.0F)
                            {
                                GlStateManager.rotate(box.rotateAngleY , 0.0F, 1.0F, 0.0F);
                            }

                            if (box.rotateAngleX != 0.0F)
                            {
                                GlStateManager.rotate(box.rotateAngleX , 1.0F, 0.0F, 0.0F);
                            }

                        }
                    }
                }
        	}  
        }      
	}
	
	//My own version since compiled and displayList are private.
	@SideOnly(Side.CLIENT)
    private void compileDisplayList(float scale)
    {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GlStateManager.glNewList(this.displayList, 4864);
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        for (int i = 0; i < this.cubeList.size(); ++i)
        {
        	
            ((ModelBox)this.cubeList.get(i)).render(bufferbuilder, scale);
            
        }

        GlStateManager.glEndList();
        this.compiled = true;
    }
}
