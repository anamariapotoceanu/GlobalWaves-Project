package app.pages;
import app.user.Artist;
import app.user.UserAbstract;

public class ArtistFactory implements PageFactory {
    /**
     *  Create a new Artist page.
     */
    public Page createPage(final UserAbstract userAbstract) {
        return new ArtistPage((Artist) userAbstract);
    }

}
