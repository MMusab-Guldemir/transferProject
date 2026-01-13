package src;

import java.util.Scanner;

public class Main {
    private static Group group = new Group("A", 4); // 4 takımlık grup oluştur
    
    public static void main(String[] args) {
        // Önceden tanımlanmış takımları oluştur ve gruba ekle
        initializeTeams();
        
        Scanner scanner = new Scanner(System.in);
        String choice;
        
        do {
            System.out.println("\n=== Qatar 2022 World Cup App ===");
            System.out.println("1. Create a new team");
            System.out.println("2. Display a team");
            System.out.println("3. Add a player to a team");
            System.out.println("4. Remove a player with ID from a team");
            System.out.println("5. Add a game to the group");
            System.out.println("6. Display standings in the group");
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1-7): ");
            
            choice = scanner.nextLine();
            
            switch(choice) {
                case "1":
                    yeniTakimOlustur(scanner);
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
                    grupSiralamasiniGoster();
                    break;  
                case "7":
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        } while(!choice.equals("7"));
        
        scanner.close();
    }
    
    private static void initializeTeams() {
        // Türkiye takımı
        Team turkey = new Team(1, "Türkiye");
        turkey.addPlayer(new Player("Mert Günok", 33, "Turkish", 1, "Goalkeeper", 3000000));
        turkey.addPlayer(new Player("Çağlar Söyüncü", 26, "Turkish", 4, "Defender", 25000000));
        turkey.addPlayer(new Player("Hakan Çalhanoğlu", 28, "Turkish", 10, "Midfielder", 35000000));
        turkey.addPlayer(new Player("Cenk Tosun", 31, "Turkish", 9, "Forward", 8000000));
        
        // İngiltere takımı
        Team england = new Team(2, "England");
        england.addPlayer(new Player("Jordan Pickford", 28, "English", 1, "Goalkeeper", 25000000));
        england.addPlayer(new Player("Harry Maguire", 29, "English", 5, "Defender", 30000000));
        england.addPlayer(new Player("Jude Bellingham", 19, "English", 22, "Midfielder", 100000000));
        england.addPlayer(new Player("Harry Kane", 29, "English", 9, "Forward", 90000000));
        
        // Almanya takımı
        Team germany = new Team(3, "Germany");
        germany.addPlayer(new Player("Manuel Neuer", 36, "German", 1, "Goalkeeper", 12000000));
        germany.addPlayer(new Player("Antonio Rüdiger", 29, "German", 2, "Defender", 40000000));
        germany.addPlayer(new Player("İlkay Gündoğan", 32, "German", 21, "Midfielder", 25000000));
        germany.addPlayer(new Player("Thomas Müller", 33, "German", 13, "Forward", 18000000));
        
        // Takımları gruba ekle
        group.addTeam(turkey);
        group.addTeam(england);
        group.addTeam(germany);
        
        System.out.println("Initial teams added to group: Türkiye, England, Germany");
        System.out.println("Group currently has " + group.getNumberOfTeams() + "/4 teams");
    }
    
    private static void yeniTakimOlustur(Scanner scanner) {
        System.out.println("\n--- Create a New Team ---");
        
        // Önce grubun dolu olup olmadığını kontrol et
        if (group.getNumberOfTeams() >= group.getGroupSize()) {
            System.out.println("Error: The group is already full (" + group.getGroupSize() + " teams maximum).");
            return;
        }
        
        System.out.print("Enter Team ID: ");
        int id;
        try {
            id = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid ID. Please enter a number.");
            scanner.nextLine(); // Hatalı girişi temizle
            return;
        }
        
        // Aynı ID'de takım var mı kontrol et
        if (group.teamExists(id)) {
            System.out.println("Error: A team with ID " + id + " already exists in the group.");
            return;
        }
        
        System.out.print("Enter Team Name: ");
        String name = scanner.nextLine();
        
        if (name.trim().isEmpty()) {
            System.out.println("Error: Team name cannot be empty.");
            return;
        }
        
        // Yeni takım oluştur
        Team newTeam = new Team(id, name);
        
        // Gruba ekle
        if (group.addTeam(newTeam)) {
            System.out.println("Team '" + name + "' created and added to the group successfully.");
        } else {
            System.out.println("Error: Could not add team to the group.");
        }
    }
    
    private static void takimBilgileriniGoster(Scanner scanner) {
        System.out.println("\n--- Display Team Information ---");
        
        System.out.print("Enter Team ID to display: ");
        int teamId;
        try {
            teamId = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid ID. Please enter a number.");
            scanner.nextLine(); // Hatalı girişi temizle
            return;
        }
        
        Team team = group.findTeamById(teamId);
        if (team != null) {
            System.out.println("\n=== " + team.getTeamName() + " ===");
            System.out.println("Team ID: " + team.getId());
            System.out.println("Country: " + (team.getCountry().isEmpty() ? "Not specified" : team.getCountry()));
            System.out.println("Players: " + team.getPlayerCount() + "/" + team.getMaxPlayers());
            System.out.println("Total Market Value: $" + team.calculateTotalMarketValue());
            
            System.out.println("\nPlayers List:");
            System.out.println("------------------------------------------------------------------------");
            System.out.printf("%-5s %-25s %-15s %-12s %-10s%n", "No.", "Name", "Nationality", "Position", "Value ($)");
            System.out.println("------------------------------------------------------------------------");
            
            for (Player player : team.getPlayers()) {
                System.out.printf("%-5d %-25s %-15s %-12s %-10d%n",
                    player.getJerseyNumber(),
                    player.getName(),
                    player.getNationality(),
                    player.getPosition(),
                    player.getMarketValue());
            }
            
            System.out.println("\nTeam Statistics:");
            System.out.println("Played: " + team.getGamesPlayed() + 
                             " | Wins: " + team.getWins() + 
                             " | Draws: " + team.getDraws() + 
                             " | Losses: " + team.getLosses());
            System.out.println("Goals For: " + team.getGoalsFor() + 
                             " | Goals Against: " + team.getGoalsAgainst() + 
                             " | Goal Difference: " + team.getGoalDifference());
            System.out.println("Points: " + team.getPoints());
            
        } else {
            System.out.println("Error: Team with ID " + teamId + " not found.");
        }
    }
    
    private static void takimaOyuncuEkle(Scanner scanner) {
        System.out.println("\n--- Add Player to Team ---");
        
        System.out.print("Enter Team ID: ");
        int teamId;
        try {
            teamId = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid ID. Please enter a number.");
            scanner.nextLine(); // Hatalı girişi temizle
            return;
        }
        
        Team team = group.findTeamById(teamId);
        if (team == null) {
            System.out.println("Error: Team with ID " + teamId + " not found.");
            return;
        }
        
        if (team.isTeamFull()) {
            System.out.println("Error: Team '" + team.getTeamName() + "' is already full (" + 
                             team.getMaxPlayers() + " players maximum).");
            return;
        }
        
        System.out.print("Player First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Player Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Jersey Number (1-99): ");
        int jerseyNumber;
        try {
            jerseyNumber = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid jersey number. Please enter a number between 1 and 99.");
            scanner.nextLine();
            return;
        }
        
        if (jerseyNumber < 1 || jerseyNumber > 99) {
            System.out.println("Error: Jersey number must be between 1 and 99.");
            return;
        }
        
        // Aynı forma numarası var mı kontrol et
        if (team.findPlayer(jerseyNumber) != null) {
            System.out.println("Error: A player with jersey number " + jerseyNumber + 
                             " is already in the team.");
            return;
        }
        
        System.out.print("Position (Goalkeeper/Defender/Midfielder/Forward): ");
        String position = scanner.nextLine();
        
        System.out.print("Age: ");
        int age;
        try {
            age = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid age. Please enter a number.");
            scanner.nextLine();
            return;
        }
        
        System.out.print("Nationality: ");
        String nationality = scanner.nextLine();
        
        System.out.print("Market Value ($): ");
        int marketValue;
        try {
            marketValue = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid market value. Please enter a number.");
            scanner.nextLine();
            return;
        }
        
        // Yeni oyuncu oluştur ve ekle
        Player newPlayer = new Player(firstName + " " + lastName, age, nationality, jerseyNumber, position, marketValue);
        
        if (team.addPlayer(newPlayer)) {
            System.out.println("Player '" + firstName + " " + lastName + "' added to team '" + 
                             team.getTeamName() + "' successfully.");
        } else {
            System.out.println("Error: Could not add player to the team.");
        }
    }
    
    private static void takimdanOyuncuSil(Scanner scanner) {
        System.out.println("\n--- Remove Player from Team ---");
        
        System.out.print("Enter Team ID: ");
        int teamId;
        try {
            teamId = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid ID. Please enter a number.");
            scanner.nextLine();
            return;
        }
        
        Team team = group.findTeamById(teamId);
        if (team == null) {
            System.out.println("Error: Team with ID " + teamId + " not found.");
            return;
        }
        
        System.out.print("Enter Player Jersey Number to remove: ");
        int jerseyNumber;
        try {
            jerseyNumber = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid jersey number. Please enter a number.");
            scanner.nextLine();
            return;
        }
        
        Player playerToRemove = team.findPlayer(jerseyNumber);
        if (playerToRemove == null) {
            System.out.println("Error: Player with jersey number " + jerseyNumber + 
                             " not found in team '" + team.getTeamName() + "'.");
            return;
        }
        
        System.out.println("Are you sure you want to remove " + playerToRemove.getName() + 
                         " (No: " + jerseyNumber + ") from " + team.getTeamName() + "? (yes/no)");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equalsIgnoreCase("yes")) {
            if (team.removePlayer(jerseyNumber)) {
                System.out.println("Player removed successfully.");
            } else {
                System.out.println("Error: Could not remove player.");
            }
        } else {
            System.out.println("Player removal cancelled.");
        }
    }
    
    private static void grubaMacEkle(Scanner scanner) {
        System.out.println("\n--- Add Game to Group ---");
        
        System.out.print("Enter Home Team ID: ");
        int homeTeamId;
        try {
            homeTeamId = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid ID. Please enter a number.");
            scanner.nextLine();
            return;
        }
        
        System.out.print("Enter Away Team ID: ");
        int awayTeamId;
        try {
            awayTeamId = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid ID. Please enter a number.");
            scanner.nextLine();
            return;
        }
        
        Team homeTeam = group.findTeamById(homeTeamId);
        Team awayTeam = group.findTeamById(awayTeamId);
        
        if (homeTeam == null || awayTeam == null) {
            System.out.println("Error: One or both teams not found.");
            return;
        }
        
        if (homeTeamId == awayTeamId) {
            System.out.println("Error: A team cannot play against itself.");
            return;
        }
        
        System.out.print("Enter Home Team Goals: ");
        int homeGoals;
        try {
            homeGoals = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid number. Please enter a number.");
            scanner.nextLine();
            return;
        }
        
        if (homeGoals < 0) {
            System.out.println("Error: Goals cannot be negative.");
            return;
        }
        
        System.out.print("Enter Away Team Goals: ");
        int awayGoals;
        try {
            awayGoals = scanner.nextInt();
            scanner.nextLine(); // Buffer temizle
        } catch (Exception e) {
            System.out.println("Invalid number. Please enter a number.");
            scanner.nextLine();
            return;
        }
        
        if (awayGoals < 0) {
            System.out.println("Error: Goals cannot be negative.");
            return;
        }
        
        // Yeni maç oluştur
        Game newGame = new Game(homeTeam, awayTeam, homeGoals, awayGoals);
        
        // Gruba maç ekle
        if (group.addGame(newGame)) {
            System.out.println("\nGame added successfully:");
            System.out.println(homeTeam.getTeamName() + " " + homeGoals + " - " + 
                             awayGoals + " " + awayTeam.getTeamName());
            System.out.println("\nUpdated statistics:");
            System.out.println(homeTeam.getTeamName() + ": " + homeTeam.getPoints() + " points");
            System.out.println(awayTeam.getTeamName() + ": " + awayTeam.getPoints() + " points");
        } else {
            System.out.println("Error: Could not add game to the group.");
        }
    }
    
    private static void grupSiralamasiniGoster() {
        group.displayStandings();
    }
}