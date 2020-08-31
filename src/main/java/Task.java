public class Task {
    protected String description;
    protected boolean isDone;

    public Task (String description){
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getDone(){
        return isDone;
    }

    public void setDone(boolean isDone){
        this.isDone = isDone;
    }

    public String getStatusIcon(){
        return "[" + (isDone ? "\u2713" : "\u2718") + "]";
    }

    public String getTypeIcon() {
        return null;
    }

    @Override
    public String toString(){
        return getTypeIcon() + getStatusIcon() + description;
    }
}
