package mcpecommander.theOvercasted.entity.renderer;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.entity.CraftStudioModelSon;
import mcpecommander.theOvercasted.entity.entities.EntityMushroomPerson;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMushroomPerson extends RenderLiving<EntityMushroomPerson>{
	
	private static final DynamicTexture TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);

	private static CraftStudioModelSon model = new CraftStudioModelSon(Reference.MODID, "mushroom_person", 64, 32);
	
	public RenderMushroomPerson(RenderManager rendermanagerIn) {
		super(rendermanagerIn, model, 0.3f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityMushroomPerson entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/mushroom_person.png");
	}
	
	@Override
	protected void renderModel(EntityMushroomPerson entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		GlStateManager.pushMatrix();
		if (!entitylivingbaseIn.isBaby()) {
			this.shadowSize = 0.6f;
			GlStateManager.scale(2d, 2d, 2d);
			GlStateManager.translate(0d, 0d - (1 / 1.33d), 0d);
		}
		
		if(entitylivingbaseIn.deathTime > 0) {
			GlStateManager.scale( 1d - entitylivingbaseIn.deathTime/10d, 1d - entitylivingbaseIn.deathTime/10d, 1d - entitylivingbaseIn.deathTime/10d);
			GlStateManager.rotate(36 * entitylivingbaseIn.deathTime, 0, 1, 0);
			double test = Math.exp(entitylivingbaseIn.deathTime/2d);
			GlStateManager.translate(0d, test/10d, 0d);
		}

		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
				scaleFactor);
		GlStateManager.popMatrix();

	}
	
	@Override
	protected void applyRotations(EntityMushroomPerson entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
		String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
		if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s)))
        {
            GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        }
	}
	
	@Override
	protected boolean setBrightness(EntityMushroomPerson entitylivingbaseIn, float partialTicks, boolean combineTextures) {
		float f = entitylivingbaseIn.getBrightness();
        int i = this.getColorMultiplier(entitylivingbaseIn, f, partialTicks);
        boolean flag = (i >> 24 & 255) > 0;
        boolean flag1 = entitylivingbaseIn.hurtTime > 0;

        if (!flag && !flag1)
        {
            return false;
        }
        else if (!flag && !combineTextures)
        {
            return false;
        }
        else
        {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            this.brightnessBuffer.position(0);

            if (flag1)
            {
                this.brightnessBuffer.put(1.0F);
                this.brightnessBuffer.put(0.0F);
                this.brightnessBuffer.put(0.0F);
                this.brightnessBuffer.put(0.3F);
            }
            else
            {
                float f1 = (i >> 24 & 255) / 255.0F;
                float f2 = (i >> 16 & 255) / 255.0F;
                float f3 = (i >> 8 & 255) / 255.0F;
                float f4 = (i & 255) / 255.0F;
                this.brightnessBuffer.put(f2);
                this.brightnessBuffer.put(f3);
                this.brightnessBuffer.put(f4);
                this.brightnessBuffer.put(1.0F - f1);
            }

            this.brightnessBuffer.flip();
            GlStateManager.glTexEnv(8960, 8705, this.brightnessBuffer);
            GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(TEXTURE_BRIGHTNESS.getGlTextureId());
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            return true;
        }
	}
	
	public static class Factory<T extends EntityMushroomPerson> implements IRenderFactory<T>{

		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderMushroomPerson(manager);
		}

	}

}
