Part 1: Project and project structure

Regarding the project methodology the team has chosen to use the Scrum project methodology, where we work in sprints from week to week. Basically we chose to assign different assignments to each member of the team, and they would then work together, or individually to complete different parts of the project. In our experience, this has worked well for our team dynamic, as people usually finish their designated work before or during each meeting. One problem we encountered was that some work would often overlap, and we had some difficulties with understanding or implementing each others code. However, we managed to resolve these issues when we met at the several meetings we had every week. We also got much better at using branches for splitting up the code, so that we only pulled from the necessary branches of the team members that had complementary code. 

The group dynamic has been good, every member usually participates in the weekly meetings, and when someone is not able to show up, we clearly communicate that with the other members. With our group, each member has different things they are good at, and we always try to assign tasks that are relevant to each persons strengths. If one member is struggling to complete their assigned task, the others will take the time to help. 

In this delivery we have tried to rotate a little bit on the tasks. The members that usually did the coding has worked more on design, such as an interface to portray hp, lives and flags, and also helping with improvements of the requirements. The members that did not do much coding before participated in discussions about how to implement the code, and also got tasks to implement parts of the code, with help from the other members. 

Retrospective summary: The biggest thing we adjusted during this project was how we used branches. We decided to each have our own branch with each of our work, and then pulling from each other. Before, we all pushed our work to the master branch, which would often cause the program to break. Instead, we decided to all push our work to each our branches, and then one person pulled from all the others to check if the program worked, and if necessary we worked together to fix the bugs that occurred before pushing it to the master branch. After we adjusted to this change, everything went a lot smoother, and we were able to use less time with fixing bugs.
What worked best was that we helped each other with completing the tasks, not by one person fixing it himself, but rather with productive discussions, so that the person struggling managed to understand how to fix the problems and implement the code themselves. We 
If we were to continue with the project, we would adjust how the code structure is set up, by adding more interfaces, and splitting the code into more appropiate classes.
The most important thing we have learned is that the usage of branches is very important, and distribution of work according to each members strenghts is essential.


Part 2: requirements

One must be able to play a complete round
We have achieved this

One must be able to win the game game by visiting the last flag (completing a game)
When one has visited the last flag, there will be a console print with the message "You won". But you can still move around and participate in the game.

There must be lasers on the board
We have implemented this, whenever a robot walks on a tile with a laser, it will take 1 damage.

There must be holes on the board
We have implemented this, so that when you walk on a hole, the robot dies and respawns at its last checkpoint.

Injury mechanisms (player gets fewer cards in case of injury)
We have implemented this so that the player gets less cards in case of injury, down to 0 cards, and then card slots gets locked after that 5 damage.

Game mechanisms to shoot other players within reach, with a laser pointing straight ahead
We have implemented this so that each robot has a laser facing the direction of the robot, if another robot steps into the direction of the laser it will take 1 damage. The laser stops when it reaches a wall.

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
If the host of the server disconnects the program will shut down, without finding a new host. If one of the other players disconnect the program will continue to run.

power down
Each player has the option of going into powerdown-mode by pressing the powerdown button.

assembly line that runs at double speed
We have implented this.

play against AI (single-player mode), possibly play against random robots
We have implented a single-player mode where you can play against 3 other AIRobots. These robots do random moves using the cards that they are assigned.



Part 3: Code

List of of faults and weaknesses:
Collision is only handled once (can only push another robot 1 tile, then pass through)
Robots behind another robot take damage from lasers even if another robot is blocking it
Priority of cards does not work for singleplayer mode


To run the program you need to open your preferred IDE, and access the main class. From here you select "run". This feature varies from IDE to IDE, but in IntelliJ you can do this by pressing the green arrow in the top right corner of the window, or by selecting Run -> Run... -> Main. To play singleplayer mode you press the start button in the main menu, then you will play against 3 other AI's. To play multiplayer, one person has to host by pressing server, and the other players must press client, and get the IP from the host (cmd -> ipconfig -> IPv4 adress) and type the IP into the IDE terminal. The host then has to press start to initialize the game for all players.

The tests we made should be run by accessing the test classes on your preferred IDE, and select the IDE's way of running the test. For example in IntelliJ IDEA you run the test by clicking the green arrow to the left of the test name, or right-clicking on the function, and selecting "run %function_name". If you move of the map to die, there is still a bug where you can instantly lose the game by staying of the map.




