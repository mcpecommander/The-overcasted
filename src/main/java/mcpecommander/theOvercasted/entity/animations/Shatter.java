package mcpecommander.theOvercasted.entity.animations;

import javax.vecmath.Quat4f;

import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.client.util.MathHelper;
import com.leviathanstudio.craftstudio.common.animation.CustomChannel;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.EntityLiving;

public class Shatter extends CustomChannel {

	public Shatter() {
		super("shatter");
	}

	@Override
	public void update(CSModelRenderer part, IAnimated animated) {
		if (animated instanceof EntityLiving) {
			EntityLiving entity = (EntityLiving) animated;
			Quat4f quat = MathHelper.quatFromEuler(entity.getRNG().nextInt(720) - 360,
					entity.getRNG().nextInt(720) - 360, entity.getRNG().nextInt(720) - 360);
			Quat4f quat2 = part.getDefaultRotationAsQuaternion();
			quat.mul(quat2);
			part.getRotationMatrix().set(quat);
			part.getRotationMatrix().transpose();
			part.setRotationPoint(entity.getRNG().nextInt(10), entity.getRNG().nextInt(10), entity.getRNG().nextInt(10));
		}

	}

}
