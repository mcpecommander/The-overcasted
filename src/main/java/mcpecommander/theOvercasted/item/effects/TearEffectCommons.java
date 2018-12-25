package mcpecommander.theOvercasted.item.effects;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Predicate;

import mcpecommander.theOvercasted.entity.entities.EntityBasicChampion;
import mcpecommander.theOvercasted.entity.entities.projectiles.EntityTear;
import mcpecommander.theOvercasted.maze.DungeonGenerator;
import mcpecommander.theOvercasted.maze.DungeonWorldProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.NonNullList;

public class TearEffectCommons {
	
	public static ITearEffect multiShot(int numberOfShots) {
		ITearEffect multiShot = new ITearEffect() {
			@Override
			public NonNullList<EntityTear> onCreation(NonNullList<EntityTear> tears) {
				int currentLength = tears.size();
				for(int x = 0; x < currentLength; x++) {
					for(int y = 0; y < numberOfShots; y++) {
						EntityTear tear = new EntityTear(tears.get(x).world, tears.get(x).shooter, tears.get(x).effectInts);
						tears.add(tear);
					}
				}
				return tears;
			}
		};
		return multiShot;
	}
	
	public static ITearEffect homingShot() {
		ITearEffect homing = new ITearEffect() {
			@Override
			public void onUpdate(EntityTear tear) {
				if(tear.hasTarget()) return;
				List<Entity> targets = tear.world.getEntitiesInAABBexcluding(tear, tear.getEntityBoundingBox().offset(tear.getPosition()).grow(3), new Predicate<Entity>() {

					@Override
					public boolean apply(Entity input) {
						if(input instanceof IMob && inRoom(tear).apply(input))return true;
						return false;
					}
					
				});
				if(!targets.isEmpty()) {
					Collections.sort(targets, new Sorter(tear));
					tear.setTarget(targets.get(0));
				}
				
			}
		};
		return homing;
	}
	
	public static ITearEffect boomerangShot() {
		ITearEffect boomerang = new ITearEffect() {
			@Override
			public NonNullList<EntityTear> onCreation(NonNullList<EntityTear> tears) {
				for(EntityTear tear : tears) {
				}
				return tears;
			}
			
			@Override
			public void onUpdate(EntityTear tear) {
				if(tear.getDistanceMoved() > tear.getRange()/8 && !tear.hasTarget()) {
					tear.setTarget(tear.shooter);
				}
				if(tear.getDistance(tear.shooter) < 1.5f) {
					tear.setTarget(null);
				}
			}
		};
		return boomerang;
	}
	
	private static final Predicate<Entity> inRoom(Entity entity){
		int tearChunkX = entity.getPosition().getX() >> 4;
		int tearChunkZ = entity.getPosition().getZ() >> 4;
		 return new Predicate<Entity>() {
			@Override
			public boolean apply(Entity input) {
				if (input.dimension == 100) {
					DungeonWorldProvider provider = (DungeonWorldProvider) input.world.provider;
					int chunkX = input.getPosition().getX() >> 4;
					int chunkZ = input.getPosition().getZ() >> 4;
					int roomLayout = provider.getDungeon().getLayout()[chunkX][chunkZ];
					switch (roomLayout) {
					case 1:
						return tearChunkX == chunkX && tearChunkZ == chunkZ;
					case 2:
						return checkNarrow(provider.getDungeon(), tearChunkX, tearChunkZ, chunkX, chunkZ);
					case 3:
					case 4:
					case 5:
					case 6:
						return checkWide(provider.getDungeon(), tearChunkX, tearChunkZ, chunkX, chunkZ);
					}
				}
				return false;
			}
		};
	}
		
	private static boolean checkWide(DungeonGenerator dungeon, int tearChunkX, int tearChunkZ, int chunkX,
			int chunkZ) {
		int[][] layout = dungeon.getLayout();
		switch(layout[chunkX][chunkZ]) {
		case 3:
			return (tearChunkX == chunkX && tearChunkZ == chunkZ) ||
					(tearChunkX == chunkX + 1 && tearChunkZ == chunkZ) ||
					(tearChunkX == chunkX && tearChunkZ == chunkZ + 1) ||
					(tearChunkX == chunkX + 1 && tearChunkZ == chunkZ + 1);
		case 4:
			return (tearChunkX == chunkX - 1 && tearChunkZ == chunkZ) ||
					(tearChunkX == chunkX && tearChunkZ == chunkZ) ||
					(tearChunkX == chunkX - 1 && tearChunkZ == chunkZ + 1) ||
					(tearChunkX == chunkX && tearChunkZ == chunkZ + 1);
		case 5:
			return (tearChunkX == chunkX && tearChunkZ == chunkZ - 1) ||
					(tearChunkX == chunkX + 1 && tearChunkZ == chunkZ - 1) ||
					(tearChunkX == chunkX && tearChunkZ == chunkZ) ||
					(tearChunkX == chunkX + 1 && tearChunkZ == chunkZ);
		case 6:
			return (tearChunkX == chunkX - 1 && tearChunkZ == chunkZ - 1) ||
					(tearChunkX == chunkX && tearChunkZ == chunkZ - 1) ||
					(tearChunkX == chunkX - 1 && tearChunkZ == chunkZ) ||
					(tearChunkX == chunkX && tearChunkZ == chunkZ);
		}
		return false;
	}
	
	public static boolean checkNarrow(DungeonGenerator dungeon, int tearChunkX, int tearChunkZ, int chunkX, int chunkZ) {
		int[][] layout = dungeon.getLayout();
		if(dungeon.isNarrowSouthNorth(chunkX, chunkZ)) {
			if(chunkZ - 1 > 1 && layout[chunkX][chunkZ - 1] == 2) {
				return (tearChunkX == chunkX && tearChunkZ == chunkZ) || (tearChunkX == chunkX && tearChunkZ == chunkZ - 1);
			}else if(chunkZ + 1 < dungeon.getMaxColumns() - 1 && layout[chunkX][chunkZ + 1] == 2) {
				return (tearChunkX == chunkX && tearChunkZ == chunkZ) || (tearChunkX == chunkX && tearChunkZ == chunkZ + 1);
			}else {
				return (tearChunkX == chunkX && tearChunkZ == chunkZ);
			}
		}else {
			if(chunkX - 1 > 1 && layout[chunkX - 1][chunkZ] == 2) {
				return (tearChunkX == chunkX && tearChunkZ == chunkZ) || (tearChunkX == chunkX - 1 && tearChunkZ == chunkZ);
			}else if(chunkX + 1 < dungeon.getMaxColumns() - 1 && layout[chunkX + 1][chunkZ] == 2) {
				return (tearChunkX == chunkX && tearChunkZ == chunkZ) || (tearChunkX == chunkX + 1 && tearChunkZ == chunkZ);
			}else {
				return (tearChunkX == chunkX && tearChunkZ == chunkZ);
			}
		}
	}
	
	
	private static class Sorter implements Comparator<Entity>
    {
        private final Entity entity;

        public Sorter(Entity entityIn)
        {
            this.entity = entityIn;
        }

        @Override
		public int compare(Entity p_compare_1_, Entity p_compare_2_)
        {
            double d0 = this.entity.getDistanceSq(p_compare_1_);
            double d1 = this.entity.getDistanceSq(p_compare_2_);

            if (d0 < d1)
            {
                return -1;
            }
            else
            {
                return d0 > d1 ? 1 : 0;
            }
        }
    }

}
