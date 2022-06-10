package zad1.models;

import java.util.Arrays;
import java.util.List;

public class TsvResult {
    private static final String YEARS_TEXT = "LATA";
    private static final String PKB_TEXT = "PKB";

    private List<Integer> years;
    private List<RateModel> rateModels;
    private List<Double> pkb;
    private List<Double> zdeks;
    private String zdeksText;

    public TsvResult(List<Integer> years, List<RateModel> rateModels, List<Double> pkb, List<Double> zdeks, String lineText) {
        this.years = years;
        this.rateModels = rateModels;
        this.pkb = pkb;
        this.zdeks = zdeks;
        this.zdeksText = lineText;
    }

    public String generateTsvResult(){
        StringBuilder builder = new StringBuilder();

        appendYears(builder);
        appendValues(builder);
        appendPkb(builder);
        appendZdeksIfExist(builder);

        return builder.toString();
    }

    private void appendYears(StringBuilder builder) {
        builder.append(YEARS_TEXT);
        years.forEach(year -> builder.append("\t").append(year));
        builder.append("\n");
    }

    private void appendValues(StringBuilder builder) {
        rateModels.forEach(rateModel -> {
            builder.append(rateModel.getLabel());
            Arrays.stream(rateModel.getValues())
                    .forEach(value -> builder.append("\t").append(value));
            builder.append("\n");
        });
    }

    private void appendPkb(StringBuilder builder) {
        builder.append(PKB_TEXT);
        pkb.forEach(pkbValue -> builder.append("\t").append(pkbValue));
        builder.append("\n");
    }

    private void appendZdeksIfExist(StringBuilder builder) {
        if(zdeks != null) {
            builder.append(zdeksText);
            zdeks.forEach(zdeksValue -> builder.append("\t").append(zdeksValue));
            builder.append("\n");
        }
    }

}
