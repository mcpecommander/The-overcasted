package mcpecommander.theOvercasted.capability.follower;

import java.util.UUID;
import java.util.concurrent.Callable;

public class Follower implements IFollower{
	
	private UUID follower = UUID.randomUUID();

	@Override
	public UUID getFollower() {
		return follower;
	}

	@Override
	public void setFollower(UUID entity) {
		this.follower = entity;
		
	}
	
	public static class Factory implements Callable<IFollower> {

		@Override
		public IFollower call() throws Exception {
			return new Follower();
		}
	}

}
