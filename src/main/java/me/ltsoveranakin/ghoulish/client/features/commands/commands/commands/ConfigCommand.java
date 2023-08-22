package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;
import me.ltsoveranakin.ghoulish.client.storage.config.ConfigFile;
import me.ltsoveranakin.ghoulish.client.util.ChatUtil;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config", "manages config");
    }

    @Override
    public boolean commandIn(String[] args) throws Exception {
        if (args[0].equals("set")) {
            if (!ConfigFile.doesExist(args[1])) {
                ChatUtil.info("That config doesn't exist");
                return false;
            }

            StorageHandler.loadNewConfig(args[1]);
            return true;
        }

        return false;
    }
}
