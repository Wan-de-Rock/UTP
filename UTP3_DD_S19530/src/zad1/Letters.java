package zad1;

public class Letters {
    private String[] args;
    private Thread[] pool;
    private boolean isInterrupted = false;

    public Letters(String str) {
        args = str.split("");
        pool = new Thread[args.length];

        for (int i = 0; i < args.length; i++) {
            //pool[i] = new Thread("Thread " + args[i]);
            int finalI = i;
            Runnable runnable = () -> {
                String data = args[finalI];
                try {
                    while (!isInterrupted){
                        System.out.print(data);
                        Thread.currentThread().sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            pool[i] = new Thread(runnable, "Thread " + args[i]);
        }

    }

    public Thread[] getThreads() {
        return pool;
    }
    public void abort(){
        isInterrupted = true;
    }
}
