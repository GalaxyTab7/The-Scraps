/*
 * CPSC220 - ProjectBadMath
 * Ethan Bostick and Garrett Mckenzie
 * class for creating room objects
 */

import java.lang.Math;
import java.util.ArrayList;

public class Room 
{
    public boolean hasRecon;
    public int scrapInRoom;
    public ArrayList<Drone> enemyDronesInRoom; 
    public boolean hasPirates;
    public String roomDescription;
    public int difficulty;
    public ArrayList<Drone> copyEnemyDronesInRoom;

    public Room(boolean _init_hasRecon , boolean _init_hasPirates ,  int _init_scrapInRoom, int _init_difficulty , String _init_roomDescription)
    {
        this.hasRecon = _init_hasRecon;
        this.hasPirates = _init_hasPirates;
        this.scrapInRoom = _init_scrapInRoom;
        this.roomDescription = _init_roomDescription;
        this.difficulty = _init_difficulty;

        //This randomly creates the enemies in the room if they are present as determined by hasPirates boolean
        if (this.hasPirates)
        {
            int num_attack = (int) ((Math.random()) * 3);
            int num_defend = (int) ((Math.random()) * 3);
            int num_miner = (int) ((Math.random()) * 2);


            if (num_attack <= 0 && num_defend <= 0 && num_miner <=0)
            {
                Drone attack = new Drone("a",this.difficulty,true);
                Drone defend= new Drone("d",this.difficulty,true);
                Drone miner = new Drone("m",this.difficulty,true);
                Drone[] listDrone = {attack,defend,miner};
                this.enemyDronesInRoom = new ArrayList<Drone>();
                for (Drone drone : listDrone)
                {
                    this.enemyDronesInRoom.add(drone);
                }
            }
            else
            {
                this.enemyDronesInRoom = new ArrayList<Drone>();
                while (num_attack > 0)
                {
                    //Drone attack = new Drone("a",this.difficulty,true);
                    this.enemyDronesInRoom.add(new Drone("a",this.difficulty,true));
                    num_attack -= 1;
                }
                while (num_defend > 0)
                {
                    //Drone defend  = new Drone("d",this.difficulty,true);
                    this.enemyDronesInRoom.add(new Drone("d",this.difficulty,true));
                    num_defend -= 1;
                }
                while (num_miner > 0 )
                {
                    //Drone miner = new Drone("m",this.difficulty,true);
                    this.enemyDronesInRoom.add(new Drone("m",this.difficulty,true));
                    num_miner -= 1;
                }
            } 
            
        //Randomly assign positions for enemy drones
        int rndPos = 0;
        for (int i = 0 ; i < enemyDronesInRoom.size() ; i++)
        {
            rndPos = (int)(Math.random()) * (enemyDronesInRoom.size()-2);
            Drone temp = enemyDronesInRoom.get(i);
            enemyDronesInRoom.remove(i);
            enemyDronesInRoom.add(rndPos,temp);
        }
        }  
        else
        {
            this.enemyDronesInRoom = new ArrayList<Drone>();
        }

        this.copyEnemyDronesInRoom = new ArrayList<Drone>();
        this.copyEnemyDronesInRoom.addAll(enemyDronesInRoom);
    }

    //This method clears enemyDronesInRoom and then resets it with basically just more of everything.
    public void priorWasRecon()
    {
        this.hasPirates = true;
        this.enemyDronesInRoom.clear();
        int num_attack = (int) ((Math.random()) * 8);
        int num_defend = (int) ((Math.random()) * 8);
        int num_miner = (int) ((Math.random()) * 8);
        if (num_attack <= 0 && num_defend <= 0 && num_miner <=0)
            {
                Drone attack1 = new Drone("a",this.difficulty,true);
                Drone defend1 = new Drone("d",this.difficulty,true);
                Drone miner1 = new Drone("m",this.difficulty,true);
                Drone attack2 = new Drone("a",this.difficulty,true);
                Drone defend2 = new Drone("d",this.difficulty,true);
                Drone miner2 = new Drone("m",this.difficulty,true);
                Drone attack3 = new Drone("a",this.difficulty,true);
                Drone defend3 = new Drone("d",this.difficulty,true);
                Drone miner3 = new Drone("m",this.difficulty,true);
                Drone[] listDrone = {attack1 , attack2, attack3, defend1, defend2, defend3, miner1, miner2, miner3};
                for (Drone drone : listDrone)
                {
                    this.enemyDronesInRoom.add(drone);
                }
            }
            else
            {
                while (num_attack > 0)
                {
                    //Drone attack = new Drone("a",this.difficulty,true);
                    this.enemyDronesInRoom.add(new Drone("a",this.difficulty,true));
                    num_attack -= 1;
                }
                while (num_defend > 0)
                {
                    //Drone defend  = new Drone("d",this.difficulty,true);
                    this.enemyDronesInRoom.add(new Drone("d",this.difficulty,true));
                    num_defend -= 1;
                }
                while (num_miner > 0 )
                {
                    //Drone miner = new Drone("m",this.difficulty,true);
                    this.enemyDronesInRoom.add(new Drone("m",this.difficulty,true));
                    num_miner -= 1; 
                }
            } 
    }

    public void resetRoom()
    {
        this.enemyDronesInRoom.clear();
        this.enemyDronesInRoom.addAll(this.copyEnemyDronesInRoom);
    }

    public String toString()
    {
        String returnme = "";
        returnme += this.roomDescription + "\n";
        returnme += "Scrap Amount: " + this.scrapInRoom + "\n";
        for (Drone drone : this.enemyDronesInRoom)
        {
            returnme += drone.toString() + "||";
        }
        return returnme;
    }
}
