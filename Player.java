/*
 * CPSC220 - ProjectBadMath
 * Ethan Bostick and Garrett Mckenzie 
 * Player attributes + other information
 * For methods in this static class that invovle an index position, the COUNT position from the player can be used and it will
 * be auto adjusted to the index position in the methods
 */

import java.util.ArrayList;

public class Player {
    //list of player's drones
    public static ArrayList<Drone> dronesList = new ArrayList<Drone>();

    //player location, used to allow certain commands
    public static boolean curSpacePort = false;
    public static boolean curInBattle = false;
    public static boolean curInMission = false;
    public static boolean playerIsAlive = true;
    public static int playerCurRoom = -1;
    public static boolean quitQuit = false;

    //player stat trackers
    public static int shipsExplored = 0;
    public static int totalScrapCollected = 0;
    public static int amountScrap = 0; 
    public static int amountAmmo = 0;
    public static int amountSupplies = 0;
    public static int roomsExplored = 0;

    public static int totalBeers = 0;
    public static int totalBudweisers = 0;
    public static int totalCoronas = 0;
    public static int totalHeinekens = 0;


    /**
     * Gets a supply amount and cost to then give the player and subtract from their scrap
     * @param supplyAmount
     * @param supplyCost
     */
    public static void buySupplies(int supplyAmount,int supplyCost)
    {
        Player.amountSupplies += supplyAmount;
        Player.amountScrap -= (supplyAmount * supplyCost);
    }

    /**
     * gets ammo amount and cost, gives the player the ammo, and subtracts costs
     * @param ammoAmount
     * @param ammoCost
     */
    public static void buyAmmo(int ammoAmount,int ammoCost)
    {
        Player.amountAmmo += ammoAmount;
        Player.amountScrap -= ammoAmount * ammoCost;
    }

    /**
     * gives scrap to the user, used to get a rooms scrap value and see how many miners they have, giving them different percentages of scrap based how many miners
     * @param scrapAmount
     */
    public static void getScrap(int scrapAmount)
    {
        double count_miner = 0.0;
        for (Drone drone : Player.dronesList)
        {
            if (drone.getType().equals("m"))
            {
                count_miner += 1;
            }
        }
        if (count_miner >= 4.0)
        {
            count_miner = 4.0;
        }
        double mul_by_me = (count_miner / 4.0);
        System.out.println("Successfully onboarded " + ((int)(scrapAmount * mul_by_me)) + " worth of scrap.");
        Player.amountScrap += (int)(scrapAmount * mul_by_me); 
        Player.totalScrapCollected += (int)(scrapAmount * mul_by_me);
        
    }

    /**
     * upgrades a given drone (arg1) at a given cost (arg2), the number given is the drone number which then is used to find its index
     * @param i
     * @param upgradeCost
     */
    public static void upgradeDrone(int i , int upgradeCost)
    {
        Player.amountScrap -= upgradeCost;
        Player.dronesList.get(i-1).upgradeDrone();
    }  

    /**
     * simple buy drone method, subtracts drone cost then adds the drone object to your drone list
     * @param drone
     * @param droneCost
     */
    public static void buyDrone(Drone drone,int droneCost)
    {
        Player.amountScrap -= droneCost;
        Player.dronesList.add(drone);
    }

//Other methods
    /**
     * rearranges drones, player gives the target drone they want to move and to what postion they want to move it to in the array list of drones
     * @param from
     * @param to
     */
    public static void reArrangeDrones(int from , int to)
    {
        Drone temp = dronesList.get(from-1);
        Player.dronesList.remove(from-1);
        if ( to-1 > from-1 )
        {
            Player.dronesList.add(to-2,temp);
        }
        else
        {
            Player.dronesList.add(to-1,temp);
        }
    }

    //Cleans the drone list, removing any dead drones
    public static void cleanDroneList()
    {
        for (int i = 0 ; i < dronesList.size() ; i++)
        {
            if (! dronesList.get(i).checkStatus())
            {
                System.out.println("//Drone " + (i+1) + " out of action//");
                dronesList.remove(i);
                i--;
            }
        }
    }
}
