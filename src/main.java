package src;

import java.net.InterfaceAddress;
import java.nio.file.WatchService;
import java.util.Scanner;
import java.util.Locale.Category;

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
                grubaMacEkle(scanner);
                break;
            
            case "6":
                grupSiralamasiniGöster(scanner);
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
            System.out.println("Cannot add team. Group is already at maximum capacity.");
            return;
        }

        System.out.println("Team ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        


        System.out.println("Team Name: ");  
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

        System.out.println("Enter Team ID: ");
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

        System.out.println("Enter Team ID: ");
        int teamId = scanner.nextInt();
        scanner.nextLine();

        Team team = teamId(teamId);
        if (team == null){
            System.out.println("Team with ID " + teamId + " not found.");
            return;
        }

        System.out.println("Player Name: ");
        String playerName = scanner.nextLine();

        System.out.println("Player Surname: ");
        String playerSurname = scanner.nextLine();

        System.out.println("Jersey Number: ");
        String playerJerseyNumber = scanner.nextLine();

        for (Player player : team.getPlayers()) {
            if (player != null && player.getJerseyNumber() == Integer.parseInt(playerJerseyNumber)) {
                System.out.println("Jersey number " + playerJerseyNumber + " is already taken by " + p.getName() + ".");
                return;
            }
        }

        System.out.println("Player Position: ");
        String playerPosition = scanner.nextLine();

        System.out.println("Player Id: ");
        String playerId = scanner.nextLine();

        Player newPlayer = new Player(playerName, teamId, playerSurname, Integer.parseInt(playerJerseyNumber), playerPosition, Integer.parseInt(playerId));

        if (team.addPlayer(newPlayer)){ 
            System.out.println("Player " + playerName + " added successfully to team " + team.getTeamName() + ".");
        } else {
            System.out.println("Failed to add player " + playerName + " to team " + team.getTeamName() + ".");
        }
    }

    private static Team teamId(int teamId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'teamId'");
    }


    private  static void takimdanOyuncuSil(Scanner scanner){
        System.out.println("\n--- Remove a Player from a Team ---");


    }

    private static void grubaMacEkle(Scanner scanner){
        System.out.println("\n--- Add a Game to the Group ---");
    }

    private static void grupSiralamasiniGöster(Scanner scanner){
        System.out.println("\n--- Display Standings ---");
    }

    
}