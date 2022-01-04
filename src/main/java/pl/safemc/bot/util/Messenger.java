package pl.safemc.bot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import pl.safemc.bot.TicketBot;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kamil on 03.01.2022
 */

public final class Messenger {

    private static final TicketBot ticketBot = TicketBot.getInstance();

    private Messenger() {}

    public static void sendPrivateMessage(User user, String message) {
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }

    public static void sendPrivateMessage(User user, Message message) {
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }

    public static void sendMessage(MessageReceivedEvent event, EmbedBuilder embedBuilder) {
        var user = event.getAuthor();

        embedBuilder.setAuthor(user.getName(), user.getAvatarUrl(), user.getAvatarUrl())
                .setTimestamp(Instant.now())
                .setFooter("Date ", event.getAuthor().getAvatarUrl());

        var message = new MessageBuilder().setEmbeds(embedBuilder.build()).build();
        if (event.getChannelType() == ChannelType.PRIVATE) {
            sendPrivateMessage(user, message);
            return;
        }

        event.getTextChannel().sendMessage(message).complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }

    public static void sendTicketMessage(MessageReceivedEvent event, EmbedBuilder embedBuilder) {
        var message = new MessageBuilder().setEmbeds(embedBuilder.build()).build();

        event.getTextChannel().sendMessage(message)
                .setActionRow(Button.of(ButtonStyle.SUCCESS, "create_ticket", ticketBot.getConfiguration().getString("ticket_create_embed.button_name"), Emoji.fromUnicode("U+1F3F7")))
                .queue();
    }

}
