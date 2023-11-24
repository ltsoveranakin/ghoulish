package me.ltsoveranakin.ghoulish.client.features.commands.tabcomplete;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubHudRender;
import me.ltsoveranakin.ghoulish.client.features.commands.CommandManager;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.ArgumentParser;
import me.ltsoveranakin.ghoulish.client.misc.MinecraftInstance;
import me.ltsoveranakin.ghoulish.client.mixin.AccessorChatScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class CommandSuggest implements ISubHudRender, MinecraftInstance {
    @Override
    public void onRender(DrawContext ctx, float tickDelta, boolean isGui) {
        if (mc.currentScreen instanceof ChatScreen) {
            TextFieldWidget chatTextField = ((AccessorChatScreen) mc.currentScreen).getChatField();
            ArgumentParser parsed = ArgumentParser.tryParseArgs(chatTextField.getText());

            if (parsed == null) {
                return;
            }

            String commandName = parsed.commandName();

            if (parsed.arguments().length == 0) {
                for (Command command : CommandManager.COMMANDS) {
                    if (!command.getName().isEmpty() && command.getName().startsWith(commandName)) {
                        chatTextField.setSuggestion(command.getName().substring(commandName.length()));
                        return;
                    }
                }
            } else {
                Command command = CommandManager.getCommand(commandName);
                if (command == null) return;

                String suggestion = command.getSuggestion(parsed.arguments());
                if (suggestion == null) return;

                String finalArgument = parsed.arguments()[parsed.arguments().length - 1];
                if (!suggestion.startsWith(finalArgument)) return;

                chatTextField.setSuggestion(suggestion.substring(finalArgument.length()));
            }
        }
    }
}
