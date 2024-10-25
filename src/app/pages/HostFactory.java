package app.pages;
import app.user.Host;
import app.user.UserAbstract;

public class HostFactory implements PageFactory {
    /**
     * Create a new Host page.
     */
    public Page createPage(final UserAbstract userAbstract) {
        return new HostPage((Host) userAbstract);
    }
}
