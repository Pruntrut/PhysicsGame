# Conception du Mini-Projet 2

## Modifications apportées à l'architecture suggérée

### Partie Fournie 
* Changement de la couleur de fond de la fenêtre;

### Partie Suggérée
* `GameEntity` possède une méthode `isSameEntity(Entity entity)` qui compare l'entité passée en paramètre à l'entité de `GameEntity`. Cette méthode permet de comparer des `Entity` dans les ContactListener sans exposer un `getEntity()` dans `GameEntity`.
* Ajout d'une classe `Payload`. Elle permet d'ajouter la fonction `isAlive()` à l'entity retournée par `ActorGame.getPayload()`. Particulièrement utile pour les `Trigger` (`Checkpoint`, `Finish`, ...) car permet d'appliquer l'effet du trigger uniquement lorsque `Bike` n'est pas mort (`ìsHit()`)
* Les graphiques de `Bike` sont séparés entre le cadre et une nouvelle classe `Cyclist` qui permet de séparer par la suite le cycliste du cadre en lui-même.

## Extensions du projet de base

### Structure
Toutes les extensions font partie du paquetage `ch.epfl.cs017.play.game.actor`, à la racine de ce répertoire se trouvent les extensions les plus générales (classes abstraites, interfaces, ...). Seule exception, dans `ch.epfl.cs017.play.utils` se trouve la classe `Animation`, qui offre certaines fonctions utilitaires d'animation.

J'ai séparé certaines parties du code dans des sous-paquetages plus spécifiques : 
* `general` contient des classes qui ne sont pas spécifiques à un bike game en particulier.
* `bike` contient les classes en rapport avec le bike game
* `crate` contient les crate et un crate game crée selon la donnée
* `levels` contient les différents niveaux du bike game
* `particles` contient les particules et les emitters

### Extensions

#### Messages
J'ai crée une sous classe `Message` de `TextGraphics` afin de simplifier certaines fonctionalités comme le fade d'un message après une certaine durée.

#### Niveaux de Jeu
Pour implémenter cette extension, j'ai crée une interface `GameWithLevels`, qui déclare les méthodes qui sont nécessaires pour changer de niveau. Une sous-classe de `Game` devra implémenter ces méthodes pour avoir des niveaux.

La classe abstraite `Level` définit un niveau de jeu. Elle diffère légèrement de celle dans l'énoncé puisqu'elle possède une liste de checkpoints afin de correctement gérer le respawn du bike (garde les checkpoints dans leurs états activés). Ce sera aux sous-classes de Level d'implémenter les méthodes `createActors` et `createCheckpoints` et non `createAllActors`, afin de séparer les checkpoints d'acteurs normaux.

#### Trigger, finish et checkpoints
J'ai crée la classe `Trigger` qui possède un contact listener et un timer. Elle représente un acteur qui est déclenché par un contact et peut avoir une certaine durée d'inactivité (timeout)

* `Finish` est une sous-classe de `Trigger` qui représente le drapeau d'arrivée. Elle s'active uniquement lorsqu'elle est touchée par le payload

* `Checkpoint`est également une sous-classe de `Trigger` mais plus complexe que `Finish`. Lorsqu'elle est déclenchée, le checkpoint est alors inactif et ne peut plus être utilisé. Lorsque `Checkpoint` est activé, cela déclenche une jolie animation qui utlise les méthodes de `Animation`.

#### Particules
* `Particle` est une classe abstraite qui représente une particule avec certaines propriétés phyisques (vitesse, position, etc...) et une certaine durée.
    * `ShapeParticle` et `ImageParticle` sont, à l'image de ShapeGraphics et ImageGraphics, des particules avec une certaine représentation graphique. 
    * `GravityWellParticle` est une `ShapeParticle` qui est générée par `GravityWellEmitter`, sa durée de vie est déterminée par la distance restante à parcourir jusqu'au bord du `GravityWell`.
    * `VictoryParticle` est une `ImageParticle` en forme d'étoile, dont la couleur est aléatoire.

* `Emitter` est un émetteur de particules. Il gère la création et la destruction de ses particules.
    * `GravityWellEmitter` est placé dans un `GravityWell` et génère ses particules. Il s'occupe de générer les `GravityWellParticle` et de calculer la distance restante à parcourir.
    * `VictoryEmitter` projette des `VictoryParticles` dans un cône au dessus de lui. Utilié au dernier niveau.

#### Objects physiques

* `Pendulum`, un pendule à vitesse et taille variable. Composé d'un point d'attache et d'un poids, reliés par une corde.

* `Seesaw`, une bascule composée d'une base triangulaire et d'une planche basculante

* `GravityWell`, un puits de gravité. Lorsqu'une entité entre dans sa hitbox, elle est propulsée dans une certaine direction. La vitesse des particules est proportionelle à la force du puits.

#### Animations 

J'ai d'abord animé `Checkpoint` en utilisant les méthodes utilitaires de `Animation`, dont des méthodes "ease-in/ease-out", qui fluidifient les mouvements et les rendent plus naturels.

Ensuite, j'ai animé le `Bike` de deux manières différentes :
* Tout d'abord, j'ai déplacé le modèle graphique de `Bike` dans `Cyclist`. Cette classe fait pédaler le cycliste en fonction de la position angulaire des roues.
* Enfin, j'ai fait que le cycliste devienne une entité physique lorsqu'il est touché (`Ragdoll`). `Bike` possède une méthode `createRagdoll` qui retourne une nouvelle entité. Cette entité correspond au modèle graphique `Cyclist` mais est simulée physiquement avec des joints liant les membres.
    * `CyclistModel` est une classe qui est créée par `Cyclist` et contient les données (positions des membres, taille, etc..) qui sont utiles pour la création de `Ragdoll`. Elle transforme également les `Polyline` en `Polygon` pour qu'ils puissent être utilisés par une `Entity`. 








