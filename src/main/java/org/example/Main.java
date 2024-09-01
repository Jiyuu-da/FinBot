package org.example;


import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import org.example.listeners.*;
import org.example.listeners.db.insertUser;
import org.example.listeners.db.insertUserSP;
import org.example.listeners.db.updateUser;
import org.example.listeners.db.updateUserSP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private final Dotenv config;
    private final ShardManager shardManager;
    public static boolean maintenance = false;
    public Main() throws LoginException {

        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setActivity(Activity.listening("Looking For Payment"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build();

        shardManager.addEventListener(new Hello());

        shardManager.addEventListener(new insertUser());
        shardManager.addEventListener(new updateUser());


        shardManager.addEventListener(new insertUserSP());
        shardManager.addEventListener(new updateUserSP());
//        shardManager.addEventListener(new viewYT());





        shardManager.addEventListener(new slashCommandManager());
    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }
    public static void main(String[] args) throws LoginException {
        try {
            Main bot = new Main();
        } catch(LoginException e) {
            System.out.println("Error: Provided bot token is invalid");
        }
    }
}
