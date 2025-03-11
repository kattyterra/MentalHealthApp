package uebungen;

import java.util.List;

/**
 * Repräsentiert eine Übungseinheit, z. B. aus dem Bereich Achtsamkeit, Atmung etc.
 * Enthält den Namen der Übung, das Ziel (z. B. Entspannung, Konzentration) und
 * eine schrittweise Anleitung als Liste von Textzeilen.
 */
public record Uebung(String name, String ziel, List<String> anleitung) {
    // Dies ist ein reiner Daten-Record – Methoden sind optional,
    // können bei Bedarf aber z. B. für Formatierungen ergänzt werden.
}
