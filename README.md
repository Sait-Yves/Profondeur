# Les profondeurs

**Les profondeurs** est un jeu de cartes et d'aventure solo réalisé en Java. Le joueur traverse un donjon composé de combats, de salles de repos et de rencontres. Il doit gérer sa santé, son énergie et ses pièces, jouer ses cartes au bon moment et choisir une récompense après ses victoires.

Le projet comprend un moteur de jeu Java et une interface web locale en HTML, CSS et JavaScript. Le navigateur communique avec le moteur par l'intermédiaire d'un petit serveur HTTP intégré au programme : aucune base de données ni aucun service externe n'est nécessaire pour jouer.

## Fonctionnalités

- Choix du nom du héros et de trois niveaux de difficulté.
- Parcours de donjons de longueurs différentes, avec des combats, des événements et des feux de camp.
- Combats au tour par tour : pioche de cartes, gestion de l'énergie, choix des cibles et fin de tour.
- Plusieurs catégories de cartes : attaque, défense, effets de statut et effets de zone.
- Ennemis dotés de comportements et de caractéristiques différents, ainsi qu'un combat de boss.
- Gestion de la vie, du blocage, de la force, des altérations, de la défausse et des cartes exilées.
- Récompenses de combat, pièces et options de repos.
- Interface adaptative, utilisable sur ordinateur et sur téléphone, avec affichage de l'état du héros et du journal de partie.

## Prérequis

- Java 17 ou version ultérieure.
- Maven 3.8 ou version ultérieure pour construire le projet avec Maven.

## Compiler et lancer le jeu

Depuis la racine du dépôt, dans PowerShell :

```powershell
mvn clean package
java -jar target\les-profondeurs-1.0.0.jar
```

Ouvrez [http://127.0.0.1:8080](http://127.0.0.1:8080) dans votre navigateur. Le serveur doit rester lancé pendant la partie ; arrêtez-le avec `Ctrl+C`.

Le serveur écoute uniquement sur l'ordinateur local. Pour utiliser un autre port, indiquez-le comme argument :

```powershell
java -jar target\les-profondeurs-1.0.0.jar 9090
```

Puis ouvrez [http://127.0.0.1:9090](http://127.0.0.1:9090).

## Vérifier le moteur

Le script PowerShell ci-dessous compile les sources et exécute les vérifications de régression du moteur :

```powershell
.\test.ps1
```

## Organisation du projet

```text
src/
  main/java/fr/projetpo/       Moteur, entités, donjon, serveur HTTP
  main/java/fr/projetpo/carte/ Cartes et effets associés
  test/java/fr/projetpo/       Vérifications du moteur
web/                           Interface HTML, styles CSS et JavaScript
pom.xml                        Configuration Maven et paquet JAR
test.ps1                       Compilation et tests sous Windows
```

## Technologies

- **Java 17** : règles, combats, progression et serveur HTTP local.
- **HTML, CSS et JavaScript** : interface graphique dans le navigateur.
- **Maven** : configuration de compilation et création du JAR exécutable.
