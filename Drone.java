/*
 * CPSC220 - ProjectBadMath
 * Ethan Bostick and Garrett Mckenzie
 * Drone object class, used to create drones
 */

import java.lang.Math;

public class Drone
{
    public int damage;
    public int health;
    public int boost;
    public boolean isAlive;
    public boolean hasBoost;
    public int scrapValue;
    public int countBoost;
    public String type;
    public int difficulty;
    public boolean isEnemy;
    public int ogDamage;
    public int ogHealth;


    //The Type is either Attack "a" , Defence "d" , or Mining "m". Difficulty should be an integer between one and 3, isEnemy will only be false when the player buys a new drone
        public Drone(String _init_type , int _init_difficulty, boolean _init_isEnemy)
        {
            hasBoost = true;
            isAlive = true;
            type = _init_type;
            difficulty  = _init_difficulty;
            isEnemy = _init_isEnemy;
    //Gives the created drone stats based off the type of the drone
            //For the miner drone
            if (_init_type.equals("m"))
            {
                countBoost = 2;
                health = 20;
                damage = 0;
                scrapValue = 20;
                if (isEnemy)
                {
                    scrapValue += (int) (((Math.random())*20)+1);
                }
            }
            else
            {
                countBoost = 1;
            }

            //For the attack drone
            if(_init_type.equals("a")){
                if (difficulty == 3 && (_init_type.equals("a")))
                {
                    damage = 8;
                    health = 10;
                    scrapValue = 18;
                }
                else if (difficulty == 2 && (_init_type.equals("a")))
                {
                    damage = 6;
                    health = 8;
                    scrapValue = 12;
                }
                else
                {
                    damage = 4;
                    health = 6;
                    scrapValue = 8;           
                }
            }
            //For the defence drone
            if (_init_type.equals("d")){
                if (difficulty == 3 && (_init_type.equals("d")))
                {
                    damage = 6;
                    health = 16;
                    scrapValue = 26;
                }
                else if (difficulty == 2 && (_init_type.equals("d")))
                {
                    damage = 4;
                    health = 14;
                    scrapValue = 20;
                }
                else
                {
                    damage = 2;
                    health = 12;
                    scrapValue = 16;           
                }

                ogDamage = damage;
                ogHealth = health;
            }
        }

        //Getters
        public boolean getHasBoost()
        {
            return this.hasBoost;
        }
       
        public int giveDmg()
        {
            Player.amountAmmo -= 1;
            return this.damage;
        }
   
        public String[] giveBoost()
        {
            String[] return_me = {this.type,Integer.toString(this.boost)};
            this.countBoost -= 1;
            if (this.countBoost <= 0)
            {
                this.hasBoost = false; 
            }
            return return_me; //returns a list. pos 0 is type. pos 1 is boost amount.
        }
   
        public String getType()
        {
            return this.type;
        }
   
        public int getScrapValue()
        {
            return this.scrapValue;
        }

        public boolean getIsEnemy()
        {
            return this.isEnemy;
        }

        //Setters
        public void takeDmg(int damage)
        {
            this.health -= damage;
        }

        public void getBoost(String[] boostInfo)
        {
            String giverType  = boostInfo[0];
            int giverAmount = Integer.parseInt(boostInfo[1]);

            if (giverType.equals("d"))
            {
                this.health += giverAmount;
            }
            else if (giverType.equals("m"))
            {
                this.health += giverAmount;
            }
            else
            {
                this.damage += giverAmount;
            }
        }

        //Other Methods
        public boolean checkStatus()
        {
            if (this.health <= 0)
            {
                return false;
            }
            else
            {
                return  true;
            }
        }

        public void refreshDrone()
        {
            this.hasBoost = true;
            this.health = ogHealth;
            this.damage = ogDamage;
        }


        public void upgradeDrone()
        {
            if (this.type.equals("m"))
            {
                this.health += 2;
                this.countBoost += 2;
            }
            else
            {
                this.damage += 2;
                this.health += 2;
                this.ogDamage = this.damage;
                this.ogHealth = this.health;
            }
        }

        public String toString()
        {
            return ("Type:" + this.type + " Health:" + this.health + " Damage:" + this.damage + " hasBoost:" + this.hasBoost);
        }
}