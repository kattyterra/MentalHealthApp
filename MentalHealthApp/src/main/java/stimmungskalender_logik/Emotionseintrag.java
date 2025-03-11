package stimmungskalender_logik;

/**
 * Repräsentiert einen einzelnen Emotionseintrag mit Emotion, Intensität (1–10) und Ursache.
 * Diese Klasse wird als Java-Record genutzt – einfach, kompakt und unveränderlich.
 */
public record Emotionseintrag(String emotion, int intensitaet, String ursache) {

    /**
     * Formatiert den Emotionseintrag als Textzeile zur Dateiablage.
     * @return Formatierter Eintrag im Stil:
     *         "- Emotion: [Name] | Intensität: [1-10] | Ursache: [Freitext]"
     */
    public String formatForFile() {
        return "- Emotion: " + emotion + " | Intensität: " + intensitaet + " | Ursache: " + ursache;
    }
}
