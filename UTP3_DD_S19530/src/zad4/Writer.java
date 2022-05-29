/**
 *
 *  @author Diedov Denys S19530
 *
 */

package zad4;


public class Writer implements Runnable {
    Author author;
    public Writer(Author author) {
        this.author = author;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (this.author.getQueue().isEmpty()) {
                    break;
                }
                System.out.println(author.getQueue().take());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
