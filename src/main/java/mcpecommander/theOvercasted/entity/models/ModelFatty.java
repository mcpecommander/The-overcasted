package mcpecommander.theOvercasted.entity.models;

import javax.vecmath.Vector3d;

import mcpecommander.theOvercasted.entity.animationTest.Animation;
import mcpecommander.theOvercasted.entity.animationTest.KeyFrame;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;

public class ModelFatty extends ModelBase {
	ModelOvercastedRenderer Bottom;
	ModelOvercastedRenderer RightLeg;
	ModelOvercastedRenderer Chest;
	ModelOvercastedRenderer LeftLeg;
	ModelOvercastedRenderer LeftArm;
	ModelOvercastedRenderer RightArm;
	ModelOvercastedRenderer Head;
	Animation animation;

	public ModelFatty() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		
		this.Head = new ModelOvercastedRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.Head.addBox(-8.0F, -16.0F, -8.0F, 16, 16, 16, 0.0F);
		this.Head.setScale(0.7d, 0.6d, 0.65d);
		
		this.Chest = new ModelOvercastedRenderer(this, 20, 32);
		this.Chest.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.Chest.addBox(-6.0F, -6.0F, -5.0F, 12, 6, 10, 0.0F);
		
		this.LeftLeg = new ModelOvercastedRenderer(this, 40, 51);
		this.LeftLeg.setRotationPoint(4.0F, 0.0F, 0.0F);
		this.LeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
		
		this.RightLeg = new ModelOvercastedRenderer(this, 52, 51);
		this.RightLeg.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.RightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
		
		this.LeftArm = new ModelOvercastedRenderer(this, 0, 32);
		this.LeftArm.setRotationPoint(6.0F, -5.6F, 0.0F);
		this.LeftArm.addBox(0.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		
		this.RightArm = new ModelOvercastedRenderer(this, 12, 32);
		this.RightArm.setRotationPoint(-6.0F, -5.6F, 0.0F);
		this.RightArm.addBox(-3.0F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.RightArm.setBoxName("RightArm");
		
		this.Bottom = new ModelOvercastedRenderer(this, 0, 48);
		this.Bottom.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.Bottom.addBox(-6.0F, -6.0F, -5.0F, 12, 6, 10, 0.0F);
		this.Bottom.addChild(this.RightLeg);
		this.Chest.addChild(this.Head);
		this.Chest.addChild(this.LeftArm);
		this.Bottom.addChild(this.Chest);
		this.Bottom.addChild(this.LeftLeg);
		this.Chest.addChild(this.RightArm);
		
		KeyFrame frame1 = new KeyFrame(new Vector3d(-4, 1.5, 17), 0);
		KeyFrame frame2 = new KeyFrame(new Vector3d(11.19, -36.67, 69.16), 5);
		KeyFrame frame3 = new KeyFrame(new Vector3d(39.78, 9.81, 129.95), 10);
		KeyFrame frame4 = new KeyFrame(new Vector3d(-63.35, 1.76, 27.35), 15);
		KeyFrame frame5 = new KeyFrame(new Vector3d(-63.35, 1.76, -25.37), 20);
		KeyFrame frame6 = new KeyFrame(new Vector3d(-68.63, 9.27, 6.07), 25);
		KeyFrame frame7 = new KeyFrame(new Vector3d(-4.01, 1.58, 17.23), 30);
		
		animation = Animation.BuildAnimation(false, 31, Animation.createAnimation(31, frame1, frame2, frame3, frame4, frame5, frame6, frame7));
		
		System.out.println(animation);
		
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.Bottom.render(scale);
//		RightArm.resetRotation();
		LeftArm.resetRotation();
//		Chest.resetRotation();
		Head.resetRotation();
//		RightArm.renderWithRotation(scale);
		Chest.showModel = true;
		Chest.isHidden = false;
		ModelOvercastedRenderer.rotateBoxes( 0, 0, 0, RightArm);
//		RightArm.playAnimation(animation, ageInTicks);
		
		GlStateManager.disableTexture2D();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		buf.begin(7, DefaultVertexFormats.POSITION_COLOR);
		float xb = this.Bottom.rotationPointX * scale;
		float xc = this.Chest.rotationPointX * scale;
		float xa = this.RightArm.rotationPointX * scale;
		float x = xc + xb + xa;
		float yb = this.Bottom.rotationPointY * scale;
		float yc = this.Chest.rotationPointY * scale;
		float ya = this.RightArm.rotationPointY * scale;
		float y =  yc + yb + ya;
		float zb = this.Bottom.rotationPointZ * scale;
		float zc = this.Chest.rotationPointZ * scale;
		float za = this.RightArm.rotationPointZ * scale;
		float z =  zc + zb + za;
		float d = 0.05f;
		buf.pos(x, -d + y, -d + z ).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos( x, d + y, -d + z).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos(x, d + y, d + z).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos(x, -d + y, d + z).color(1f, 1f, 0f, 1f).endVertex();
		buf.pos(-d + x, -d + y, z ).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos( d + x, -d + y, z).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos(d + x, d + y, z).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos(-d + x, d + y, z).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos(-d + x, y, -d + z ).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos( d + x, y, -d + z).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos(d + x, y, d + z).color(1f, 0f, 0f, 1f).endVertex();
		buf.pos(-d + x, y, d + z).color(1f, 0f, 1f, 1f).endVertex();
		
		tessellator.draw();
		GlStateManager.enableTexture2D();

		
	}
	
	public void postRenderArm(float scale, EnumHandSide side)
    {
		RightArm.postRerender(scale, Bottom, Chest, RightArm);
    }
	
	protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? this.LeftArm : this.RightArm;
    }

}
