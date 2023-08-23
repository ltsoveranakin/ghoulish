package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;
import me.ltsoveranakin.ghoulish.client.storage.config.ConfigFile;
import me.ltsoveranakin.ghoulish.client.util.ChatUtil;

import java.io.File;
import java.net.URL;

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
            info("loaded config " + args[1]);
            return true;
        } else if(args[0].equals("save")) {
            ConfigFile tmpConfig = new ConfigFile(args[1]);
            tmpConfig.save();

            info("saved new config to " + tmpConfig.getFile());
            return true;
        } else if (args[0].equals("list")) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n Local:");

            listFromDir(StorageHandler.GHOULISH_CONFIG_PATH, sb);

            URL url = getClass().getResource("/ghoulish/default_configs");
            File defaultConfigs = new File(url.getPath());
            sb.append("\n Default:");
            listFromDir(defaultConfigs, sb);
        }

        info("expected set, save, or list; got " + args[0]);

        return false;
    }

    private void listFromDir(File dir, StringBuilder sb) {
        for(File file: dir.listFiles()) {
            sb
                    .append("\n")
                    .append(file.getName())
                    .append(" - ")
                    .append(file.toPath());
        }
    }
}
