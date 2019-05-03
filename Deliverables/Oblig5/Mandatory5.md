Part 1: Project and project structure

Regarding the project methodology the team has chosen to use the Scrum project methodology, where we work in sprints from week to week. Basically we chose to assign different assignments to each member of the team, and they would then work together, or individually to complete different parts of the project. In our experience, this has worked well for our team dynamic, as people usually finish their designated work before or during each meeting. One problem we encountered was that some work would often overlap, and we had some difficulties with understanding or implementing each others code. However, we managed to resolve these issues when we met at the several meetings we had every week. We also got much better at using branches for splitting up the code, so that we only pulled from the necessary branches of the team members that had complementary code. 

The group dynamic has been good, every member usually participates in the weekly meetings, and when someone is not able to show up, we clearly communicate that with the other members. With our group, each member have different areas that they are good at, and we always try to assign tasks that are relevant to each persons strengths. If one member is struggling to complete their assigned task, the others will take the time to help. 

In this delivery we have tried to rotate a little bit on the tasks. The members that usually did the coding has worked more on design, such as an interface to portray hp, lives and flags, and also helping with improvements of the requirements. The members that did not do much coding before participated in discussions about how to implement the code, and also got tasks to implement parts of the code, with help from the other members. 

Retrospective summary: The biggest thing we adjusted during this project was how we used branches. We decided to each have our own branch with each of our work, and then pulling from each other. Before, we all pushed our work to the master branch, which would often cause the program to break. Instead, we decided to all push our work to each our branches, and then one person pulled from all the others to check if the program worked, and if necessary we worked together to fix the bugs that occurred before pushing it to the master branch. After we adjusted to this change, everything went a lot smoother, and we were able to use less time with fixing bugs.
What worked best was that we helped each other with completing the tasks, not by one person fixing it himself, but rather with productive discussions, so that the person struggling managed to understand how to fix the problems and implement the code themselves.
If we were to continue with the project, we would adjust how the code structure is set up, by adding more interfaces, and splitting the code into more appropiate classes 
The most important thing we have learned is that the usage of branches, and


Part 2: requirements
One must be able to play a complete round
This was already achived by the last oblig.
There must be lasers on the board.
This has been completed
One must be able to win the game by visiting the last flag (completing a game)
Like the lasers goal, the implementation of the grid system allowed us to focus our efforts on implementing more game features. Our implementation will not be 'ready for release' but we will at least have a the game register that we have arrived at the flag and write out "You won" or something similar in the console.
Injury mechanisms (player gets fewer cards in case of injury)
Injury mechanisms go hand in hand with board elements and the grid as well. It will also make the game feel more like what it is, a game. If we are to implement lasers, why not the consequences as well?
Game over after 3 lost lives.
Game mechanisms to shoot other players within reach, with a laser pointing straight ahead
This has been implemented, such that each robot, or AIRobot has a laser in the direction it is facing, the laser stops when it reaches a wall, or when it hits a robot or AIRobot.
There must be holes on the board
We have implemented this, so that you die when you step on a hole, and respawn at your latest checkpoint.
Functioning gyros on the board that move the robots.
Same answer as above. After our grid system has been implemented, this has really opened our options for integrating board elements into our game. This feature will give us even more opportunities to improve our movement system



One must be able to play a complete round
We have achieved this

One must be able to win the game game by visiting the last flag (completing a game)
When one has visited the last flag, there will be a console print with the message "You won". But you can still move around and participate in the game.

There must be lasers on the board
We have implemented this, whenever a robot walks on a tile with a laser, it will take 1 damage. The laser only shoots the first robot in its path.

There must be holes on the board
We have implemented this, so that when you walk on a hole, the robot dies and respawns at its last checkpoint.

Injury mechanisms (player gets fewer cards in case of injury)
We have implemented this so that the player gets less cards in case of injury, down to 5 cards, and then card slots gets locked after that.

Game mechanisms to shoot other players within reach, with a laser pointing straight ahead
We have implemented this so that each robot has a laser facing the direction of the robot, if another robot steps into the direction of the laser it will take 1 damage. The laser stops when it reaches a wall, or a robot, so that if two robots stand next to each other, only the first one will take damage.

Functioning assembly line on the board that moves the robots
We have implemented this.

Functioning gyros on the board that move the robots
We have implemented this.

game over after 3 lost lives
When one player loses 3 lives, that players character gets deleted from the game

multiplayer over LAN or Internet (you don't need to do anything fancy here but you must be able to play on
different machines against each other)
We have implemented multiplayer with up to 8 other players over LAN

Error handling by disconnect. (The very minimum should be that the game does not crash)

power down
Each player has the option of going into powerdown-mode by pressing the powerdown button.

assembly line that runs at double speed
We have implented this.

play against AI (single-player mode), possibly play against random robots
We have implented a single-player mode where you can play against 3 other AIRobots. These robots do random moves using the cards that they are assigned.



Part 3: Code
To run the program you need to open your preferred IDE, and access the main class. From here you select "run". This feature varies from IDE to IDE, but in IntelliJ you can do this by pressing the green arrow in the top right corner of the window, or by selecting Run -> Run... -> Main.

The tests we made should be run by accessing the test classes on your preferred IDE, and select the IDE's way of running the test. For example in IntelliJ IDEA you run the test by clicking the green arrow to the left of the test name, or right-clicking on the function, and selecting "run %function_name". If you move of the map to die, there is still a bug where you can instantly loose the game by staying of the map.




