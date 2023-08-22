package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.combat;

import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;

public class Reach extends Module {
    public FloatSetting BLOCK_REACH = addFloat("blockreach", "reach for blocks", 4.5F, 4.5f, 5.5f);
    // TODO: Fix reach being able to reach the moon and back
    public Reach() {
        super("reach", "reach further", Category.COMBAT);
//        GameRenderer:633
    }
}
