/*
 * CPSC220 - ProjectBadMath
 * Ethan Bostick and Garrett Mckenzie
 * Player terminal + other methods
 */

public class Bar {
    //space bar for the space port, purchasing beer does nothing but add to your player stats
    public static void spaceBar(){
        int costBudweiser = 4;
        int costCorona = 4;
        int costHeineken = 5;
        String cmd = "";

        System.out.println("\n~Welcome to KT\'s bar!~");
        System.out.println("There are several beers available:");
        System.out.println("   Budweiser: 1 scrap\n   Corona: 1 scrap\n   Heineken: 2 scrap");
        System.out.println("To order a beer argue:");
        System.out.println("\'B\' - budweiser; \'C\' - Corona; \'H\' - Heineken");
        System.out.println("To See funds argue 'f'");
        System.out.println("To exit argue \'Q\'");
        System.out.println("Argue \'HELP\' to see these options again");
    //while loop for the bar, and bool to track when the player wants to leave
        boolean inBar = true;
        while(inBar){
            System.out.println("External//Terminal//KT\'s_Bar//");
            cmd = Main.in.nextLine();
            if(cmd.toLowerCase().equals("b")){
                if(Player.amountScrap>=costBudweiser){
                    Player.totalBeers++;
                    Player.totalBudweisers++;
                    Player.amountScrap-= costBudweiser;
                    System.out.println("Enjoy the beer!");
                }
                else{
                    System.out.println("Your funds are insufficient, please leave.");
                }
            }
            else if(cmd.toLowerCase().equals("c")){
                if(Player.amountScrap>=costCorona){
                    Player.totalBeers++;
                    Player.totalCoronas++;
                    Player.amountScrap-= costCorona;
                    System.out.println("Enjoy the beer!");
                }
                else{
                    System.out.println("Your funds are insufficient, please leave.");
                }
            }
            else if(cmd.toLowerCase().equals("h")){
                if(Player.amountScrap>=costHeineken){
                    Player.totalBeers++;
                    Player.totalHeinekens++;
                    Player.amountScrap-= costHeineken;
                    System.out.println("Enjoy the beer!");
                }
                else if(Player.amountScrap>=1){
                    System.out.println("This seems a bit expensive for you, perhaps try a Budweiser or Corona.");
                }
                else{
                    System.out.println("Your funds are insufficient, please leave.");
                }
            }
            else if(cmd.toLowerCase().equals("f")){
                System.out.printf("\n//Scrap: %s//%n", Player.amountScrap);
            }
            else if(cmd.toLowerCase().equals("q")){
                System.out.println("Goodbye! Safe adventures traveler!");
                inBar = false;
            }
            else if(cmd.toLowerCase().equals("help")){
                System.out.println("There are several beers available:");
                System.out.println("   Budweiser: 1 scrap\n   Corona: 1 scrap\n   Heineken: 2 scrap");
                System.out.println("To order a beer argue:");
                System.out.println("\'B\' - budweiser; \'C\' - Corona; \'H\' - Heineken");
                System.out.println("To exit argue \'Q\'");
                System.out.println("Argue \'HELP\' to see these options again");
            }
            else{
                System.out.println("!Order not recognized!");
            }

        }
    }
}
