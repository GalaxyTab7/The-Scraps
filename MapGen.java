/*
 * CPSC220 - ProjectBadMath
 * Ethan Bostick and Garrett Mckenzie
 * map generation using the room object
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
public class MapGen {

    /**
     * creates a random map with a random set of rooms in the range of 5 to 20
     * @return returns an array list of room objects
     */
    public static ArrayList<Room> makeMap(int difficulty){
        Player.amountSupplies--;
        Player.shipsExplored++;
        Random rand = new Random();
        //array list of possible room descriptions
        ArrayList<String> rmDscpList = new ArrayList<String>();

        //interate through the txt file to create a list of room descriptions
        String fileName = "roomDesc.txt";
        try {
            Scanner fin = new Scanner(new FileInputStream(fileName));
            while (fin.hasNext()) {
                String line = fin.nextLine();
                rmDscpList.add(line);
            }
            fin.close();
        } 
        catch (FileNotFoundException e) {
            rmDscpList.add("!missing roomDesc.txt!");
        }
        int descNum = rmDscpList.size();

        //Room params
        boolean hasRecon = false;
        boolean hasPirates = false;
        int scrapInRoom = 0;
        String roomDescription = "";

        //random number for how many rooms the map can have
        int randNum = rand.nextInt(16)+5;
        ArrayList<Room> mapArrayList = new ArrayList<Room>();

        //generates the rooms with random elements
        int cnt = randNum;
        while(cnt>0){
            //room description fetch by index then removes it from list
            int randRmIn = rand.nextInt(descNum);
            roomDescription = rmDscpList.get(randRmIn);
            rmDscpList.remove(randRmIn);

            scrapInRoom = rand.nextInt(112)+22;

            int pirateProb = rand.nextInt(101);
            if (pirateProb > 69){
                hasPirates = true;
            }
            else{
                hasPirates = false;
            }

            //On the last room gen, creates a recon drone room and inserts it at a random index into the map array list
            if(cnt == 1){
                hasPirates = false;
                hasRecon = true;
                Room room = new Room(hasRecon, hasPirates, scrapInRoom, difficulty, roomDescription);
                int randIn = rand.nextInt(randNum);
                mapArrayList.add(randIn, room);
            }
            else{
                Room room = new Room(hasRecon, hasPirates, scrapInRoom, difficulty, roomDescription);
                mapArrayList.add(room);
            }
            cnt--;
            //prevents out of bounds on room description fetch
            descNum--;
        }
        return(mapArrayList);
    }
}
