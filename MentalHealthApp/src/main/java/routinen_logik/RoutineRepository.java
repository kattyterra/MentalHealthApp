package routinen_logik;

import java.util.List;

public interface RoutineRepository {
    List<Routine> getRoutinen();
    void hinzufuegen(Routine routine) throws RoutineException;
    void bearbeiten(int index, RoutinenArt neueArt, String neueBeschreibung) throws RoutineException;
    void loeschen(int index) throws RoutineException;
    void speichern() throws RoutineException;
}