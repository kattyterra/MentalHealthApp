package Menus;

import java.util.Scanner;

public class AtemUndEnspannung {
    void showMenu(Scanner scanner){
        while (true) {
            System.out.println("\n Wähle bitte aus, was du tun möchtest:");
            System.out.println("1 - Atemübung durchführen");
            System.out.println("2 - Entspannungsübung durchführen");
            System.out.println("3 - Zurück zum Hauptmenü");
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
                    System.out.println("Atemübung durchführen");
                    break;
                case 2:
                    System.out.println("Entspannungsübung durchführen");
                    break;
                case 3:
                    System.out.println("Zurück zum Hauptmenü");
                    return;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }
}
