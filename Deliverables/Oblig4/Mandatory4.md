### Part 1: Project and project structure

We have decided that Wim will be taking over the test role. He will make sure that new
methods are tested adequately and follow up on tests added by the other team members.

As for experiences relating to project methology, we have been using branches more and
more as the project grows in scale, and this has lead to improvement in terms of
efficiency in some aspects and a lot of headaches in other. Technical issues have still
plagued many of the members, and we still need to make a habit of pulling more frequently
and make sure the respective branches stay up to date. On the last oblig we somehow had
a HelloWorld file in our sources folder which was a result of varying versions across the
branches. 

The group dynamics is still going strong in our group, although we plan on having the people
who have coded the least to code more in the future. This also means that the person who
has coded the most will take a different role going forward. Other than that people are
completing their given tasks in a timely fashion, and the workload overall is shared
proportionally.

The communication is also a strong asset in our team. We communicate over many different
platforms and we are quick to respond to communications from our teammembers.

Thinking back to what has we have done so far and what can be better, branches come to mind
immidiately. We had a whole two hour meeting dedicated to making sure everyones branches
worked with the current version of the game and fixing java version issues. This was an
avoidable problem that could have been avoided if we pulled regularly from eachothers
branches. We also need to stay on top of our list of priorites. We have been inconsistent
in implementing crucial elements of the requirements that have limited our developemnt. Currently
and in the future we will be working on implementing these elements into our game and 
emphesize the importance of our priority list. Our code also needs to cleaned up in some aspects. 
After implementing an aspect of the game we have often left that as a functioning mess in our 
RoboRallyDemo class. Not only is this class inappropriately named, but its filled with methods
that should be implemented in other classes for ease of implementation of other facets of
the game. 

We plan on working on cleaning up the code for the next sprint. We concluded that in order to keep
adding to the code in the current state will only make it harder in the future.


### Part 2: requirements
 > * One must be able to play a complete round
 > > * This was already achived by the last oblig.

 > * There must be lasers on the board.
 > > * This has been a goal for us for a while. We have been working on finishing up our grid before we felt comfortable setting our focus on lasers and other board elements. Now that we have a working grid this is a logical next step to focus on. 

 > * One must be able to win the game by visiting the last flag (completing a game)
 > > * Like the lasers goal, the implementation of the grid system allowed us to focus our efforts on implementing more game features. Our implementation will not be 'ready for release' but we will at least have a the game register that we have arrived at the flag and write out "You won" or something similar in the console.

 > * Injury mechanisms (player gets fewer cards in case of injury)
 > > * Injury mechanisms go hand in hand with board elements and the grid as well. It will also make the game feel more like what it is, a game. If we are to implement lasers, why not the consequences as well?

 > * Game over after 3 lost lives.
 > > * After discussing the injury mechanism we concluded that implementing a basic game over feature is feasible with little addition to our code. Therefore, this seems like a realistic goal to pursue.

> * Place flags even before the game starts
> > * We are unsure exactly what is meant by this. We agreed that if this means that flags will be visible on the map as you launch the game, then yes we will implement this. However, if the player is to place the flags themselves before the game begins we will save that for later.
 
 > * There must be holes on the board
 > > * Much like lasers, holes in the ground are not only a great feature for the game, but also a great opportunity for us to test our injury mechanism and grid system. 

 > * Functioning gyros on the board that move the robots.
 > > * Same answer as above. After our grid system has been implemented, this has really opened our options for integrating board elements into our game. This feature will give us even more opportunities to improve our movement system. 


 ### Part 3: Code

 To run the program you need to open your preferred IDE, and access the main class. From here you select "run". This feature varies from IDE to IDE, but in IntelliJ you can do this by pressing the green arrow in the top right corner of the window, or by selecting Run -> Run... -> Main.

 The tests we made should be run by accessing the test classes on your preferred IDE, and select the IDE's way of running the test. For example in IntelliJ IDEA you run the test by clicking the green arrow to the left of the test name, or right-clicking on the function, and selecting "run %function_name".