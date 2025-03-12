package routinen_logik;

/**
 * Enum zur Definition der Routinenarten in der MentalHealthApp.
 * <p>
 * Jede Routine kann einer Tageszeit zugeordnet werden:
 * <ul>
 *     <li>MORGEN</li>
 *     <li>MITTAG</li>
 *     <li>ABEND</li>
 *     <li>NACHT</li>
 * </ul>
 * Zusätzlich enthält jede Routinenart einen benutzerfreundlichen Anzeigetext.
 */
public enum RoutinenArt {
    MORGEN("Morgen"),
    MITTAG("Mittag"),
    ABEND("Abend"),
    NACHT("Nacht");

    /** Benutzerfreundlicher Anzeigetext für die Ausgabe im Menü */
    private final String anzeigeName;

    /**
     * Konstruktor des Enums – ordnet jeder Routinenart einen Anzeigetext zu.
     *
     * @param anzeigeName String zur Anzeige der Routinenart
     */
    RoutinenArt(String anzeigeName) {
        this.anzeigeName = anzeigeName;
    }

    /**
     * Gibt den benutzerfreundlichen Anzeigetext der Routinenart zurück.
     *
     * @return Anzeigetext der Routinenart (z. B. "Morgen")
     */
    public String getAnzeigeName() {
        return anzeigeName;
    }

    /**
     * Wandelt einen übergebenen Text in eine Routinenart um.
     * Der Text wird dabei mit dem Anzeigetext verglichen.
     *
     * @param text der Text, z. B. aus einer Datei oder Benutzereingabe
     * @return die passende {@link RoutinenArt}
     * @throws IllegalArgumentException bei ungültigem Text
     */
    public static RoutinenArt fromText(String text) {
        for (RoutinenArt art : values()) {
            if (art.anzeigeName.equalsIgnoreCase(text.trim())) {
                return art;
            }
        }
        throw new IllegalArgumentException("Ungültige Routinenart: " + text);
    }

    /**
     * Wandelt eine numerische Benutzereingabe in eine Routinenart um.
     * Wird z. B. im Auswahlmenü verwendet.
     *
     * @param eingabe Ganzzahl von 1 bis 4
     * @return entsprechende {@link RoutinenArt}
     * @throws IllegalArgumentException bei ungültiger Eingabe
     */
    public static RoutinenArt fromInt(int eingabe) {
        return switch (eingabe) {
            case 1 -> MORGEN;
            case 2 -> MITTAG;
            case 3 -> ABEND;
            case 4 -> NACHT;
            default -> throw new IllegalArgumentException("Ungültige Routinenart-Auswahl");
        };
    }
}
