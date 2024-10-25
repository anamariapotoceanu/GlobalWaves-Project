package app.pages;
import app.user.UserAbstract;

public interface PageFactory {
    /**
     * Create a new Page.
     */
    Page createPage(UserAbstract userAbstract);

}
