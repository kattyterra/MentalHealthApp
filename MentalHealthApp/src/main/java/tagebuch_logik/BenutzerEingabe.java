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
}
