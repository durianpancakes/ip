package duke.task;

public class Todo extends Task {
    public Todo(String taskTitle) {
        super(taskTitle);
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
