package zielverwaltung_logik_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import zielverwaltung_logik.Wiederholungstyp;
import zielverwaltung_logik.Ziel;
import zielverwaltung_logik.ZielKategorie;
import zielverwaltung_logik.ZielSpeicher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZielSpeicherTest {

    @TempDir
    Path tempDir;

    private Ziel createTestZiel() {
        Ziel ziel = new Ziel(ZielKategorie.GESUNDHEIT, "Mehr Wasser trinken", 2,
                LocalDate.of(2025, 6, 1), Wiederholungstyp.TAEGLICH);
        ziel.setErledigt(true);
        ziel.setMotivationsnotiz("Bleib hydriert!");
        return ziel;
    }

    @Test
    void speichernUndLaden_gibtIdentischeZieleZurueck() {
        String ordnerPfad = tempDir.resolve("Ziele").toString() + "/";
        ZielSpeicher speicher = new ZielSpeicher(ordnerPfad);

        Ziel ziel = createTestZiel();
        speicher.speichern(List.of(ziel));

        List<Ziel> geladen = speicher.laden();

        assertEquals(1, geladen.size());
        Ziel geladenesZiel = geladen.getFirst();

        assertEquals(ziel.getKategorie(), geladenesZiel.getKategorie());
        assertEquals(ziel.getBeschreibung(), geladenesZiel.getBeschreibung());
        assertEquals(ziel.getPrioritaet(), geladenesZiel.getPrioritaet());
        assertEquals(ziel.getFaelligkeit(), geladenesZiel.getFaelligkeit());
        assertEquals(ziel.getWiederholung(), geladenesZiel.getWiederholung());
        assertEquals(ziel.getMotivationsnotiz(), geladenesZiel.getMotivationsnotiz());
        assertEquals(ziel.isErledigt(), geladenesZiel.isErledigt());
    }

    @Test
    void ladenMitNichtExistierenderDatei_gibtLeereListeZurueck() {
        String ordnerPfad = tempDir.resolve("ZieleLeer").toString() + "/";
        ZielSpeicher speicher = new ZielSpeicher(ordnerPfad);

        List<Ziel> ziele = speicher.laden();

        assertTrue(ziele.isEmpty());
    }

    @Test
    void ladenMitFehlerhafterDatei_verursachtKeineException() throws IOException {
        Path ordner = tempDir.resolve("ZieleFehlerhaft");
        Files.createDirectories(ordner);
        Path datei = ordner.resolve("ziele.txt");
        Files.write(datei, List.of(
                "GESUNDHEIT",
                "Unvollstaendiger Eintrag" // nur 2 Zeilen statt 7
        ));

        ZielSpeicher speicher = new ZielSpeicher(ordner.toString() + "/");
        List<Ziel> ziele = speicher.laden();

        assertTrue(ziele.isEmpty()); // Fehlerhaft → nicht lesbar
    }
}
