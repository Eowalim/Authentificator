package fr.eowalim.auth.database.tables;

import fr.eowalim.auth.Main;
import fr.eowalim.auth.database.DBConnection;
import fr.eowalim.auth.database.DBInfosConnection;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.Date;

/**
 * Classe TableAuth qui gère le stockages des comptes.
 * @author Eowalim
 * @version 22/04/2020
 */

public class TableAuth {

    /**
     * Attributs de la classe TableAuth
     */
    private DBConnection authConnection;
    private static final String TABLE = Main.getInstance().getConfig().getString("table_name");
    private static final FileConfiguration cfg = Main.getInstance().getConfig();


    /**
     * Constructeur par défaut qui permet de se connecter.
     */
    public TableAuth(){
        this.authConnection = new DBConnection(new DBInfosConnection(
                cfg.getString("database.host"),
                cfg.getString("database.user"),
                cfg.getString("database.pass"),
                cfg.getString("database.dbname"),
                cfg.getInt("database.port")));
    }

    /**
     * Permet de créer la table si elle n'existe pas
     */
    public void createTableLogger(){
        authConnection.update("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                "player_name VARCHAR(255)," +
                "password VARCHAR(255)," +
                "date TIMESTAMP)");
    }

    /**
     * Récupère la connexion de la table auth
     * @return authConnection
     */
    public DBConnection getAuthConnection() {
        return authConnection;
    }

    /**
     * Ferme la connexion à la base de donnée.
     */
    public void close(){
        try {
            this.authConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet d'ajouter un compte
     * @param name pseudo
     * @param hashPass mot de passe hashé
     */
    public void createAccount(String name, String hashPass){
        Main.getInstance().getTableAuth().getAuthConnection().update(String.format("INSERT INTO " + TABLE + "(player_name, password, date) VALUES ('%s', '%s', '%s')", name, hashPass, new Timestamp(new Date().getTime())));
    }

    /**
     * Check si le joueur à un compte
     * @param name pseudo
     * @return boolean
     */
    public boolean hasAccount(String name){
        return (boolean) Main.getInstance().getTableAuth().getAuthConnection().query("SELECT player_name FROM " + TABLE + " WHERE EXISTS(SELECT * FROM " + TABLE + " WHERE player_name  ='" + name + "')", rs ->{
            try {
                if(rs.next()){
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    /**
     * Récupère le mot de passe hashé du compte en questions
     * @param name pseuod
     * @return boolean
     */
    public String getHashPassword(String name){
        return (String) Main.getInstance().getTableAuth().getAuthConnection().query("SELECT password FROM " + TABLE + " WHERE player_name='" + name + "'", rs ->{
            try {
                if(rs.next()){
                    return rs.getString("password");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
