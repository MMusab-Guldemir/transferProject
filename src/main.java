package src;

import java.lang.Thread.State;
import java.net.InterfaceAddress;
import java.nio.file.WatchService;
import java.security.PrivateKey;
import java.util.Scanner;
import java.util.Locale.Category;

import javax.management.loading.PrivateClassLoader;

public class Main {
    public static void main(String[] args) {
        // // Türk takımı oluştur
        Team turkishTeam = new Team(34, "Türkiye");
        Team germanTeam = new Team(6, "Germany");
        Team englandTeam = new Team(44, "England");

        System.out.println("---------------------------------------------------------------");
        System.out.println("Welcome to Qatar 2022! Get Ready for the World Cup!");
        System.out.println("---------------------------------------------------------------");
        System.out.println("--------------------------- Group: A ---------------------------");
        System.out.println("1 - Create a new Team");    
        System.out.println("2 - Display a Team ");
        System.out.println("3 - Add Player to a Team");
        System.out.println("4 - Remove a player with ID from a Team");
        System.out.println("5 - Add a Game to the Group");
        System.out.println("6 - Display Standings");
        System.out.println("7 - Exit");
        System.out.println("-----------------------------------------------------------");
        
        System.out.print("Your choice: ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        


        switch(choice) {
            case "1":
                yeniTakimOlustur(scanner, null);
                break;

            case "2":
                takimBilgileriniGoster(scanner);
                break;
            
            case "3":
                takimaOyuncuEkle(scanner);
                break;

            case "4":
                takimdanOyuncuSil(scanner);
                break;
            
            case "5":
                grubaMacEkle(scanner, null);
                break;
            
            case "6":
                grupSiralamasiniGöster(scanner, null);
                break;  

            case "7":
                System.out.println("Exiting the program. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        } 
        scanner.close();
    } 


    private static void yeniTakimOlustur(Scanner scanner, Group group){
        System.out.println("\n--- Creat a New Team ---");

        if (group.getGroupSize() <= group.getNumberOfTeams()) {
            System.out.print("Cannot add team. Group is already at maximum capacity.");
            return;
        }

        System.out.print("Team ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        


        System.out.print("Team Name: ");  
        String name = scanner.nextLine();


        

        if (group.getName() == null || group.getName().isEmpty()  || name == group.getName()) {
            System.out.println("Group name is not set. Cannot add team.");
            return;
        }

        Team newTeam = new Team(id, name);

        if (group.addTeam(newTeam)){
            System.out.println("Team " + name + " created successfully.");
        } else {
            System.out.println("Failed to create team " + name + ".");
        }

        group.addTeam(newTeam);
        
    }

    private static void takimBilgileriniGoster(Scanner scanner){
        System.out.println("\n--- Display Team Information ---");

        System.out.print("Enter Team ID: ");
        int teamId = scanner.nextInt();
        scanner.nextLine();


        Team team = teamId(teamId);
        if (team != null){
            System.out.println(team.toString());
            System.out.println("Players: ");
            if (team.getPlayers().size() > 0){
                for (Player player : team.getPlayers()) {
                    if (player != null) {
                        System.out.println(" " + player.getJerseyNumber() + ". " +
                                           player.getName() + " (" + 
                                           player.getNationality() + 
                                           ") - Market Value: " + 
                                           player.getMarketValue() + "M");
                    }
                }
            } else {
                System.out.println(" No players in this team.");
            }
        } else {
            System.out.println("Team with ID " + teamId + " not found.");
        }

    }

    private static void takimaOyuncuEkle(Scanner scanner){
        System.out.println("\n--- Add Player to a Team ---");

        System.out.print("Enter Team ID: ");
        int teamId = scanner.nextInt();
        scanner.nextLine();

        Team team = teamId(teamId);
        if (team == null){
            System.out.println("Team with ID " + teamId + " not found.");
            return;
        }

        System.out.print("Player Name: ");
        String playerName = scanner.nextLine();

        System.out.print("Player Surname: ");
        String playerSurname = scanner.nextLine();

        System.out.print("Jersey Number: ");
        String playerJerseyNumber = scanner.nextLine();

        for (Player player : team.getPlayers()) {
            if (player != null && player.getJerseyNumber() == Integer.parseInt(playerJerseyNumber)) {
                System.out.println("Jersey number " + playerJerseyNumber + " is already taken by " + player.getName() + ".");
                return;
            }
        }

        System.out.print("Player Position: ");
        String playerPosition = scanner.nextLine();

        System.out.print("Player Id: ");
        String playerId = scanner.nextLine();

        Player newPlayer = new Player(playerName, teamId, playerSurname, Integer.parseInt(playerJerseyNumber), playerPosition, Integer.parseInt(playerId));

        if (team.addPlayer(newPlayer)){ 
            System.out.println("Player " + playerName + " added successfully to team " + team.getTeamName() + ".");
        } else {
            System.out.println("Failed to add player " + playerName + " to team " + team.getTeamName() + ".");
        }
    }

    private static Team teamId(int teamId) {
        return teamId(teamId);
    }

    private  static void takimdanOyuncuSil(Scanner scanner){
        System.out.println("\n--- Remove a Player from a Team ---");

        System.out.print("Enter Team ID: ");
        int teamId = scanner.nextInt();
        scanner.nextLine();

        Team team = teamId(teamId);
        if (team == null){
            System.out.println("Team with ID " + teamId + " not found.");
            return;
        }

        

        System.out.print("Enter player Jersey Number to remove: ");
        int jerseyNumber = scanner.nextInt();
        scanner.nextLine();

        if (team.removePlayer(jerseyNumber)){
            System.out.println("Player with Jersey Number " + jerseyNumber + " removed successfully from team " + team.getTeamName() + ".");
        } else {
            System.out.println("Failed to remove player with Jersey Number " + jerseyNumber + " from team " + team.getTeamName() + ".");
        }

    }

    private static void grubaMacEkle(Scanner scanner, Group group){
        System.out.print("\n--- Add a Game to the Group ---");

        System.out.println("\n--- Add Game to Group ---");
        
        System.out.print("Enter Home Team ID: ");
        int homeTeamId = scanner.nextInt();
        scanner.nextLine(); // Buffer temizle
        
        System.out.print("Enter Away Team ID: ");
        int awayTeamId = scanner.nextInt();
        scanner.nextLine(); // Buffer temizle
        
        Team homeTeam = findTeamById(homeTeamId, group);
        Team awayTeam = findTeamById(awayTeamId, group);
        
        if (homeTeam == null || awayTeam == null) {
            System.out.println("Error: One or both teams not found.");
            return;
        }
        
        if (homeTeamId == awayTeamId) {
            System.out.println("Error: A team cannot play against itself.");
            return;
        }
        
        System.out.print("Enter Home Team Goals: ");
        int homeGoals = scanner.nextInt();
        scanner.nextLine(); // Buffer temizle
        
        System.out.print("Enter Away Team Goals: ");
        int awayGoals = scanner.nextInt();
        scanner.nextLine(); // Buffer temizle
        
        // Yeni maç oluştur
        Game newGame = new Game(homeTeam, awayTeam, homeGoals, awayGoals);
        
        // Gruba maç ekle
        if (group.addGame(newGame)) {
            System.out.println("Game added successfully:");
            System.out.println(homeTeam.getTeamName() + " " + homeGoals + " - " + 
                             awayGoals + " " + awayTeam.getTeamName());
            
            // Maç sonuçlarına göre takımları güncelle (puan, gol farkı vb.)
            homeTeam.updatePoints(homeGoals, awayGoals);
            awayTeam.updatePoints(awayGoals, homeGoals);
            
            System.out.println("Team statistics updated.");
        } else {
            System.out.println("Error: Could not add game to the group.");
        }
    }

    private static void grupSiralamasiniGöster(Scanner scanner , Group group){
        System.out.println("\n--- Display Standings ---");
        
        Team[] teams = group.getTeams();
        
        if (teams == null || teams.length == 0) {
            System.out.println("No teams in the group.");
            return;
        }
        
        // Sıralama yap - Arrays.sort kullanarak
        // Team sınıfının Comparable interface'ini implemente ettiğini varsayıyorum
        java.util.Arrays.sort(teams, java.util.Collections.reverseOrder());
        
        // Tablo başlığı
        System.out.println("=================================================================");
        System.out.printf("%-5s %-15s %-5s %-5s %-5s %-5s %-5s%n", 
                         "Pos", "Team", "P", "W", "D", "L", "Pts");
        System.out.println("=================================================================");
        
        // Takımları listele
        for (int i = 0; i < teams.length; i++) {
            if (teams[i] != null) {
                System.out.printf("%-5d %-15s %-5d %-5d %-5d %-5d %-5d%n",
                                 i + 1,
                                 teams[i].getTeamName(),
                                 teams[i].getPlayers(),
                                 teams[i].getClass(),
                                 teams[i].getClass(),
                                 teams[i].getClass(),
                                 teams[i].getClass());
            }
        }
        System.out.println("=================================================================");
    }
    
    // Yardımcı metod: ID'ye göre takım bul
    private static Team findTeamById(int id, Group group) {
        for (Team team : group.getTeams()) {
            if (team != null && team.getId() == id) {
                return team;
            }
        }
        return null;
    }
    

    
}