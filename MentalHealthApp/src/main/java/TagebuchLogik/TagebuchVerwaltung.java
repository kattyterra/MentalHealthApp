package TagebuchLogik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TagebuchVerwaltung {
    private final TagebuchRepository repository;
    private final BenutzerEingabe eingabe;
    private final BenutzerAbfrageDateiLoeschen abfrage;

    public TagebuchVerwaltung(TagebuchRepository repository) {
        this.repository = repository;
        this.eingabe = new BenutzerEingabe();
        this.abfrage = new BenutzerAbfrageDateiLoeschen();
    }

    public void eintragSchreiben(Scanner scanner) {
        String datum = LocalDate.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd"));
        String uhrzeit = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String text = eingabe.leseEintrag(scanner);
        TagebuchEintrag eintrag = new TagebuchEintrag(datum, uhrzeit, text);
        repository.speichern(eintrag);
    }

    public void eintragLoeschen(Scanner scanner) {
        List<String> eintraege = repository.getVerfuegbareEintraege();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Tagebucheinträge vorhanden.");
            return;
        }

        int auswahl;
        while(true) {
            System.out.println("\nVerfügbare Tagebucheinträge zum Löschen:");
            for (int i = 0; i < eintraege.size(); i++) {
                System.out.println((i + 1) + ". " + eintraege.get(i));
            }

            System.out.print("\nWähle eine Nummer, um den Eintrag zu löschen: ");
            try {
                auswahl = Integer.parseInt(scanner.nextLine());
                if (auswahl < 1 || auswahl > eintraege.size()) {
                    System.out.println("Ungültige Auswahl.");
                }else{
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
            }
        }

        String gewaehltesDatum = eintraege.get(auswahl - 1);

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

    public void eintragLesen(Scanner scanner) {
        List<String> eintraege = repository.getVerfuegbareEintraege();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Tagebucheinträge vorhanden.");
            return;
        }

        int auswahl;
        while(true) {
            System.out.println("\nVerfügbare Tagebucheinträge:");
            for (int i = 0; i < eintraege.size(); i++) {
                System.out.println((i + 1) + ". " + eintraege.get(i));
            }

            System.out.print("\nWähle eine Nummer, um den Eintrag zu lesen: ");
            try {
                auswahl = Integer.parseInt(scanner.nextLine());
                if (auswahl < 1 || auswahl > eintraege.size()) {
                    System.out.println("Ungültige Auswahl.");
                }else{
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

    public void eintragBearbeiten(Scanner scanner) {
        List<String> eintraege = repository.getVerfuegbareEintraege();
        if (eintraege.isEmpty()) {
            System.out.println("Keine Tagebucheinträge vorhanden.");
            return;
        }

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
                }else{
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
            }
        }

        String gewaehltesDatum = eintraege.get(auswahl - 1);
        System.out.print("\nGib die Uhrzeit des Eintrags ein, den du bearbeiten möchtest (HH:mm:ss): ");
        String uhrzeit = scanner.nextLine();

        System.out.println("Gib den neuen Text für den Eintrag ein:");
        String neuerText = scanner.nextLine();

        boolean erfolgreich = repository.bearbeiten(gewaehltesDatum, uhrzeit, neuerText);
        if (erfolgreich) {
            System.out.println("Der Eintrag wurde erfolgreich aktualisiert!");
        } else {
            System.out.println("Fehler beim Bearbeiten des Eintrags.");
        }
    }
}