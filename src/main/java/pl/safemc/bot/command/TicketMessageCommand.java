package pl.safemc.bot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.safemc.bot.command.api.Command;
import pl.safemc.bot.command.api.CommandContext;
import pl.safemc.bot.util.Messenger;

/**
 * Created by Kamil on 04.01.2022
 */

@CommandContext(name = "ticket", permissions = { Permission.ADMINISTRATOR })
public final class TicketMessageCommand extends Command {

    @Override
    public void execute(MessageReceivedEvent event, User user, String... args) {
        Messenger.sendTicketMessage(event, new EmbedBuilder()
                .setColor(ticketBot.getColor())
                .setTitle(ticketBot.getConfiguration().getString("ticket_create_embed.title"))
                .setDescription(ticketBot.getConfiguration().getString("ticket_create_embed.description")));
    }

}
