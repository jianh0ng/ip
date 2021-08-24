import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class to keep track of Tasks.
 */
public class Duke {

    /**
     * TaskList containing Tasks.
     */
    private TaskList tl;
    private Storage storage;
    private Ui ui;
    private Parser parser;

    /**
     * Constructor for a Duke.
     */
    public Duke() {
        this.storage = new Storage();
        this.tl = this.storage.load();
        this.ui = new Ui();
        this.parser = new Parser();
    }

    /**
     * Exit the Duke program.
     */
    private void exit() {
        this.ui.showExitMessage();
        System.exit(0);
    }

    /**
     * Adds a task for Duke to track given a parsed command.
     * @param t String array of parsed user input.
     * @throws DukeException Exception for wrong user inputs.
     */
    private void add(String[] t) throws DukeException{
        Task newTask = null;
        switch (t[0]) {
            case "todo":
                if (t[1].equals("")) {
                    throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
                } else {
                    newTask = new Todo(t);
                }
                break;
            case "deadline":
                if (t[1].equals("") || t[2].equals("")) {
                    throw new DukeException("☹ OOPS!!! The description or deadline of a deadline cannot be empty.");
                } else {
                    newTask = new Deadline(t);
                }
                break;
            case "event":
                if (t[1].equals("") || t[2].equals("")) {
                    throw new DukeException("☹ OOPS!!! The description or scheduled time of an event cannot be empty.");
                } else {
                    newTask = new Event(t);
                }
                break;
        }
        this.tl.addTask(newTask);
        this.storage.write(this.tl);
        this.ui.showAddMessage(newTask, this.tl);
    }

    /**
     * Gets the task from duke given an index.
     * @param index Index of Task.
     * @return Task of the given index.
     */
    private Task getTaskByIndex(int index) {
        return this.tl.getTaskByIndex(index);
    }

    /**
     * Marks a task as done given its position in the list.
     * @param itemNum Position of Task in the list.
     */
    private void markDone(int itemNum){
        this.tl.markDone(itemNum);
        this.storage.write(this.tl);
        this.ui.showMarkDoneMessage(this.getTaskByIndex(itemNum - 1));
    }

    /**
     * Deletes a task from the list.
     * @param items Parsed delete command from user.
     * @throws DukeException Exception for wrong user inputs.
     */
    private void deleteTask(String[] items) throws DukeException{
        if (items[1].equals("")) {
            throw new DukeException("☹ OOPS!!! The task's number cannot be empty");
        } else {
            int itemNum = Integer.parseInt(items[1]);
            Task toBeDeleted = this.getTaskByIndex(itemNum - 1);
            String taskName = toBeDeleted.toString();
            this.tl.deleteTask(itemNum - 1);
            this.storage.write(this.tl);
            this.ui.showDeleteMessage(toBeDeleted, this.tl);
        }
    }

    /**
     * Start Duke to allow for inputs.
     */
    private void run() {
        this.ui.showGreetMessage();
        this.storage.load();
        boolean isExit = false;
        String userInput;
        while (true) {
            userInput = this.ui.getUserInput();
            String[] items = this.parser.parse(userInput);

            try {
                switch (items[0]) {
                case "bye":
                    this.exit();
                    break;
                case "list":
                    this.ui.showTaskList(this.tl);
                    break;
                case "done":
                    this.markDone(Integer.parseInt(items[1]));
                    break;
                case "todo":
                    //Fallthrough
                case "deadline":
                    //Fallthrough
                case "event":
                    this.add(items);
                    break;
                case "delete":
                    this.deleteTask(items);
                    break;
                default:
                    throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public static void main(String[] args){
        Duke duke = new Duke();
        duke.run();
    }
}
