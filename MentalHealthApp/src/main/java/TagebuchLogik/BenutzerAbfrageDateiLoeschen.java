package TagebuchLogik;

import java.util.Scanner;

public class BenutzerAbfrageDateiLoeschen {
    public boolean eingabe_lesen(Scanner scanner) {
        while (true) {
            System.out.println("Möchtest du den ganzen EIntrag für diesen Tag löschen, oder nur einen bestimmten Teil?:");
            System.out.println("1 - Ganzen Eintrag");
            System.out.println("2 - Bestimmten Teil");
            System.out.print("Deine Wahl: ");

            int benutzerAuswahl;
            try {
                benutzerAuswahl = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                continue;
            }

            switch (benutzerAuswahl) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }
}
