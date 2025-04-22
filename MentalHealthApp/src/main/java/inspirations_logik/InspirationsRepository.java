package inspirations_logik;

import java.util.List;

/**
 * Dieses Interface definiert die Schnittstelle zur Bereitstellung von Inspirationssätzen.
 * Die konkrete Implementierung (z.B. Datei, Datenbank) bestimmt,
 * wie die Sätze geladen werden.
 */
public interface InspirationsRepository {

    /**
     * Lädt alle verfügbaren Inspirationssätze.
     * @return Liste der Inspirationssätze als Strings
     */
    List<String> ladeSaetze();
}
