package RoutinenLogik;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RoutineManager {
    private final String routinenOrdner = "Routinen";
    private final String stammlistePfad = routinenOrdner + "/stammliste.txt";
    private final String tagesdateiPfad;
    private final List<Routine> routinen = new ArrayList<>();

    public RoutineManager() {
        File ordner = new File("Routinen");
        if (!ordner.exists()) {
            if (!ordner.mkdirs()) {
                System.out.println("Ordner 'Routinen' konnte nicht erstellt werden!");
            }
        }

        File stammliste = new File(stammlistePfad);
        if (!stammliste.exists()) {
            try {
                if (stammliste.createNewFile()) {
                    System.out.println("\n");
                }
            } catch (IOException e) {
                System.out.println("⚠ Fehler beim Erstellen von stammliste.txt: " + e.getMessage());
            }
        }

        String datum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tagesdateiPfad = routinenOrdner + "/" + datum + ".txt";

        if (new File(tagesdateiPfad).exists()) {
            ladeTagesdatei();
        } else {
            ladeStammliste();
            speichereTagesdatei();
        }
    }

    public void ladeStammliste() {
        routinen.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(stammlistePfad))) {
            String art;
            while ((art = reader.readLine()) != null) {
                String beschreibung = reader.readLine();
                if (beschreibung != null) {
                    System.out.println("Du hast noch keine Routinen hinzugefügt. Zeit deine 1. Routine zu erstellen!");
                    routinen.add(new Routine(art.trim(), beschreibung.trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("⚠ Fehler beim Laden der Stammliste: " + e.getMessage());
        }
    }

    public void ladeTagesdatei() {
        routinen.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(tagesdateiPfad))) {
            String art;
            while ((art = reader.readLine()) != null) {
                String beschreibung = reader.readLine();
                boolean erledigt = Boolean.parseBoolean(reader.readLine());
                Routine r = new Routine(art.trim(), beschreibung.trim());
                r.setErledigt(erledigt);
                routinen.add(r);
            }
        } catch (IOException e) {
            System.out.println("⚠ Fehler beim Laden der Tagesdatei: " + e.getMessage());
        }
    }

    public void speichereTagesdatei() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tagesdateiPfad))) {
            for (Routine r : routinen) {
                writer.write(r.getRoutinenArt());
                writer.newLine();
                writer.write(r.getBeschreibung());
                writer.newLine();
                writer.write(Boolean.toString(r.isErledigt()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Tagesdatei: " + e.getMessage());
        }
    }

    public void routineHinzufuegen(Routine routine) {
        routinen.add(routine);
        speichereTagesdatei();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(stammlistePfad, true))) {
            writer.write(routine.getRoutinenArt());
            writer.newLine();
            writer.write(routine.getBeschreibung());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern in Stammliste: " + e.getMessage());
        }
    }

    public void routineBearbeiten(int index, String neueArt, String neueBeschreibung) {
        Routine r = routinen.get(index);
        r.setRoutinenArt(neueArt);
        r.setBeschreibung(neueBeschreibung);
        speichereTagesdatei();
    }


    public void routineLoeschen(int index) {
        routinen.remove(index);
        speichereTagesdatei();
    }

    public List<Routine> getRoutinen() {
        return routinen;
    }
}
