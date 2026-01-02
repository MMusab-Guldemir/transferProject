package src;

import java.io.NotActiveException;
import java.net.ContentHandler;
import java.util.ArrayList;

public class team {
    int ID;
    String name;
    int avarageAge;
    int marketValue;
    ArrayList<player> squad = new ArrayList<>();

    public team(int ID, String name, int avarageAge, int marketValue, ArrayList squad) {
        this.ID = ID;
        this.name = name;
        this.avarageAge = avarageAge;
        this.marketValue = marketValue;
        this.squad = squad;
    }

    public static boolean playerExist(){
        return false;
    }

    public static void addPlayer() {
        
    }

    public static void removePlayer(){
        
    }
    @Override
    public String toString(){
        return "";
    } 

}
