package app.user;
import fileio.input.CommandInput;

public interface Command {
    /**
     * @param commandInput The commandInput
     * @return A String representing the result.
     */
    String execute(CommandInput commandInput);
}
