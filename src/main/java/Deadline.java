import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class for Deadline, a child class of Task.
 * @author Liew Jian Hong
 */

public class Deadline extends Task{
    /**
     * The date and time of the deadline.
     */
    protected LocalDate date;

    /**
     * Constructor for a Deadline task.
     * @param desc String array consisting of parsed description.
     */
    public Deadline(String[] desc) {

        super(desc[1], false);
        this.date = LocalDate.parse(desc[2]);

    }

    @Override
    public String toWrite() {
        return "deadline--" + Boolean.toString(this.isDone) + "--" + this.desc + "--" + this.date.toString() +"\n";
    }

    /**
     * Return a string representation of the deadline.
     * @return Return the type, completion status and description of the deadline.
     */
    @Override
    public String toString() {
        String dateString = this.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        return "[D]" + super.toString() + " (by: " + dateString + ")\n";
    }
}
