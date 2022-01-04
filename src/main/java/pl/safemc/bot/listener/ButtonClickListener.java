package pl.safemc.bot.listener;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import org.jetbrains.annotations.NotNull;
import pl.safemc.bot.TicketBot;
import pl.safemc.bot.util.Messenger;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kamil on 04.01.2022
 */

@RequiredArgsConstructor
public final class ButtonClickListener extends ListenerAdapter {

    private final TicketBot ticketBot;
    private int userCount;

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        var member = event.getMember();
        var guild = event.getGuild();
        var category = event.getJDA().getCategoryById(ticketBot.getConfiguration().getString("settings.ticket_category_id"));
        if (Objects.isNull(member) || Objects.isNull(guild) || Objects.isNull(category) || member.getUser().isBot()) {
            return;
        }

        if (event.getComponentId().equals("create_ticket")) {
            for (var channel : category.getTextChannels()) {
                if (channel.getName().endsWith(member.getUser().getId())) {
                    userCount++;
                    if (userCount >= 1) {
                        Messenger.sendPrivateMessage(member.getUser(), ticketBot.getConfiguration().getString("settings.ticket_is_already_opened"));
                        event.deferEdit().complete();
                        return;
                    }
                }
            }

            var supportChannel = category.createTextChannel("ticket-" + member.getUser().getId()).complete();
            var builder = new EmbedBuilder()
                    .setColor(ticketBot.getColor())
                    .setDescription(ticketBot.getConfiguration().getString("ticket_embed.description").replace("%user%", member.getUser().getAsMention()));

            supportChannel.sendMessage(new MessageBuilder().setEmbeds(builder.build()).build()).setActionRow(Button.of(ButtonStyle.DANGER, "close_ticket", ticketBot.getConfiguration().getString("ticket_embed.close_ticket"), Emoji.fromUnicode("U+1F510"))).complete();
            supportChannel.sendMessage(member.getAsMention()).complete().delete().completeAfter(5L, TimeUnit.MILLISECONDS);
            supportChannel.putPermissionOverride(member).setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY).complete();
            event.deferEdit().complete();
            return;
        }

        if (event.getChannel().getName().startsWith("ticket-") && event.getComponentId().equals("close_ticket")) {
            event.getTextChannel().delete().complete();
        }
    }

}
