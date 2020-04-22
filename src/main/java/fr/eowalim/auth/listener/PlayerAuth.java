package fr.eowalim.auth.listener;

import fr.eowalim.auth.Main;
import fr.eowalim.auth.database.tables.TableAuth;
import fr.eowalim.auth.object.PlayerAuthentification;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

/**
 * Classe PlayerAuth qui gère les joueurs authentifié et non authentifié.
 * @author Eowalim
 * @version 22/04/2020
 */

public class PlayerAuth implements Listener {

    /**
     * Attributs de la classe PlayerAuth
     */
    final TableAuth log = Main.getInstance().getTableAuth();
    final FileConfiguration cfg = Main.getInstance().getConfig();


    /**
     * Événement lorsqu'un joueur se connecte au serveur
     * @param event join
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Main.getInstance().getPlayerAuth().put(event.getPlayer(), new PlayerAuthentification(event.getPlayer()));
        Main.getInstance().getPlayerAuth().get(event.getPlayer()).timer();
        if(log.hasAccount(event.getPlayer().getDisplayName())){
            event.getPlayer().sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.join_login")));
        } else {
            event.getPlayer().sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.join_register")));
        }
    }

    /**
     * Événement lorsqu'un joueur quitte le serveur
     * @param event quit
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Main.getInstance().getPlayerAuth().get(event.getPlayer()).delete();
    }

    /**
     * Événement lorsqu'un joueur est en mouvement
     * @param event move
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if (!Main.getInstance().getPlayersLogged().contains(event.getPlayer())){
            event.setTo(event.getFrom());
        }
    }

    /**
     * Événement lorsqu'un joueur subit des dégâts
     * @param event join
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof Player){
            Player player = (Player) entity;
            if (!Main.getInstance().getPlayersLogged().contains(player)){
                event.setCancelled(true);
            }
        }
    }

    /**
     * Événement lorsqu'un joueur drop un item
     * @param event drop
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if (!Main.getInstance().getPlayersLogged().contains(event.getPlayer())){
            event.setCancelled(true);
        }
    }

    /**
     * Événement lorsqu'un joueur click dans un inventaire
     * @param event click inventaire
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!Main.getInstance().getPlayersLogged().contains(player)){
            event.setCancelled(true);
        }
    }

    /**
     * Retourne le message non formaté, formaté
     * @param msg le message à formater
     * @return message formaté
     */
    private String formatString(String msg){
        return msg.replace("&", "§");
    }
}
