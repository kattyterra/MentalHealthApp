package zielverwaltung_logik_tests;
import org.junit.jupiter.api.Test;
import zielverwaltung_logik.Wiederholungstyp;
import zielverwaltung_logik.Ziel;
import zielverwaltung_logik.ZielKategorie;
import zielverwaltung_logik.ZielStatistikVerwaltung;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZielStatistikVerwaltungTest {

    @Test
    void generiereStatistikText_zaehltErledigteUndGruppiertNachKategorie() {
        Ziel ziel1 = new Ziel(ZielKategorie.GESUNDHEIT, "Trinken", 1, LocalDate.now(), Wiederholungstyp.KEINE);
        ziel1.setErledigt(true);
        Ziel ziel2 = new Ziel(ZielKategorie.GESUNDHEIT, "Sport", 2, LocalDate.now(), Wiederholungstyp.KEINE);
        ziel2.setErledigt(true);
        Ziel ziel3 = new Ziel(ZielKategorie.ARBEIT, "Bewerbung", 1, LocalDate.now(), Wiederholungstyp.KEINE);
        ziel3.setErledigt(true);

        ZielStatistikVerwaltung statistik = new ZielStatistikVerwaltung();
        String ausgabe = statistik.generiereStatistikText(List.of(ziel1, ziel2, ziel3));

        assertTrue(ausgabe.contains("✅ Erledigte Ziele: 3 von 3"));
        assertTrue(ausgabe.contains("GESUNDHEIT: 2 erledigt"));
        assertTrue(ausgabe.contains("ARBEIT: 1 erledigt"));
    }

    @Test
    void generiereStatistikText_beiLeererListe() {
        ZielStatistikVerwaltung statistik = new ZielStatistikVerwaltung();
        String ausgabe = statistik.generiereStatistikText(List.of());

        assertTrue(ausgabe.contains("✅ Erledigte Ziele: 0 von 0"));
        assertTrue(ausgabe.contains("Zielerreichung nach Kategorien"));
    }
}