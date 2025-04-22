package routinen_logik;

/**
 * Repräsentiert eine einzelne Routine in der MentalHealthApp.
 * Eine Routine enthält:
 *     -eine Routinenart (z.B. MORGEN, ABEND)
 *     -eine Beschreibung der Aktivität
 *     -einen Erledigt-Status (true/false)
 */
public class Routine {

    /** Art der Routine (z.B. MORGEN, MITTAG, ABEND) */
    private RoutinenArt art;

    /** Beschreibung der Routine (z.B. „5 Minuten meditieren“) */
    private String beschreibung;

    /** Status: true, wenn Routine als erledigt markiert wurde */
    private boolean erledigt;

    /**
     * Konstruktor – erstellt eine neue Routine mit Art und Beschreibung.
     * Der Erledigt-Status ist initial immer false.
     * @param art Routinenart
     * @param beschreibung Beschreibung der Routine
     */
    public Routine(RoutinenArt art, String beschreibung) {
        this.art = art;
        this.beschreibung = beschreibung;
        this.erledigt = false;
    }

    /** @return die Art der Routine */
    public RoutinenArt getArt() { return art; }

    /** @return die Beschreibung der Routine */
    public String getBeschreibung() { return beschreibung; }

    /** @return true, wenn die Routine erledigt wurde */
    public boolean isErledigt() { return erledigt; }

    /** Setzt die Art der Routine. */
    public void setArt(RoutinenArt art) { this.art = art; }

    /** Setzt die Beschreibung der Routine. */
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }

    /** Setzt den Erledigt-Status der Routine. */
    public void setErledigt(boolean erledigt) { this.erledigt = erledigt; }

    /**
     * Gibt eine lesbare Darstellung der Routine zurück (inkl. Statussymbol).
     * Beispiel: [✓] [MORGEN] – 5 Minuten meditieren
     * @return formatierter String
     */
    @Override
    public String toString() {
        String status = erledigt ? "[✓]" : "[ ]";
        return status + " [" + art + "] – " + beschreibung;
    }
}
