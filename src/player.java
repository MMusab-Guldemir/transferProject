package src;

import java.util.Arrays;
import java.util.List;

public class Player { 
    private String name;
    private int age;
    private String nationality;
    private int jerseyNumber;
    private String position;
    private int marketValue;

    public Player(String name, int age, String nationality, int jerseyNumber, String position, int marketValue) {
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.jerseyNumber = jerseyNumber;
        this.position = position;
        this.marketValue = marketValue;
    }
    
    // Alternatif constructor (Main'de kullanmak için)
    public Player(int id, String name, String surname, int jerseyNumber, String position) {
        this.name = name + " " + surname;
        this.age = 25; // varsayılan yaş
        this.nationality = "Unknown"; // varsayılan milliyet
        this.jerseyNumber = jerseyNumber;
        this.position = position;
        this.marketValue = 1000000; // varsayılan piyasa değeri
    }

    // Getter metodları
    public String getName() {
        return name;
    }
    
    public String getNationality() {
        return nationality;
    }
    
    public int getAge() {
        return age;
    }
    
    public int getJerseyNumber() {
        return jerseyNumber;
    }
    
    public String getPosition() {
        return position;
    }
    
    public int getMarketValue() {
        return marketValue;
    }
    
    // Setter metodları
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Invalid age. Age must be positive.");
        }
    }

    public void setJerseyNumber(int jerseyNumber) {
        if (jerseyNumber >= 1 && jerseyNumber <= 99) {
            this.jerseyNumber = jerseyNumber;
        } else {
            throw new IllegalArgumentException("Invalid jersey number. It must be between 1 and 99.");
        }
    }

    public void setPosition(String position) {
        List<String> validPositions = Arrays.asList("Goalkeeper", "Defender", "Midfielder", "Forward");
        
        if (validPositions.contains(position)) {
            this.position = position;
        } else {
            throw new IllegalArgumentException("Invalid position. Valid positions are: " + validPositions);
        }
    }
    
    public void setMarketValue(int marketValue) {
        if (marketValue > 0) {
            this.marketValue = marketValue;
        } else {
            throw new IllegalArgumentException("Invalid market value. It must be positive.");
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return jerseyNumber == player.jerseyNumber && 
               name.equals(player.name);
    }
    
    @Override
    public int hashCode() {
        return 31 * name.hashCode() + jerseyNumber;
    }

    @Override
    public String toString() {
        return String.format("%-25s %-15s No: %-2d Pos: %-12s Value: %d$", 
            name, nationality, jerseyNumber, position, marketValue);
    }
}