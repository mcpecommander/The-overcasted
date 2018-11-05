package mcpecommander.theOvercasted.entity.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

//Unused, Reference only.
public class ModelConjoinedSack extends ModelBase {

	ModelOvercastedRenderer Bottom;
	ModelOvercastedRenderer LeftLeg;
	ModelOvercastedRenderer RightLeg;
	ModelOvercastedRenderer Neck;
	ModelOvercastedRenderer LeftArm;
	ModelOvercastedRenderer RightArm;
	ModelOvercastedRenderer Head;
	ModelOvercastedRenderer Head1;

	public ModelConjoinedSack() {
		this.textureWidth = 64;
        this.textureHeight = 64;
        this.Bottom = new ModelOvercastedRenderer( this, 0, 42 );
        this.Bottom.setRotationPoint(0.0F, 14.0F, 0.0F);
        this.Bottom.addBox(-6.0F, -6.0F, -5.0F, 12, 12, 10, 0.0F);
        this.LeftLeg = new ModelOvercastedRenderer( this, 52, 33 );
        this.LeftLeg.setRotationPoint(4.0F, 6.0F, 0.0F);
        this.LeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.Head1 = new ModelOvercastedRenderer( this, 0, 0 );
        this.Head1.setRotationPoint(3.0F, -7.0F, 0.0F);
        this.Head1.addBox(-8.0F, -16.0F, -8.0F, 16, 16, 16, 0.0F);
        this.LeftArm = new ModelOvercastedRenderer( this, 52, 40 );
        this.LeftArm.setRotationPoint(6.0F, -4.0F, 0.0F);
        this.LeftArm.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
        this.LeftArm.setOriginalRotation(-5.79417894272F, 8.18643243601F, -54.9415990654F);
        this.Head = new ModelOvercastedRenderer( this, 0, 0 );
        this.Head.setRotationPoint(-2.0F, -7.0F, 0.0F);
        this.Head.addBox(-8.0F, -16.0F, -8.0F, 16, 16, 16, 0.0F);
        this.Neck = new ModelOvercastedRenderer( this, 0, 35 );
        this.Neck.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Neck.addBox(-5.0F, -1.5F, -2.0F, 10, 3, 4, 0.0F);
        this.RightArm = new ModelOvercastedRenderer( this, 40, 40 );
        this.RightArm.setRotationPoint(-6.0F, -4.0F, 0.0F);
        this.RightArm.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
        this.RightArm.setOriginalRotation(-5.79417894272F, -8.18643243601F, 54.9415990654F);
        this.RightLeg = new ModelOvercastedRenderer( this, 40, 33 );
        this.RightLeg.setRotationPoint(-4.0F, 6.0F, 0.0F);
        this.RightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.Bottom.addChild(this.LeftLeg);
        this.Bottom.addChild(this.LeftArm);
        this.Bottom.addChild(this.Neck);
        this.Bottom.addChild(this.RightArm);
        this.Bottom.addChild(this.RightLeg);		
        Head.setScale(0.5d, 0.5d, 0.5d);
		Head1.setScale(0.4d, 0.4d, 0.4d);

		
		Bottom.addChild(LeftLeg);
		Bottom.addChild(RightLeg);
		Bottom.addChild(Neck);
		Bottom.addChild(LeftArm);
		Bottom.addChild(RightArm);
		Bottom.addChild(Head);
		Bottom.addChild(Head1);

	}

	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

		boolean test = false;

		ModelOvercastedRenderer.rotateBoxes(0, Bottom.balancedWave(0, ageInTicks, true, 2f, 3f), Bottom.balancedWave(0, ageInTicks, false, 2f, 1.2f), Bottom);
		Bottom.render(scale);

		LeftLeg.rotateAngleX = ModelOvercastedRenderer.unbalancedWave(LeftLeg.rotateAngleX, ageInTicks, false, false, 2f);

		RightLeg.rotateAngleX = ModelOvercastedRenderer.unbalancedWave(RightLeg.rotateAngleX, ageInTicks, true, false, 2f);

		if (test) {
			LeftArm.resetRotation();
		} else {
			ModelOvercastedRenderer.rotateBoxes(ModelOvercastedRenderer.balancedWave(-90, ageInTicks, true, 5f, 10f), -20f, 0f, LeftArm);
		}

		if (test) {
			RightArm.resetRotation();
		} else {
			ModelOvercastedRenderer.rotateBoxes(ModelOvercastedRenderer.balancedWave(-90, ageInTicks, false, 5f, 10f), 20f, 0f, RightArm);
		}

		Head.rotateAngleZ = (float) (-18.4556180235F + Math.sin(ageInTicks / 5f));

		Head1.rotateAngleZ = (float) (17.8408916051F + Math.sin(ageInTicks / 5f));

	}

}
