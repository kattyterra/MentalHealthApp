package gedanken_reflexion_logik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Diese Klasse steuert die zentrale Benutzerinteraktion für das Modul „Gedankenkarussell stoppen“.
 * Sie bietet Funktionen zum Erstellen neuer Gedankenreflexionseinträge sowie zum Anzeigen aller bisherigen Einträge.
 */
public class GedankenReflexionVerwaltung {

    /** Repository zur Speicherung und Verwaltung der Einträge */
    private final GedankenReflexionRepository repository;

    /** Konstruktor */
    public GedankenReflexionVerwaltung() {
        this.repository = new GedankenReflexionSpeicher();
    }

    /** Injektion-Konstruktor */
    public GedankenReflexionVerwaltung(GedankenReflexionRepository repository) {
        this.repository = repository;
    }


    /**
     * Führt eine geführte Selbstreflexion durch. Der Benutzer beantwortet fünf strukturierte Fragen.
     * Die Antworten sowie Datum, Uhrzeit und Belastungswert werden in einem Eintrag gespeichert.
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void neuerEintrag(Scanner scanner) {
        String datum = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String uhrzeit = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        List<String> antworten = new ArrayList<>();

        // Geführte Reflexionsfragen
        System.out.println("Was beschäftigt dich gerade?");
        antworten.add(scanner.nextLine());

        System.out.print("Wie stark belastet dich das (1–10)? ");
        String belastung = scanner.nextLine();

        System.out.println("Was spricht objektiv dafür?");
        antworten.add(scanner.nextLine());

        System.out.println("Was spricht dagegen?");
        antworten.add(scanner.nextLine());

        System.out.println("Welche neue Sichtweise könntest du einnehmen?");
        antworten.add(scanner.nextLine());

        System.out.println("Was könntest du dir jetzt Gutes tun?");
        antworten.add(scanner.nextLine());

        // Eintrag erstellen und speichern
        GedankenReflexionEintrag eintrag = new GedankenReflexionEintrag(datum, uhrzeit, belastung, antworten);
        repository.speichern(eintrag);
        System.out.println("✅ Reflexion gespeichert.");
    }

    /**
     * Gibt alle gespeicherten Gedankenreflexionseinträge in der Konsole aus.
     * Falls keine Einträge vorhanden sind, wird eine entsprechende Info angezeigt.
     */
    public void alleEintraegeAnzeigen() {
        List<String> eintraege = repository.lesenAlle();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Einträge vorhanden.");
            return;
        }

        eintraege.forEach(System.out::println);
    }
}
