package app.user;
import fileio.input.CommandInput;
import app.Admin;

/**
 * The user will navigate to the next page.
 */
public class NextPage implements Command {
    private Admin admin;

    public NextPage(final Admin admin) {
        this.admin = admin;
    }

    @Override
    public final String execute(final CommandInput commandInput) {
        return admin.nextPage(commandInput);
    }
}
