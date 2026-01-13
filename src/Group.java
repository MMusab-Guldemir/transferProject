package src;

import java.util.Arrays;

public class Group {
    
    private String name;
    private int groupSize;
    private int numberOfTeams;
    private Team[] teams;
    private Game[][] games;
    private int[] points;
    private int[] goalsFor;
    private int[] goalsAgainst;

    public Group(String name, int groupSize) {
        if (groupSize <= 0) {
            throw new IllegalArgumentException("Group size must be positive!");
        }

        this.name = name;
        this.groupSize = groupSize;
        this.numberOfTeams = 0;
        this.teams = new Team[groupSize];
        this.games = new Game[groupSize][groupSize];
        this.points = new int[groupSize];
        this.goalsFor = new int[groupSize];
        this.goalsAgainst = new int[groupSize];

        for (int i = 0; i < groupSize; i++) {
            points[i] = 0;
            goalsFor[i] = 0;
            goalsAgainst[i] = 0;
        }
    }

    public String getName() {
        return name;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public Team[] getTeams() {
        Team[] result = new Team[numberOfTeams];
        for (int i = 0; i < numberOfTeams; i++) {
            result[i] = teams[i];
        }
        return result;
    }

    public boolean teamExists(int teamId) {
        for (int i = 0; i < numberOfTeams; i++) {
            if (teams[i].getId() == teamId) {
                return true;
            }
        }
        return false;
    }

    public boolean addTeam(Team team) {
        if (numberOfTeams >= groupSize) {
            System.out.println("Cannot add team. Group is already at maximum capacity.");
            return false;
        }

        if (teamExists(team.getId())) {
            System.out.println("Team with ID " + team.getId() + " already exists in the group " + name);
            return false;
        }

        teams[numberOfTeams] = team;
        numberOfTeams++;
        System.out.println("Successfully added team " + team.getTeamName() + " to group " + name + ".");
        return true;
    }

    public Team findTeamById(int teamId) {
        for (int i = 0; i < numberOfTeams; i++) {
            if (teams[i].getId() == teamId) {
                return teams[i];
            }
        }
        return null;
    }

    private int findTeamIndexById(int teamId) {
        for (int i = 0; i < numberOfTeams; i++) {
            if (teams[i].getId() == teamId) {
                return i;
            }
        }
        return -1;
    }

    public boolean addGame(Game game) {
        if (game == null) {
            System.out.println("Invalid game: Game is null.");
            return false;
        }
        
        java.util.ArrayList<Team> gameTeams = game.getTeams();
        if (gameTeams.size() < 2) {
            System.out.println("Invalid game. A game must have two teams.");
            return false;
        }
        
        Team team1 = gameTeams.get(0);
        Team team2 = gameTeams.get(1);

        int index1 = findTeamIndexById(team1.getId());
        int index2 = findTeamIndexById(team2.getId());

        if (index1 == -1 || index2 == -1) {
            System.out.println("Cannot add game: One or both teams are not in the group " + name);
            return false;
        }

        if (games[index1][index2] != null) {
            System.out.println("Game between " + team1.getTeamName() + " and " + team2.getTeamName() + 
                             " already exists in group " + name);
            return false;
        }

        if (team1.equals(team2)) {
            System.out.println("A team cannot play against itself");
            return false;
        }

        games[index1][index2] = game;
        games[index2][index1] = game;
    
        updatePoints(game, index1, index2);
        
        // Takımların istatistiklerini de güncelle
        team1.updateStats(game.getGoals().get(0), game.getGoals().get(1));
        team2.updateStats(game.getGoals().get(1), game.getGoals().get(0));

        System.out.println("Game added to group " + name + ": " + game.toString());
        return true;
    }   

    private void updatePoints(Game game, int index1, int index2) {
        java.util.ArrayList<Integer> gameGoals = game.getGoals();
        if (gameGoals.size() < 2) return;

        int team1Goals = gameGoals.get(0);
        int team2Goals = gameGoals.get(1);
        
        // Gol istatistiklerini güncelle
        goalsFor[index1] += team1Goals;
        goalsAgainst[index1] += team2Goals;
        goalsFor[index2] += team2Goals;
        goalsAgainst[index2] += team1Goals;

        if (team1Goals > team2Goals) {
            points[index1] += 3;
        } else if (team1Goals < team2Goals) {
            points[index2] += 3;
        } else {
            points[index1] += 1;
            points[index2] += 1;
        }
    }

    public void displayStandings() {
        System.out.println("\n=== Group " + name + " Standings ===");
        System.out.println("Group Size: " + groupSize);
        System.out.println("Current Teams: " + numberOfTeams + "/" + groupSize);
        
        if (numberOfTeams == 0) {
            System.out.println("No teams in the group yet.");
            return;
        }
        
        // Sıralama için yardımcı sınıf oluştur
        class TeamStanding {
            Team team;
            int points;
            int goalsFor;
            int goalsAgainst;
            
            TeamStanding(Team team, int points, int goalsFor, int goalsAgainst) {
                this.team = team;
                this.points = points;
                this.goalsFor = goalsFor;
                this.goalsAgainst = goalsAgainst;
            }
            
            int getGoalDifference() {
                return goalsFor - goalsAgainst;
            }
        }
        
        // Takım verilerini topla
        TeamStanding[] standings = new TeamStanding[numberOfTeams];
        for (int i = 0; i < numberOfTeams; i++) {
            standings[i] = new TeamStanding(teams[i], points[i], goalsFor[i], goalsAgainst[i]);
        }
        
        // Sıralama: önce puan, sonra gol farkı, sonra attığı gol
        for (int i = 0; i < numberOfTeams - 1; i++) {
            for (int j = i + 1; j < numberOfTeams; j++) {
                boolean swap = false;
                
                // 1. Puan karşılaştırması
                if (standings[j].points > standings[i].points) {
                    swap = true;
                } 
                // 2. Puan eşitse gol farkı
                else if (standings[j].points == standings[i].points) {
                    if (standings[j].getGoalDifference() > standings[i].getGoalDifference()) {
                        swap = true;
                    }
                    // 3. Gol farkı da eşitse attığı gol
                    else if (standings[j].getGoalDifference() == standings[i].getGoalDifference()) {
                        if (standings[j].goalsFor > standings[i].goalsFor) {
                            swap = true;
                        }
                    }
                }
                
                if (swap) {
                    TeamStanding temp = standings[i];
                    standings[i] = standings[j];
                    standings[j] = temp;
                }
            }
        }
        
        // Tablo başlığı
        System.out.println("\nPos  Team                 Pld   W   D   L   GF   GA   GD   Pts");
        System.out.println("----------------------------------------------------------------");
        
        // Sıralanmış takımları göster
        for (int i = 0; i < numberOfTeams; i++) {
            Team team = standings[i].team;
            int pts = standings[i].points;
            int gf = standings[i].goalsFor;
            int ga = standings[i].goalsAgainst;
            int gd = gf - ga;
            
            System.out.printf("%-4d %-20s %-4d %-3d %-3d %-3d %-4d %-4d %-4d %-4d%n",
                i + 1,
                team.getTeamName(),
                team.getGamesPlayed(),
                team.getWins(),
                team.getDraws(),
                team.getLosses(),
                gf,
                ga,
                gd,
                pts);
        }
        
        // Oynanan maçları listele
        System.out.println("\nPlayed Games:");
        boolean hasGames = false;
        for (int i = 0; i < numberOfTeams; i++) {
            for (int j = i + 1; j < numberOfTeams; j++) {
                if (games[i][j] != null) {
                    System.out.println("- " + games[i][j].toString());
                    hasGames = true;
                }
            }
        }
        
        if (!hasGames) {
            System.out.println("No games played yet.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Group ").append(name).append(" ===\n");
        sb.append("Group Size: ").append(groupSize).append("\n");
        sb.append("Current Teams: ").append(numberOfTeams).append("/").append(groupSize).append("\n");
        
        if (numberOfTeams == 0) {
            sb.append("No teams in the group yet.\n");
            return sb.toString();
        }
        
        for (int i = 0; i < numberOfTeams; i++) {
            sb.append(i + 1).append(". ").append(teams[i].getTeamName())
              .append(" (ID: ").append(teams[i].getId()).append(")\n");
        }
        
        return sb.toString();
    }
}