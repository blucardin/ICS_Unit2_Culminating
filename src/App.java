/* This is the file that runs the snake game.
 * It displays the menu with an animation.
 * Based on user input it will then either start the snake game, go to the shop, print the rules, or go back the main menu.
 */

import java.util.ArrayList; // Import libraries
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class snake {

  static Scanner key = new Scanner(System.in); //create new scanner

  //create  array for colors
  static String[] colors = { color.RESET, color.RESET, color.RED, color.GREEN };

  static int coins = 0; //create coins variable

  // colors 0 represents menu color, 1 represents background color, 2 represents target color, 3 represents snake color

  public static void shop() throws InterruptedException { //Method for shop
    while (true) {
      System.out.print("\033[H\033[2J"); //clear screen
      System.out.flush();

      System.out.println(colors[0]);
      System.out.println("Welcome to the shop!"); //print item menu and prompt user to choose an option
      System.out.println("You have " + coins + " coins.");
      System.out.println("What object would you like to buy a color for?");
      System.out.println("1. Menu");
      System.out.println("2. Background");
      System.out.println("3. Target");
      System.out.println("4. Snake");
      System.out.println("5. Exit back to menu");
      System.out.println("Enter your choice: ");
      String choice = key.next(); //get user choice
      if (choice.equals("5")) { //if user chooses 5, exit back to menu
        break;
      }

      Thread.sleep(1000);
      System.out.print("\033[H\033[2J"); //clear screen
      System.out.flush();

      System.out.println("What color would you like to buy?"); //print out color menu and prompt user to choose an option
      System.out.println("1. Green (10 coins)");
      System.out.println("2. Red (20 coins)");
      System.out.println("3. Blue (30 coins)");
      System.out.println("4. Yellow (40 coins)");
      System.out.println("5. Exit back to menu");
      System.out.println("\nYou have " + coins + " coins.\n");
      System.out.println("Enter your choice: ");
      String colorChoice = key.next(); //get user choice
      if (colorChoice.equals("5")) { //if user chooses 5, exit back to menu
        break;
      }

      Thread.sleep(1000);
      System.out.print("\033[H\033[2J"); //clear screen
      System.out.flush();

      int object = Integer.parseInt(choice) - 1; //create selector for the object the user wants to change

      boolean approved = false; //create boolean to check if the user has enough coins

      switch (colorChoice) { //set color based on user choice
        case "1":
          if (coins >= 10) { //if user has enough coins, set color and subtract coins
            coins -= 10; //subtract coins
            colors[object] = color.GREEN; //set color
            approved = true; //set approved to true
          }
          break;
        case "2":
          if (coins >= 20) { //see above
            coins -= 20;
            colors[object] = color.RED;
            approved = true;
          }
          break;
        case "3":
          if (coins >= 30) { //see above
            coins -= 30;
            colors[object] = color.BLUE;
            approved = true;
          }
          break;
        case "4":
          if (coins >= 40) { //see above
            coins -= 40;
            colors[object] = color.YELLOW;
            approved = true;
          }
        default:
          System.out.println("Invalid choice, try again."); //if user chooses an invalid choice, print error message
          approved = false; //set approved to false
          break;
      }
      if (approved == true) {
        System.out.println(
          "Your purchase was approved! Your color is now equipped!"
        ); //if user has enough coins, print success message
        System.out.println("You have " + coins + " coins.");
      } else {
        System.out.println("You don't have enough coins!"); //if user doesn't have enough coins, print error message
      }
      System.out.println("Press enter to continue..."); //prompt user to continue
      key.nextLine();
      key.nextLine();
    }
  }

  public static void game() throws InterruptedException {
    boolean run = true;
    while (run) { //if the user wants to play again, this loop runs the game again
      final char BACKGROUND = '.'; //set background character
      final int SIZE = 20; // Size of the board
      final int[] STARTING_POS = { SIZE / 2, SIZE / 2 }; //Starting position for the snake
      ArrayList<ArrayList<Integer>> blocks = new ArrayList<ArrayList<Integer>>();
      blocks.add(
        new ArrayList<Integer>(
          Arrays.asList(STARTING_POS[0], STARTING_POS[1] - 1) // Add the head to the snake at the starting position
        )
      ); //create starting snake
      blocks.add(
        new ArrayList<Integer>(Arrays.asList(STARTING_POS[0], STARTING_POS[1])) // Add the body to the snake at the starting position
      ); //create starting snake

      Random rand = new Random();
      ArrayList<Integer> Target = new ArrayList<Integer>( //create random target
        Arrays.asList(rand.nextInt(SIZE), rand.nextInt(SIZE))
      );

      char[][] board = new char[SIZE][SIZE * 2]; //create game board
      for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE * 2; j++) {
          board[i][j] = BACKGROUND; //fill board with the background (dots)
        }
      }

      char direction = 'w'; //define variable to remember the last direction of movement, set the starting last direction to up

      System.out.println("Welcome to the game! Press any key to begin.");

      int x = STARTING_POS[0];
      int y = STARTING_POS[1]; //set x and y to starting positions
      boolean triggered = true; //create boolean to check if the user has input a valid direction, or just pressed enter
      String character = new String(); //create string to store user input
      boolean firstTime = true; //create boolean to check if the user is on the first time
      String reason = ""; //create reason for death

      while (true) {
        if (firstTime == true) { // if it is the users first time playing, just print the board, don't accept user input
          character = "d";
          firstTime = false;
        } else {
          character = key.nextLine(); //if it is not the first time, get user input
        }
        do {
          if (character.equals("w") && direction != 's') { // check the inputted character against the current direction and move the snake accordingly
            direction = 'w';
            y--;
            triggered = true;
          } else if (character.equals("a") && direction != 'd') { //see above
            direction = 'a';
            x--;
            triggered = true;
          } else if (character.equals("s") && direction != 'w') { //see above
            direction = 's';
            y++;
            triggered = true;
          } else if (character.equals("d") && direction != 'a') { //see above
            direction = 'd';
            x++;
            triggered = true;
          } else if (character.equals("q")) { // if the user inputs q, quit the game
            reason = "You quit the game.";
            break;
          } else if (character.equals("p")) { // if the user inputs p, pause the game
            System.out.println("Game Paused");
            System.out.println("Press any key to continue.");
            key.nextLine();
          } else {
            character = "" + direction; //if the user inputs nothing, or an invalid letter, set the character to the last direction
            triggered = false; //set triggered to false, to make the code loop again
          }
        } while (!triggered); //if the user inputted a valid direction, go forward with the code. Otherwise, loop back to the top and move in the last direction.

        if (triggered) {
          for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE * 2; j++) {
              board[i][j] = BACKGROUND; // Fill the board with the background character (dots)
            }
          }

          System.out.print("\033[H\033[2J");
          System.out.flush(); //clear the screen

          ArrayList<Integer> coords = new ArrayList<Integer>(); //create arraylist to store the coordinates of the new snake block
          coords.add(y);
          coords.add(x);

          if (blocks.contains(coords)) { // if the new blck is already in the snake, the snake has hit itself
            reason = "You hit yourself."; // set the reason for death as, "You hit yourself."
            break;
          }

          //check if the snake has went out of bounds
          if (x < 0 || x >= board[0].length || y < 0 || y >= board.length) {
            reason = "You went out of bounds."; //set the reason for death as, "You went out of bounds."
            break;
          }

          blocks.add(coords); //add the new block to the snake

          if (blocks.contains(Target)) { //if the snake has eaten the target, create a new target
            Target =
              new ArrayList<Integer>(
                Arrays.asList(rand.nextInt(SIZE), rand.nextInt(SIZE))
              );
          } else {
            blocks.remove(0); //if the snake has not eaten the target, remove the last block from the snake
          }
        }
        triggered = true;

        for (int i = 0; i < blocks.size(); i++) { //for each block in the snake, fill the board with * at that coordinate.
          board[blocks.get(i).get(0)][blocks.get(i).get(1)] = '*';
        }
        board[Target.get(0)][Target.get(1)] = '0'; //fill the board with the target at the target's coordinates

        System.out.println(colors[1]); //Give the board the

        for (int i = 0; i < SIZE; i++) {
          for (int j = 0; j < SIZE * 2; j++) { //iterate over each row and column of the board
            if (board[i][j] == '*') {
              System.out.print(colors[3] + board[i][j] + colors[1]); // if the character is * print the * with the color of the snake
            } else if (board[i][j] == '0') {
              System.out.print(colors[2] + board[i][j] + colors[1]); // if the character is 0 print the 0 with the color of the target
            } else {
              System.out.print(board[i][j]); // if the character is not * or 0, print the character with the color of the background
            }
          }
          System.out.println(); //print a new line
        }
      }

      System.out.print("\033[H\033[2J"); //clear the screen
      System.out.flush();
      System.out.println(color.RESET);
      System.out.println("Game Over");
      System.out.println(reason); //print the reason for death
      System.out.println();
      Thread.sleep(1000);

      System.out.println("Your score was: " + blocks.size()); //print the score
      int coinsEarned = (blocks.size() - 2) * 10; //calculate the coins earned
      coins += coinsEarned; //add the coins to the user's total
      System.out.println("You earned " + coinsEarned + " coins!"); //print the coins earned
      System.out.println("You now have " + coins + " coins!"); //print the user's total coins
      System.out.println();

      Thread.sleep(1000);
      System.out.println("Would you like to play again? (y/n)"); //ask the user if they want to play again
      String playAgain = key.next(); //get the user's input
      if (playAgain.equals("n")) { //if the user inputs n, break the loop
        run = false;
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    boolean menu = true;
    while (menu) {
      //loop over array
      for (int i = 0; i < animation.length; i++) { //for each frame in the animation, flush the screen, print it, and wait for 125 milliseconds
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Welcome to Snake!");

        System.out.println(
          "	........................................		" +
          "\n" +
          "	........................................		" +
          "\n" +
          animation[i] +
          "	........................................		" +
          "\n" +
          "	........................................		" +
          "\n" +
          "	........................................		" +
          "\n" +
          "	........................................		" +
          "\n" +
          "	........................................		" +
          "\n" +
          "	........................................		" +
          "\n" +
          "	........................................		" +
          "\n"
        );
        Thread.sleep(125);
      }
      System.out.print(colors[0]);
      System.out.println("Enter a number for your selection:"); //print the menu and ask the user for their selection
      System.out.println("1. Snake Game");
      System.out.println("2. Exit");
      System.out.println("3. How to play");
      System.out.println("4. Shop");
      System.out.println(color.RESET);

      String selection = key.next(); //get the user's input

      switch (selection) { //switch statement to handle the user's selection
        case "1":
          game();
          break;
        case "2": //if the user selects 2, break the loop and return to the main menu
          System.out.print("\033[H\033[2J");
          System.out.flush();
          System.out.println("Goodbye!");
          menu = false;
          break;
        case "3": //if the user selects 3, print the instructions
          System.out.print("\033[H\033[2J");
          System.out.flush();
          System.out.println("How to play: ");
          System.out.println(
            "Use the w, a, s, and d keys to change the snake's direction."
          );
          System.out.println("Press enter to load the next frame of the game.");
          System.out.println("Eat the 0 to grow.");
          System.out.println("Avoid the walls and yourself.");
          System.out.println("Press 'p' to pause the game.");
          System.out.println("Press 'q' to quit the game.");
          Thread.sleep(1000);
          System.out.println("Enter any key to go back to the menu.");
          key.next();
          System.out.print("\033[H\033[2J");
          System.out.flush();
          break;
        case "4": //if the user selects 4, run the shop
          shop();
          break;
        default: //if the user inputs something else, print an error message
          System.out.println("Invalid input, please try again.");
          System.out.print("\033[H\033[2J");
          System.out.flush();
      }
    }
  }

  public static String[] animation = { //array of strings to play the opening animation
    "	....***.................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	.....***................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	......***...............................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	.......***..............................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	........***.............................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	.........***............................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	..........***...........................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	...........***..........................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	............***.........................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	.............***........................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	..............***.......................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	...............**.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	........................................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................0.......................		" +
    "\n",
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	........................................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	................*.......................		" +
    "\n" +
    "	........................................		" +
    "\n",
  };
}