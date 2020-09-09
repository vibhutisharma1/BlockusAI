# BlockusAI
Built a virtual game of Blockus with a user player and an AI player

––Description–––

This game simulates a real life blockus board game with two players. However, this is with an AI and a user. There are two players one purple and one orange, one of them is the AI and the other one is the player. Constructed different AI's to see their efficacy against the player: one has random moves, one has strategic moves,and one just tries to block the big pieces.   

How it works: 

BlokusBoard Class: Makes the declarations for the two colors, builds 2d array, playing components are kept track here/ status, the graphics of the orange and purple blocks are also in this class including the board, the movement of the pieces on the 2d array is done here, and each player skips and checks are done here. 

Shape: moves the "blocks" in the game to move and see in specific direction like rotate or flipping it to put the block in the desired location these actions can be done. 


Central AI: This is the AI constructed to defeat the user player by attempting to find all the possible ways to move given each scenario. The winning percentage is around 75%, (the AI could be immproved for a better winning rate). 

RandomAI: AI class that randomizes the moves and plays against the player with that method. 

PlayerScores: Keeps track of the scores for purple and orange and assings points according to different scenarios. the scores are displayed on the screen using graphics. 

Player: Defines the general player such as the color it is playing and its name that needs to be assinged 

Move: keeps track of the blocks and see if it flips, rotate, position on board, and which block it is (piece number), this is where all the info is stored.

IntPoint: has the x,y values and is used for the AI class as an object for the moves

DisplayScreen: This is the Jframe of the project and is used to display the graphics and all moving parts of the project on the screen while its running. 

Client Main: Basically the main of the project it is where the program is executed and the player and AI are made and called to, the scors are printed here, the calls to the client are exected from here. 

BigMoverAI: This AI makes moves based on the board and is not consisent 

––Contributed by Mr. Tully––

ServersListener: send the servers information to the main to be executed
ServerMain: Hosts the game server
CommandToClient: stores commands from the user to the client 
CommandToServer: Connects the client to the server, which will be sent to the listenrer

Contributions: 
Thanks to Jason Tully, computer science teacher, for his help and contribution to some classes. 



