package me.ltsoveranakin.ghoulish.client.features.commands;

import me.ltsoveranakin.ghoulish.client.event.sub.Subscriptions;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.packet.ISubCPacket;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.suggest.CommandSuggestor;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.ArgumentParser;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.commands.*;
import me.ltsoveranakin.ghoulish.client.util.ChatUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

public class CommandManager implements ISubCPacket {
    public static final List<Command> COMMANDS = new ArrayList<>();

    public static void init() {
        addCommands();

        COMMANDS.sort(Comparator.comparing(Command::getName));

        Subscriptions.addSub(new CommandManager());
        Subscriptions.addSub(new CommandSuggestor());
    }

    private static void addCommands() {
        add(new ListCommand());
        add(new HelpCommand());
        add(new ToggleCommand());
        add(new SetCommand());
        add(new BindCommand());
        add(new GetCommand());
        add(new OfflineAuthCommand());
        add(new ConfigCommand());
        add(new PrefixCommand());
    }

    private static void add(Command cmd) {
        COMMANDS.add(cmd);
    }

    public static @Nullable Command getCommand(String cmdName) {
        for (Command cmd : COMMANDS) {
            if (cmd.getName().equalsIgnoreCase(cmdName)) {
                return cmd;
            }
        }
        return null;
    }

    @Override
    public void onCPacket(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ChatMessageC2SPacket chatPacket) {
            ArgumentParser parsed = ArgumentParser.tryParseArgs(chatPacket.chatMessage());
            if (parsed != null) {
                ci.cancel();
                try {
                    Command command = getCommand(parsed.commandName());
                    if (command == null) {
                        ChatUtil.error("Unknown command");
                        return;
                    }
                    command.commandIn(parsed.arguments());
                } catch (ParseException e) {
                    ChatUtil.error("Error parsing command arguments");
                } catch (InsufficientArgumentException e) {
                    ChatUtil.error("Insufficient arguments");
                }
            }
        }
    }
}
