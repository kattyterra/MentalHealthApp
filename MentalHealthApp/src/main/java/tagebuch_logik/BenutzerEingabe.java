package tagebuch_logik;

import java.util.Scanner;

/**
 * Diese Klasse dient der Benutzerinteraktion für Eingaben im Tagebuch.
 * Sie enthält Methoden zur Abfrage von Eintragtext, Datum und Uhrzeit.
 */
public class BenutzerEingabe {

    /**
     * Liest einen Freitext-Eintrag vom Benutzer ein.
     *
     * @param scanner Scanner-Objekt für die Konsoleneingabe
     * @return der eingegebene Tagebucheintrag
     */
    public String leseEintrag(Scanner scanner) {
        System.out.println("Gib den Text für deinen Eintrag ein:");
        return scanner.nextLine();
    }

    /**
     * Liest ein Datum im Format yyyy-MM-dd vom Benutzer ein.
     *
     * @param scanner Scanner-Objekt für die Konsoleneingabe
     * @return das eingegebene Datum als String
     */
    public String leseDatum(Scanner scanner) {
        System.out.println("Gib das Datum im Format yyyy-MM-dd ein:");
        return scanner.nextLine();
    }

    /**
     * Liest die Uhrzeit im Format HH:mm:ss ein. Falls das Format nicht passt,
     * wird der Benutzer wiederholt zur Eingabe aufgefordert.
     *
     * @param scanner Scanner-Objekt für die Konsoleneingabe
     * @return die gültige Uhrzeit als String
     */
    public String leseUhrzeit(Scanner scanner) {
        while (true) {
            System.out.println("Gib die Uhrzeit im Format HH:mm:ss ein:");
            String eingabe = scanner.nextLine();
            if (eingabe.matches("\\d{2}:\\d{2}:\\d{2}")) {
                return eingabe;
            } else {
                System.out.println("Ungültiges Format. Bitte erneut versuchen.");
            }
        }
    }
}
