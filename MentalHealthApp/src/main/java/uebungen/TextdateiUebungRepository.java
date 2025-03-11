package uebungen;

import java.util.List;

public class TextdateiUebungRepository implements UebungRepository {
    private final String dateipfad;

    public TextdateiUebungRepository(String dateipfad) {
        this.dateipfad = dateipfad;
    }

    @Override
    public List<Uebung> ladeAlle() {
        return UebungLoader.ladeUebungen(dateipfad);
    }
}
