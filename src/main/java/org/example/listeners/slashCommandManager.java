package org.example.listeners;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.Main;

import javax.swing.text.html.Option;
import java.util.ArrayList;
public class slashCommandManager extends ListenerAdapter {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        ArrayList<CommandData> commandData = new ArrayList<>();

        commandData.add(Commands.slash("hello", "say hello"));

        OptionData insertNameYT = new OptionData(OptionType.STRING, "name", "name of the user",true);
        OptionData insertAmountYT = new OptionData(OptionType.STRING, "amount", "amount to be paid by them",true);
        OptionData insertDueDateYT = new OptionData(OptionType.STRING, "due_date", "The due date of the user", true);
        commandData.add(Commands.slash("registeryt", "register a user in YT").addOptions(insertNameYT, insertAmountYT, insertDueDateYT));

        OptionData updateNameYT = new OptionData(OptionType.STRING, "name", "name of the user",true);
        OptionData updateAmountYT = new OptionData(OptionType.STRING, "amount", "amount paid",true);
        OptionData updateAdvanceMonthsYT = new OptionData(OptionType.INTEGER, "advance_months", "no. of months paid in advance", false);
        commandData.add(Commands.slash("updateyt", "update a user in YT").addOptions(updateNameYT, updateAmountYT, updateAdvanceMonthsYT));

        OptionData insertNameSP = new OptionData(OptionType.STRING, "name", "name of the user",true);
        OptionData insertAmountSP = new OptionData(OptionType.STRING, "amount", "amount to be paid by them",true);
        OptionData insertDueDateSP = new OptionData(OptionType.STRING, "due_date", "The due date of the user", true);
        commandData.add(Commands.slash("registersp", "register a user in spotify").addOptions(insertNameSP, insertAmountSP, insertDueDateSP));

        OptionData updateNameSP = new OptionData(OptionType.STRING, "name", "name of the user",true);
        OptionData updateAmountSP = new OptionData(OptionType.STRING, "amount", "amount paid",true);
        OptionData updateAdvanceMonthsSP = new OptionData(OptionType.INTEGER, "advance_months", "no. of months paid in advance", false);
        commandData.add(Commands.slash("updatesp", "update a user in spotify").addOptions(updateNameSP, updateAmountSP, updateAdvanceMonthsSP));


//        commandData.add(Commands.slash("viewyt", "look at users for yt premium"));

        event.getGuild().updateCommands().addCommands(commandData).queue();
        super.onGuildReady(event);
    }
}
