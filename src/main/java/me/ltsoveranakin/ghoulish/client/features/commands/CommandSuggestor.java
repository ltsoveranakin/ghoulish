package me.ltsoveranakin.ghoulish.client.features.commands;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubHudRender;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubKey;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.ArgumentParser;
import me.ltsoveranakin.ghoulish.client.misc.MinecraftInstance;
import me.ltsoveranakin.ghoulish.client.mixin.AccessorChatScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class CommandSuggestor implements ISubHudRender, ISubKey, MinecraftInstance {
    private String suggestionSubStr;
    @Nullable
    private TextFieldWidget chatTextField;

    @Override
    public void onRender(DrawContext ctx, float tickDelta, boolean isGui) {
        chatTextField = null;

        if (mc.currentScreen instanceof ChatScreen) {
            suggestionSubStr = null;
            chatTextField = ((AccessorChatScreen) mc.currentScreen).getChatField();
            ArgumentParser parsed = ArgumentParser.tryParseArgs(chatTextField.getText());

            if (parsed == null) {
                return;
            }

            String commandName = parsed.commandName();

            if (parsed.arguments().length == 0) {
                for (Command command : CommandManager.COMMANDS) {
                    if (!command.getName().isEmpty() && command.getName().startsWith(commandName)) {
                        suggestionSubStr = command.getName().substring(commandName.length());
                        chatTextField.setSuggestion(suggestionSubStr);
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

                suggestionSubStr = suggestion.substring(finalArgument.length());
                chatTextField.setSuggestion(suggestionSubStr);
            }
        }
    }

    @Override
    public void onKey(long window, int key, int scancode, int action, int modifier) {
        if (chatTextField == null || suggestionSubStr == null || action != GLFW.GLFW_PRESS || key != GLFW.GLFW_KEY_TAB)
            return;
        chatTextField.setText(chatTextField.getText() + suggestionSubStr);
    }
}
