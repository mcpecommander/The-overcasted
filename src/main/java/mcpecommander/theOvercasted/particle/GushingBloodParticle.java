package mcpecommander.theOvercasted.particle;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GushingBloodParticle extends Particle {
	
	private final ResourceLocation lava = new ResourceLocation(Reference.MODID ,"particle/lava_particle");
	
	public static GushingBloodParticle createParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
		return new GushingBloodParticle(worldIn, xCoordIn, yCoordIn, zCoordIn);
	}

    protected GushingBloodParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.800000011920929D;
        this.motionY *= 0.800000011920929D;
        this.motionZ *= 0.800000011920929D;
        this.motionY = (double)(this.rand.nextFloat() * 0.2F + 0.05F);
        this.particleRed = 1.0F;
        this.particleGreen = 0.0F;
        this.particleBlue = 0.0F;
        this.particleScale *= this.rand.nextFloat() + 0.2F;
        this.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(lava.toString());
	    setParticleTexture(sprite);
    }
    
    @Override
	public int getFXLayer() {
		return 1;
	}

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
    	double minU = this.particleTexture.getMinU();
	    double maxU = this.particleTexture.getMaxU();
	    double minV = this.particleTexture.getMinV();
	    double maxV = this.particleTexture.getMaxV();

	    double scale = 0.1F * this.particleScale;
	    final double scaleLR = scale;
	    final double scaleUD = scale;
	    double x = this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX;
	    double y = this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY;
	    double z = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ;
	    int combinedBrightness = this.getBrightnessForRender(partialTicks);
	    int skyLightTimes16 = combinedBrightness >> 16 & 65535;
	    int blockLightTimes16 = combinedBrightness & 65535;

	    buffer.pos(x - rotationX * scaleLR - rotationXY * scaleUD,
	            y - rotationZ * scaleUD,
	            z - rotationYZ * scaleLR - rotationXZ * scaleUD)
	                 .tex(maxU, maxV)
	                 .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
	                 .lightmap(skyLightTimes16, blockLightTimes16)
	                 .endVertex();
	    buffer.pos(x - rotationX * scaleLR + rotationXY * scaleUD,
	            y + rotationZ * scaleUD,
	            z - rotationYZ * scaleLR + rotationXZ * scaleUD)
	            .tex(maxU, minV)
	            .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
	            .lightmap(skyLightTimes16, blockLightTimes16)
	            .endVertex();
	    buffer.pos(x + rotationX * scaleLR + rotationXY * scaleUD,
	            y + rotationZ * scaleUD,
	            z + rotationYZ * scaleLR + rotationXZ * scaleUD)
	            .tex(minU, minV)
	            .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
	            .lightmap(skyLightTimes16, blockLightTimes16)
	            .endVertex();
	    buffer.pos(x + rotationX * scaleLR - rotationXY * scaleUD,
	            y - rotationZ * scaleUD,
	            z + rotationYZ * scaleLR - rotationXZ * scaleUD)
	            .tex(minU, maxV)
	            .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
	            .lightmap(skyLightTimes16, blockLightTimes16)
	            .endVertex();
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

        float f = (float)this.particleAge / (float)this.particleMaxAge;

        this.motionY -= 0.03D;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9990000128746033D;
        this.motionY *= 0.9990000128746033D;
        this.motionZ *= 0.9990000128746033D;

        if (this.onGround)
        {
            this.setExpired();
        }
    }

}
