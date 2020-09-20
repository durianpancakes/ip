package duke.data.task;

public class Todo extends Task {
    public Todo(String taskTitle) {
        super(taskTitle);
    }

    public Todo(String taskTitle, boolean isDone) {
        super(taskTitle);
        this.isDone = isDone;
    }

    @Override
    public String getTypeIcon(){
        return "[T]";
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
