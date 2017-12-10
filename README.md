# PhysicsGame

## Launch
In `Program.java`, modify line 34 to select the game, then execute `Program.java`
For example : 
```java
Game game = new BikeGameWithLevels(); // Replace BikeGame with any instance of Game
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

### Level 1 (Basic level)

Start by going right, over the hill, you will encounter a pendulum
Wait until just before the pendulum swings away from you to pass under it.
Once at the checkpoint, simply go forward until the finish line, passing over the seesaw.

### Level 2 (Jump level)

Slide down the slope into the booster, then as you are in midair,
angle your bike back so that only the wheels hit the crates
It might take a few tries to get it right

### Level 3 (Checkpoint level)

This level was mainly build a showcase of the checkpoint animation =P
Simply go right until you hit the finish line

### Level 4 (Victory level)

Congratulations, you have reached the final level !
Admire the VictoryParticles(TM) for as long as you wish then 
feel free to exit the game.