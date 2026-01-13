package src;

import java.util.ArrayList;
import java.util.List;

public class Team { 
    private int id; 
    private String teamName;
    private String country;
    private List<Player> players;
    private int maxPlayers;
    
    // Maç istatistikleri için yeni alanlar
    private int points;
    private int gamesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;
    
    // Ana constructor (3 parametreli)
    public Team(String teamName, String country, int maxPlayers) {
        this.teamName = teamName;
        this.country = country;
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
        this.points = 0;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
    }

    // Yeni constructor (2 parametreli - Main'de kullanılan)
    public Team(int id, String teamName) {
        this.id = id;
        this.teamName = teamName;
        this.country = ""; // boş bırakabiliriz veya null
        this.players = new ArrayList<>();
        this.maxPlayers = 25; // varsayılan değer
        this.points = 0;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
    }

    public boolean addPlayer(Player player) {
        if (players.size() >= maxPlayers) {
            System.out.println("Cannot add player. Team is already at maximum capacity.");
            return false;
        }
        
        // Oyuncu zaten listede mi?
        for (Player p : players) {
            if (p.getName().equals(player.getName()) && p.getJerseyNumber() == player.getJerseyNumber()) {
                System.out.println("Player is already in the team.");
                return false;
            }
        }
        
        // country boşsa kontrol yapma
        if (country != null && !country.isEmpty() && 
            !player.getNationality().equalsIgnoreCase(this.country)) {
            System.out.println("Error: " + player.getName() + " is not from " + this.country + 
                             " (" + player.getNationality() + ")");
            return false;
        }
        
        // Aynı forma numarası var mı?
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

    public boolean removePlayer(int jerseyNumber) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getJerseyNumber() == jerseyNumber) {
                Player removedPlayer = players.remove(i);
                System.out.println("Removed player: " + removedPlayer.getName() + " from " + teamName);
                return true;
            }
        }
        System.out.println("Player with jersey number " + jerseyNumber + " not found in " + teamName);
        return false;
    }

    public Player findPlayer(int jerseyNumber) {
        for (Player player : players) {
            if (player.getJerseyNumber() == jerseyNumber) {
                return player;
            }
        }
        return null;
    }

    public double calculateTotalMarketValue() {
        double total = 0;
        for (Player player : players) {
            total += player.getMarketValue();
        }
        return total;
    }

    // Maç sonrası istatistik güncelleme
    public void updateStats(int goalsScored, int goalsConceded) {
        this.gamesPlayed++;
        this.goalsFor += goalsScored;
        this.goalsAgainst += goalsConceded;
        
        if (goalsScored > goalsConceded) {
            this.wins++;
            this.points += 3;
        } else if (goalsScored == goalsConceded) {
            this.draws++;
            this.points += 1;
        } else {
            this.losses++;
        }
    }

    // Getter metodları
    public int getId() {
        return id;
    }
    
    public String getTeamName() {
        return teamName;
    }

    public String getName() {
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

    public int getPoints() {
        return points;
    }
    
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    
    public int getWins() {
        return wins;
    }
    
    public int getDraws() {
        return draws;
    }
    
    public int getLosses() {
        return losses;
    }
    
    public int getGoalsFor() {
        return goalsFor;
    }
    
    public int getGoalsAgainst() {
        return goalsAgainst;
    }
    
    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

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
}