package uebungen;

import java.util.List;

/**
 * Der {@code UebungService} ist eine Serviceklasse zur Bereitstellung aller verfügbaren {@link Uebung}-Einträge.
 * <p>
 * Er dient als Vermittler zwischen der Benutzeroberfläche (z. B. Menü) und dem {@link UebungRepository}.
 * Dadurch wird die Anwendung gemäß dem **Dependency Inversion Principle (DIP)** entkoppelt.
 */
public class UebungService {

    /** Das verwendete Repository zum Laden der Übungen */
    private final UebungRepository repository;

    /**
     * Konstruktor – initialisiert den Service mit einem konkreten Repository.
     *
     * @param repository das Repository zur Bereitstellung der Übungen
     */
    public UebungService(UebungRepository repository) {
        this.repository = repository;
    }

    /**
     * Gibt eine Liste aller verfügbaren {@link Uebung}-Objekte zurück.
     *
     * @return Liste aller Übungen aus dem Repository
     */
    public List<Uebung> getAlleUebungen() {
        return repository.ladeAlle();
    }
}
