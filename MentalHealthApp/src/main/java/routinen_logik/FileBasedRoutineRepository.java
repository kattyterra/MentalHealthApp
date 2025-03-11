package routinen_logik;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileBasedRoutineRepository implements RoutineRepository {
    private final String ordner = "Routinen";
    private final String stammliste = ordner + "/stammliste.txt";
    private final String tagesdatei;
    private final List<Routine> routinen = new ArrayList<>();

    public FileBasedRoutineRepository() throws RoutineException {
        try {
            File dir = new File(ordner);
            if (!dir.exists()) {
                boolean success = dir.mkdirs();
                if (!success) {
                    System.err.println("Ordner '" + ordner + "' konnte nicht erstellt werden!");
                }
            }
            File stamm = new File(stammliste);
            if (!stamm.exists()) {
                if (stamm.createNewFile()) {
                    System.out.println("✅ Stammliste wurde neu erstellt.");
                } else {
                    System.out.println("⚠ Stammliste konnte nicht erstellt werden.");
                }
            }
            String datum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            tagesdatei = ordner + "/" + datum + ".txt";
            if (new File(tagesdatei).exists()) ladeTagesdatei();
            else {
                ladeStammliste();
                speichern();
            }
        } catch (IOException e) {
            throw new RoutineException("Fehler bei Initialisierung: " + e.getMessage());
        }
    }

    private void ladeStammliste() throws RoutineException {
        routinen.clear();
        try (BufferedReader r = new BufferedReader(new FileReader(stammliste))) {
            String zeile;
            while ((zeile = r.readLine()) != null) {
                RoutinenArt art = RoutinenArt.fromText(zeile.trim());
                String beschreibung = r.readLine();
                routinen.add(new Routine(art, beschreibung));
            }
        } catch (IOException e) {
            throw new RoutineException("Fehler beim Laden der Stammliste: " + e.getMessage());
        }
    }


    private void ladeTagesdatei() throws RoutineException {
        routinen.clear();
        try (BufferedReader r = new BufferedReader(new FileReader(tagesdatei))) {
            String zeile;
            while ((zeile = r.readLine()) != null) {
                RoutinenArt art = RoutinenArt.fromText(zeile.trim());
                String beschreibung = r.readLine();
                boolean erledigt = Boolean.parseBoolean(r.readLine());
                Routine routine = new Routine(art, beschreibung);
                routine.setErledigt(erledigt);
                routinen.add(routine);
            }
        } catch (IOException e) {
            throw new RoutineException("Fehler beim Laden der Tagesdatei: " + e.getMessage());
        }
    }

    @Override
    public List<Routine> getRoutinen() {
        return routinen;
    }

    @Override
    public void hinzufuegen(Routine routine) throws RoutineException {
        routinen.add(routine);
        speichern();
        try (BufferedWriter w = new BufferedWriter(new FileWriter(stammliste, true))) {
            w.write(routine.getArt().name());
            w.newLine();
            w.write(routine.getBeschreibung());
            w.newLine();
        } catch (IOException e) {
            throw new RoutineException("Fehler beim Speichern in Stammliste: " + e.getMessage());
        }
    }

    @Override
    public void bearbeiten(int index, RoutinenArt neueArt, String neueBeschreibung) throws RoutineException {
        if (index < 0 || index >= routinen.size()) throw new RoutineException("Ungültiger Index.");
        Routine r = routinen.get(index);
        r.setArt(neueArt);
        r.setBeschreibung(neueBeschreibung);
        speichern();
    }

    @Override
    public void loeschen(int index) throws RoutineException {
        if (index < 0 || index >= routinen.size()) throw new RoutineException("Ungültiger Index.");
        routinen.remove(index);
        speichern();
    }

    @Override
    public void speichern() throws RoutineException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(tagesdatei))) {
            for (Routine r : routinen) {
                w.write(r.getArt().name());
                w.newLine();
                w.write(r.getBeschreibung());
                w.newLine();
                w.write(Boolean.toString(r.isErledigt()));
                w.newLine();
            }
        } catch (IOException e) {
            throw new RoutineException("Fehler beim Speichern der Tagesdatei: " + e.getMessage());
        }
    }
}
