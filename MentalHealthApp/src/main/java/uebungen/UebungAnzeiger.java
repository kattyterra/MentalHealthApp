package uebungen;

import java.util.Scanner;

/**
 * Diese Klasse zeigt eine einzelne Übung im Detail auf der Konsole an.
 * <p>
 * Die Anzeige umfasst:
 * <ul>
 *     <li>Name der Übung</li>
 *     <li>Ziel der Übung</li>
 *     <li>Schrittweise Anleitung</li>
 * </ul>
 * Der Benutzer kann die Anzeige durch Drücken der Eingabetaste schließen.
 */
public class UebungAnzeiger {

    /**
     * Zeigt die übergebene {@link Uebung} inklusive Name, Ziel und Anleitung.
     * Wartet anschließend auf eine Eingabetaste, bevor zur vorherigen Ansicht zurückgekehrt wird.
     *
     * @param uebung  die anzuzeigende Übung
     * @param scanner Scanner zur Benutzereingabe (Enter-Taste zum Fortfahren)
     */
    public void zeige(Uebung uebung, Scanner scanner) {
        System.out.println("\n🌿 " + uebung.name());
        System.out.println("🎯 Ziel: " + uebung.ziel());
        System.out.println("\n📖 Anleitung:");
        for (String s : uebung.anleitung()) {
            System.out.println("• " + s);
        }
        System.out.println("\nDrücke Enter, um zurückzukehren.");
        scanner.nextLine();
    }
}
