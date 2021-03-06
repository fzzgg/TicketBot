package pl.safemc.bot.listener;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import pl.safemc.bot.TicketBot;
import pl.safemc.bot.util.EmbedUtil;
import pl.safemc.bot.util.Messenger;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Kamil on 04.01.2022
 */

@RequiredArgsConstructor
public final class MessageReceivedListener extends ListenerAdapter {

    private final TicketBot ticketBot;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        var message = event.getMessage();
        var displayText = message.getContentDisplay();
        var user = event.getAuthor();
        if (!displayText.startsWith(ticketBot.getPrefix()) || displayText.length() == 1) {
            return;
        }

        try {
            message.delete().queue();
            var args = displayText.split(" ");
            var fixedArgs = Arrays.copyOfRange(args, 1, args.length);
            var command = ticketBot.getCommandManager().findCommand(args[0].toLowerCase());

            if (Objects.isNull(command)) {
                Messenger.sendMessage(event, new EmbedBuilder()
                        .setColor(new Color(255, 0, 33))
                        .setTitle(" ")
                        .setDescription(":no_entry_sign: Command not found! **" + args[0] + "**")
                        .setAuthor(user.getName(), user.getAvatarUrl(), user.getAvatarUrl())
                        .setTimestamp(Instant.now())
                        .setFooter("Date ", event.getAuthor().getAvatarUrl()));
                return;
            }

            command.execute(event, user, fixedArgs);
        } catch (Exception ex) {
            Messenger.sendMessage(event, EmbedUtil.create(":no_entry_sign: Error!", ex.getMessage(), new Color(255, 0, 33)));
        }
    }


}
