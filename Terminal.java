/*
 * CPSC220 - ProjectBadMath
 * Ethan Bostick and Garrett Mckenzie
 * Player terminal + other methods
 */

import java.lang.Math;
import java.util.*;

public class Terminal {

    private static int costUpgrade = 0;
    private static int costAmmo = 0;
    private static int costSupplies = 0;
    private static boolean activatedShop = false;
    private static int costAttackDrone;
    private static int costDefenseDrone;
    private static int costMiningDrone;
    

    /**
     * Used to get the last number in a string, will be used for targeting commands
     * @param cmd
     * @return last number in string as int
     */

    private static String extractNum(String cmd){
        int indexLen = cmd.length() - 1;
        char lastChar = cmd.charAt(indexLen);
        String num =Character.toString(lastChar);
        return(num);
    }
    /**
     * Used to identify the first letter of a command entered
     * @param cmd
     * @return The first character of a string
     */
    private static String extractId(String cmd){
        char firstChar = cmd.charAt(0);
        String num =Character.toString(firstChar);
        return(num);
    }
    /**
     * Checks if a string can become an int
     * @param strNum
     * @return true if string can be an int, false otherwise
     */
    public static boolean isNum(String strNum){
        try{ 
            int num = Integer.parseInt(strNum);
            num = num + 1; //here to get rid of the yellow warning
        }
        catch(NumberFormatException nfe){
            return(false);
        }
        return(true);
    }

    //lists out the commands and their uses
    public static void getHelp(){
        System.out.println("\nGame Info:");
        System.out.println(" Basics:\n - You need three things to continue venturing, drones, ammo, and supplies. All of\n   these things can be obtained using scrap at a space port, and to get scrap you need to\n   go on scrap runs into The Great Scraps");
        System.out.println(" Drones:\n - You have three types of drones, attack, defense, and mining.\n   attack and defense drones function as your basic offensive drones\n   both with their own specializations, mining drones allow you to harvest scrap from\n   ships. Mining drones do not have any offensive capabilities, instead they can\n   boost their allies stats.");
        System.out.println("   Drones are commanded from your terminal, you have several commands to navigate them");
        System.out.println(" Scrap Runs:\n - Upon using the jump command you will have three different sectors of The\n   Great Scraps to go to, all with different difficulties, upon going to a sector you will enter\n   a randomized ship, each room has a random chance to contain pirates and an ammount of scrap.\n   Each ship also has one pirate recon drone in a room. If this drone finds you, the next\n   room will contain alarge group of pirates.");
        System.out.println(" - You require mining drones to collect scrap, the less mining drones you have the\n   less efficiently you collect scrap.");
        System.out.println(" Fighting:\n - Fighting is turn based, player drones then enemy drones. Drones are given either an\n   Attack or boost command, if the player loses all their drones in combat they will die.");
        System.out.println(" - Defeating enemy drones will reward you scrap.");
        System.out.println(" End:\n - There are Three ways to end the game; the first is by inputing the quit command.\n   The second is by getting stranded via running out of supplies (jumping requires one supply)\n And finally by losing all your drones in combat will result in your termination via pirates.");
        System.out.println("\nCommand List:");
        System.out.println(" QQQ (quit)\n - Quits the game");
        System.out.println(" S (status)\n - Shows the current player status and inventory of drones");
        System.out.println(" MP (map)\n - Reads out the visual feed of the current locations map");
        System.out.println(" MG# (move group)\n - Moves a group of drones to the target area\n   Ex: MG3 (Moves drones to area 3)");
        System.out.println(" J (jump)\n - Menu of location options will printed, ship will jump to selected location");
        System.out.println(" R (retreat)\n - allows the user to retract their drones out of the ship and out of battles");
        System.out.println(" RE (rearrange)\n - Only available in the spaceport, allows the user to rearrange their drones");
        System.out.println("");
        System.out.println("Battle commands (only available while in battle):\n DMG# (damage)\n - Used to damage a target drone, target is passed as a number with the command\n   Ex: DMG2 (damages enemy drone 2)");
        System.out.println(" B# (boost)\n - Boosts a target friendly drone increasing their stats\n   Ex: B3 (Boosts friendly drone 3)\n");
    }

    /*
     * Method to move ship to another sector of The Great Scraps
     * open using this command the user will be given a warning prompt to confirm then select which sector they want to jump to
     */
    public static void moveShip(){
        Random rand = new Random();
        String playerInput = "";
        boolean goodToMove = true;
        if((Player.amountSupplies <= 1) && (Player.curSpacePort)){
            System.out.println("//Warning guild supply requirment not met//");
            System.out.println("//Buy more supplies from space port or quit main sequence and wait for guild to bring more supplies//");
            goodToMove = false;
        }
        else if ((Player.amountSupplies == 1) && (Player.curInMission))
        {
            System.out.println("//Warning low supplies//");
            System.out.println("//Failure to return to space port now will result in a slow and painfull death//");
        } 
        else if ((Player.amountSupplies <= 0 ) && (Player.curInMission))
        {
            System.out.println("//No more supplies onboard//");
            System.out.println("//Unable to reach guild tow ship//");
            System.out.println("//Warning low O2 levels//");
            System.out.println("//Goodbye scrapper//");
            Player.playerIsAlive = false;
            Player.curInBattle = false;
            Player.curInMission = false;
            Player.curSpacePort = false;
            Main.bruteForceQuit = true;
            goodToMove = false;
        }
        if (goodToMove)
        {
            boolean inMenu = true;
            System.out.println("User//Foster//JumpDrive//Selector//");
            System.out.println("Select jump location");
            System.out.println("Sector 1, current danger level = low. Argure '1'.");
            System.out.println("Sector 2, current danger level = medium. Argure '2'.");
            System.out.println("Sector 2, current danger level = high. Argure '3'.");
            System.out.println("Main space port. Argue 'port'.");
            System.out.println("To quit this menue, argue 'q'.");
            System.out.println("To repeat this menu, argure 'help'.");
            while (inMenu)
            {
                playerInput = Main.in.nextLine();
                if (playerInput.equals("1"))
                {
                    Main.curMap = MapGen.makeMap(1);
                    Main.seed = rand.nextInt(2000) + rand.nextInt(2000) + rand.nextInt(500);
                    System.out.println("//User//Foster//JumpDrive//CheckStatus//Execute");
                    System.out.println("//Checks true//");
                    System.out.println("//Location lock sector 1//");
                    System.out.println("Executing jump sequence...");
                    activatedShop=false;
                    Player.curInMission = true;
                    Player.curSpacePort = false;
                    inMenu = false;
                    Main.missionStart = true;
                }
                else if (playerInput.equals("2"))
                {
                    Main.curMap = MapGen.makeMap(2);
                    Main.seed = rand.nextInt(2000) + rand.nextInt(2000) + rand.nextInt(500);
                    System.out.println("//User//Foster//JumpDrive//CheckStatus//Execute");
                    System.out.println("//Checks true//");
                    System.out.println("//Location lock sector 2//");
                    System.out.println("Executing jump sequence...");
                    activatedShop=false;
                    Player.curInMission = true;
                    Player.curSpacePort = false;
                    Main.missionStart = true;
                    inMenu = false;
                }
                else if (playerInput.equals("3"))
                {
                    Main.curMap = MapGen.makeMap(3);
                    Main.seed = rand.nextInt(2000) + rand.nextInt(2000) + rand.nextInt(500);
                    System.out.println("//User//Foster//JumpDrive//CheckStatus//Execute");
                    System.out.println("//Checks true//");
                    System.out.println("//Location lock sector 3//");
                    System.out.println("Executing jump sequence...");
                    activatedShop=false;
                    Player.curInMission = true;
                    Player.curSpacePort = false;
                    Main.missionStart = true;
                    inMenu = false;
                }
                else if (playerInput.equals("port"))
                {
                    System.out.println("//User//Foster//JumpDrive//CheckStatus//Execute");
                    System.out.println("//Checks true//");
                    System.out.println("//Location lock port//");
                    System.out.println("Executing jump sequence...");
                    Player.amountSupplies -= 1;
                    Player.curInMission = false;
                    Player.curSpacePort = true;
                    inMenu = false;
                    for (Drone drone : Player.dronesList)
                    {
                        drone.refreshDrone();
                    }
                }
                else if (playerInput.equals("help"))
                {
                    System.out.println("Sector 1, current danger level = low. Argure '1'.");
                    System.out.println("Sector 2, current danger level = medium. Argure '2'.");
                    System.out.println("Sector 2, current danger level = high. Argure '3'.");
                    System.out.println("To quit this menue, argue 'q'.");
                    System.out.println("To repeat this menu, argure 'help'.");
                }
                else if (playerInput.equals("q"))
                {
                    System.out.println("//Cooling jump core//");
                    System.out.println("//Cooldown sequence complete//");
                    System.out.println("//Exiting jump sequence//");
                    inMenu = false;
                }
                else
                {
                    System.out.println("Argument not recognized. Please try again.");
                    System.out.println("Argue 'help' to see the options for sequence User//Foster//JumpDrive//Selector//");
                }
            }

        }
    }

    /**
     * moves the player's current room to the target room
     * @param tarRm
     */
    public static void moveGroup(int tarRm){
        Player.playerCurRoom = tarRm-1;
    }

    //method to rearrange order of your drones
    public static void droneRe(){
        String tarDrone;
        String newSpot;
        int tarDroneInt = 0;
        int newSpotInt = 0;
        //display drones
        System.out.print("\nPlayer Drones:");
        int count = 1;
        for (int i = 0 ; i < Player.dronesList.size() ; i ++){
            if (i%2 == 0)
            {
                System.out.printf("\n[(%s)%s] ",count, Player.dronesList.get(i));
            }
            else
            {
                System.out.printf("[(%s)%s] ",count, Player.dronesList.get(i));
            }
            count++;
        }
        System.out.println(" ");

        boolean done = false;
        while(!done){
            System.out.println("Enter Drone Num:");
            tarDrone = Main.in.nextLine();
            if(isNum(tarDrone)){
                tarDroneInt = Integer.parseInt(tarDrone);
                if(tarDroneInt>Player.dronesList.size()){
                System.out.println("!Invalid Drone Number: out of range!");
                continue;
                }
                done = true;
            }
            else{
                System.out.println("!Invalid Drone Number: not int!");
                continue;
            }
        }
        boolean done2 = false;
        while(!done2){
            System.out.println("Enter Desired Drone Position:");
            newSpot = Main.in.nextLine();
            if(isNum(newSpot)){
                newSpotInt = Integer.parseInt(newSpot);
                if(newSpotInt>Player.dronesList.size()){
                System.out.println("!Invalid Drone Number: out of range!");
                continue;
                }
                done2 = true;
            }
            else{
                System.out.println("!Invalid Drone Number: not int!");
                continue;
            }
        }
        Player.reArrangeDrones(tarDroneInt, newSpotInt);
        System.out.printf("\n//Drone %s and %s swapped//%n", tarDroneInt, newSpotInt);
    }

    //method to print your current status
    public static void getStats()
    {
        System.out.println("\nPlayer Stats...");
        System.out.println("Scrap Amount: " + Player.amountScrap + "; Ammo Amount: " + Player.amountAmmo + "; Supplies Amount: " + Player.amountSupplies);
        System.out.print("\nPlayer Drone Stats...");
        int count = 1;
        for (int i = 0 ; i < Player.dronesList.size() ; i ++){
            if (i%2 == 0)
            {
                System.out.printf("\n[(%s)%s] ",count, Player.dronesList.get(i));
            }
            else
            {
                System.out.printf("[(%s)%s] ",count, Player.dronesList.get(i));
            }
            count++;
        }
        System.out.println(" ");

    }

    //This is the command that called when the player wants to quit, functions be breaking all the loops it can
    public static void quitGame(){
        Player.quitQuit = true;
        Player.curSpacePort = false;
        Player.curInBattle = false;
        Player.curInMission = false;
    }

    //buy command brings up the shop in the spaceport, allowing the player to buy drones, supplies, and upgrades: typing q will exit
    public static void buy(){   
        if (!(activatedShop))
        {
            //random values for each instance of the shop
            costUpgrade = (int) ((Math.random())*11)+5;
            costSupplies = (int) ((Math.random())*7)+2;
            costAmmo = (int) ((Math.random())*3)+1;
            costAttackDrone = (int) ((Math.random())*30)+20;
            costDefenseDrone = (int) ((Math.random())*25)+15;
            costMiningDrone = (int) ((Math.random())*25)+15;
            activatedShop = true;
        }
        boolean inShop = true;
        int target = 0;
        String tempTarget = "";
        String player_arg = "";
        System.out.println("\n~Welcome to the Shop!~");
        System.out.println("Cost to upgrade a drone = " + costUpgrade + ". Argue 'U'.");
        System.out.println("Cost to buy supplies = " + costSupplies + ". Argue 'S'.");
        System.out.println("Cost to buy ammo = " + costAmmo + ". Argue 'A'.");
        System.out.println("To enter the drone garage, argue 'drone'.");
        System.out.println("Exit the shop, argue 'Q'.");
        System.out.println("Player inventory, argure 'ST'.");
        System.out.println("To see these options again, argue 'help'.");

    //the loop for the shop allowing error checking and player to continue shopping
        while(inShop)
        {
            System.out.println("External//Terminal//Kevin\'s_General_Shop//");
             player_arg = Main.in.nextLine();
        //upgrading a drone block
             if (player_arg.toLowerCase().equals("u"))
             {
                System.out.println("Which drone number would you like to upgrade?");
                boolean check = true;
                while (check)
                {
                    tempTarget = Main.in.nextLine();
                    if (isNum(tempTarget))
                    {
                        target = Integer.parseInt(tempTarget);
                        check = false;
                    }
                    else
                    {
                        System.out.println("Warning argument must be of type int");
                    }
                }
                if (target < Player.dronesList.size() && Player.amountScrap >= costUpgrade)
                {
                    System.out.println("Drone number " + target + " is upgraded.");
                    Player.upgradeDrone(target, costUpgrade);
                }
                else if(Player.amountScrap < costUpgrade)
                {
                    System.out.println("What do you think this is a charity!!!! Get out of here before we blast you!");
                    System.out.println("External terminal executed force quit. In main true.");
                    inShop = false;
                }
                else
                {
                   System.out.println("Sorry we didnt recognize that command. It looks like that drone number isnt in your group.");
                }
             }
        //buying supplies code block
             else if (player_arg.toLowerCase().equals("s"))
             {
                System.out.println("How many supplies would you like to buy?");
                boolean check = true;
                while (check)
                {
                    tempTarget = Main.in.nextLine();
                    if (isNum(tempTarget))
                    {
                        target = Integer.parseInt(tempTarget);
                        check = false;
                    }
                    else
                    {
                        System.out.println("Warning argument must be of type int");
                    }
                }
                if (target*costSupplies <= Player.amountScrap)
                {
                    System.out.println("Transfer Complete, supplies are in the hull.");
                    Player.buySupplies(target, costSupplies);
                }
                else
                {
                    System.out.println("What do you think this is a charity!!!! Get out of here before we blast you!");
                    System.out.println("External terminal executed force quit. In main true.");
                    inShop = false;
                }
             }
        //buying ammo code block
             else if (player_arg.toLowerCase().equals("a"))
             {
                System.out.println("How much ammo would you like to buy?");
                boolean check = true;
                while (check)
                {
                    tempTarget = Main.in.nextLine();
                    if (isNum(tempTarget))
                    {
                        target = Integer.parseInt(tempTarget);
                        check = false;
                    }
                    else
                    {
                        System.out.println("Warning argument must be of type int");
                    }
                }
                if (target*costAmmo <= Player.amountScrap)
                {
                    System.out.println("Transfer Complete, ammo is in the hull.");
                    Player.buyAmmo(target, costAmmo);
                }
                else
                {
                    System.out.println("What do you think this is a charity!!!! Get out of here before we blast you!");
                    System.out.println("External terminal executed force quit. In main true.");
                    inShop = false;
                }
            
             }
        //seperate part of the shop for buying drones
            else if (player_arg.equals("drone")){
                System.out.println("\nExternal//Terminal//Kevins_Garage//");
                System.out.println("AHHH my friend welcome to the garage. My name is Kevin, the greatest drone mechanic this side of the great scraps.");
                System.out.println("Cost to buy attack drone = " + costAttackDrone + ". Argue 'A'.");
                System.out.println("Cost to buy defense drone = " + costDefenseDrone + ". Argue 'D'.");
                System.out.println("Cost to buy mining drone = " + costMiningDrone + ". Argue 'M'.");
                System.out.println("To see internal terminal stats, argure 'ST'.");
                System.out.println("To leave the garage, argue 'q'.");
                System.out.println("To see these options again. Argue 'help'.");
                boolean inGarage = true;
    //while loop for the drone garage
                while (inGarage){
                    player_arg = Main.in.nextLine();
            //buy an attack drone
                    if (player_arg.toLowerCase().equals("a"))
                    {
                        if (Player.amountScrap >= costAttackDrone)
                        {
                            System.out.println("The new attack drone has been added to the group.");
                            Drone attack = new Drone("a",0,false);
                            Player.buyDrone(attack,costAttackDrone);
                        }
                        else if (Player.amountScrap < costAttackDrone)
                        {
                            System.out.println("You try to scam me!!! Get out of my shop before for I plant a homing device on your balls and luanch a tactical nuke at it!!");
                            System.out.println("External terminal executed force quit. In main true.");
                            inGarage = false;
                            inShop = false;
                        }
                    }
            //buying a defense drone
                    else if (player_arg.toLowerCase().equals("d"))
                    {
                        if (Player.amountScrap >= costDefenseDrone)
                        {
                            System.out.println("The new defense drone has been added to the group.");
                            Drone defend = new Drone("d",0,false);
                            Player.buyDrone(defend,costDefenseDrone);
                        }
                        else if (Player.amountScrap < costDefenseDrone)
                        {
                            System.out.println("You try to scam me!!! Get out of my shop before for I plant a homing device on your balls and luanch a tactical nuke at it!!");
                            System.out.println("External terminal executed force quit. In main true.");
                            inGarage = false;
                            inShop = false;
                        }
                    }
            //buying a mining drone
                    else if (player_arg.toLowerCase().equals("m"))
                    {
                        if (Player.amountScrap >= costMiningDrone)
                        {
                            System.out.println("The new miner drone has been added to the group.");
                            Drone miner = new Drone("m",0,false);
                            Player.buyDrone(miner,costMiningDrone);
                        }
                        else if (Player.amountScrap < costMiningDrone)
                        {
                            System.out.println("You try to scam me!!! Get out of my shop before for I plant a homing device on your balls and luanch a tactical nuke at it!!");
                            System.out.println("External terminal executed force quit. In main true.");
                            inGarage = false;
                            inShop = false;
                        }
                    }
            //help menu for this part of the shop
                    else if (player_arg.equals("help")){
                        System.out.println("Cost to buy attack drone = " + costAttackDrone + ". Argue 'A'.");
                        System.out.println("Cost to buy attack drone = " + costDefenseDrone + ". Argue 'D'.");
                        System.out.println("Cost to buy attack drone = " + costMiningDrone + ". Argue 'M'.");
                        System.out.println("To see internal terminal stats, argure 'ST'.");
                        System.out.println("To leave the garage, argue 'Q'.");
                        System.out.println("To see these options again. Argue 'help'.");
                    }
                    else if (player_arg.toLowerCase().equals("st")){
                        getStats();
                    }
                    else if (player_arg.equals("q")){
                        System.out.println("Goodbye my friend! Kevin wishes you well.");
                        inGarage = false;
                    }
                    else{
                        System.out.println("I am sorry my friend, my terminal didn't recognize that.");
                        System.out.println("To see the help menu. Argue 'help'.");
                    }
                }
             }
             else if (player_arg.equals("help")){
                System.out.println("Cost to upgrade a drone = " + costUpgrade + ". Argue 'U'.");
                System.out.println("Cost to buy supplies = " + costSupplies + ". Argue 'S'.");
                System.out.println("Cost to buy ammo = " + costAmmo + ". Argue 'A'.");
                System.out.println("If you would like to exit the shop, argue 'Q'.");
                System.out.println("To see internal terminal stats, argure 'ST'.");
                System.out.println("If you would like to see these options again, argue 'help'.");

             }
             //exits the shop
             else if (player_arg.equals("q")){
                inShop = false;
                System.out.println("Thank you for your patronage! Have a nice day.");
                System.out.println("External terminal exit sequence true.");
             }
             //prints the players inventory and drones
             else if (player_arg.toLowerCase().equals("st")){
                getStats();
             }
             //when a command is not recognized
             else{
                System.out.println("Sorry we didnt recognize that argument to our terminal. Please try again.");
                System.out.println("If you would like options, argue 'help'.");
             }
        }
    }   
    
    /**
     * Terminal method, used to call the terminal
     * Can be called in other classes with Terminal.term()
     * uses the most common letter in commands to efficiently find the command entered
     */
    public static ArrayList<String> term(){
        ArrayList<String> cmdRtrn = new ArrayList<String>();
        System.out.println("User//Foster//cmd_term//");
        boolean act = false;
        while(!act){
            String cmd = Main.in.nextLine();
            if (cmd.toLowerCase().equals("help")){
                getHelp();
            }
            else if (cmd.toLowerCase().equals("qqq")){
                quitGame();
                cmdRtrn.add(0, "z");
                cmdRtrn.add(1, "0");
                act = true;
            }
            //All commands containing s
            else if (cmd.toLowerCase().contains("s")){
                if (cmd.toLowerCase().equals("s")){
                    getStats();
                }
                else{
                    System.out.println("!Invalid Command (See \"HELP\")!");
                }
            }
            //All commands containing d, no repeats if in s above
            else if (cmd.toLowerCase().contains("d")){
        //damages a target drone
                if (cmd.toLowerCase().contains("dmg")){
                    if (Player.curInBattle){
                        String action = extractId(cmd);
                        String target = extractNum(cmd);
                        if(isNum(target)){
                            cmdRtrn.add(0, action);
                            cmdRtrn.add(1, target);
                            act = true;
                        }
                        else{
                            System.out.println("!Command Lacks Target!");
                        }
                    }
                    else {
                        System.out.println("!Command Unavailable!");
                        continue;
                    }
                }
                else{
                    System.out.println("!Invalid Command (See \"HELP\")!");
                }
            }
            //All commands containg m, no repats if in s or d above
            else if (cmd.toLowerCase().contains("m")){
        //moves drones to a room, sets the player location to that room, this is how the missions function
                if (cmd.toLowerCase().contains("mg")){
                    String target = extractNum(cmd);
                    if (Player.curInMission && (!Player.curInBattle)){
                        if(isNum(target)){
                            int targetRm = Integer.parseInt(target);
                            if(targetRm <= Main.curMap.size()){
                                moveGroup(targetRm);
                                act = true;
                            }
                            else{
                                System.out.println("!Invalid Target Room!");
                                continue;
                            }
                        }
                        else{
                            System.out.println("!Invalid Command (See \"HELP\")!");
                        }
                    }
                    else {
                        System.out.println("!Command Unavailable!");
                        continue;
                    }
                }
            //displays the current map using the map class
                else if (cmd.toLowerCase().equals("mp")){
                    if (Player.curInMission){
                        Map.displayMap(Main.curMap, Main.seed);
                    }
                    else {
                        System.out.println("!Command Unavailable!");
                        continue;
                    }
                }
                else{
                    System.out.println("!Invalid Command (See \"HELP\")!");
                } 
            }
        //buy command for in the spaceport
            else if (cmd.toLowerCase().equals("buy")){
                if(Player.curSpacePort){
                    buy();
                    act = true;
                }
                else{
                    System.out.println("!Command Unavailable!");
                    continue;
                }
            }
            else if (cmd.toLowerCase().equals("bar")){
                if(Player.curSpacePort){
                    Bar.spaceBar();
                    act = true;
                }
                else{
                    System.out.println("!Command Unavailable!");
                    continue;
                }
            }
        //jump command, calls the moveShip method which allows the player to move to different locations in space
            else if (cmd.toLowerCase().equals("j")){
                if(!Player.curInBattle){
                    moveShip();
                    act = true;
                }
                else{
                    System.out.println("!Command Unavailable!");
                    continue;
                }
            }
        //retreat command, retracting drones 
            else if(cmd.toLowerCase().equals("r")){
                if (Player.curInMission){
                    moveGroup(0);
                    cmdRtrn.add(0, "q");
                    cmdRtrn.add(1, "0");
                    act = true;
                }
                else {
                    System.out.println("!Command Unavailable!");
                    continue;
                }
            }
            else if (cmd.toLowerCase().equals("re")){
                if(Player.curSpacePort){
                    droneRe();
                    act = true;
                }
                else{
                    System.out.println("!Command Unavailable!");
                    continue;
                }
            }
        //boost command for in fights
            else if (cmd.toLowerCase().contains("b")){
                if (Player.curInBattle){
                    String action = extractId(cmd);
                    String target = extractNum(cmd);
                    if(isNum(target)){
                        cmdRtrn.add(0, action);
                        cmdRtrn.add(1, target);
                        act = true;
                    }
                    else{
                        System.out.println("!Command Lacks Target!");
                    }
                }
                else {
                    System.out.println("!Command Unavailable!");
                    continue;
                }
            }
            else{
                System.out.println("!Invalid Command (See \"HELP\")!");
            }
        }
        return(cmdRtrn);  
    }
}
