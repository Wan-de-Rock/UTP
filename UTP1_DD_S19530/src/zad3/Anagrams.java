/**
 *
 *  @author Diedov Denys S19530
 *
 */

package zad3;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Comparator;

public class Anagrams {

    Comparator<List<String>> listComparator = (p1, p2) -> {

        if (p1.size() > p2.size())
            return -1;

        else if (p1.size() < p2.size())
            return 1;

        else
            return p1.get(0).compareTo(p2.get(0));
    };

    private List<String> list = new ArrayList<String>();
    private List<List<String>> anagrams = new ArrayList<List<String>>();

    public Anagrams(String allWords) {
        try {
            Scanner sc = new Scanner(new File(allWords));
            while(sc.hasNext()){
                list.add(sc.next());
                //listCopy.add(sc.next());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static boolean isAnagram(String s1, String s2){
        if (s1.length() != s2.length())
            return false;

        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();

        Arrays.sort(c1);
        Arrays.sort(c2);

        for(int i = 0; i < c1.length; i++) {
            if(c1[i] != c2[i])
                return false;
        }

        return true;
    }


    public List<List<String>> getSortedByAnQty() {

        List<String> listCopy = new ArrayList<String>(list);

        while (!listCopy.isEmpty()){
            anagrams.add(new ArrayList<String>());
            anagrams.get(anagrams.size()-1).add(listCopy.get(0));
            for (int k = 1; k < listCopy.size(); k++) {
                if (isAnagram(listCopy.get(0), listCopy.get(k))){
                    anagrams.get(anagrams.size()-1).add(listCopy.get(k));
                    listCopy.remove(k);
                    k--;
                }
            }
            listCopy.remove(0);
        }
        anagrams.sort(listComparator);
        return anagrams;
    }

    public String getAnagramsFor(String word) {
        String result = word + ": ";
        List<String> selection = new ArrayList<String>();

        for (int i = 0; i < anagrams.size(); i++) {
            if (anagrams.get(i).contains(word)){

                if (anagrams.get(i).size() == 1)
                    return result += selection.toString();

                for (String s : anagrams.get(i)) {
                    if (!s.equals(word))
                        selection.add(s);
                }

            }
        }

        if (selection.isEmpty())
            return result += "null";
        else
            return result += selection.toString();
    }
}  
