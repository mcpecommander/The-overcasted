package mcpecommander.theOvercasted.entity.animationTest;

import java.nio.FloatBuffer;
import java.util.SortedMap;

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
		this.rotationPointX += translate.x;
		this.rotationPointY += translate.y;
		this.rotationPointZ += translate.z;
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


	private void setRotationPoint(double x, double y, double z) {
		this.rotationPointX = (float) x;
        this.rotationPointY = (float) y;
        this.rotationPointZ = (float) z;
		
	}

}
