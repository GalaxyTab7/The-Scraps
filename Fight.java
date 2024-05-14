/*
 * CPSC220 - ProjectBadMath
 * Ethan Bostick and Garrett Mckenzie
 * fight class, creates a fight object with the two drone lists as parameters, this is how drone fights are intiated
 */

import java.util.ArrayList;
import java.lang.Math;

public class Fight 
{
    //Instance Var
    private boolean isFighting;
    private int scrapReward;
    private ArrayList<Drone> player;
    private ArrayList<Drone> opponent;
    private boolean playerWin;
    
    //Constructor
    public Fight(ArrayList<Drone> _init_player , ArrayList<Drone> _init_opponent)
    {  
        player = _init_player;
        opponent = _init_opponent;
        isFighting = true;
        for (Drone drone : opponent)
        {
            if (drone.getIsEnemy())
            {
                scrapReward += drone.getScrapValue();
            }
        }
    }

    //Getter for scrapReward amount if the player wins and if they are still fighting
    public int getScrapReward()
    {
        return this.scrapReward;
    }

    //returns a boolean for whether or not a fight is ongoing. Not really use.
    public boolean getIsFighting()
    {
        return this.isFighting;
    }

    //Remove dead drones from the enemy drone list
    public void cleanOpponent()
    {
        int i = 0;
        while (i < opponent.size())
        {
            if (opponent.get(i).health <= 0)
            {
                opponent.remove(i);
            }
            else
            {
                i ++;
            }
        }
    }

    //Remove dead from the player drone list
    public void cleanPlayer()
    {
        int i = 0;
        while (i < player.size())
        {
            if (player.get(i).health <= 0)
            {
                player.remove(i);
            }
            else
            {
                i ++;
            }
        }    
    }

    //The actual fight happens here
    public boolean haveFight()
    {
        //Bunch of variables f
        ArrayList<String> command = new ArrayList<String>();
        boolean havingFight = true;
        boolean bruteForceQuit = false;
        boolean retreat = false;
        String action = "";
        int target = 0;
        int enemyTarget = 0;
        String enemyAction = "";


        //Frontend
        System.out.println("//Drones weapons are go//");
        System.out.println("//Battle sequence initiated//");

        //fight continues while havingFight is true
        while (havingFight)
        {
            //removes dead drones from both arraylists kinda all over the place just for redundancy :)
            cleanPlayer();
            cleanOpponent();

            //checks if opponent is out of drones
            if (opponent.isEmpty())
            {
                System.out.println("//All Contacts down//");
                playerWin = true;
                havingFight = false;
            }

            //checks if player is out of drones
            if (player.isEmpty())
            {
                System.out.println("//Ship under attack// systems red// hull breached//");
                System.out.println("//systems failing// evacua-");
                Player.playerIsAlive  = false;
                Player.curInMission = false;
                Player.curSpacePort = false;
                Player.playerCurRoom = -1;
                Player.curInBattle = false;
                Main.bruteForceQuit = true;
                playerWin = false;
                havingFight = false;
            }

            //ends loop if fight is over
            if (!havingFight)
            {
                return playerWin;
            }

            //prints player and opponent drone info
            System.out.println("\n");
            System.out.print("\nPlayer Drones: " + player.size());
            int count = 1;
            for (int i = 0 ; i < player.size() ; i ++)
            {
                if (i%2 == 0)
                {
                    System.out.printf("\n[(%s)%s] ",count, player.get(i));
                }
                else
                {
                    System.out.printf("[(%s)%s] ",count, player.get(i));
                }
                count++;
            }
            System.out.println("\n");
            System.out.print("\nEnemy Drones: " + opponent.size());
            count = 1;
            for (int i = 0 ; i < opponent.size() ; i ++)
            {
                if (i%2 == 0)
                {
                    System.out.printf("\n[(%s)%s] ",count, opponent.get(i));
                }
                else
                {
                    System.out.printf("[(%s)%s] ",count, opponent.get(i));
                }
                count++;
            }

            //Player Action loop. Goes drone by drone.
            int index = 0;
            for (Drone drone : player)
            {
                //frontend
                System.out.println("\n");
                System.out.println("//Drone " + (index+1) + drone.getType() + "//");
                index += 1;
                if (retreat)
                {
                    System.out.println("//Rotate out execute//");
                }
                boolean valid_cmd = false;
                boolean outOfBoost = false;
                boolean outOfAmmo = false;
                int attempts = 0;

                //While loop to validate a command from terminal
                while (!valid_cmd && !retreat)
                {
                    //get player command
                    System.out.println("//Awaiting battle cmd//");
                    command.addAll(Terminal.term());
                    action = command.get(0);
                    target = Integer.parseInt(command.get(1));
                    target -= 1;
                    command.clear();

                    //validating attack action
                    if (action.equals("d"))
                    {
                        if (target >= opponent.size() && target > 0 )
                        {
                            System.out.println("//Out of targeting range//");
                        }
                        else if (Player.amountAmmo <= 0)
                        {
                            System.out.println("//Out of ammo//");
                            valid_cmd = true;
                            outOfAmmo = true;
                        }
                        else if(drone.getType().equals("m"))
                        {
                            System.out.println("//Mining drones cannot attack");
                        }
                        else if (!(opponent.get(target).checkStatus()))
                        {
                            System.out.println("//Target already down//");
                        }
                        else
                        {
                            valid_cmd = true;
                        }
                    }
                    //validating boost action
                    else if (action.equals("b"))
                    {
                        if (target >= player.size())
                        {
                            System.out.println("//Out of targeting range//");
                        }
                        else if (!drone.getHasBoost())
                        {
                            System.out.println("//Drone out of boost//");
                            valid_cmd = true;
                            outOfBoost = true;
                        }
                        else if (drone.getType().equals("a") && (!player.get(target).checkStatus()))
                        {
                            System.out.println("//Target already down//");
                        }
                        else
                        {
                            valid_cmd = true;
                        }
                    }

                    //validating retreat action and executing
                    else if (action.equals("q"))
                    {
                        retreat = true;
                        valid_cmd = true;
                    }

                    //validating quit action and executing
                    else if (action.equals("z"))
                    {
                        bruteForceQuit = true;
                        valid_cmd = true;
                        break;
                    }
                    attempts += 1; 

                    //Limits the player to 5 attempts to give a correct order
                    if (attempts == 5)
                    {
                        System.out.println("//Delay in response moving to next drone//");
                        valid_cmd = true;
                        target = 0;
                        action = " ";
                        break;
                    }
                }//end while loop for validation

                //force quit
                if (bruteForceQuit)
                {
                    break;
                }

                //Taking valid actions and executing them

                //attacking
                if (action.equals("d") && !outOfAmmo)
                {
                    System.out.println("//Attacking enemy drone " + (target + 1) + "//");
                    (opponent.get(target)).takeDmg(drone.giveDmg());
                    if (!((opponent.get(target)).checkStatus()))
                    {
                        System.out.println("//Tango down//");
                    }
                }

                //boosting
                else if (action.equals("b") && !outOfBoost)
                {
                    System.out.println("//Boosting allied drone " + target + "//" );
                    (player.get(target)).getBoost(drone.giveBoost());
                }

                //do nothing print
                else if (!retreat)
                {
                    System.out.println("//No valid action given//");
                }

                //Remove dead drones from opponents list
                cleanOpponent();

                //checks if opponent is out of drones
                if (opponent.isEmpty())
                {
                    System.out.println("//All Contacts down//");
                    playerWin = true;
                    havingFight = false;
                }

                //ends loop if fight is over
                if (!havingFight)
                {
                    return playerWin;
                }

                //prints player and opponent drone info
                System.out.print("\nPlayer Drones: " + player.size());
                count = 1;
                for (int i = 0 ; i < player.size() ; i ++)
                {
                    if (i%2 == 0)
                    {
                        System.out.printf("\n[(%s)%s] ",count, player.get(i));
                    }
                    else
                    {
                        System.out.printf("[(%s)%s] ",count, player.get(i));
                    }
                    count++;
                }
                System.out.println("\n");
                System.out.print("\nEnemy Drones: " + opponent.size());
                count = 1;
                for (int i = 0 ; i < opponent.size() ; i ++)
                {
                    if (i%2 == 0)
                    {
                        System.out.printf("\n[(%s)%s] ",count, opponent.get(i));
                    }
                    else
                    {
                        System.out.printf("[(%s)%s] ",count, opponent.get(i));
                    }
                    count++;
                }
            }//end for loop of the player action
            System.out.println("\n");

            //force quit
            if (bruteForceQuit)
            {
                Player.quitQuit = true;
                break;
            }

            //removes dead drones from both arraylists
            cleanPlayer();
            cleanOpponent();

            //checks if opponent is out of drones
            if (opponent.isEmpty())
            {
                System.out.println("//All Contacts down//");
                playerWin = true;
                havingFight = false;

            }

            //checks if player is out of drones
            if (player.isEmpty())
            {
                System.out.println("//Ship under attack systems critical//");
                Player.playerIsAlive  = false;
                playerWin = false;
                havingFight = false;
                
            }

            //ends loop if fight is over
            if (!havingFight)
            {
                return playerWin;
            }

            //Enemy's turn goes drone for drone in the enemy's list
            int choice = 0;
            int randNum = 0;
            for (Drone enemyDrone : opponent)
            {
                String[] listoptions = new String[2];
                //Set the options for the drone
                listoptions[0] = "d";
                if (enemyDrone.getHasBoost())
                {
                    listoptions[1] = "b";
                }
                else 
                {
                    listoptions[1] = "d";
                }

                //Makes it so mining drones can only boost
                if (enemyDrone.getType().equals("m"))
                {
                    listoptions[0] = "b";
                    listoptions[1] = "b";
                }

                //randomly choose an action for the drone and a target
                randNum = (int) ((Math.random())*(101));
                if ( 50 < randNum)
                {
                    choice = 1;
                }
                else if (50 > randNum)
                {
                    choice = 0;
                }

                enemyAction = listoptions[choice];
                if (enemyAction.equals("d"))
                {
                    enemyTarget = (int) ((Math.random()) * (player.size()));
                }
                else if (enemyAction.equals("b"))
                {
                    enemyTarget = (int) ((Math.random()) * (opponent.size()));
                }

                //perform randomly selected action and tell that to the player

                //attack
                if (enemyAction.equals("d"))
                {
                    System.out.println("//Enemy drone is attacking " + (enemyTarget + 1) + "//");
                    (player.get(enemyTarget)).takeDmg(enemyDrone.giveDmg());
                    if (!player.get(enemyTarget).checkStatus())
                    {
                        System.out.println("//Allied Drone " + (enemyTarget+1) + " out of action//");
                    }
                }

                //boost
                else if (enemyAction.equals("b"))
                {
                    System.out.println("//Enemy drone is boosting " + (enemyTarget + 1) + "//");
                    (opponent.get(enemyTarget)).getBoost(enemyDrone.giveBoost());
                }

                //removes dead drones from player list
                cleanPlayer();

                //checks if opponent is out of drones
                if (player.isEmpty())
                {
                    System.out.println("//Ship under attack// systems red// hull breached//");
                    System.out.println("//systems failing// evacua-");
                    Player.playerIsAlive  = false;
                    Player.curInMission = false;
                    Player.curSpacePort = false;
                    Player.playerCurRoom = -1;
                    Player.curInBattle = false;
                    Main.bruteForceQuit = true;
                    playerWin = false;
                    havingFight = false;
                }

                //ends loop if fight is over
                if (!havingFight)
                {
                    return playerWin;
                }
            }//end enemy turn

            //Completes the retreat action
            if (retreat)
            {
                cleanPlayer();
                cleanOpponent();
                if (player.size() > 0)
                {
                    System.out.println("//Retreat complete drones ready for redeployment or jump//");
                }
                else
                {
                    System.out.println("//No drones recovered but the ship remains intact. Jump immediately//");
                }
                playerWin = false;
                havingFight = false;
                return playerWin;
            }

        }//ends  while havingFight

        //Removes dead drones from both player and opponent drone lists
        cleanPlayer();
        cleanOpponent();
        return playerWin;

    }//ends haveFight()
}//end class Fight
