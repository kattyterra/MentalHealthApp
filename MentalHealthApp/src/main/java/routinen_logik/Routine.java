package routinen_logik;

/**
 * Repräsentiert eine einzelne Routine (z. B. Morgenroutine, Abendroutine etc.).
 * Eine Routine besteht aus einer Art (z. B. "Morgen"), einer Beschreibung (z. B. "Zähne putzen")
 * und einem Erledigt-Status (true/false).
 */
public class Routine {
    private String routinenArt;
    private String beschreibung;
    private boolean erledigt;

    /**
     * Konstruktor zur Initialisierung einer neuen Routine.
     *
     * @param routinenArt  Kategorie der Routine (z. B. Morgen, Abend)
     * @param beschreibung Beschreibung der konkreten Aufgabe
     */
    public Routine(String routinenArt, String beschreibung) {
        this.routinenArt = routinenArt;
        this.beschreibung = beschreibung;
        this.erledigt = false;
    }

    // Getter-Methoden

    /**
     * Gibt die Kategorie (Art) der Routine zurück.
     * @return Routinenart als String
     */
    public String getRoutinenArt() {
        return routinenArt;
    }

    /**
     * Gibt die Beschreibung der Routine zurück.
     * @return Beschreibung als String
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Gibt zurück, ob die Routine bereits erledigt ist.
     * @return true = erledigt, false = offen
     */
    public boolean isErledigt() {
        return erledigt;
    }

    // Setter-Methoden

    /**
     * Setzt den Erledigt-Status dieser Routine.
     * @param erledigt true = erledigt, false = nicht erledigt
     */
    public void setErledigt(boolean erledigt) {
        this.erledigt = erledigt;
    }

    /**
     * Setzt die Routinenart neu.
     * @param routinenArt neue Kategorie
     */
    public void setRoutinenArt(String routinenArt) {
        this.routinenArt = routinenArt;
    }

    /**
     * Setzt eine neue Beschreibung für die Routine.
     * @param beschreibung neue Beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Gibt eine textuelle Darstellung der Routine zurück,
     * z. B.: "[✓] Morgen – Zähne putzen"
     *
     * @return formatierter String
     */
    @Override
    public String toString() {
        String status = erledigt ? "[✓]" : "[ ]";
        return status + " " + routinenArt + " – " + beschreibung;
    }
}
