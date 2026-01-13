package src;

import java.util.ArrayList;

public class Game {
    
    private ArrayList<Team> teams;
    private ArrayList<Integer> goals;

    public Game(Team team1, Team team2, Integer team1Goals, Integer team2Goals) {
        this.teams = new ArrayList<>();
        this.goals = new ArrayList<>();
        
        this.teams.add(team1);
        this.teams.add(team2);
        this.goals.add(team1Goals);
        this.goals.add(team2Goals);
    }

    public ArrayList<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    public ArrayList<Integer> getGoals() {
        return new ArrayList<>(goals);
    }

    public Team getHomeTeam() {
        return teams.get(0);
    }
    
    public Team getAwayTeam() {
        return teams.get(1);
    }
    
    public int getHomeGoals() {
        return goals.get(0);
    }
    
    public int getAwayGoals() {
        return goals.get(1);
    }

    public void getTeamPoints(Team team) {
        if (teams.contains(team)) {
            int index = teams.indexOf(team);
            int teamGoals = goals.get(index);
            int opponentGoals = goals.get(index == 0 ? 1 : 0);

            if (teamGoals > opponentGoals) {
                System.out.println(team + " wins and gets 3 points.");
            } else if (teamGoals == opponentGoals) {
                System.out.println(team + " draws and gets 1 point.");
            } else {
                System.out.println(team + " loses and gets 0 points.");
            }
        } else {
            System.out.println("Team not found in this game.");
        }
    }

    @Override
    public String toString() {
        if (teams.size() >= 2 && goals.size() >= 2) {
            return teams.get(0).getTeamName() + " " + goals.get(0) + " - " + 
                   goals.get(1) + " " + teams.get(1).getTeamName();
        }
        return "Game not properly initialized";
    }
}