package routinen_logik;

/**
 * Eigene Ausnahme (Exception) für Fehler im Routinen-Modul der MentalHealthApp.
 * <p>
 * Diese Exception wird verwendet, um spezifische Fehlerfälle im Zusammenhang mit
 * der Routinenverwaltung gezielt behandeln und kommunizieren zu können – z. B.
 * bei Dateioperationen, ungültigen Indizes oder Initialisierungsproblemen.
 */
public class RoutineException extends Exception {

    /**
     * Konstruktor zur Erzeugung einer neuen RoutineException mit einer Fehlernachricht.
     *
     * @param message Fehlermeldung, die die Ursache beschreibt
     */
    public RoutineException(String message) {
        super(message);
    }
}
