package me.ltsoveranakin.ghoulish.client.features.modules.conglomeration.conglomeration;

import me.ltsoveranakin.ghoulish.client.features.modules.conglomeration.Conglomeration;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.combat.*;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.autoclicker.autoclicker.LeftClicker;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.autoclicker.autoclicker.RightClicker;

public class AttackHelper extends Conglomeration {
    public AttackHelper() {
        super("Attack Helper", "Aim Assist & Trigger Bot");

        allow(AimAssist.class);
        allow(TriggerBot.class);

        disallow(RightClicker.class);
        disallow(LeftClicker.class);
    }
}
