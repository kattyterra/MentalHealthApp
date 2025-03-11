package tagebuch_logik;

/**
 * Repräsentiert einen einzelnen Eintrag im Tagebuch mit Datum, Uhrzeit und Textinhalt.
 * Diese Klasse nutzt ein Java Record – eine kompakte, unveränderliche Datenstruktur.
 */
public record TagebuchEintrag(String datum, String uhrzeit, String text) {

    /**
     * Formatiert den Eintrag als Text für die Dateiablage.
     * @return Formatierter Eintrag im Stil:
     *         "Eingetragen um HH:mm:ss:\n[Text]"
     */
    public String formatForFile() {
        return "Eingetragen um " + uhrzeit + ":\n" + text;
    }
}
