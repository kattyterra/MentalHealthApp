package tagebuch_logik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Diese Klasse enthält die zentrale Steuerung für alle Funktionen des Tagebuchs:
 * Einträge schreiben, lesen, bearbeiten und löschen.
 * Sie koordiniert die Benutzereingabe, Repository-Operationen und Dialoge.
 */
public class TagebuchVerwaltung {
    private final TagebuchRepository repository;
    private final BenutzerEingabe eingabe;
    private final BenutzerAbfrageDateiLoeschen abfrage;

    /**
     * Konstruktor – initialisiert Repository und Benutzerinteraktions-Komponenten.
     * @param repository konkreter Speichermechanismus für Tagebucheinträge
     */
    public TagebuchVerwaltung(TagebuchRepository repository) {
        this.repository = repository;
        this.eingabe = new BenutzerEingabe();
        this.abfrage = new BenutzerAbfrageDateiLoeschen();
    }

    /**
     * Erstellt einen neuen Tagebucheintrag mit aktuellem Datum und Uhrzeit.
     * Der Text wird vom Benutzer eingegeben.
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void eintragSchreiben(Scanner scanner) {
        // Aktuelles Datum und Uhrzeit ermitteln
        String datum = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uhrzeit = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Text vom Benutzer erfassen
        String text = eingabe.leseEintrag(scanner);

        // Eintrag erzeugen und speichern
        TagebuchEintrag eintrag = new TagebuchEintrag(datum, uhrzeit, text);
        repository.speichern(eintrag);
    }

    /**
     * Ermöglicht dem Benutzer, einen Eintrag zu löschen – entweder vollständig oder nur einzelne Uhrzeit-Einträge.
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void eintragLoeschen(Scanner scanner) {
        List<String> eintraege = repository.getVerfuegbareEintraege();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Tagebucheinträge vorhanden.");
            return;
        }

        // Auswahl eines Datums
        int auswahl;
        while (true) {
            System.out.println("\nVerfügbare Tagebucheinträge zum Löschen:");
            for (int i = 0; i < eintraege.size(); i++) {
                System.out.println((i + 1) + ". " + eintraege.get(i));
            }

            System.out.print("\nWähle eine Nummer, um den Eintrag zu löschen: ");
            try {
                auswahl = Integer.parseInt(scanner.nextLine());
                if (auswahl < 1 || auswahl > eintraege.size()) {
                    System.out.println("Ungültige Auswahl.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
            }
        }

        String gewaehltesDatum = eintraege.get(auswahl - 1);

        // Benutzer fragen: ganze Datei löschen oder Teil
        boolean ganzeDateiLoeschen = abfrage.eingabe_lesen(scanner);
        if (ganzeDateiLoeschen) {
            repository.loeschen(gewaehltesDatum);
            System.out.println("Der gesamte Eintrag für " + gewaehltesDatum + " wurde gelöscht.");
        } else {
            System.out.print("\nGib die Uhrzeit des Eintrags ein, den du löschen möchtest (HH:mm:ss): ");
            String uhrzeit = scanner.nextLine();
            repository.loeschenEintrag(gewaehltesDatum, uhrzeit);
        }
    }

    /**
     * Zeigt dem Benutzer den Inhalt eines bestimmten Tagebucheintrags.
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void eintragLesen(Scanner scanner) {
        List<String> eintraege = repository.getVerfuegbareEintraege();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Tagebucheinträge vorhanden.");
            return;
        }

        // Datumsauswahl
        int auswahl;
        while (true) {
            System.out.println("\nVerfügbare Tagebucheinträge:");
            for (int i = 0; i < eintraege.size(); i++) {
                System.out.println((i + 1) + ". " + eintraege.get(i));
            }

            System.out.print("\nWähle eine Nummer, um den Eintrag zu lesen: ");
            try {
                auswahl = Integer.parseInt(scanner.nextLine());
                if (auswahl < 1 || auswahl > eintraege.size()) {
                    System.out.println("Ungültige Auswahl.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine Zahl eingeben.");
            }
        }

        String gewaehltesDatum = eintraege.get(auswahl - 1);
        String inhalt = repository.lesen(gewaehltesDatum);
        System.out.println("\nEintrag für " + gewaehltesDatum + ":\n" + inhalt);
    }

    /**
     * Ermöglicht das Bearbeiten eines Eintrags anhand von Datum und Uhrzeit.
     * Der neue Text ersetzt den alten Inhalt des gewählten Eintrags.
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void eintragBearbeiten(Scanner scanner) {
        List<String> eintraege = repository.getVerfuegbareEintraege();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Tagebucheinträge vorhanden.");
            return;
        }

        // Auswahl eines Datums
        int auswahl;
        while (true) {
            System.out.println("\nVerfügbare Tagebucheinträge zum Bearbeiten:");
            for (int i = 0; i < eintraege.size(); i++) {
                System.out.println((i + 1) + ". " + eintraege.get(i));
            }

            System.out.print("\nWähle eine Nummer, um einen Eintrag zu bearbeiten: ");
            try {
                auswahl = Integer.parseInt(scanner.nextLine());
                if (auswahl < 1 || auswahl > eintraege.size()) {
                    System.out.println("Ungültige Auswahl.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
            }
        }

        String gewaehltesDatum = eintraege.get(auswahl - 1);

        // Uhrzeit des zu bearbeitenden Eintrags
        System.out.print("\nGib die Uhrzeit des Eintrags ein, den du bearbeiten möchtest (HH:mm:ss): ");
        String uhrzeit = scanner.nextLine();

        // Neuer Text für den Eintrag
        System.out.println("Gib den neuen Text für den Eintrag ein:");
        String neuerText = scanner.nextLine();

        // Bearbeitung durchführen
        boolean erfolgreich = repository.bearbeiten(gewaehltesDatum, uhrzeit, neuerText);
        if (erfolgreich) {
            System.out.println("Der Eintrag wurde erfolgreich aktualisiert!");
        } else {
            System.out.println("Fehler beim Bearbeiten des Eintrags.");
        }
    }
}
