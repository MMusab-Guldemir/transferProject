package src;

import java.util.ArrayList;
import java.util.List;

public class Team { 
    private int id; 
    private String teamName;
    private String country;
    private List<Player> players;
    private int maxPlayers;
    
    // Ana constructor (3 parametreli)
    public Team(String teamName, String country, int maxPlayers) {
        this.teamName = teamName;
        this.country = country;
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
    }

    // Yeni constructor (2 parametreli - Main'de kullanılan)
    public Team(int id, String teamName) {
        this.id = id;
        this.teamName = teamName;
        this.country = ""; // boş bırakabiliriz veya null
        this.players = new ArrayList<>();
        this.maxPlayers = 25; // varsayılan değer
    }

    public boolean addPlayer(Player player) {
        if (players.size() >= maxPlayers) {
            System.out.println("Cannot add player. Team is already at maximum capacity.");
            return false;
        }
        
        if (players.contains(player)) {
            System.out.println("Player is already in the team.");
            return false;
        }
        
        // country boşsa kontrol yapma
        if (country != null && !country.isEmpty() && 
            !player.getNationality().equalsIgnoreCase(this.country)) {
            System.out.println("Error: " + player.getName() + " is not from " + this.country + 
                             " (" + player.getNationality() + ")");
            return false;
        }
        
        for (Player p : players) {
            if (p.getJerseyNumber() == player.getJerseyNumber()) {
                System.out.println("Error: Jersey number " + player.getJerseyNumber() + 
                                 " is already taken by " + p.getName() + ".");
                return false;
            }
        }
        
        players.add(player);
        System.out.println("Successfully added " + player.getName() + " to " + this.teamName + ".");
        return true;
    }

    // Diğer metodlar aynı kalabilir...
    // getTeamInfo, removePlayer, findPlayer, calculateTotalMarketValue vs.

    // toString metodunu ekleyelim
    @Override
    public String toString() {
        return teamName + (id > 0 ? " (ID: " + id + ")" : "");
    }
    
    // equals ve hashCode metodlarını ekleyelim
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Team team = (Team) obj;
        return id == team.id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }

    // Getter metodları
    public int getId() {
        return id;
    }
    
    public String getTeamName() {
        return teamName;
    }

    public String getCountry() {
        return country;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public int getPlayerCount() {
        return players.size();
    }
    
    public int getMaxPlayers() {
        return maxPlayers;
    }
    
    public boolean isTeamFull() {
        return players.size() >= maxPlayers;
    }
}