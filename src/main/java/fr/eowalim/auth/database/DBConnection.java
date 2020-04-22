package fr.eowalim.auth.database;

import org.bukkit.Bukkit;

import java.sql.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Classe DBConnection qui gère la connexion à la BDD.
 * @author Eowalim
 * @version 22/04/2020
 */

public class DBConnection {

    /**
     * Attributs de la classe DBConnection
     */
    private DBInfosConnection dbInfosConnection;
    private Connection connection;

    /**
     * Constructeur per initialisation
     * @param dbInfosConnection les informations pour se co à la BDD
     */
    public DBConnection(DBInfosConnection dbInfosConnection) {
        this.dbInfosConnection = dbInfosConnection;
        this.connect();
    }

    /**
     * Permet de se connecter à la BDD
     */
    public void connect (){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.dbInfosConnection.toURI(), this.dbInfosConnection.getUser(), this.dbInfosConnection.getPass());
        } catch ( ClassNotFoundException | SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§f[§6Logger§f] §7§l>>§c Unable to connect to the database. Please check the information entered in the §aconfig.yml §ffile.");
        }
    }

    /**
     * Permet de fermer la connexion à la BDD
     */
    public void close () throws SQLException {
        if(this.connection != null){
            if(!this.connection.isClosed()){
                this.connection.close();
            }
        }
    }


    /**
     * Récupére la connection de la base de donnée
     * @return la connexion de la BDD
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        if(this.connection != null){
            if(!this.connection.isClosed()){
                return this.connection;
            }
        }
        connect();
        return this.connection;
    }

    /**
     * Permet d'update la base de donnée avec une requête
     * @param qry requête
     */
    public void update(String qry){
        try (Connection c = getConnection();
             PreparedStatement s = c.prepareStatement(qry)) {
            s.executeUpdate();
        } catch(Exception e){
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }

    /**
     * Permet d'envoyer une requête à la BDD
     * @param qry requête
     */
    public Object query(String qry, Function<ResultSet, Object> consumer){
        try (Connection c = getConnection();
             PreparedStatement s = c.prepareStatement(qry);
             ResultSet rs = s.executeQuery()) {
            return consumer.apply(rs);
        } catch(SQLException  e){
            throw new IllegalStateException("1");
        }
    }

    /**
     * Permet d'envoyer une requête à la BDD
     * @param qry requête
     */
    public void query(String qry, Consumer<ResultSet> consumer){
        try (Connection c = getConnection();
             PreparedStatement s = c.prepareStatement(qry);
             ResultSet rs = s.executeQuery()) {
            consumer.accept(rs);
        } catch(SQLException e){
            throw new IllegalStateException("2");
        }
    }
}
