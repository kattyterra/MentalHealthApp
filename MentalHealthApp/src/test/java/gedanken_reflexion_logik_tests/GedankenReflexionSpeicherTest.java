package gedanken_reflexion_logik_tests;

import gedanken_reflexion_logik.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.DateiLeseHelfer;
import utility.DateiSchreibHelfer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GedankenReflexionSpeicherTest {

    private MockSchreibHelfer mockSchreiber;
    private MockLeseHelfer mockLeser;
    private GedankenReflexionSpeicher speicher;

    static class MockSchreibHelfer extends DateiSchreibHelfer {
        boolean wurdeAufgerufen = false;
        String letzterOrdner;
        String letzterDateiname;
        List<CharSequence> letzterInhalt = new ArrayList<>();

        @Override
        public void anhaengen(String ordner, String dateiname, Iterable<? extends CharSequence> inhalte) {
            System.out.println("Mock anhaengen wurde aufgerufen!");
            wurdeAufgerufen = true;
            letzterOrdner = ordner;
            letzterDateiname = dateiname;
            letzterInhalt.clear();
            inhalte.forEach(letzterInhalt::add);
        }
    }

    static class MockLeseHelfer extends DateiLeseHelfer {
        boolean wurdeAufgerufen = false;
        String letzterPfad;

        @Override
        public List<String> leseAlleZeilen(String ordnerPfad) {
            wurdeAufgerufen = true;
            letzterPfad = ordnerPfad;
            return List.of("Reflexion A", "Reflexion B");
        }
    }

    @BeforeEach
    void setup() {
        mockSchreiber = new MockSchreibHelfer();
        mockLeser = new MockLeseHelfer();
        speicher = new GedankenReflexionSpeicher(mockSchreiber, mockLeser);
    }

    @Test
    void speichern_ruftAnhaengenMitRichtigenWertenAuf() {
        GedankenReflexionEintrag eintrag = new GedankenReflexionEintrag(
                LocalDate.now().toString(), "10:00", "6",
                List.of("Gedanke", "Dafür", "Dagegen", "Sichtweise", "Gutes tun")
        );

        speicher.speichern(eintrag);

        assertTrue(mockSchreiber.wurdeAufgerufen);
        assertEquals("Reflexionen/", mockSchreiber.letzterOrdner);
        assertEquals(LocalDate.now() + ".txt", mockSchreiber.letzterDateiname);
        assertNotNull(mockSchreiber.letzterInhalt);
        assertTrue(mockSchreiber.letzterInhalt.getFirst().toString().contains("Belastungsskala: 6"));
    }

    @Test
    void lesenAlle_ruftLeseHelferMitOrdnerAuf() {
        List<String> result = speicher.lesenAlle();

        assertTrue(mockLeser.wurdeAufgerufen);
        assertEquals("Reflexionen/", mockLeser.letzterPfad);
        assertEquals(2, result.size());
        assertEquals("Reflexion A", result.getFirst());
    }
}
