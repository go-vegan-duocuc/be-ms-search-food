package cl.govegan.mssearchfood.utils.request;

import java.text.Normalizer;

public class Normalization {

    private Normalization () {
        throw new IllegalStateException("Utility class");
    }

    public static String normalizeText (String query) {
        String normalized = Normalizer.normalize(query, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        return normalized.toLowerCase();
    }
}