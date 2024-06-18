package cl.govegan.mssearchfood.utils.request;

public class NormalizationWrapper {
    public String normalizeText (String input) {
        return Normalization.normalizeText(input);
    }
}
