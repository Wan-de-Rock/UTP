/**
 *
 *  @author Diedov Denys S19530
 *
 */

package zad2;


public class Main {
  public static void main(String[] args) throws InterruptedException {
    StringTask task = new StringTask("A", 70000);
    System.out.println("Task " + task.getState());
    task.start();
    if (args.length > 0 && args[0].equals("abort")) {
      Runnable timer = () -> {
        try {
          Thread.currentThread().sleep(1000);
          task.abort();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      };

      Thread thread = new Thread(timer);
      thread.start();
    }
    while (!task.isDone()) {
      Thread.sleep(500);
      switch(task.getState()) {
        case RUNNING: System.out.print("R."); break;
        case ABORTED: System.out.println(" ... aborted."); break;
        case READY: System.out.println(" ... ready."); break;
        default: System.out.println("unknown state");
      }

    }
    System.out.println("Task " + task.getState());
    System.out.println(task.getResult().length());
  }
}
