/**
 *
 *  @author Diedov Denys S19530
 *
 */

package zad4;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Author implements Runnable {
    private ArrayList<String> words;
    private BlockingQueue<String> queue;

    public Author(String[] args) {
        this.words = new ArrayList<>();
        this.queue = new LinkedBlockingQueue<>();
        String[] arg;
        for (String argument:args) {
            arg = argument.split(" ");
            words.addAll(Arrays.asList(arg));
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < words.size(); i++) {
            try {
                queue.put(words.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }
}
