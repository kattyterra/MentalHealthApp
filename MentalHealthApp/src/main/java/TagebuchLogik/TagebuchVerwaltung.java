package TagebuchLogik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        String datum = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uhrzeit = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String text = eingabe.leseEintrag(scanner);
        TagebuchEintrag eintrag = new TagebuchEintrag(datum, uhrzeit, text);
        repository.speichern(eintrag);
    }

    public void eintragLoeschen(Scanner scanner) {
        String datum = eingabe.leseDatum(scanner);
        boolean ganzeDateiLöschen = abfrage.eingabe_lesen(scanner);

        if (ganzeDateiLöschen) {
            repository.loeschen(datum);
        } else {
            String uhrzeit = eingabe.leseUhrzeit(scanner);
            repository.loeschenEintrag(datum, uhrzeit);
        }
    }
}