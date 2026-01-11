package src;

import java.lang.classfile.TypeAnnotation.SupertypeTarget;
import java.lang.foreign.GroupLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeMap;

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

        int index1 = findTeamIndex(team1);
        int index2 = findTeamIndex(team2);

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


    
    
}
