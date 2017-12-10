# PhysicsGame

## Launch
In `Program.java`, modify line 34 to select the game, then execute `Program.java`
For example : 
```java
34. Game game = new BikeGameWithLevels(); // Replace BikeGame with any instance of Game
```

Possible instances of game include :
- **BikeGameWithLevels**, the main game
- *BikeGame*, deprecated (use BikeGameWithLevels instead)
- CrateGame, a game used to test crates
- Tutorials :
    - HelloWorldGame, very first game
    - SimpleCrateGame, tests physics engine
    - RopeGame, tests rope contstraints
    - ScaleGame, tests keyboard controls and revolute constraints	
    - ContactGame, tests contact listeners


## Controls
| Key           | Description                    |
| :-----------: | ------------------------------ |
| *Space*       | Change orientation of cyclist  |
| *Up arrow*    | Accelerate back wheel of bike  |
| *Down arrow*  | Brake                          |
| *Left arrow*  | Lean left 		             |
| *Right arrow* | Lean right 		             |
| *R*           | Reset level                    |

## Levels of BikeGameWithLevel

### Level 1

This level showcases 