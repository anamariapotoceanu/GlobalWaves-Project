package app.pages;

import app.user.User;
import app.user.UserAbstract;

public class LikedContentFactory implements PageFactory {
    /**
     * Create a new LikedContent page.
     */
    public Page createPage(final UserAbstract userAbstract) {
        return new LikedContentPage((User) userAbstract);
    }
}
