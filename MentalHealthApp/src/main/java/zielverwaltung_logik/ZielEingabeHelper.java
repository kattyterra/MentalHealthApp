package zielverwaltung_logik;

import java.time.LocalDate;
import java.util.Scanner;

public class ZielEingabeHelper {
    public static Ziel erstelleZielVomBenutzer(Scanner s) {
        System.out.println("\nKategorie auswählen:");
        ZielKategorie[] kategorien = ZielKategorie.values();
        for (int i = 0; i < kategorien.length; i++) {
            System.out.println((i + 1) + " - " + kategorien[i]);
        }
        System.out.print("\nAuswahl: ");

        int katIndex = Integer.parseInt(s.nextLine()) - 1;
        ZielKategorie kategorie = kategorien[Math.max(0, Math.min(katIndex, kategorien.length - 1))];

        System.out.print("\nBeschreibung: ");
        String beschreibung = s.nextLine();

        System.out.print("\nPriorität (1-3): ");
        int prio = Integer.parseInt(s.nextLine());

        System.out.print("\nWiederholung (KEINE/TAEGLICH/WOECHENTLICH/MONATLICH): ");
        Wiederholungstyp wiederholungstyp = Wiederholungstyp.valueOf(s.nextLine().toUpperCase());

        System.out.print("\nFälligkeitsdatum (yyyy-MM-dd) [optional, Enter für Standard]: ");
        String eingabeDatum = s.nextLine();
        LocalDate faelligkeit = eingabeDatum.isBlank()
                ? LocalDate.now().plusWeeks(1)
                : LocalDate.parse(eingabeDatum);

        System.out.print("\nMotivationsnotiz [optional]: ");
        String notiz = s.nextLine();

        Ziel ziel = new Ziel(kategorie, beschreibung, prio, faelligkeit, wiederholungstyp);
        ziel.setMotivationsnotiz(notiz);
        return ziel;
    }

    public static int indexAbfragen(Scanner s) {
        System.out.print("Index: ");
        try { return Integer.parseInt(s.nextLine()) - 1; } catch (Exception e) { return -1; }
    }
}
