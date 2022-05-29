package zad2;

public class StringTask implements Runnable {

    private String data;
    private String result = "";
    private int countOfIterations;
    private TaskState state = TaskState.CREATED;
    private boolean isDone = false;


    public StringTask(String str, int count) {
        data = str;
        countOfIterations = count;
    }

    public TaskState getState() {
        return state;
    }

    public void start() {
        new Thread(this).start();
        state = TaskState.READY;
    }

    public void abort() {
        state = TaskState.ABORTED;
        Thread.currentThread().interrupt();
    }

    public boolean isDone() {
        if (state == TaskState.ABORTED || isDone)
            return true;
        else
            return false;
    }

    public String getResult() {
        return result;
    }

    @Override
    public void run() {
        state = TaskState.RUNNING;

        for (int i = 0; i < countOfIterations; i++) {
            if (state == TaskState.ABORTED) {
                isDone = true;
                return;
            }
            result += data;
        }
        state = TaskState.READY;
        isDone = true;
    }
}
