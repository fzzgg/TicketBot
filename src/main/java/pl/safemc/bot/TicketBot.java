package pl.safemc.bot;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import pl.safemc.bot.command.api.CommandManager;
import pl.safemc.bot.configuration.Configuration;
import pl.safemc.bot.listener.ButtonClickListener;
import pl.safemc.bot.listener.MessageReceivedListener;

import java.awt.*;
import java.io.File;

/**
 * Created by Kamil on 04.01.2022
 */

@Getter
public final class TicketBot {

    @Getter
    private static TicketBot instance;

    private final Configuration configuration;
    private final CommandManager commandManager;
    private final JDA jda;
    private String prefix;
    private Color color;

    TicketBot() throws Throwable {
        instance = this;

        configuration = new Configuration();
        commandManager = new CommandManager(this);

        configuration.load(new File("config.toml"));

        jda = JDABuilder.createDefault(getConfiguration().getString("settings.token"))
                .setActivity(Activity.listening(getConfiguration().getString("settings.activity")))
                .setStatus(OnlineStatus.fromKey(getConfiguration().getString("settings.online_status")))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();
    }

    public void init() {
        prefix = getConfiguration().getString("settings.prefix");
        color = new Color(Integer.parseInt(getConfiguration().getString("ticket_create_embed.color").replaceFirst("#", ""), 16));

        commandManager.initCommands();
        jda.addEventListener(new ButtonClickListener(this),
                new MessageReceivedListener(this));
    }

}
