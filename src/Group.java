package src;



public class Group {
    
    private String name;
    private int groupSize;
    private int numberOfTeams;
    private Team[] teams;
    private Game[][] games; // 2D Arrays for games
    private int[] points;

    public Group(String name, int groupSize){
        if (groupSize <= 0){
            throw new IllegalArgumentException("Group size must be positive!");
        }

        this.name = name;
        this.groupSize = groupSize;
        this.numberOfTeams = 0;
        this.teams = new Team[groupSize];
        this.games = new Game[groupSize][groupSize];
        this.points = new int[groupSize];

        for (int i = 0; i < groupSize; i++){
            points[i] = 0;
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

    public boolean teamExists(int teamId){
        for (int i = 0; i < numberOfTeams; i++){
            if (teams[i].getId() == teamId){
                return true;
            }
        }
        return false;
    }

    public boolean addTeam(Team team){

        if (numberOfTeams >= groupSize) {
            System.out.println("Cannot add team. Group is already at maximum capacity.");
            return false;
        }

        if (teamExists(team.getId())) {
            System.out.println("Team with ID " + team.getId() + " already exists in the group." + name);
            return false;
        }

        teams[numberOfTeams] = team;
        numberOfTeams++;
        System.out.println("Successfully added team " + team.getTeamName() + " to group " + name + ".");
        return true;
    }

    private int findTeamIndexById(int teamId){
        for (int i = 0; i < numberOfTeams; i++) {
            if (teams[i].getId() == teamId) {
                return i;
            }
        }
        return -1;
    }

    public boolean addGame(Game game) {
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
            System.out.println("Cannot add game: One or both teams are not in the group " + name );
            return false;
        }

        if (games[index1][index2] != null || games[index2][index1] != null) {
            System.out.println("Game between " + team1.getTeamName() + " and " + team2.getTeamName() + " already exists in group " + name);
            return false;
        }

        if (team1.equals(team2)) {
            System.out.println("A team cannot play against itself");
            return false;
        }

        games[index1][index2] = game;
        games[index2][index1] = game; // simetrik ekleme
    

        updatePoints(game, index1, index2);

        System.out.println("Game added to group " + name + ": " + game.toString());
        return true;
    }   

    private void updatePoints(Game game, int index1, int index2) {
        java.util.ArrayList<Integer> gameGoals = game.getGoals();
        if (gameGoals.size() < 2) return;

        int team1Goals = gameGoals.get(0);
        int team2Goals = gameGoals.get(1);


        if (team1Goals > team2Goals) {

            points[index1] += 3;
            points[index2] += 0;
        } else if (team1Goals < team2Goals) {

            points[index1] += 0;
            points[index2] += 3;
        } else {

            points[index1] += 1;
            points[index2] += 1;

        }


    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Group ").append(name).append(" Standings ===\n");
        sb.append("Group Size: ").append(groupSize).append("\n");
        sb.append("Current Teams: ").append(numberOfTeams).append("/").append(groupSize).append("\n");
        
        if (numberOfTeams == 0) {
            sb.append("No teams in the group yet.\n");
            return sb.toString();
        }
        
        // Tablo başlığı
        sb.append("\nTeam Standings (sorted by points):\n");
        sb.append(String.format("%-5s %-20s %-8s %-6s %-6s %-6s\n", 
                "Pos", "Team", "Played", "W", "D", "L", "Pts"));
        sb.append("------------------------------------------------------------\n");
        
        // Takımları ve istatistiklerini sırala (kabarcık sıralama)
        for (int i = 0; i < numberOfTeams - 1; i++) {
            for (int j = i + 1; j < numberOfTeams; j++) {
                if (points[j] > points[i]) {
                    // Takım ve puanları değiştir
                    Team tempTeam = teams[i];
                    teams[i] = teams[j];
                    teams[j] = tempTeam;
                    
                    int tempPoints = points[i];
                    points[i] = points[j];
                    points[j] = tempPoints;
                }
            }
        }
        
        // Her takım için istatistikleri göster
        for (int i = 0; i < numberOfTeams; i++) {
            int played = getGamesPlayed(i);
            int wins = getWins(i);
            int draws = getDraws(i);
            int losses = getLosses(i);
            
            sb.append(String.format("%-5d %-20s %-8d %-6d %-6d %-6d %-6d\n",
                    i + 1, 
                    teams[i].getTeamName(), 
                    played, 
                    wins, 
                    draws, 
                    losses, 
                    points[i]));
        }
        
        // Oynanan maçları listele
        sb.append("\nPlayed Games:\n");
        boolean hasGames = false;
        for (int i = 0; i < numberOfTeams; i++) {
            for (int j = i + 1; j < numberOfTeams; j++) {
                if (games[i][j] != null) {
                    sb.append("- ").append(games[i][j].toString()).append("\n");
                    hasGames = true;
                }
            }
        }
        
        if (!hasGames) {
            sb.append("No games played yet.\n");
        }
        
        return sb.toString();
    }
    
    // Yardımcı metodlar: Takım istatistiklerini hesapla
    private int getGamesPlayed(int teamIndex) {
        int count = 0;
        for (int j = 0; j < numberOfTeams; j++) {
            if (games[teamIndex][j] != null) {
                count++;
            }
        }
        return count;
    }
    
    private int getWins(int teamIndex) {
        int wins = 0;
        for (int j = 0; j < numberOfTeams; j++) {
            if (games[teamIndex][j] != null) {
                Game game = games[teamIndex][j];
                java.util.List<Team> gameTeams = game.getTeams();
                java.util.List<Integer> goals = game.getGoals();
                
                if (gameTeams.get(0).equals(teams[teamIndex])) {
                    // Takım ilk takım olarak oynamış
                    if (goals.get(0) > goals.get(1)) wins++;
                } else {
                    // Takım ikinci takım olarak oynamış
                    if (goals.get(1) > goals.get(0)) wins++;
                }
            }
        }
        return wins;
    }
    
    private int getDraws(int teamIndex) {
        int draws = 0;
        for (int j = 0; j < numberOfTeams; j++) {
            if (games[teamIndex][j] != null) {
                Game game = games[teamIndex][j];
                java.util.List<Integer> goals = game.getGoals();
                
                if (goals.get(0).equals(goals.get(1))) {
                    draws++;
                }
            }
        }
        return draws;
    }
    
    private int getLosses(int teamIndex) {
        return getGamesPlayed(teamIndex) - getWins(teamIndex) - getDraws(teamIndex);
    }

    public Team[] getTeams() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTeams'");
    }
}