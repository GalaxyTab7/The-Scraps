/*
 * CPSC220 - project3
 * Main, where the game is run from
 * Ethan Bostick and Garrett Mckenzie
 * We pledge
 */

import java.util.*;
public class Main {
    public static Scanner in = new Scanner(System.in);
    public static int seed;
    private static boolean priorWasRecon = false;
    public static boolean missionStart = true;
    public static ArrayList<Room> curMap;
    public static boolean bruteForceQuit = false;

    /**
     * Does the rooms actions, which includes fighting pirates, identifiying if there is a recon drone, and harvesting scrap
     * @param rm
     */
    public static void doRoomAct(int rm){
        if(Player.playerCurRoom >= 0){
            Room curRm = Main.curMap.get(rm);
            boolean result = false;
            System.out.println("");
            System.out.println(curRm.roomDescription);
            Player.roomsExplored += 1;
            if(priorWasRecon){
                curRm.priorWasRecon();
                priorWasRecon = false;
            }
            if((curRm.hasPirates)){
                System.out.println("//Scanner contact hit//");
                System.out.println("//Likelyhood hostile 100%//");
                System.out.println("Recommend battlemode startup to battle sequence");
                System.out.println("//Transfer sequence true//");
                Player.curInBattle = true;
                Fight fight = new Fight(Player.dronesList, curRm.enemyDronesInRoom);
                result = fight.haveFight();
                Player.curInBattle = false;
                if(result){
                    Player.getScrap(fight.getScrapReward());
                    System.out.println("Pirates terminated | scrap collected");
                    curRm.hasPirates = false;
                }
                else if(!result && !Player.playerIsAlive){
                    result = false;
                    curRm.hasRecon = false;
                    curRm.hasPirates = true;
                    Player.curSpacePort = false;
                    Player.curInBattle = false;
                    Player.curInMission = false;
                }
                else
                {
                    System.out.println("");
                }
            }
            if((curRm.hasRecon)){
                priorWasRecon = true;
                System.out.println("//Enemy recon drone detected//");
                System.out.println("//You have been spotted by pirates//");
            }
            if(!curRm.hasPirates && curRm.scrapInRoom > 0){
                Player.getScrap(curRm.scrapInRoom);
                curRm.scrapInRoom -= curRm.scrapInRoom;
                System.out.println("Room scrap salvaged");
            }
        }
    }


    public static void main(String[] args){
        boolean start = true;
        boolean play = Menu.menu();
//main play loop of the game
        while (play){
            //sequence that only goes at the start of the game
            if(start){
                if (bruteForceQuit)
                {
                    break;
                }
                System.out.println("\nWelcome to the edge of The Great Scraps.");
                System.out.println("As part of the guild it is your job to venture into The Great Scraps to scavenge ship parts.");
                System.out.println("Be careful though, pirates often look for easy targets in the scrap fields.");
                System.out.println("Here, to get you started take this scrapper info-manual...");
                System.out.println("Press Enter to Read:");
                in.nextLine();
                Terminal.getHelp();
                System.out.println("Press Enter to Continue:");
                in.nextLine();
                System.out.println("You can purchase various goods at the nearest spaceport.");
                System.out.println("Here is some starting scrap you can trade in for some supplies.");
                System.out.println("Good luck greenhorn.");
                Player.curSpacePort = true;
                Player.amountScrap = 220;
                System.out.println("//220 scrap transfered//");
                System.out.println("//type \'HELP\' at any point to pull up the info-manual//\n");
                System.out.println("Press Enter to Continue:");
                in.nextLine();
            }
    //check player location then do actions for each location
            if(Player.curSpacePort){
                System.out.println("\n~Welcome to the spaceport!~");
                System.out.println("//Locations: [BUY - go to shop] [BAR - go to bar]");
                //loop while the player is in the spaceport
                while(Player.curSpacePort){
                    Terminal.term();
                }
                //quits game if player enters qqq cmd
                if(Player.quitQuit){
                    break;
                }
            }
            
            if(Player.curInMission){
                //loop for missions
                while(Player.curInMission){
                    if (bruteForceQuit)
                    {
                        break;
                    }
                    if(missionStart){
                        System.out.println("Ship layout:");
                        Map.displayMap(curMap, seed);
                        System.out.println("\n//Drone systems intializing//All systems positive//drones deployed");
                    }
                    doRoomAct(Player.playerCurRoom);
                    missionStart = false;
                    if (bruteForceQuit)
                    {
                        break;
                    }
                    Terminal.term();
                }
                //quits game if player enters qqq cmd
                if(Player.quitQuit){
                    break;
                }
            }
        }
        if(Player.quitQuit)
        {
            System.out.println("\n");
            System.out.println("Very well operator.");
            System.out.println("As per guild requirments, your relevant statistics will be sent to guild mentants for analysis.");
            System.out.println("Transmitting information...");
            System.out.println("Total Scrap Collected: " + Player.totalScrapCollected);
            System.out.println("Total Scrap Profit: " + Player.amountScrap);
            System.out.println("Total Scrap Ships Explored: " + Player.shipsExplored);
            System.out.println("Total Rooms Explored: " + Player.roomsExplored);
            System.out.printf("Purchased: %s Budweisers, %s Coronas, %s Heinekens\nTotal Beers Purchased: %s%n", Player.totalBudweisers, Player.totalCoronas, Player.totalHeinekens, Player.totalBeers);
            System.out.println("Transmission complete");
            System.out.println("Thank you for your service to the guild scrapper.");
            System.out.println("Shutdown sequence is true, exit main sequence....");
        }
        else if (!Player.playerIsAlive)
        {
            System.out.println("\n");
            System.out.println("The legend of the great scrapper named Foster echoed through time after his untimely death");
            System.out.println("In the great Book of Foster, written by his fellow guild associates, it is rumored that he achieved many great things including...");
            System.out.println("Collecting a total of: " + Player.totalScrapCollected);
            System.out.println("Making a total profit for the great guild of: " + Player.amountScrap);
            System.out.println("Exploring a total number of ships of: " + Player.shipsExplored);
            System.out.println("Exploring a total number of rooms of: " + Player.roomsExplored);
            System.out.printf("And purchasing %s Budweisers, %s Coronas, %s Heinekens\nA total of %s beers purchased%n", Player.totalBudweisers, Player.totalCoronas, Player.totalHeinekens, Player.totalBeers);
            System.out.println("Also in the Book of Foster, it is rumored that his last words were....'May thy drones malfuction and implode'....");
            System.out.println("Wiser words have not been spoken.");
            System.out.println("Thank you guild scrapper");
        }
    }
}
