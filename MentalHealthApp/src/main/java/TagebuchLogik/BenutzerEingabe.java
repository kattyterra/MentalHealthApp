package TagebuchLogik;

import java.util.Scanner;

public class BenutzerEingabe {

    public String leseEintrag(Scanner scanner) {
        System.out.println("Gib den Text für deinen Eintrag ein:");
        return scanner.nextLine();
    }

    public String leseDatum(Scanner scanner) {
        System.out.println("Gib das Datum im Format yyyy-MM-dd ein:");
        return scanner.nextLine();
    }

    public String leseUhrzeit(Scanner scanner) {
        while (true) {
            System.out.println("Gib die Uhrzeit im Format HH:mm:ss ein:");
            String eingabe = scanner.nextLine();
            if (eingabe.matches("\\d{2}:\\d{2}:\\d{2}")) {
                return eingabe;
            } else {
                System.out.println("Ungültiges Format. Bitte erneut versuchen.");
            }
        }
    }

}
