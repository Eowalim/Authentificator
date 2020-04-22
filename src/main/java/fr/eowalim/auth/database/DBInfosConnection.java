package fr.eowalim.auth.database;

/**
 * Classe DBInfosConnection qui gère les infos de la connexion à la BDD.
 * @author Eowalim
 * @version 22/04/2020
 */

public class DBInfosConnection {

    /**
     * Attributs de la classe DBInfosConnection
     */
    private String host;
    private String user;
    private String pass;
    private String dbName;
    private int port;

    /**
     * Constructeur par initialistaion
     * @param host host de la BDD
     * @param user username de la BDD
     * @param pass mot de passe de la BDD
     * @param dbName nom de la BDD
     * @param port port de la BDD
     */
    public DBInfosConnection(String host, String user, String pass, String dbName, int port) {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
        this.port = port;
    }

    /**
     * Permet de crée l'adresse de connexion à la base de donnée
     * @return l'adresse de connexion de la base de donnée
     */
    public String toURI(){
        final StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(dbName)
                .append("?autoReconnect=true&useSSL=false");

        return sb.toString();
    }

    /**
     * Récupére le username de la BDD
     * @return le user de la BDD
     */
    public String getUser() {
        return user;
    }

    /**
     * Récupére le mot de passe de la BDD
     * @return le mdp de la BDD
     */
    public String getPass() {
        return pass;
    }
}
