/**
 *
 *  @author Diedov Denys S19530
 *
 */

package zad2;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CustomersPurchaseSortFind {

    private static List<Purchase> list = new ArrayList<Purchase>();;

    Comparator<Purchase> comparatorName = (p1, p2) -> {
        int com = p1.getName().compareTo(p2.getName());

        if (com == 0)
            return p1.getId().compareTo(p2.getId());
        else
            return com;
    };
    Comparator<Purchase> comparatorCost = (p1, p2) -> {
        double cost1 = p1.getPrice()* p1.getQuantity();
        double cost2 = p2.getPrice()* p2.getQuantity();

        if (cost1 > cost2)
            return -1;
        else if (cost1<cost2)
            return 1;
        else
            return p1.getId().compareTo(p2.getId());
    };

    public void readFile(String fname) {
        try {
            Scanner sc = new Scanner(new File(fname));
            while(sc.hasNext()){
                sc.useDelimiter(";|\n");
                list.add(new Purchase(sc.next(),
                        sc.next(),
                        sc.next(),
                        Double.parseDouble(sc.next()),
                        Double.parseDouble(sc.next())));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showSortedBy(String var) {
        List<Purchase> sortedList = new ArrayList<Purchase>(list);

        switch (var) {
            case "Nazwiska":

                System.out.println(var);

                sortedList.sort(comparatorName);
                for (Purchase p : sortedList) {
                    System.out.println(p);
                }

                System.out.println();
                break;
            case "Koszty":
                System.out.println(var);

                sortedList.sort(comparatorCost);
                for (Purchase p : sortedList) {
                    System.out.println(p + "(koszt: " + p.getPrice()*p.getQuantity() + ")");
                }

                System.out.println();
                break;
            default:
                System.out.println("showSortedBy : ArgumentExeption");
        }

    }

    public void showPurchaseFor(String id) {
        System.out.println("Klient " + id);
        for (Purchase p : list) {
            if (p.getId().equals(id))
                System.out.println(p);
        }
        System.out.println();
    }
}
