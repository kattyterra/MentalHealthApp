package RoutinenLogik;

public class Routine {
    private String RoutinenArt;
    private String beschreibung;
    private boolean erledigt;

    public Routine(String RoutinenArt, String beschreibung) {
        this.RoutinenArt = RoutinenArt;
        this.beschreibung = beschreibung;
        this.erledigt = false;
    }

    public String getRoutinenArt() {
        return RoutinenArt;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public boolean isErledigt() {
        return erledigt;
    }

    public void setErledigt(boolean erledigt) {
        this.erledigt = erledigt;
    }

    public void setRoutinenArt(String routinenArt) {
        this.RoutinenArt = routinenArt;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public String toString() {
        String status = erledigt ? "[✓]" : "[ ]";
        return status + " " + RoutinenArt + " – " + beschreibung;
    }
}
