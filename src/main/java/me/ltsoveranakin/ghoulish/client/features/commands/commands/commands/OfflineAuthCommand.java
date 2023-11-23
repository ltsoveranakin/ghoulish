package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.StringArgument;
import me.ltsoveranakin.ghoulish.client.misc.MCInst;
import net.minecraft.client.util.Session;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

public class OfflineAuthCommand extends Command implements MCInst {
    private final StringArgument name = addStr("name", "the offline name to log in as");

    public OfflineAuthCommand() {
        super("offlineauth", "logs in as an offline user");
    }

    @Override
    protected void handleCommandImpl() {
        try {
            Field field = mc.getClass().getDeclaredField("session");
            field.setAccessible(true);
            field.set(mc, new Session(name.get(), UUID.randomUUID().toString(), "0", Optional.empty(), Optional.empty(), Session.AccountType.LEGACY));
            info("Set offline session, relog to take effect.");
        } catch (Exception e) {

        }
    }
}
