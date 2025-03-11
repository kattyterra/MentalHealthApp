package routinen_logik;

public class Routine {
    private RoutinenArt art;
    private String beschreibung;
    private boolean erledigt;

    public Routine(RoutinenArt art, String beschreibung) {
        this.art = art;
        this.beschreibung = beschreibung;
        this.erledigt = false;
    }

    public RoutinenArt getArt() { return art; }
    public String getBeschreibung() { return beschreibung; }
    public boolean isErledigt() { return erledigt; }

    public void setArt(RoutinenArt art) { this.art = art; }
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
    public void setErledigt(boolean erledigt) { this.erledigt = erledigt; }

    @Override
    public String toString() {
        String status = erledigt ? "[✓]" : "[ ]";
        return status + " [" + art + "] – " + beschreibung;
    }
}
