package InspirationsLogik;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class InspirationsSpeicher implements InspirationsRepository {

    @Override
    public List<String> ladeSaetze() {
        try {
            String pfad = "Textvorlagen(nicht_ändern!)/Inspo.txt";
            return Files.readAllLines(Paths.get(pfad));
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Inspirationssätze: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
