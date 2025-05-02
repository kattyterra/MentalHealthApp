package stimmungskalender_logik_tests;

import org.junit.jupiter.api.Test;
import stimmungskalender_logik.Emotionseintrag;
import stimmungskalender_logik.Stimmungseintrag;
import stimmungskalender_logik.StimmungskalenderSpeicher;
import utility.DateiLeseHelfer;
import utility.DateiSchreibHelfer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StimmungskalenderSpeicherTest {

    static class MockDateiSchreibHelfer extends DateiSchreibHelfer {
        boolean anhaengenAufgerufen = false;
        String letzterOrdner;
        String letzterDateiname;
        List<CharSequence> letzteZeilen;

        @Override
        public void anhaengen(String ordner, String dateiname, Iterable<? extends CharSequence> inhalte) {
            anhaengenAufgerufen = true;
            letzterOrdner = ordner;
            letzterDateiname = dateiname;
            letzteZeilen = new ArrayList<>();
            inhalte.forEach(zeile -> letzteZeilen.add(zeile));
        }
    }

    static class MockDateiLeseHelfer extends DateiLeseHelfer {
        boolean lesenAufgerufen = false;
        String letzterOrdner;

        @Override
        public List<String> leseAlleZeilen(String ordnerPfad) {
            lesenAufgerufen = true;
            letzterOrdner = ordnerPfad;
            return List.of("Eintrag1", "Eintrag2");
        }
    }

    @Test
    void speichern_ruftAnhaengenMitHeutigemDatumAuf() {
        MockDateiSchreibHelfer mock = new MockDateiSchreibHelfer();
        StimmungskalenderSpeicher speicher = new StimmungskalenderSpeicher(mock, new MockDateiLeseHelfer());

        String heutigesDatum = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        Stimmungseintrag eintrag = new Stimmungseintrag(heutigesDatum, "10:00", 7);
        speicher.speichern(eintrag);

        assertTrue(mock.anhaengenAufgerufen);
        assertEquals("Stimmungskalender/", mock.letzterOrdner);
        assertEquals(heutigesDatum + ".txt", mock.letzterDateiname);
        assertTrue(mock.letzteZeilen.getFirst().toString().contains("Stimmung: 7"));
    }

    @Test
    void speichernEmotionen_ruftAnhaengenMitFormatiertenZeilenAuf() {
        MockDateiSchreibHelfer mock = new MockDateiSchreibHelfer();
        StimmungskalenderSpeicher speicher = new StimmungskalenderSpeicher(mock, new MockDateiLeseHelfer());

        Emotionseintrag eintrag = new Emotionseintrag("Freude", 8, "Lächeln");
        speicher.speichernEmotionen(List.of(eintrag));

        assertTrue(mock.anhaengenAufgerufen);
        assertEquals("Stimmungskalender/", mock.letzterOrdner);

        String erwartetesDatum = LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".txt";
        assertEquals(erwartetesDatum, mock.letzterDateiname);

        assertTrue(mock.letzteZeilen.getFirst().toString().contains("Freude"));
    }

    @Test
    void lesenAlle_ruftLeseHelferMitOrdnerAuf() {
        MockDateiLeseHelfer mock = new MockDateiLeseHelfer();
        StimmungskalenderSpeicher speicher = new StimmungskalenderSpeicher(new MockDateiSchreibHelfer(), mock);

        List<String> ergebnis = speicher.lesenAlle();

        assertTrue(mock.lesenAufgerufen);
        assertEquals("Stimmungskalender/", mock.letzterOrdner);
        assertEquals(2, ergebnis.size());
    }
}
