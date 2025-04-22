package zielverwaltung_logik;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Der {@code ZielEingabeHelper} unterstützt die Benutzerinteraktion zur Eingabe neuer Ziele.
 * Er kapselt alle Eingabeprozesse für die verschiedenen Eigenschaften eines {@link Ziel}-Objekts,
 * einschließlich Kategorie, Beschreibung, Priorität, Wiederholung, Fälligkeitsdatum und Motivationsnotiz.
 * Die Eingabe erfolgt über ein {@link Scanner}-Objekt, typischerweise in einer Konsolenanwendung.
 */
public class ZielEingabeHelper {
    /**
     * Fragt alle erforderlichen Informationen vom Benutzer ab, um ein neues {@link Ziel} zu erstellen.
     * Die Eingabe erfolgt schrittweise über die Konsole:
     *   -Kategorieauswahl aus vordefinierten Werten
     *   -Beschreibung des Ziels
     *   -Priorität (1–3)
     *   -Wiederholungstyp (enum)
     *   -Optionales Fälligkeitsdatum (Standard: +1 Woche)
     *   -Optionaler Motivationshinweis
     * @param s Ein {@link Scanner}-Objekt zur Eingabe
     * @return Ein vollständig befülltes {@link Ziel}-Objekt
     * @throws IllegalArgumentException wenn ungültige Enum-Werte eingegeben werden
     */
    public Ziel erstelleZielVomBenutzer(Scanner s) {
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

    /**
     * Fragt eine Ganzzahl vom Benutzer ab und wandelt sie in einen nullbasierten Index um.
     * Bei ungültiger Eingabe (z.B. kein Integer) wird {@code -1} zurückgegeben.
     * @param s Ein {@link Scanner}-Objekt zur Eingabe
     * @return Der eingegebene Index minus 1, oder -1 bei Eingabefehlern
     */
    public int indexAbfragen(Scanner s) {
        System.out.print("Index: ");
        try {
            return Integer.parseInt(s.nextLine()) - 1;
        } catch (Exception e) {
            return -1;
        }
    }
}
