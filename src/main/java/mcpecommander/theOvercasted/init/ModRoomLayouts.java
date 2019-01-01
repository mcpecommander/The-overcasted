package mcpecommander.theOvercasted.init;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mcpecommander.theOvercasted.Reference;
import mcpecommander.theOvercasted.maze.RoomLayout;
import mcpecommander.theOvercasted.maze.RoomLayout.RoomType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

public class ModRoomLayouts {
	
	public static RegistrySimple<RoomType, Map<Integer, RoomLayout>> layouts ;
	
	public static void init() {
		layouts = new RegistrySimple<>();
		for(RoomType type : RoomType.values()) {
			layouts.putObject(type, Maps.newHashMap());
		}
	}
	
	
	public static void initLayouts(String chapter, RoomType size) {
		boolean done = false;
		int i = 0;
		while(!done) {
			ResourceLocation location = new ResourceLocation(Reference.MODID, "maps/" + chapter + "/" + size.toString().toLowerCase() + "_" + i + ".json");
			IResource resource = null;
			BufferedReader reader = null;
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			try {
				resource = Minecraft.getMinecraft().getResourceManager().getResource(location);
				InputStream inputstream = resource.getInputStream();
				reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
				RoomLayout layout = gson.fromJson(reader, RoomLayout.class);
				layouts.getObject(size).put(i, layout);
			}catch(Exception e) {
				//This is really an ineffective way to know when to stop reading files and is prone to a lot of errors,
				//but minecraft is an asshole and does not have a way to get all resources inside the assets folder.
				done = true;
			}finally {
				IOUtils.closeQuietly(resource, reader);
			}
			
			i++;
		}

		
	}

}