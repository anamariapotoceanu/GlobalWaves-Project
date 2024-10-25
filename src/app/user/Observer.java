package app.user;

public interface Observer {
    /**
     * Update the notification.
     * @param notification The notification
     */
    void update(Notification notification);

}
