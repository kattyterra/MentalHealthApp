package uebungen_tests;
import org.junit.jupiter.api.Test;
import uebungen.Uebung;
import uebungen.UebungRepository;
import uebungen.UebungsVerwaltung;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UebungsVerwaltungTest {

    @Test
    void getAlleUebungen_gibtListeAusRepositoryZurueck() {
        // Arrange: Repository-Mock
        UebungRepository mockRepo = new UebungRepository() {
            @Override
            public List<Uebung> ladeAlle() {
                return List.of(
                        new Uebung("Atemübung 1", "Ruhe finden", List.of("Einatmen", "Ausatmen")),
                        new Uebung("Atemübung 2", "Fokus stärken", List.of("Langsam ein", "Langsam aus"))
                );
            }
        };

        UebungsVerwaltung verwaltung = new UebungsVerwaltung(mockRepo);

        // Act
        List<Uebung> uebungen = verwaltung.getAlleUebungen();

        // Assert
        assertEquals(2, uebungen.size());
        assertEquals("Atemübung 1", uebungen.get(0).name());
        assertEquals("Atemübung 2", uebungen.get(1).name());
        assertEquals(List.of("Einatmen", "Ausatmen"), uebungen.get(0).anleitung());
    }
}