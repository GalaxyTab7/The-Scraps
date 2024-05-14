/*
 * CPSC220 - ProjectBadMath
 * Ethan Bostick and Garrett Mckenzie
 * map processing, takes in the map and the seed to display the rooms in the map 
 */

import java.util.*;

public class Map{

    /**
     * method to print out the map, uses a seed to print out the same result every time for each map
     * @param map
     * @param seed
     */
    public static void displayMap(ArrayList<Room> map, int seed){
        Random rand = new Random(seed);

        //reveals a number of rooms and whether or not they have enemies, based on the map difficulty
        int revealNum = rand.nextInt(4-(map.get(0).difficulty));

        //interate through map list to get each rooms data
        int rmNum = 1;
        for(int i=0; i<map.size();i++){
            Room room = map.get(rmNum-1);
            boolean reveal = rand.nextBoolean();
            if(reveal && revealNum>0){
                if(i%2 == 0){
                    System.out.printf("\n[Room %s: %s scrap|enemy detected = %s]", rmNum, room.scrapInRoom, room.hasPirates);
                }
                else{
                    System.out.printf("[Room %s: %s scrap|enemy detected = %s]", rmNum, room.scrapInRoom, room.hasPirates);
                }
                revealNum--;
            }
            else{
                if(i%2 == 0){
                    if (room.scrapInRoom-rand.nextInt(16) > 0)
                    {
                        System.out.printf("\n[Room %s: %s-%s scrap|enemy detected = ?]", rmNum, room.scrapInRoom-rand.nextInt(16), room.scrapInRoom+rand.nextInt(20));
                    }
                    else 
                    {
                        System.out.printf("\n[Room %s: %s-%s scrap|enemy detected = ?]", rmNum, 0, room.scrapInRoom+rand.nextInt(20));
                    }
                }
                else{
                    if (room.scrapInRoom-rand.nextInt(16) > 0)
                    {
                        System.out.printf("[Room %s: %s-%s scrap|enemy detected = ?]", rmNum, room.scrapInRoom-rand.nextInt(16), room.scrapInRoom+rand.nextInt(20));
                    }
                    else
                    {
                        System.out.printf("[Room %s: %s-%s scrap|enemy detected = ?]", rmNum, 0, room.scrapInRoom+rand.nextInt(20));
                    }
                }
            }
            rmNum++;
        }
        System.out.println("\n");
    }
}

