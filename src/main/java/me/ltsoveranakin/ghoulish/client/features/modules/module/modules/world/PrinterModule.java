package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.world;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubTick;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import net.minecraft.client.world.ClientWorld;

public class PrinterModule extends Module implements ISubTick {
    public PrinterModule() {
        super("printer", "Automatically prints the loaded schematic (doesn't work)", Category.World);
    }

    @Override
    public void onTick(ClientWorld world) {

    }
}
