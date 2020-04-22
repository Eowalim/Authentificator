package fr.eowalim.auth.commands;

import fr.eowalim.auth.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class CMDLogger implements CommandExecutor {

    final FileConfiguration cfg = Main.getInstance().getConfig();

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (label.equalsIgnoreCase("register")) {
                if(player.hasPermission("auth.command.use")){
                    if (args.length != 2) {
                        player.sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.cmd_register")));
                    } else {
                        Main.getInstance().getPlayerAuth().get(player).register(args[0], args[1]);
                    }
                } else {
                    player.sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.permission")));
                }

            }

            else if (label.equalsIgnoreCase("login")) {
                if(player.hasPermission("auth.command.use")){
                    if (args.length != 1) {
                        player.sendMessage(formatString(cfg.getString("prefix"))+ formatString(cfg.getString("messages.cmd_login")));
                    } else {
                        Main.getInstance().getPlayerAuth().get(player).login(args[0]);
                    }
                } else {
                    player.sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.permission")));
                }

            }

        }

        return false;
    }

    private String formatString(String msg){
        return msg.replace("&", "§");
    }
}
