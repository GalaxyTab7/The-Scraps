/*
 * CPSC220 - ProjectBadMath
 * Ethan Bostick and Garrett Mckenzie
 * starting menu
 */

public class Menu {
   public Menu() {
   }

   public static boolean menu() {
      String user_input = "";
      int rand_int = (int)(Math.random() * 10000.0 + 100.0);
      System.out.println("Boot sequence is true, enter main sequence....\n");
      System.out.println("Welcome back to the command terminal operator.");
      System.out.printf("There are currently %d other spacers out in the great scraps.\n", rand_int);
      System.out.println("Are you ready to deploy? (y/n)");


      user_input = Main.in.nextLine();
      if (user_input.toLowerCase().contains("y")) {
         return true;
      } else {
         System.out.println("Very well operator.");
         System.out.println("Remember the guild requires your constant dedication to remain operational");
         System.out.println("DO NOT forget that.\n");
         System.out.println("Shutdown sequence is true, exit main sequence....");
         return false;
      }
   }
}

