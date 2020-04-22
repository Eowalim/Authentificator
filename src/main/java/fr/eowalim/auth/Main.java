package fr.eowalim.auth;

import fr.eowalim.auth.commands.CMDLogger;
import fr.eowalim.auth.database.tables.TableAuth;
import fr.eowalim.auth.listener.PlayerAuth;
import fr.eowalim.auth.object.PlayerAuthentification;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe Main qui est la classe principale.
 * @author Eowalim
 * @version 22/04/2020
 */

public class Main extends JavaPlugin {

    /**
     * Attributs de la classe Main
     */
    private static Main instance;
    private TableAuth tableAuth;
    private HashMap<Player, PlayerAuthentification> playerAuth = new HashMap<>();
    private List<Player> playersLogged = new ArrayList<>();
    private int kickTime;

    /**
     * Méthode qui s'exécute au lancement du plugin
     */
    @Override
    public void onEnable(){
        instance = this;
        this.tableAuth = new TableAuth();
        this.tableAuth.createTableLogger();
        saveDefaultConfig();
        registerListeners();
        registerCommands();
        this.kickTime = getConfig().getInt("timeKick");
        Bukkit.getConsoleSender().sendMessage(getConfig().get("cprefix") + ":" + getConfig().get("messages.plugin_enable"));
    }

    /**
     * Méthode qui s'exécute à l'arrêt du plugin
     */
    @Override
    public void onDisable(){this.tableAuth.close();}

    /**
     * Méthode qui enregistre les listeners
     */
    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerAuth(), this);
    }

    /**
     * Méthode qui enregistre les commandes
     */
    public void registerCommands() {
        Bukkit.getPluginCommand("register").setExecutor(new CMDLogger());
        Bukkit.getPluginCommand("login").setExecutor(new CMDLogger());
    }

    /**
     * Retourne l'instance du plugin
     * @return instance
     */
    public static Main getInstance() { return instance; }

    /**
     * Retourne la table d'authentification
     * @return tableAuth
     */
    public TableAuth getTableAuth() { return tableAuth; }

    /**
     * Récupère la liste des authentification en cours
     * @return playerAuth
     */
    public HashMap<Player, PlayerAuthentification> getPlayerAuth() { return playerAuth; }

    /**
     * Récupère la liste des joueur authentifié
     * @return playersLogged
     */
    public List<Player> getPlayersLogged() { return playersLogged; }

    /**
     * Récupère le temps au dela du quel le joueur est kick
     * @return kickTime
     */
    public int getKickTime() { return kickTime; }
}
