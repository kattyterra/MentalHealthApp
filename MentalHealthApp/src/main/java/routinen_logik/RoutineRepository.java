package routinen_logik;

import java.util.List;

/**
 * Schnittstelle für ein Repository zur Verwaltung von Routinen.
 * <p>
 * Definiert alle grundlegenden Operationen, die eine konkrete Routine-Speicherklasse
 * (z. B. {@link FileBasedRoutineRepository}) bereitstellen muss.
 * <p>
 * Die Trennung über ein Interface ermöglicht lose Kopplung und Austauschbarkeit
 * der Speicherstrategie (z. B. später Datenbank statt Datei).
 */
public interface RoutineRepository {

    /**
     * Liefert alle aktuell gespeicherten Routinen.
     *
     * @return Liste der Routinen
     */
    List<Routine> getRoutinen();

    /**
     * Fügt eine neue Routine hinzu und speichert sie dauerhaft.
     *
     * @param routine die hinzuzufügende Routine
     * @throws RoutineException bei Speicherfehlern
     */
    void hinzufuegen(Routine routine) throws RoutineException;

    /**
     * Bearbeitet eine bestehende Routine anhand ihres Indexes.
     *
     * @param index Index der zu bearbeitenden Routine
     * @param neueArt neue Routinenart
     * @param neueBeschreibung neue Beschreibung
     * @throws RoutineException bei ungültigem Index oder Speicherfehler
     */
    void bearbeiten(int index, RoutinenArt neueArt, String neueBeschreibung) throws RoutineException;

    /**
     * Löscht eine Routine anhand ihres Indexes.
     *
     * @param index Index der zu löschenden Routine
     * @throws RoutineException bei ungültigem Index oder Speicherfehler
     */
    void loeschen(int index) throws RoutineException;

    /**
     * Speichert den aktuellen Zustand aller Routinen (z. B. in einer Tagesdatei).
     *
     * @throws RoutineException bei Speicherfehlern
     */
    void speichern() throws RoutineException;
}
