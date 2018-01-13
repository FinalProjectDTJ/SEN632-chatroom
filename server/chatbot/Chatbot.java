package server.chatbot;

import server.ConnectionThread;

public class Chatbot {

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
        return "(◕ܫ◕) ";
      }
      if (subString[0].equals("cheers")) {
        return " (╬▔▽▔)凸";
      }
      if (subString[0].equals("help")) {
        return "fight cute cheers";
      }

      return "No such things ...";
    }
    return "No such command";
  } 
}
