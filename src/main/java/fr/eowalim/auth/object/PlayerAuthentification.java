package fr.eowalim.auth.object;

import fr.eowalim.auth.Main;
import fr.eowalim.auth.database.tables.TableAuth;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe Authentificator qui gère l'authentification du joueur.
 * @author Eowalim
 * @version 22/04/2020
 */

public class PlayerAuthentification {

    /**
     * Attributs de la classe Authentificator
     */
    private Player player;
    private TableAuth log = Main.getInstance().getTableAuth();
    private FileConfiguration cfg = Main.getInstance().getConfig();

    /**
     * Constructeur par initialisation
     * @param player le joueur qui a rejoint le serveur
     */
    public PlayerAuthentification(Player player) {
        this.player = player;
    }

    /**
     * Récupère le joueur
     * @return player
     */
    private Player getPlayer() {
        return player;
    }

    /**
     * Check si le joueur est bien connecté au "compte"
     * @return boolean
     */
    public boolean isAUTH() { return Main.getInstance().getPlayersLogged().contains(this.getPlayer()); }

    /**
     * Retire le joueur de la liste des comptes connectés
     */
    public void delete(){
        if(this.isAUTH()){
            Main.getInstance().getPlayersLogged().remove(this.getPlayer());
        }
    }

    /**
     * Permet la connexion au compte du joueur
     * @param pass le mot de passe
     */
    public void login(String pass){
        if(log.hasAccount(this.getPlayer().getDisplayName())){
            if(checkPassword(pass)){
                Main.getInstance().getPlayersLogged().add(this.getPlayer());
                Main.getInstance().getPlayerAuth().remove(this.getPlayer());
                this.getPlayer().sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.login_sucess")));
            } else {
                this.getPlayer().sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.login_wrongPass")));
            }
        } else {
            this.getPlayer().sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.login_noAccount")));
        }
    }

    /**
     * Permet l'enregistrement du compte du joueur
     * @param pass1 le mot de passe
     * @param pass2 le mot de passe répété
     */
    public void register(String pass1, String pass2){
        if(!log.hasAccount(this.getPlayer().getDisplayName())){
            if(pass1.equals(pass2)){
                log.createAccount(this.getPlayer().getDisplayName(), BCrypt.hashpw(pass1, BCrypt.gensalt(12)));
                Main.getInstance().getPlayersLogged().add(this.getPlayer());
                Main.getInstance().getPlayerAuth().remove(this.getPlayer());
                this.getPlayer().sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.register_sucess")));
            } else {
                this.getPlayer().sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.register_passDiff")));
            }
        } else {
            this.getPlayer().sendMessage(formatString(cfg.getString("prefix")) + formatString(cfg.getString("messages.register_accountExist")));
        }
    }

    /**
     * Check si le mot de passe entré est cohérent à celui lors la création du compte
     * @param password le mot de passe entré
     * @return boolean
     */
    private boolean checkPassword(String password){
        return BCrypt.checkpw(password, log.getHashPassword(this.getPlayer().getDisplayName()));
    }

    /**
     * Lance un timer, qui est le temps que le joueur a pour se connecter ou s'enregistrer
     */
    public void timer(){
        final Player p = this.getPlayer();
        final int kickTime = Main.getInstance().getKickTime();
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
              if(!isAUTH()){
                  p.kickPlayer(formatString(cfg.getString("messages.kick_message")));
              }
            }
        }, (20* kickTime));
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
