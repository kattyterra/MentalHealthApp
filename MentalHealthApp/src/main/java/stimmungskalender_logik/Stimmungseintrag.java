package stimmungskalender_logik;

/**
 * Repräsentiert einen einzelnen Stimmungseintrag mit Datum, Uhrzeit und Stimmungswert.
 * Diese Klasse wird als Java-Record umgesetzt – kompakt und unveränderlich.
 */
public record Stimmungseintrag(String datum, String uhrzeit, int stimmung) {

    /**
     * Formatiert den Stimmungseintrag als Textzeile für die Dateiablage.
     * @return Formatierter Eintrag: "yyyy-MM-dd HH:mm:ss - Stimmung: [Wert]"
     */
    public String formatForFile() {
        return datum + " " + uhrzeit + " - Stimmung: " + stimmung;
    }
}
