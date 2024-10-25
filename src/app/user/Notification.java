package app.user;

public class Notification {
    private String name;
    private String description;

    /**
     * Instantiates a new Notification.
     *
     * @param name        the name
     * @param description the description
     */
    public Notification(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
