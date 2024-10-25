package app.pages;
import app.user.User;
import app.user.UserAbstract;
public class HomeFactory implements PageFactory {
    /**
     * Create a new Home page.
     */
    public Page createPage(final UserAbstract userAbstract) {
        return new HomePage((User) userAbstract);
    }
}
