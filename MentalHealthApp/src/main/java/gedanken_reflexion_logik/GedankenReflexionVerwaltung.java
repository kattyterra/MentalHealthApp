package gedanken_reflexion_logik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Diese Klasse steuert die zentrale Benutzerinteraktion für das Modul „Gedankenkarussell stoppen“.
 * Sie bietet Funktionen zum Erstellen neuer Gedankenreflexionseinträge sowie zum Anzeigen aller bisherigen Einträge.
 */
public class GedankenReflexionVerwaltung {

    /** Repository zur Speicherung und Verwaltung der Einträge */
    private final GedankenReflexionRepository repository;

    /** Standard-Konstruktor*/
    public GedankenReflexionVerwaltung() {
        this.repository = new GedankenReflexionSpeicher();
    }

    /** Injektion-Konstruktor*/
    public GedankenReflexionVerwaltung(GedankenReflexionRepository repository) {
        this.repository = repository;
    }

    /**
     * Führt eine geführte Selbstreflexion durch.
     * Der Benutzer beantwortet mehrere strukturierte Fragen.
     * Zusätzlich wird der Belastungswert abgefragt.
     * Antworten, Datum und Uhrzeit werden anschließend gespeichert.
     *
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void neuerEintrag(Scanner scanner) {
        String datum = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String uhrzeit = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String belastung = frageBelastung(scanner);
        List<String> antworten = fuehreReflexionsDialog(scanner);

        // Eintrag erstellen und im Repository speichern
        GedankenReflexionEintrag eintrag = new GedankenReflexionEintrag(datum, uhrzeit, belastung, antworten);
        repository.speichern(eintrag);
        System.out.println("✅ Reflexion gespeichert.");
    }

    /**
     * Fragt den Belastungswert des Benutzers zwischen 1 und 10 ab.
     * @param scanner Scanner für die Benutzereingabe
     * @return Eingabe als String (ohne Validierung)
     */
    private String frageBelastung(Scanner scanner) {
        System.out.print("Wie stark belastet dich das (1–10)? ");
        return scanner.nextLine();
    }

    /**
     * Führt den Benutzer durch eine Liste von Reflexionsfragen.
     * Die Antworten werden in einer Liste gesammelt und zurückgegeben.
     *
     * @param scanner Scanner zur Benutzereingabe
     * @return Liste der Antworten des Benutzers
     */
    private List<String> fuehreReflexionsDialog(Scanner scanner) {
        List<String> fragen = List.of(
                "Was beschäftigt dich gerade?",
                "Was spricht objektiv dafür?",
                "Was spricht dagegen?",
                "Welche neue Sichtweise könntest du einnehmen?",
                "Was könntest du dir jetzt Gutes tun?"
        );

        List<String> antworten = new ArrayList<>();
        for (String frage : fragen) {
            System.out.println(frage);
            antworten.add(scanner.nextLine());
        }
        return antworten;
    }

    /**
     * Gibt alle gespeicherten Gedankenreflexionseinträge in der Konsole aus.
     * Falls keine Einträge vorhanden sind, wird eine entsprechende Info angezeigt.
     */
    public void alleEintraegeAnzeigen() {
        List<String> eintraege = repository.lesenAlle();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Einträge vorhanden.");
        } else {
            eintraege.forEach(System.out::println);
        }
    }
}
