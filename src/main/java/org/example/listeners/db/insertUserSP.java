package org.example.listeners.db;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.time.LocalDate;

public class insertUserSP extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();
        String dev_kira_ID = event.getUser().getId();

        if((dev_kira_ID.equalsIgnoreCase("576834455306633216" ))|| (dev_kira_ID.equalsIgnoreCase("677251211187060751"))) {
            if(command.equalsIgnoreCase("registersp")) {
                String name = event.getOption("name").getAsString();
                double amount = event.getOption("amount").getAsDouble();
                LocalDate lastPayment = LocalDate.now();
                LocalDate dueDate = LocalDate.parse(event.getOption("due_date").getAsString());

                try {
                    DBSetupSP.insertUsersp(name, amount, lastPayment, dueDate);
                    event.reply("User inserted successfully!").setEphemeral(false).queue();
                } catch (SQLException e) {
                    String[] eArr = e.toString().split(" ");
                    StringBuilder eRes = new StringBuilder();

                    for(int i=1; i<eArr.length; i++) {
                        eRes.append(eArr[i]);

                        if (i < eArr.length - 1) {
                            eRes.append(" ");
                        }
                    }
                    System.out.println(eRes.toString());
                    event.reply("Error inserting user." + eRes.toString()).setEphemeral(false).queue();
                    e.printStackTrace();
                }
            }
        } else {
            event.reply("You do not have the permission to run this command").setEphemeral(true).queue();
        }
        super.onSlashCommandInteraction(event);
    }
}
