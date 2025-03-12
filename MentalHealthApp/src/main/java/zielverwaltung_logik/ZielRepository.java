package zielverwaltung_logik;

import java.util.List;

/**
 * Interface zur Abstraktion der Speicherquelle für Ziele.
 * Ermöglicht verschiedene Implementierungen (z. B. Datei, Datenbank).
 */
public interface ZielRepository {

    /**
     * Speichert alle Ziele dauerhaft (z. B. in Datei oder DB).
     * @param ziele Liste der zu speichernden Ziele
     */
    void speichern(List<Ziel> ziele);

    /**
     * Lädt alle gespeicherten Ziele aus dem Datenspeicher.
     * @return Liste der geladenen Ziele
     */
    List<Ziel> laden();
}
