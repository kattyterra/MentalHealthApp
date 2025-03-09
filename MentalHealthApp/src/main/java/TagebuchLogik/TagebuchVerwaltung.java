package TagebuchLogik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TagebuchVerwaltung {
    private final Dateioperationen operationen = new Dateioperationen();
    private final BenutzerEingabe eingabe = new BenutzerEingabe();


    public void eintragSchreiben(Scanner scanner) {
        String datum = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String text = "Eingetragen um " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + ":\n" + eingabe.leseEintrag(scanner);
        operationen.speichern(datum, text);
    }

    public void eintragLoeschen(Scanner scanner) {
        String datum = eingabe.leseDatum(scanner);
        operationen.loeschen(datum);
    }

}
