# Authentificator

Ce plugin permet d'avoir un système de "compte" sur le serveur lorsque celui accepte les versions crackées.

---

### Comment fonctionne-il ?
Pour que le plugin fonctionne il vous faudra configurer dans le **config.yml** la partie concernant les informations de la base de donnée:

```yml
database:
  host: "hôte"
  user: "user"
  pass: "mot de passe"
  dbname: "nom de votre base de donnée"
  #(MySQL: 3306, PostgreSQL: 5432, MongoDB: 27017)
  port: 3306 
```

Vous pouvez aussi éditer nom de la table qui sera créée dans votre base de donnée:
   
 ```yml
  table_name: "auth"
```
  
La table possède 3 colonnes : 
 - **pseudo**: Pseudo du joueur à qui le compte a été crée.
 - **password**: Mot de passe du compte. 
 - **date**: Date et heure de création. 

**/!\ Bien évidemment pour la sécurité aucun mot de passe brut se sera stocké dans la base de donnée celui ci sera hashé !**

## Informations

- Au premier lancement du plugin une erreur apparaîtra dans la console ceci est normal. Il vous faudra configurer la BDD dans le **config.yml**. Comme expliqué ci-dessus.
- Version minecraft supporté: **1.15**
- Vous pouvez aussi éditer d'autre options dans le **config.yml**.

    - Le préfix du tchat en jeu:
    
    ```yml
    prefix: "&7[&6AUTH&7] &7&l>>&c "
    ```
    
    - Le temps maximale en seconde que le joueur peut prendre pour se connecter ou s'enregistrer:
    
    ```yml
    timeToKick: 15
    ```
    
    - Les messages que le plugin enverra.
    
     ```yml
      messages:
         ...
     ```

- Téléchargeable sur [Bukkit]().

## Commandes

Permmision requise: **auth.command.use**

Commandes | Description 
--- | --- 
**/register password password** | *Crée un compte sur le serveur.* 
**/login password** | *Se connecter au compte.* 


## Support

**[FR]:** Si vous devez signaler un bug si vous voulez suggérer une nouvelle fonctionnalité, vous pouvez [ouvrir une issue sur GitHub](https://github.com/Eowalim/Authentificator/issues/1).

**[EN]:** If you need to report a bug or want to suggest a new feature, you can [open an issue on GitHub](https://github.com/Eowalim/Authentificator/issues/2).
