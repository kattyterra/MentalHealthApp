package tagebuch_logik_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tagebuch_logik.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TagebuchVerwaltungTest {

    private MockTagebuchRepository repository;
    private MockBenutzerEingabe eingabe;
    private MockAbfrage abfrage;
    private TagebuchVerwaltung verwaltung;

    static class MockTagebuchRepository implements TagebuchRepository {
        public TagebuchEintrag gespeicherterEintrag;
        public String zuletztGelesenesDatum;
        public String geloeschtesDatum;
        public String geloeschteUhrzeit;
        public String bearbeitetesDatum;
        public String bearbeiteteUhrzeit;
        public String bearbeiteterText;
        public boolean bearbeitenErgebnis = true;

        @Override
        public void speichern(TagebuchEintrag eintrag) {
            this.gespeicherterEintrag = eintrag;
        }

        @Override
        public void loeschen(String datum) {
            this.geloeschtesDatum = datum;
        }

        @Override
        public void loeschenEintrag(String datum, String uhrzeit) {
            this.geloeschtesDatum = datum;
            this.geloeschteUhrzeit = uhrzeit;
        }

        @Override
        public String lesen(String datum) {
            this.zuletztGelesenesDatum = datum;
            return "Test-Inhalt";
        }

        @Override
        public List<String> getVerfuegbareEintraege() {
            return List.of("2025-04-18");
        }

        @Override
        public boolean bearbeiten(String datum, String uhrzeit, String neuerText) {
            this.bearbeitetesDatum = datum;
            this.bearbeiteteUhrzeit = uhrzeit;
            this.bearbeiteterText = neuerText;
            return bearbeitenErgebnis;
        }
    }

    static class MockAbfrage extends BenutzerAbfrageDateiLoeschen {
        private final boolean ganzeDatei;

        public MockAbfrage(boolean ganzeDatei) {
            this.ganzeDatei = ganzeDatei;
        }

        @Override
        public boolean eingabe_lesen(Scanner scanner) {
            return ganzeDatei;
        }
    }

    static class MockBenutzerEingabe extends BenutzerEingabe {
        private final String text;

        public MockBenutzerEingabe(String text) {
            this.text = text;
        }

        @Override
        public String leseEintrag(Scanner scanner) {
            return text;
        }
    }

    @BeforeEach
    void setup() {
        repository = new MockTagebuchRepository();
        eingabe = new MockBenutzerEingabe("Testtext");
        abfrage = new MockAbfrage(false); // default: nur Eintrag löschen, nicht ganze Datei
        verwaltung = new TagebuchVerwaltung(repository, eingabe, abfrage);
    }

    @Test
    void eintragSchreiben_speichertEintragMitHeutigemDatumUndText() {
        verwaltung.eintragSchreiben(new Scanner(""));

        assertEquals("Testtext", repository.gespeicherterEintrag.text());
        assertEquals(LocalDate.now().toString(), repository.gespeicherterEintrag.datum());
    }

    @Test
    void eintragLesen_gibtEintragFuerGewaehltesDatumAus() {
        Scanner scanner = new Scanner("1\n");
        verwaltung.eintragLesen(scanner);

        assertEquals("2025-04-18", repository.zuletztGelesenesDatum);
    }

    @Test
    void eintragBearbeiten_ruftBearbeitenMitEingabenAuf() {
        Scanner scanner = new Scanner("1\n08:00:00\nNeuer Text\n");
        verwaltung.eintragBearbeiten(scanner);

        assertEquals("2025-04-18", repository.bearbeitetesDatum);
        assertEquals("08:00:00", repository.bearbeiteteUhrzeit);
        assertEquals("Neuer Text", repository.bearbeiteterText);
    }

    @Test
    void eintragLoeschen_ganzeDateiWirdGeloeschtWennAbfrageTrue() {
        abfrage = new MockAbfrage(true);
        verwaltung = new TagebuchVerwaltung(repository, eingabe, abfrage);
        Scanner scanner = new Scanner("1\n");

        verwaltung.eintragLoeschen(scanner);

        assertEquals("2025-04-18", repository.geloeschtesDatum);
        assertNull(repository.geloeschteUhrzeit); // weil ganze Datei gelöscht
    }

    @Test
    void eintragLoeschen_einzelnerEintragWirdGeloeschtWennAbfrageFalse() {
        abfrage = new MockAbfrage(false);
        verwaltung = new TagebuchVerwaltung(repository, eingabe, abfrage);
        Scanner scanner = new Scanner("1\n08:00:00\n");

        verwaltung.eintragLoeschen(scanner);

        assertEquals("2025-04-18", repository.geloeschtesDatum);
        assertEquals("08:00:00", repository.geloeschteUhrzeit);
    }
}
