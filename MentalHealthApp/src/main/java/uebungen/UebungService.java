package uebungen;

import java.util.List;

public class UebungService {
    private final UebungRepository repository;

    public UebungService(UebungRepository repository) {
        this.repository = repository;
    }

    public List<Uebung> getAlleUebungen() {
        return repository.ladeAlle();
    }
}
