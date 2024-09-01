package org.example.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class viewYT extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        String dev_kira_ID = event.getUser().getId();

        if((dev_kira_ID.equalsIgnoreCase("576834455306633216" ))|| (dev_kira_ID.equalsIgnoreCase("677251211187060751"))) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("YouTube Payments");
            embed.setColor(Constants.EMBED_COLOUR);
            event.replyEmbeds(embed.build()).queue();
        }  else {
            event.reply("You do not have the permission to run this command").setEphemeral(true).queue();
        }
            super.onSlashCommandInteraction(event);
    }
}
