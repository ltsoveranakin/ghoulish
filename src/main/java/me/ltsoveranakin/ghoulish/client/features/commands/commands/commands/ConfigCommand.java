package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.EnumArgument;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.StringArgument;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;
import me.ltsoveranakin.ghoulish.client.storage.config.ConfigFile;
import me.ltsoveranakin.ghoulish.client.util.ChatUtil;

import java.io.File;
import java.net.URL;

public class ConfigCommand extends Command {
    private final EnumArgument<ConfigAction> configAction = addEnum("config action", "the action to perform", ConfigAction.class);
    private final StringArgument configName = addStr("config name", "the name of the config to set or save").optional();
    public ConfigCommand() {
        super("config", "manages config");
    }


    @Override
    protected void handleCommandImpl() {
        switch (configAction.get()) {
            case LOAD:
                loadAction();
                break;
            case SAVE:
                saveAction();
                break;
            case LIST:
                listAction();
                break;
        }
    }

    private void loadAction() {
        if (!ConfigFile.doesExist(configName.get())) {
            ChatUtil.info("That config doesn't exist");
            return;
        }

        StorageHandler.loadNewConfig(configName.get());
        info("loaded config " + configName.get());
    }

    private void saveAction() {
        ConfigFile tmpConfig = new ConfigFile(configName.get());

        try {
            tmpConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
            info("failed to save config");
            return;
        }

        info("saved new config to " + tmpConfig.getFile());
    }

    private void listAction() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n Local:");

        listFromDir(StorageHandler.GHOULISH_CONFIG_PATH, sb);

        URL url = getClass().getResource("/ghoulish/default_configs");
        File defaultConfigs = new File(url.getPath());
        sb.append("\n Default:");
        listFromDir(defaultConfigs, sb);
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

    private enum ConfigAction {
        LOAD,
        SAVE,
        LIST
    }
}
