package mcpecommander.theOvercasted.particle;

import java.awt.Color;

import mcpecommander.theOvercasted.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StinkyParticle extends Particle{
	
	private final ResourceLocation stink = new ResourceLocation(Reference.MODID ,"particle/stink_particle");
	
	public static StinkyParticle createParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, int color) {
		return new StinkyParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, color);
	}
	
	public static Color IntToRGB(int i) {
		return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
	}
	
	private StinkyParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
    {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.1, 0.1, 0.1);
		this.motionX *= 0.009999999776482582D;
        this.motionY *= 0.009999999776482582D;
        this.motionZ *= 0.009999999776482582D;
        this.motionY += 0.1D;
        this.particleScale *= 0.95F;
        this.particleAlpha = 0.8f;
        this.particleMaxAge = 16;
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(stink.toString());
	    setParticleTexture(sprite);
    }
	
	private StinkyParticle(World world, double xCoordIn, double yCoordIn, double zCoordIn, int color) {
		this(world, xCoordIn, yCoordIn, zCoordIn);
		Color colorRGB = IntToRGB(color); 
		this.particleBlue = colorRGB.getBlue()/255f;
		this.particleGreen = colorRGB.getGreen()/255f;
		this.particleRed = colorRGB.getRed()/255f;
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

        this.move(this.motionX, this.motionY, this.motionZ);

        if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }

        this.motionX *= 0.8600000143051147D;
        this.motionY *= 0.8600000143051147D;
        this.motionZ *= 0.8600000143051147D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

}
