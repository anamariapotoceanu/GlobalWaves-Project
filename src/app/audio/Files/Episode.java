package app.audio.Files;

import lombok.Getter;

@Getter
public final class Episode extends AudioFile {
    private final String description;
    private Integer listeners;
    public Episode(final String name, final Integer duration, final String description) {
        super(name, duration);
        this.description = description;
        this.listeners = 0;
    }

    /**
     * Increase the number of listeners.
     */
    public void listernesNr() {
        this.listeners++;
    }

    /**
     * Reset the number of listeners.
     */
    public void resetListeners() {
        this.listeners = 0;
    }
}
