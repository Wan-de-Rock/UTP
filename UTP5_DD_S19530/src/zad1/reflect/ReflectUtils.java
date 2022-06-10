package zad1.reflect;

import zad1.models.Bind;
import zad1.models.Model1;
import zad1.models.RateModel;

import java.lang.reflect.Field;
import java.util.List;

public class ReflectUtils {

    private static final String YEARS_FIELD = "LL";

    private Model1 model1;
    private List<Integer> years;
    private List<RateModel> rateModels;

    public ReflectUtils(Model1 model1, List<Integer> years, List<RateModel> rateModels) throws Exception {
        this.model1 = model1;
        this.years = years;
        this.rateModels = rateModels;
    }

    public void addValues() throws Exception {
        addYearsToModel(years);
        addRateModelsToModel(rateModels);
    }

    public double[] retrieveDoubleArrayFieldFromModel(String fieldName) throws Exception {
        Class<? extends Model1> modelClass = model1.getClass();
        Field pkbField = modelClass.getDeclaredField(fieldName);
        pkbField.setAccessible(true);

        if (pkbField.getAnnotation(Bind.class) == null) {
            throw new Exception("PKB nie ma adnotacji @Bind");
        }
        return (double[]) pkbField.get(model1);
    }

    private void addYearsToModel(List<Integer> years) throws Exception {
        Class<? extends Model1> modelClass = model1.getClass();
        Field yearsField = modelClass.getDeclaredField(YEARS_FIELD);
        yearsField.setAccessible(true);

        if (yearsField.getAnnotation(Bind.class) != null) {
            yearsField.setInt(model1, years.size());
        }
    }

    private void addRateModelsToModel(List<RateModel> rmodels) {
        rmodels.forEach(rateModel -> {
            try {
                injectValuesToField(rateModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void injectValuesToField(RateModel rateModel){
        Class<? extends Model1> mclass = model1.getClass();
        Field yearsField;
        try {
            yearsField = mclass.getDeclaredField(rateModel.getLabel());
            yearsField.setAccessible(true);

            if (yearsField.getAnnotation(Bind.class) != null) {
                yearsField.set(model1, rateModel.getValues());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}