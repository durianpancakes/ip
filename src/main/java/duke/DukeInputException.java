package duke;

public class DukeInputException extends Exception {
    String error;

    public DukeInputException(String error) {
        this.error = error;
    }
}
