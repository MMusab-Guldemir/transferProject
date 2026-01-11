package src;

public class Main {
    public static void main(String[] args) {
        // // Türk takımı oluştur
        // Team turkishTeam = new Team("Turkish National Team", "Türkiye", 23);
        
        // // Oyuncular oluştur
        // Player player1 = new Player("Arda Güler", 19, "Türkiye", 10, "Midfielder", 20000000);
        // Player player2 = new Player("Hakan Çalhanoğlu", 30, "Türkiye", 20, "Midfielder", 35000000);
        // Player player3 = new Player("Mert Günok", 35, "Türkiye", 1, "Goalkeeper", 5000000);
        // Player player4 = new Player("Messi", 36, "Argentina", 10, "Forward", 50000000); // Yabancı
        
        // System.out.println("=== Adding Players ===");
        // turkishTeam.addPlayer(player1); // Başarılı
        // turkishTeam.addPlayer(player2); // Başarılı
        // turkishTeam.addPlayer(player3); // Başarılı
        // turkishTeam.addPlayer(player1); // Başarısız (zaten var)
        // turkishTeam.addPlayer(player4); // Başarısız (milliyet uyumsuz)
        
        // // Aynı forma numarası denemesi
        // Player player5 = new Player("Cenk Tosun", 32, "Türkiye", 10, "Forward", 8000000);
        // turkishTeam.addPlayer(player5); // Başarısız (numara zaten var)
        
        // System.out.println("\n=== Team Information ===");
        // turkishTeam.showTeamInfo();
        
        // System.out.println("\n=== Finding Players ===");
        // Player found = turkishTeam.findPlayer("Arda Güler");
        // if (found != null) {
        //     System.out.println("Found: " + found);
        // }
        
        // System.out.println("\n=== Removing Player ===");
        // turkishTeam.removePlayer(player2);
        
        // System.out.println("\n=== Updated Team Information ===");
        // turkishTeam.showTeamInfo();
        
        // System.out.println("\n=== Used Jersey Numbers ===");
        // System.out.println(turkishTeam.getUsedJerseyNumbers());


        Team germany = new Team(6, "Germany");
        Team turkiye = new Team(38, "Türkiye");
        
        Game g1 = new Game(turkiye, germany, 2, 1);
        
        System.out.println("Match Result: " + g1.toString());
        System.out.println();
        
        System.out.println("Points calculation:");
        g1.getTeamPoints(turkiye);
        g1.getTeamPoints(germany);

    }
}