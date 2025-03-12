package zielverwaltung_logik;

import java.util.*;

public class ZielService {
    private final List<Ziel> ziele;
    private final ZielRepository repository;

    public ZielService(ZielRepository repository) {
        this.repository = repository;
        this.ziele = repository.laden();
    }

    public void hinzufuegen(Ziel ziel) {
        ziele.add(ziel);
        speichern();
    }

    public void abhaken(int index) {
        if (index >= 0 && index < ziele.size()) {
            ziele.get(index).setErledigt(true);
            speichern();
        }
    }

    public void bearbeiten(int index, Ziel aktualisiert) {
        if (index >= 0 && index < ziele.size()) {
            Ziel ziel = ziele.get(index);
            ziel.setBeschreibung(aktualisiert.getBeschreibung());
            ziel.setPrioritaet(aktualisiert.getPrioritaet());
            ziel.setFaelligkeit(aktualisiert.getFaelligkeit());
            ziel.setWiederholung(aktualisiert.getWiederholung());
            ziel.setMotivationsnotiz(aktualisiert.getMotivationsnotiz());
            speichern();
        }
    }

    public void loeschen(int index) {
        if (index >= 0 && index < ziele.size()) {
            ziele.remove(index);
            speichern();
        }
    }

    public List<Ziel> getZiele() {
        return ziele;
    }

    public List<Ziel> filterNachKategorie(String kategorie) {
        try {
            ZielKategorie filterKategorie = ZielKategorie.valueOf(kategorie.toUpperCase());
            return ziele.stream()
                    .filter(z -> z.getKategorie() == filterKategorie)
                    .toList();
        } catch (IllegalArgumentException e) {
            System.out.println("⚠ Ungültige Kategorie: " + kategorie);
            return List.of(); // leere Liste zurückgeben
        }
    }

    public List<Ziel> filterNachStatus(boolean erledigt) {
        return ziele.stream()
                .filter(z -> z.isErledigt() == erledigt)
                .toList();
    }

    private void speichern() {
        repository.speichern(ziele);
    }
}
