package app.user;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User abstract.
 */
public abstract class UserAbstract {
    private String username;
    private int age;
    private String city;
    public final void setNotifications(final List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Getter
    private List<Notification> notifications = new ArrayList<>();

    /**
     * Instantiates a new User abstract.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public UserAbstract(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * User type string.
     *
     * @return the string
     */
    public abstract String userType();

    /**
     * Clear the notifications.
     */
    public final void clearNotifications() {
        notifications.clear();
    }
    public final void updateNotifications() {
    }
}
