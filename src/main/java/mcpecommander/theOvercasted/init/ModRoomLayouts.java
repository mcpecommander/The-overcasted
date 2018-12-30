package mcpecommander.theOvercasted.init;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.maze.RoomLayout;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

public class ModRoomLayouts {
	
	public static RegistrySimple<Integer, RoomLayout> basement_normal_layouts ;
	public static RegistrySimple<Integer, RoomLayout> basement_narrow_layouts ;
	public static RegistrySimple<Integer, RoomLayout> basement_wide_layouts ;
	
	public static void init() {
		basement_normal_layouts = new RegistrySimple<>();
		basement_narrow_layouts = new RegistrySimple<>();
		basement_wide_layouts = new RegistrySimple<>();
	}
	
	
	public static void initLayouts(String chapter, String size) {
		boolean done = false;
		int i = 0;
		while(!done) {
			ResourceLocation location = new ResourceLocation(Reference.MODID, "maps/" + chapter + "/" + size + "_" + i + ".json");
			IResource resource = null;
			BufferedReader reader = null;
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			try {
				resource = Minecraft.getMinecraft().getResourceManager().getResource(location);
				InputStream inputstream = resource.getInputStream();
				reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
				RoomLayout layout = gson.fromJson(reader, RoomLayout.class);
				Field field = ModRoomLayouts.class.getField(chapter + "_" + size + "_" + "layouts");
				((RegistrySimple)field.get(null)).putObject(i, layout);
//    			basement_normal_layouts.putObject(i, layout);
			}catch(Exception e) {
				done = true;
			}finally {
				IOUtils.closeQuietly(resource, reader);
			}
			
			i++;
		}

		
	}

}