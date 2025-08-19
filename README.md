# GitHub User Activity

Petit projet Java pour récupérer l’activité d’un utilisateur GitHub (notamment les repos).

## Prérequis

- Java 21
- Maven

## Compilation et création du JAR

Le projet utilise Maven (maven-shade-plugin) pour produire un *fat JAR* exécutable.

Pour compiler et créer le `.jar` dans le dossier `target` :

~~~bash
mvn clean package
~~~

Le fichier généré attendu :  
`target/github-user-activity-1.0-SNAPSHOT.jar`  
(Main class configurée : `org.example.Main` dans le `pom.xml`.)

## Exécution

Exemple avec un utilisateur spécifique :

~~~bash
java -jar target/github-user-activity-1.0-SNAPSHOT.jar torvalds
~~~

Ou sans argument (l’utilisateur doit être mentionné dans le CLI) :

~~~bash
java -jar target/github-user-activity-1.0-SNAPSHOT.jar
~~~
