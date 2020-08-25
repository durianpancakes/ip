public class Task {
    protected String title;
    protected boolean isDone;

    public Task (String taskTitle){
        this.title = taskTitle;
        isDone = false;
    }

    public Task (String title, boolean isDone){
        this.title = title;
        this.isDone = isDone;
    }

    public String getTaskTitle() {
        return title;
    }

    public void setTaskTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getStatusIcon(){
        return (isDone ? "\u2713" : "\u2718");
    }
}