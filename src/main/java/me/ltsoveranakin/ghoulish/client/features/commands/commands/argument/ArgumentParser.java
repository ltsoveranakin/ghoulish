package me.ltsoveranakin.ghoulish.client.features.commands.commands.argument;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public record ArgumentParser(String commandName, String[] arguments) {

    @Nullable
    public static ArgumentParser tryParseArgs(String message) {
        String msg = message.toLowerCase();

        if (msg.startsWith(Command.PREFIX)) {
            String[] spl = msg.substring(Command.PREFIX.length()).split(" ");
            if (spl.length == 0) return null;
            String commandName = spl[0];
            String[] arguments = Arrays.copyOfRange(spl, 1, spl.length);
            return new ArgumentParser(commandName, arguments);
        }

        return null;
    }
}
