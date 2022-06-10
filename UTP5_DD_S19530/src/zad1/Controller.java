package zad1;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import zad1.models.*;
import zad1.reflect.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Controller {

    private static final String YEARS_LABEL_TEXT = "LATA";
    private static final String YEARS_TEXT = "LL";
    private static final String EXPORT_TEXT = "EKS";
    private static final String PKB_TEXT = "PKB";
    private static final int LINES_TO_SKIP = 1;

    private String modelName;
    private Model1 model1;
    private ReflectUtils reflectUtils;

    private List<Integer> years;
    private List<RateModel> rateModels;
    private List<Double> exp_arr;
    private String export_rate;

    public Controller(String modelName) {
        this.modelName = modelName;
        model1 = new Model1();
    }

    public Controller readDataFrom(String fName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fName));
            years = getYears(lines);
            rateModels = getRateModels(lines, years.size());
            reflectUtils = new ReflectUtils(model1, years, rateModels);
            reflectUtils.addValues();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public Controller runModel() {
        model1.run();
        return this;
    }

    public Controller runScriptFromFile(String fname) {
        try {
            GroovyShell shell = prepareShell();
            evaluateScriptFromFile(shell, fname);
            BufferedReader Buff = new BufferedReader(new FileReader(fname));
            String[] textArray = Buff.readLine().split("=");
            export_rate = new String(textArray[0].trim());
            saveScriptResult(shell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public Controller runScript(String script) {
        try {
            GroovyShell shell = prepareShell();
            evaluateScriptFromString(shell, script);
            String[] textArray = script.split("=");
            export_rate = new String(textArray[0].trim());
            saveScriptResult(shell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getResultsAsTsv() {
        try {
            double[] pkbArray = reflectUtils.retrieveDoubleArrayFieldFromModel(PKB_TEXT);
            List<Double> pkbValues = DoubleStream.of(pkbArray).mapToObj(Double::valueOf).collect(Collectors.toList());
            return new TsvResult(years, rateModels, pkbValues, exp_arr, export_rate).generateTsvResult();
        } catch (Exception e) {
            throw new RuntimeException("TSV nie moze zostac zrobiony", e);
        }

    }

    private GroovyShell prepareShell() throws Exception {
        Binding binding = new Binding();
        binding.setVariable(YEARS_TEXT, years.size());
        binding.setVariable(EXPORT_TEXT, reflectUtils.retrieveDoubleArrayFieldFromModel(EXPORT_TEXT));
        binding.setVariable(PKB_TEXT, reflectUtils.retrieveDoubleArrayFieldFromModel(PKB_TEXT));
        return new GroovyShell(binding);
    }

    private void evaluateScriptFromFile(GroovyShell shell, String filePath) throws IOException {
        shell.evaluate(new File(filePath));
    }

    private void evaluateScriptFromString(GroovyShell shell, String script) {
        shell.evaluate(script);
    }

    private void saveScriptResult(GroovyShell shell) {
        double[] exp_arr = (double[]) shell.getProperty(this.export_rate);
        this.exp_arr = DoubleStream.of(exp_arr)
                .mapToObj(Double::valueOf)
                .collect(Collectors.toList());
    }

    private List<Integer> getYears(List<String> lines) {
        String firstLine = lines.iterator().next();
        Scanner scanner = new Scanner(firstLine);
        scanner.skip(YEARS_LABEL_TEXT);

        List<Integer> years = new ArrayList<>();
        while (scanner.hasNextInt()) {
            years.add(scanner.nextInt());
        }
        scanner.close();
        return years;
    }

    private List<RateModel> getRateModels(List<String> lines, int years) {
        return lines.stream()
                .skip(LINES_TO_SKIP)
                .map(line -> line.replace(".", ","))
                .map(line -> mapToRateModel(line, years))
                .collect(Collectors.toList());
    }

    private RateModel mapToRateModel(String line, int years) {
        Scanner scanner = new Scanner(line);
        String label = scanner.next();

        List<Double> values = new ArrayList<>();
        while (scanner.hasNextDouble()) {
            values.add(scanner.nextDouble());
        }

        if (values.size() < years) {
            if (label.contains("tw")) {
                extendValues(values, years);
            } else {
                extendWithZero(values, years);
            }
        }
        scanner.close();
        return new RateModel(label, values.stream().mapToDouble(Double::doubleValue).toArray());
    }

    private void extendValues(List<Double> values, int years) {
        double lastValue = values.get(values.size() - 1);
        int diff = years - values.size();
        for (int i = 0; i < diff; i++) {
            values.add(lastValue);
        }
    }

    private void extendWithZero(List<Double> values, int years) {
        int diff = years - values.size();
        for (int i = 0; i < diff; i++) {
            values.add(0.0);
        }
    }

}