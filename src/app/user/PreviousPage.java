package app.user;
import fileio.input.CommandInput;
import app.Admin;

/**
 * The user will navigate to the previous page.
 */
public class PreviousPage implements Command {
    private Admin admin;

    public PreviousPage(final Admin admin) {
        this.admin = admin;
    }

    @Override
    public final String execute(final CommandInput commandInput) {
        return admin.previousPage(commandInput);
    }
}
