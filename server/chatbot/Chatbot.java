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
    return "No such command";
  } 
}
