package src;
import java.util.ArrayList;

public class Game {
    
    ArrayList<Team> teams = new ArrayList<>();
    ArrayList<Integer> goals = new ArrayList<>();

    public Game(Team team1, Team team2, Integer team1Goals, Integer team2Goals){
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

    public void getTeamPoints(Team team){
        if (teams.contains(team)){
            int index = teams.indexOf(team);
            int teamGoals = goals.get(index);
            int opponentGoals = goals.get(index == 0 ? 1 : 0); /// Anlamadım ???

            if (teamGoals > opponentGoals){
                System.out.println(team + " wins and gets 3 points.");
            }
            else if (teamGoals == opponentGoals){
                System.out.println(team + " draws and gets 1 point.");
            }
            else {
                System.out.println(team + " loses and gets 0 points.");
            }
        } else{
            System.out.println("Team not found in this game.");
        }
    }

    @Override
    public String toString(){
        if (teams.size() >= 2 && goals.size() >= 2) {
            // Team nesnesinin toString() metodunu çağır
            return teams.get(0).toString() + " " + goals.get(0) + " - " + 
                goals.get(1) + " " + teams.get(1).toString();
        }
        return "Game not properly initialized";
    }

}
