package org.example.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Hello extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        if(command.equalsIgnoreCase("hello")) {
            event.reply("Come on Pay up").queue();
        }
        super.onSlashCommandInteraction(event);
    }
}
