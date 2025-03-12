package menus;

import zielverwaltung_logik.*;

import java.util.List;
import java.util.Scanner;

public class ZielMenu {
    private final ZielService zielService;
    private final ZielStatistikService statistikService;

    public ZielMenu() {
        ZielRepository repo = new DateibasierterZielRepository();
        this.zielService = new ZielService(repo);
        this.statistikService = new ZielStatistikService();
    }

    public void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n🎯 Zielsetzungsmenü");
            System.out.println("1 - Neues Ziel hinzufügen");
            System.out.println("2 - Ziel abhaken");
            System.out.println("3 - Ziel bearbeiten");
            System.out.println("4 - Ziel löschen");
            System.out.println("5 - Alle Ziele anzeigen");
            System.out.println("6 - Statistik anzeigen");
            System.out.println("7 - Nach Kategorie filtern");
            System.out.println("8 - Nach Status filtern");
            System.out.println("9 - Zurück");
            System.out.print("Auswahl: ");
            String wahl = scanner.nextLine();
            switch (wahl) {
                case "1" -> zielService.hinzufuegen(ZielEingabeHelper.erstelleZielVomBenutzer(scanner));
                case "2" -> zielService.abhaken(ZielEingabeHelper.indexAbfragen(scanner));
                case "3" ->
                        zielService.bearbeiten(ZielEingabeHelper.indexAbfragen(scanner), ZielEingabeHelper.erstelleZielVomBenutzer(scanner));
                case "4" -> zielService.loeschen(ZielEingabeHelper.indexAbfragen(scanner));
                case "5" -> {
                    List<Ziel> alleZiele = zielService.getZiele();
                    if (alleZiele.isEmpty()) {
                        System.out.println("⚠ Es sind aktuell keine Ziele vorhanden.");
                    } else {
                        alleZiele.forEach(System.out::println);
                    }
                }
                case "6" -> statistikService.zeigeStatistik(zielService.getZiele());
                case "7" -> {
                    System.out.print("Kategorie: ");
                    zielService.filterNachKategorie(scanner.nextLine()).forEach(System.out::println);
                }
                case "8" -> {
                    System.out.print("Status (true/false): ");
                    zielService.filterNachStatus(Boolean.parseBoolean(scanner.nextLine())).forEach(System.out::println);
                }
                case "9" -> {
                    return;
                }
                default -> System.out.println("Ungültige Eingabe");
            }
        }
    }
}