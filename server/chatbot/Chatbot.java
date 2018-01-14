package server.chatbot;

import server.ConnectionThread;
import java.util.Random;

public class Chatbot {

  private static String[] jokes = new String[] {"\nMan said to God --- Why did you make women so beautiful?\nGod said to man --- So that you will love them.\nMan said to God --- But why did you make them so dumb?\nGod said to man --- So that they will love you.",
    "\nDid you hear about the skeleton who walked into a cafe?\nHe ordered a cup of coffee and a mop.",
    "\nMary: John says I'm pretty. Andy says I'm ugly. What do you think, Peter?\nPeter: I think you're pretty ugly.",
    "\n\"Do you know what really amazes me about you?\"\n\"No.What?\"\n\"Oops.Sorry. I was thinking about someone else!\" "}; 

  public Chatbot() {

    System.out.print("Chatbot ");
  }

  public static void loading() {

    System.out.println("Chatbot loaded.");
  }

  public static String singleStr(ConnectionThread ct, String input) {

    String returnStr = "";
    if (input.startsWith("@name ")) {
      String newName;
      newName = (input.substring(6)).trim();
      String oldName = ct.getConnectionName();
      ct.changeConnectionName(newName);
      oldName = oldName + " change name to " + newName;
      return oldName;
    }

    if (input.startsWith("@v ")) {
      String[] subString = new String[3];
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
      return "No such things ...";
    }

    if (input.startsWith("@joke")) {
      Random random = new Random();
      int s = random.nextInt(jokes.length);
      return jokes[s];
    }

    return "No such command";
  } 
}
