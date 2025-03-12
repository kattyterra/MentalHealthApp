package zielverwaltung_logik;

import java.time.LocalDate;

public class Ziel {
    private ZielKategorie kategorie;
    private String beschreibung;
    private int prioritaet;
    private boolean erledigt;
    private LocalDate faelligkeit;
    private Wiederholungstyp wiederholung;
    private String motivationsnotiz;

    public Ziel(ZielKategorie kategorie, String beschreibung, int prioritaet, LocalDate faelligkeit, Wiederholungstyp wiederholung) {
        this.kategorie = kategorie;
        this.beschreibung = beschreibung;
        this.prioritaet = prioritaet;
        this.faelligkeit = faelligkeit;
        this.wiederholung = wiederholung;
        this.erledigt = false;
        this.motivationsnotiz = "";
    }

    public ZielKategorie getKategorie() {
        return kategorie;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public int getPrioritaet() {
        return prioritaet;
    }

    public boolean isErledigt() {
        return erledigt;
    }

    public LocalDate getFaelligkeit() {
        return faelligkeit;
    }

    public Wiederholungstyp getWiederholung() {
        return wiederholung;
    }

    public String getMotivationsnotiz() {
        return motivationsnotiz;
    }

    public void setKategorie(ZielKategorie kategorie) {
        this.kategorie = kategorie;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setPrioritaet(int prioritaet) {
        this.prioritaet = prioritaet;
    }

    public void setErledigt(boolean erledigt) {
        this.erledigt = erledigt;
    }

    public void setFaelligkeit(LocalDate faelligkeit) {
        this.faelligkeit = faelligkeit;
    }

    public void setWiederholung(Wiederholungstyp wiederholung) {
        this.wiederholung = wiederholung;
    }

    public void setMotivationsnotiz(String motivationsnotiz) {
        this.motivationsnotiz = motivationsnotiz;
    }

    @Override
    public String toString() {
        String status = erledigt ? "[✓]" : "[ ]";
        String prioText = switch (prioritaet) {
            case 1 -> "hoch";
            case 2 -> "mittel";
            case 3 -> "niedrig";
            default -> "unbekannt";
        };
        return status + " [" + kategorie + "] (Prio: " + prioText + ", bis: " + faelligkeit + ", " + wiederholung + ") – " + beschreibung;
    }
}

