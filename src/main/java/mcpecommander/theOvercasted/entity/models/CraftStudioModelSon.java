package mcpecommander.theOvercasted.entity.models;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.client.exception.CSResourceNotRegisteredException;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.json.CSReadedModelBlock;
import com.leviathanstudio.craftstudio.client.model.CSModelBox;
import com.leviathanstudio.craftstudio.client.registry.RegistryHandler;
import com.leviathanstudio.craftstudio.client.util.MathHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class CraftStudioModelSon extends ModelBase{

	public CSModelRendererOvercasted getModelRendererFromName(String name) {
        CSModelRendererOvercasted result;
        for (CSModelRendererOvercasted parent : this.getParentBlocks()) {
            result = CraftStudioModelSon.getModelRendererFromNameAndBlocks(name, parent);
            if (result != null)
                return result;
        }
        return null;
    }
	
	public static CSModelRendererOvercasted getModelRendererFromNameAndBlocks(String name, CSModelRendererOvercasted block) {
        CSModelRendererOvercasted childModel, result;

        if (block.boxName.equals(name))
            return block;
        if(block.childModels != null && !block.childModels.isEmpty()){
	        for (ModelRenderer child : block.childModels)
	            if (child instanceof CSModelRendererOvercasted) {
	                childModel = (CSModelRendererOvercasted) child;
	                result = getModelRendererFromNameAndBlocks(name, childModel);
	                if (result != null)
	                    return result;
	            }
        }

        return null;
    }
	
	
	private List<CSModelRendererOvercasted> parentBlocks = new ArrayList<>();

    /**
     * @param modid
     *            The ID of your mod
     * @param modelNameIn
     *            The name of your craftstudio model your have registered with
     *            {@link com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper#register
     *            CraftStudioRegistry#register}
     * @param textureSize
     *            The size of your texture if it's the same width/height
     */
    public CraftStudioModelSon(String modid, String modelNameIn, int textureSize) {
        this(modid, modelNameIn, textureSize, textureSize);
    }

    /**
     * @param modid
     *            The ID of your mod
     * @param modelNameIn
     *            The name of your craftstudio model your have registered with
     *            {@link com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper#register
     *            CraftStudioRegistry#register}
     * @param textureWidth
     *            The width texture of your model
     * @param textureHeight
     *            The height texture of your model
     */
    public CraftStudioModelSon(String modid, String modelNameIn, int textureWidth, int textureHeight) {
        this(new ResourceLocation(modid, modelNameIn), textureWidth, textureHeight);
    }

    private CraftStudioModelSon(ResourceLocation modelIn, int textureWidth, int textureHeight) {

        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        CSReadedModel rModel = RegistryHandler.modelRegistry.getObject(modelIn);
        if (rModel == null)
            throw new CSResourceNotRegisteredException(modelIn.toString());
        CSModelRendererOvercasted modelRend;

        for (CSReadedModelBlock rBlock : rModel.getParents()) {
            modelRend = this.generateCSModelRend(rBlock);
            this.parentBlocks.add(modelRend);
            this.generateChild(rBlock, modelRend);
        }
    }

    /** Generate childs part of a model */
    private void generateChild(CSReadedModelBlock rParent, CSModelRendererOvercasted parent) {
        CSModelRendererOvercasted modelRend;
        for (CSReadedModelBlock rBlock : rParent.getChilds()) {
            modelRend = this.generateCSModelRend(rBlock);
            parent.addChild(modelRend);
            this.generateChild(rBlock, modelRend);
        }
    }

    /** Generate CSModelRendererOvercasted from readed model block */
    private CSModelRendererOvercasted generateCSModelRend(CSReadedModelBlock rBlock) {
        CSModelRendererOvercasted modelRend = new CSModelRendererOvercasted(this, rBlock.getName(), rBlock.getTexOffset()[0], rBlock.getTexOffset()[1]);
        if (rBlock.getVertex() != null) {
            PositionTextureVertex vertices[] = new PositionTextureVertex[8];
            for (int i = 0; i < 8; i++)
                vertices[i] = new PositionTextureVertex(rBlock.getVertex()[i][0], rBlock.getVertex()[i][1], rBlock.getVertex()[i][2], 0.0F, 0.0F);
            modelRend.addBox(vertices, CSModelBox.getTextureUVsForRect(rBlock.getTexOffset()[0], rBlock.getTexOffset()[1], rBlock.getSize().x,
                    rBlock.getSize().y, rBlock.getSize().z));
        }
        else
            modelRend.addBox(-rBlock.getSize().x / 2, -rBlock.getSize().y / 2, -rBlock.getSize().z / 2, rBlock.getSize().x, rBlock.getSize().y,
                    rBlock.getSize().z);
        modelRend.setDefaultRotationPoint(rBlock.getRotationPoint().x, rBlock.getRotationPoint().y, rBlock.getRotationPoint().z);
        modelRend.setInitialRotationMatrix(rBlock.getRotation().x, rBlock.getRotation().y, rBlock.getRotation().z);
        modelRend.setDefaultOffset(rBlock.getOffset().x, rBlock.getOffset().y, rBlock.getOffset().z);
        modelRend.setDefaultStretch(rBlock.getStretch().x, rBlock.getStretch().y, rBlock.getStretch().z);
        modelRend.setTextureSize(this.textureWidth, this.textureHeight);
        return modelRend;
    }
    
    public void translateToHand(String... armParents) {
    	for(int i = 0; i < armParents.length; i++) {
    		CSModelRendererOvercasted box = getModelRendererFromName(armParents[i]);
    		GlStateManager.translate(box.rotationPointX * 0.0625, box.rotationPointY * 0.0625, box.rotationPointZ * 0.0625);
    		GlStateManager.multMatrix(MathHelper.makeFloatBuffer(box.getRotationMatrix()));
    		GlStateManager.translate(box.offsetX * 0.0625, box.offsetY * 0.0625, box.offsetZ * 0.0625);
    	}
    }

    /** Render methods for an Entity */
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	
        for (int i = 0; i < this.parentBlocks.size(); i++)
            this.parentBlocks.get(i).render(scale);
    }

    /** Getter */
    public List<CSModelRendererOvercasted> getParentBlocks() {
        return this.parentBlocks;
    }

}
