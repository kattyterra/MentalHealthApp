package uebungen;

import java.util.List;

/**
 * Schnittstelle für ein Repository zur Bereitstellung von {@link Uebung}-Objekten.
 * <p>
 * Ziel dieser Abstraktion ist es, verschiedene Datenquellen (z. B. Textdateien, Datenbanken)
 * austauschbar in die Anwendung einzubinden.
 */
public interface UebungRepository {

    /**
     * Lädt alle verfügbaren {@link Uebung}-Einträge.
     *
     * @return Liste aller geladenen Übungen
     */
    List<Uebung> ladeAlle();
}
