package org.example.listeners.db;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;
import java.time.LocalDate;

public class updateUserSP extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();
        String dev_kira_ID = event.getUser().getId();

        if((dev_kira_ID.equalsIgnoreCase("576834455306633216" ))|| (dev_kira_ID.equalsIgnoreCase("677251211187060751"))) {
            if(command.equalsIgnoreCase("updatesp")) {
                String name = event.getOption("name").getAsString();
                double amount = event.getOption("amount").getAsDouble();

                OptionMapping advanceOption = event.getOption("advance_months");
                int advance_months = 0;

                if(advanceOption != null) {
                    advance_months = event.getOption("advance_months").getAsInt();
                }

                System.out.println(advance_months);
                LocalDate lastPayment = LocalDate.now();


                try {
                    LocalDate dueDate = DBSetupSP.getDueDatesp(name);
                    DBSetupSP.updateUserAmountsp(name, amount, lastPayment, dueDate, advance_months);
                    event.reply("User updated successfully!").setEphemeral(true).queue();
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
                    event.reply("Error updating user." + eRes.toString()).setEphemeral(true).queue();
                    e.printStackTrace();
                }
            }
        } else {
            event.reply("You do not have the permission to run this command").setEphemeral(true).queue();
        }
        super.onSlashCommandInteraction(event);
    }
}
