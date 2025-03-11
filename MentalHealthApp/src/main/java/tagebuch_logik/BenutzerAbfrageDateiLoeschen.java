package tagebuch_logik;

import java.util.Scanner;

/**
 * Diese Klasse dient der Benutzerinteraktion zur Auswahl,
 * ob ein kompletter Tagebucheintrag oder nur ein Teil davon gelöscht werden soll.
 */
public class BenutzerAbfrageDateiLoeschen {

    /**
     * Fragt den Benutzer über die Konsole, ob ein kompletter Eintrag oder ein Teil gelöscht werden soll.
     *
     * @param scanner Scanner-Objekt zur Benutzereingabe
     * @return true, wenn der gesamte Eintrag gelöscht werden soll,
     *         false, wenn nur ein Teil gelöscht werden soll
     */
    public boolean eingabe_lesen(Scanner scanner) {
        while (true) {
            System.out.println("Möchtest du den ganzen Eintrag für diesen Tag löschen, oder nur einen bestimmten Teil?");
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
                    return true;  // Ganzer Eintrag löschen
                case 2:
                    return false; // Nur bestimmter Teil löschen
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }
}
