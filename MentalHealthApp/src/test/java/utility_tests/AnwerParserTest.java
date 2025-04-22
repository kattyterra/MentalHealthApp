package utility_tests;

import utility.AnswerParser;

import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnswerParserTest {

    @Test
    void GueltigeEingabe() {
        Scanner scanner = new Scanner("4");
        AnswerParser parser = new AnswerParser();

        int result = parser.parsen(scanner);

        assertEquals(4, result, "Parsen von '4' sollte 4 zurückgeben");
    }

    @Test
    void UngueltigerEingabe() {
        Scanner scanner = new Scanner("abc");
        AnswerParser parser = new AnswerParser();

        int result = parser.parsen(scanner);

        assertEquals(99, result, "Ungültige Eingabe sollte 99 zurückgeben");
    }

    @Test
    void LeereEingabe() {
        Scanner scanner = new Scanner("");
        AnswerParser parser = new AnswerParser();

        int result = parser.parsen(scanner);

        assertEquals(99, result, "Leere Eingabe sollte 99 zurückgeben");
    }
}
