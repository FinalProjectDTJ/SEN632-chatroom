package server.chatbot;

import server.ConnectionThread;
import java.util.Random;

public class Chatbot {

  private static String gameBoard = "\n    |   |   \n ---+---+---\n    |   |   \n ---+---+---\n    |   |   ";

  private static char pieceBeLast = ' ';
  private static String[] jokes = new String[] {"\nMan said to God --- Why did you make women so beautiful?\nGod said to man --- So that you will love them.\nMan said to God --- But why did you make them so dumb?\nGod said to man --- So that they will love you.",
    "\nDid you hear about the skeleton who walked into a cafe?\nHe ordered a cup of coffee and a mop.",
    "\nMary: John says I'm pretty. Andy says I'm ugly. What do you think, Peter?\nPeter: I think you're pretty ugly.",
    "\n\"Do you know what really amazes me about you?\"\n\"No.What?\"\n\"Oops.Sorry. I was thinking about someone else!\" ",
    "\nIf tin whistles are made of tin, what are fog horns made of? ",
    "\nIf vegetarians eat vegetables, what do humanitarians eat? ",
    "\nA person who speaks two languages is bilingual...A person who speaks three languages is trilingual...A person who speaks four or more languages is multilingual. \nWhat is a person who speaks one language?\n  An American. ",
    "\nWhy do we park our car in the driveway and drive our car on the parkway? "
    }; 

  public static String showGameBoard() {
    return(gameBoard);
  }

  public Chatbot() {

    System.out.print("Chatbot ");
  }

  public static void loading() {

    System.out.println("Chatbot loaded.");
  }

  public static String singleStr(ConnectionThread ct, String input) {

    String returnStr = "";
    String[] subString = new String[3];
    if (input.startsWith("@name ")) {
      String newName;
      newName = (input.substring(6)).trim();
      String oldName = ct.getConnectionName();
      ct.changeConnectionName(newName);
      oldName = oldName + " change name to " + newName;
      return oldName;
    }

    if (input.startsWith("@v ")) {
      //String[] subString = new String[3];
      subString = ((input.substring(3)).trim()).split(" ", 2);
      if (subString[0].equals("fight")) {
        return "(ง •̀_•́)ง┻━┻";
      }
      if (subString[0].equals("cute")) {
        return "(◕ܫ◕)";
      }
      if (subString[0].equals("cheers")) {
        return "(╬▔▽▔)凸";
      }
      if (subString[0].equals("help")) {
        return "fight cute cheers";
      }
      return "Unkown expression, no such things ...";
    }

    if (input.startsWith("@joke")) {
      Random random = new Random();
      int s = random.nextInt(jokes.length);
      return jokes[s];
    }
    
    if (input.startsWith("@game ")) {
      subString = (((input.substring(6)).trim()).toLowerCase()).split(" ", 3);
      if (subString[0].equals("show")) {
        return showGameBoard();
      }
      if (subString[0].equals("x")) {
        return writeBoard('x',subString[1]);
      }
      if (subString[0].equals("o")) {
        return writeBoard('o',subString[1]);
      }
      if (subString[0].startsWith("new")) {
        gameBoard = "\n    |   |   \n ---+---+---\n    |   |   \n ---+---+---\n    |   |   "; 
        pieceBeLast = ' ';
        return " Reset Game Board Successful.";
      }
    }

    return "No such command";
  } 

  private static String writeBoard (char piece, String pieceLoc) {
    int numOfLoc, numOfRow, numOfCol;
    numOfLoc = Integer.valueOf(pieceLoc);
    numOfRow = numOfLoc / 10;
    numOfCol = numOfLoc % 10;
    if (piece == pieceBeLast) return "Please wait your opponent.";
    if (numOfRow < 1 || numOfRow >3) return " Please input a Row number between 1 and 3.";
    if (numOfCol < 1 || numOfCol >3) return " Please input a Col number between 1 and 3.";
    int locOfString = ((numOfRow - 1) * 26) + (numOfCol * 4) - 1;
    if (gameBoard.charAt(locOfString) == ' ') {
      char[] charsBoard = gameBoard.toCharArray();
      charsBoard[locOfString] = piece;
      gameBoard = new String(charsBoard);
      pieceBeLast = piece;
      return gameBoard;
    } else {
      return " Position taken. Choose another.";
    } 
    //return " Put " + piece + " on " + Integer.toString(numOfRow) + " Row, " + Integer.toString(numOfCol) + " Col.";
  }

}
