package src;

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
            case 1:
                yeniTakimOlustur(scanner);
                break;

            case 2:
                takimBilgileriniGoster(scanner);
                break;
            
            case 3:
                takimaOyuncuEkle(scanner);
                break;

            case 4:
                takimdanOyuncuSil(scanner);
                break;
            
            case 5:
                grubaMacEkle(scanner);
                break;
            
            case 6:
                grupSiralamasiniGöster(scanner);
                break;  

            case 7:
                System.out.println("Exiting the program. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        } 
        scanner.close();
    } 


    private static void yeniTakimOlustur(Scanner scanner){
        System.out.println("\n--- Creat a New Team ---");

        System.out.println("Team ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
    }
}