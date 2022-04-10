package zad1;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class XList<T> extends ArrayList<T> {

    Comparator<String> comparator = (x1, x2) -> {
        String str1 = new StringBuilder(x1).reverse().toString();
        String str2 = new StringBuilder(x2).reverse().toString();
        return str1.compareTo(str2);
    };


    public XList(T... elements) {
        Collections.addAll(this, elements);
    }
    public XList(Collection<T> collection) {
        super(collection);
    }

    public static <K> XList<K> of (K... elements) {
        return new XList<K>(elements);
    }
    public static <K> XList<K> of (Collection<K> collection) {
        return new XList<K>(collection);
    }

    public static XList<String> charsOf(String string) {
        return tokensOf(string, "");
    }
    public static XList<String> tokensOf(String string) {
        return XList.tokensOf(string, "\\s+");
    }
    public static XList<String> tokensOf(String string, String separator) {
        return XList.of(string.split(separator));
    }

    public XList<T> union(Collection<T> collection) {
        XList<T> result = new XList<T>(this);
        result.addAll(collection);

        return result;
    }
    public XList<T> union(T... elements) {
        return this.union(XList.of(elements));
    }

    public XList<T> diff(Collection<T> collection) {
        XList<T> result = new XList<T>(this);
        result.removeAll(collection);

        return result;
    }
    public XList<T> diff(T... elements) {
        return this.diff(XList.of(elements));
    }

    public XList<T> unique() {
        return new XList<T>(this.stream().distinct().collect(Collectors.toList()));
    }

    private void helperMethod2(String oldCombination, int row, int col, List<List<String>> data, List<String> res) {
        String newCombination = oldCombination + data.get(row).get(col);

        if (row == data.size() - 1) {
            res.add(newCombination);
        }

        if (row < data.size() - 1) {
            helperMethod2(newCombination, row + 1, 0, data, res);
        }

        if (col < data.get(row).size() - 1) {
            helperMethod2(oldCombination, row, col + 1, data, res);
        }
    }

    public XList<XList<String>> combine() {

        List<List<String>> list = new ArrayList<>();
        List<String> res = new ArrayList<>();
        XList<XList<String>> result = new XList<XList<String>>();

        for (T t: this) {
            list.add((List<String>) t);
        }

        helperMethod2("", 0, 0, list, res);

        res.sort(comparator);


        for (int i = 0; i < res.size(); i++) {
            result.add(charsOf(res.get(i)));
        }

        return result;
    }

    public <K> XList<K> collect(Function<T, K> function) {
        XList<K> result = new XList<K>();

        for (T t: this) {
            result.add(function.apply(t));
        }

        return result;
    }

    public String join(String separator){
        return this.stream()
                .map(Object::toString)
                .collect(Collectors.joining(separator));
    }

    public String join(){
        return this.join("");
    }

    public void forEachWithIndex(BiConsumer<T,Integer> consumer) {
        for (int i = 0; i < this.size(); i++) {
            consumer.accept(this.get(i), i);
        }
    }


    

}
