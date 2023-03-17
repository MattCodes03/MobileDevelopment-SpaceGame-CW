# MobileDevelopment-SpaceGame-CW
Coursework for Mobile Development Module

## Overview
‘Space War’ is an android mobile game where the player will control a spaceship fighting to defend space from a wave of enemy aliens. These aliens will be in spaceships of their own and will attempt to destroy the players spaceship at all costs.

Gameplay will be done vertically meaning the player will not need to change the default orientation of their device. The players movement will be controlled using androids motion events meaning the player will touch the screen and hold their finger down to move their ship in a desired direction. When the player wishes to shoot at an enemy they will just have to touch anywhere on the screen and the bullet will be fired in the direction the players ship is facing.

The gameplay loop will be as follows:

Player will be spawned in at the bottom of the screen, once they start moving the gameplay will begin. Several enemies will be spawned in and will start flying towards the player shooting at them at the same time, these enemies will only have 100 health and 1 life. The player will have to defeat a total of 24 enemies before they can claim victory, these enemies will attack the player in waves of 3. Once the final enemy of each wave has been defeated the next wave will be sent.

During each wave 3 bombs and 3 healable items will be spawned randomly across the screen. Bombs will be red and healable items will be green. If the player collides with a bomb, the bomb will detonate dealing the player 100 damage, which will result in the loss of a life (Players will have 3 lives to start with), the bomb will then be removed form the screen and will reappear at a random location during the next wave.

If the player collides with a healable item, they will be given a new life, however if the player already has the maximum number of 3 lives nothing will happen and the healable will remain in place until the player becomes eligible to consume it. These will also be removed from the screen once consumed and will reappear during the next wave.

Player will begin moving towards the enemies and shooting at them. If the players bullet collides with an enemy it will deal 50 damage meaning it will take 2 bullets to fully defeat an enemy. Every time the player is successful in shooting down an enemy they will be rewarded with 10 points.

Once the player has 50 points they will receive an ally that will fly around shooting at and tracking the enemies.

If the player loses all their lives the game will end, and the end screen will be displayed with a status of defeat. If the player is successful in holding off all 8 waves of enemies without losing all their lives the end screen will be displayed with a status of victory.

From the end screen the player will be able to view statistics about their game, these will be the number of bombs detonated, the number of healable items consumed and their total number of points. They will be given the option to either replay or quit the game.

If the player choses to replay, the gameplay loop will begin again, and all stats will be reset. If they chose to quit, the application will be terminated.
