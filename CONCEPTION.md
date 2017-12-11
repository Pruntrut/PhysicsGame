#Modifications apportées à l'architecture suggérée

## Partie Fournie 
* Changement de la couleur de fond de la fenêtre;

## Partie Suggérée
* `GameEntity` possède une méthode `isSameEntity(Entity entity)` qui compare l'entité passée en paramètre à l'entité de `GameEntity`. Cette méthode permet de comparer des `Entity` dans les ContactListener sans exposer un `getEntity()` dans `GameEntity`.
* Ajout d'une classe `Payload`. Elle permet d'ajouter la fonction `isAlive()` à l'entity retournée par `ActorGame.getPayload()`. Particulièrement utile pour les `Trigger` (`Checkpoint`, `Finish`, ...) car permet d'appliquer l'effet du trigger uniquement lorsque `Bike` n'est pas mort (`ìsHit()`)
* Les graphiques de `Bike` sont séparés entre le cadre et une nouvelle classe `Cyclist` qui permet de séparer par la suite le cycliste du cadre en lui-même.

# Extensions ajoutées à la version de base

## Structure
Toutes les extensions font partie du paquetage `ch.epfl.cs017.play.game.actor`, à la racine de ce répertoire se trouvent les extensions les plus générales (classes abstraites, interfaces, ...). Seule exception, dans `ch.epfl.cs017.play.utils` se trouve la classe `Animation`, qui offre certaines fonctions utilitaires d'animation.

J'ai séparé certaines parties du code dans des sous-paquetages plus spécifiques : 
* `general` contient des classes qui ne sont pas spécifiques à un bike game en particulier.
* `bike` contient les classes en rapport avec le bike game
* `crate` contient les crate et un crate game crée selon la donnée
* `levels` contient les différents niveaux du bike game
* `particles` contient les particules et les emitters