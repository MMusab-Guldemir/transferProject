package src;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class player {
    
    String name;
    int age;
    String nationality;
    int jerseyNumber;
    String position;
    int marketValue;

    public player(String name, int age, String nationality, int jerseyNumber, String position, int marketValue) {
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.jerseyNumber = jerseyNumber;
        this.position = position;
        this.marketValue = marketValue;

        /* Yz
        setAge(age);
        setJerseyNumber(jerseyNumber);
        setPosition(position);
        setMarketValue(marketValue);       
        */
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age) {
        if (age > 0){
            this.age = age;
        }
        else
        {
            throw new IllegalArgumentException("Invalid age. Age must be positive.");
        }
    }

    public void setJerseyNumber(int jerseyNumber) {
        if (jerseyNumber >= 1 && jerseyNumber <= 99){
            this.jerseyNumber = jerseyNumber;
        }
        else
        {
            throw new IllegalArgumentException("Invalid jersey number. It must be between 1 and 99.");
        }
    }

    public void setPosition(String position){
        List<String> validPositions = Arrays.asList("Goalkeeper", "Defender", "Midfielder", "Forward");

        if (validPositions.contains(position)){
            this.position = position;
        }
        else
        {
            throw new IllegalArgumentException("Invalid position. Valid positions are: " + validPositions);
        }
    }
    
    public void setMarketValue(int marketValue){
        if (marketValue > 0){
            this.marketValue = marketValue;
        }
        else
        {
            throw new IllegalArgumentException("Invalid market value. It must be positive.");
        }
    }

    @Override
    public String toString() {
        return "New Player -> "  + name + "  " + age + "  " + nationality + "  " + jerseyNumber + "  " + position + "  " + marketValue + " $";
    }
}
