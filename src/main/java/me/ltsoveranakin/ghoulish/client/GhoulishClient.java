package me.ltsoveranakin.ghoulish.client;

import me.ltsoveranakin.ghoulish.client.event.EventInitializer;
import me.ltsoveranakin.ghoulish.client.features.commands.CommandManager;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class GhoulishClient implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("GHOULISH");
    public static final String VERS = "0.0.0";

    public static boolean loaded = false;


    @Override
    public void onInitializeClient() {
        long startTime = System.currentTimeMillis();

        ModuleManager.init();
        StorageHandler.init();
        CommandManager.init();
        EventInitializer.init();

        loaded = true;

        ModuleManager.MODULES.forEach(Module::postInit);

        long endTime = System.currentTimeMillis();

        LOG.info("Ghoulish init completed in " + (endTime - startTime) + " milliseconds.");
    }
}
