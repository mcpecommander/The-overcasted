package mcpecommander.theOvercasted.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.maze.RoomLayout;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

public class ModRoomLayouts {
	
	public static RegistrySimple<Integer, RoomLayout> layouts ;
	
	public static void init() {
		layouts = new RegistrySimple<>();
	}
	
	
	public static void initLayouts() {
		boolean done = false;
		int i = 0;
		while(!done) {
			ResourceLocation location = new ResourceLocation(Reference.MODID, "maps/basement/normal_" + i + ".json");
			IResource resource = null;
			BufferedReader reader = null;
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			try {
				resource = Minecraft.getMinecraft().getResourceManager().getResource(location);
				InputStream inputstream = resource.getInputStream();
				reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
				RoomLayout layout = gson.fromJson(reader, RoomLayout.class);
    			layouts.putObject(i, layout);
			}catch(Exception e) {
				done = true;
			}finally {
				IOUtils.closeQuietly(resource, reader);
			}
			
			i++;
		}

		
	}

}