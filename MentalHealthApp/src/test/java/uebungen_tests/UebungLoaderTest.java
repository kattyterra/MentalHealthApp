package uebungen_tests;
import org.junit.jupiter.api.Test;
import uebungen.Uebung;
import uebungen.UebungLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UebungLoaderTest {

    @Test
    void ladeUebungen_parstGueltigeDateiMitMehrerenUebungen() throws IOException {
        Path tempFile = Files.createTempFile("uebungen", ".txt");
        Files.write(tempFile, List.of(
                "1. Tief durchatmen",
                "Ziel: Entspannung",
                "Einatmen durch die Nase",
                "Ausatmen durch den Mund",
                "",
                "2. Fokus finden",
                "Ziel: Konzentration",
                "Schließe die Augen",
                "Atme tief ein"
        ));

        UebungLoader loader = new UebungLoader();
        List<Uebung> uebungen = loader.ladeUebungen(tempFile.toString());

        assertEquals(2, uebungen.size());

        Uebung erste = uebungen.getFirst();
        assertEquals("Tief durchatmen", erste.name());
        assertEquals("Entspannung", erste.ziel());
        assertEquals(List.of("Einatmen durch die Nase", "Ausatmen durch den Mund"), erste.anleitung());

        Uebung zweite = uebungen.get(1);
        assertEquals("Fokus finden", zweite.name());
        assertEquals("Konzentration", zweite.ziel());
        assertEquals(List.of("Schließe die Augen", "Atme tief ein"), zweite.anleitung());
    }

    @Test
    void ladeUebungen_gibtLeereListeZurueckWennDateiFehlt() {
        UebungLoader loader = new UebungLoader();
        List<Uebung> uebungen = loader.ladeUebungen("nicht_existierend.txt");
        assertTrue(uebungen.isEmpty());
    }

    @Test
    void ladeUebungen_parstDateiMitNurEinerUebung() throws IOException {
        Path tempFile = Files.createTempFile("eineUebung", ".txt");
        Files.write(tempFile, List.of(
                "1. Meditation",
                "Ziel: Innere Ruhe",
                "Setze dich bequem hin",
                "Schließe die Augen"
        ));

        UebungLoader loader = new UebungLoader();
        List<Uebung> uebungen = loader.ladeUebungen(tempFile.toString());

        assertEquals(1, uebungen.size());
        Uebung u = uebungen.getFirst();
        assertEquals("Meditation", u.name());
        assertEquals("Innere Ruhe", u.ziel());
        assertEquals(List.of("Setze dich bequem hin", "Schließe die Augen"), u.anleitung());
    }

    @Test
    void ladeUebungen_ignoriertLeereZeilenInAnleitung() throws IOException {
        Path tempFile = Files.createTempFile("mitLeerzeilen", ".txt");
        Files.write(tempFile, List.of(
                "1. Achtsamkeit",
                "Ziel: Klarheit",
                "Atme ein",
                "",
                "Atme aus"
        ));

        UebungLoader loader = new UebungLoader();
        List<Uebung> uebungen = loader.ladeUebungen(tempFile.toString());

        assertEquals(1, uebungen.size());
        assertEquals(List.of("Atme ein", "Atme aus"), uebungen.getFirst().anleitung());
    }

    @Test
    void ladeUebungen_liefertZielNullWennNichtVorhanden() throws IOException {
        Path tempFile = Files.createTempFile("ohneZiel", ".txt");
        Files.write(tempFile, List.of(
                "1. Kurzatmung stoppen",
                "Durch die Nase einatmen",
                "Durch den Mund ausatmen"
        ));

        UebungLoader loader = new UebungLoader();
        List<Uebung> uebungen = loader.ladeUebungen(tempFile.toString());

        assertEquals(1, uebungen.size());
        assertEquals("Kurzatmung stoppen", uebungen.getFirst().name());
        assertNull(uebungen.getFirst().ziel());
    }
}
