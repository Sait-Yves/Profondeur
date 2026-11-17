# Les profondeurs

Jeu de cartes et d'aventure en Java, avec interface web locale.

## Prérequis

- Java 17 ou version ultérieure
- Maven 3.8 ou version ultérieure

## Lancer le jeu

```powershell
mvn clean package
java -jar target\les-profondeurs-1.0.0.jar
```

Ouvrez ensuite [http://127.0.0.1:8080](http://127.0.0.1:8080) dans votre navigateur.
Un autre port peut être choisi en le passant comme argument, par exemple `java -jar target\les-profondeurs-1.0.0.jar 9090`.

Le serveur écoute uniquement sur l'ordinateur local. Arrêtez-le avec `Ctrl+C`.

## Vérifier le moteur

```powershell
.\test.ps1
```

## Structure

- `src/main/java/fr/projetpo` : moteur, salles et serveur HTTP
- `src/main/java/fr/projetpo/carte` : cartes et effets
- `web` : interface HTML, CSS et JavaScript
