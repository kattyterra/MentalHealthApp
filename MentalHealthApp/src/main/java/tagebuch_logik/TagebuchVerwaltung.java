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

    /** Injektion-Konstruktor */
    public TagebuchVerwaltung(TagebuchRepository repository, BenutzerEingabe eingabe, BenutzerAbfrageDateiLoeschen abfrage) {
        this.repository = repository;
        this.eingabe = eingabe;
        this.abfrage = abfrage;
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
        List<String> eintraege = pruefeUndHoleEintraege();
        if (eintraege.isEmpty()) return;

        String datum = waehleDatumAusListe(scanner, eintraege, "löschen");

        boolean ganzeDateiLoeschen = abfrage.eingabe_lesen(scanner);
        if (ganzeDateiLoeschen) {
            repository.loeschen(datum);
            System.out.println("Der gesamte Eintrag für " + datum + " wurde gelöscht.");
        } else {
            System.out.print("\nGib die Uhrzeit des Eintrags ein, den du löschen möchtest (HH:mm:ss): ");
            String uhrzeit = scanner.nextLine();
            repository.loeschenEintrag(datum, uhrzeit);
        }
    }

    /**
     * Zeigt dem Benutzer den Inhalt eines bestimmten Tagebucheintrags.
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void eintragLesen(Scanner scanner) {
        List<String> eintraege = pruefeUndHoleEintraege();
        if (eintraege.isEmpty()) return;

        String datum = waehleDatumAusListe(scanner, eintraege, "lesen");
        String inhalt = repository.lesen(datum);
        System.out.println("\nEintrag für " + datum + ":\n" + inhalt);
    }

    /**
     * Ermöglicht das Bearbeiten eines Eintrags anhand von Datum und Uhrzeit.
     * Der neue Text ersetzt den alten Inhalt des gewählten Eintrags.
     * @param scanner Scanner-Objekt für Benutzereingaben
     */
    public void eintragBearbeiten(Scanner scanner) {
        List<String> eintraege = pruefeUndHoleEintraege();
        if (eintraege.isEmpty()) return;

        String datum = waehleDatumAusListe(scanner, eintraege, "bearbeiten");
        String uhrzeit = leseUhrzeit(scanner);
        String neuerText = leseBearbeitungstext(scanner);
        fuehreBearbeitungDurch(datum, uhrzeit, neuerText);
    }

    private String waehleDatumAusListe(Scanner scanner, List<String> eintraege, String aktion) {
        int auswahl;
        while (true) {
            System.out.println("\nVerfügbare Tagebucheinträge zum " + aktion + ":");
            for (int i = 0; i < eintraege.size(); i++) {
                System.out.println((i + 1) + ". " + eintraege.get(i));
            }

            System.out.print("\nWähle eine Nummer, um den Eintrag zu " + aktion + ": ");
            try {
                auswahl = Integer.parseInt(scanner.nextLine());
                if (auswahl >= 1 && auswahl <= eintraege.size()) {
                    return eintraege.get(auswahl - 1);
                }
                System.out.println("Ungültige Auswahl.");
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
            }
        }
    }

    private List<String> pruefeUndHoleEintraege() {
        List<String> eintraege = repository.getVerfuegbareEintraege();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Tagebucheinträge vorhanden.");
            return List.of();
        }
        return eintraege;
    }

    private String leseUhrzeit(Scanner scanner) {
        System.out.print("\nGib die Uhrzeit des Eintrags ein, den du bearbeiten möchtest (HH:mm:ss): ");
        return scanner.nextLine();
    }

    private String leseBearbeitungstext(Scanner scanner) {
        System.out.println("Gib den neuen Text für den Eintrag ein:");
        return scanner.nextLine();
    }

    private void fuehreBearbeitungDurch(String datum, String uhrzeit, String text) {
        boolean erfolgreich = repository.bearbeiten(datum, uhrzeit, text);
        if (erfolgreich) {
            System.out.println("Der Eintrag wurde erfolgreich aktualisiert!");
        } else {
            System.out.println("Fehler beim Bearbeiten des Eintrags.");
        }
    }
}
