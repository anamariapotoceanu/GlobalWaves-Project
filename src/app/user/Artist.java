package app.user;

import java.util.ArrayList;
import java.util.List;

import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Files.Song;
import app.pages.ArtistPage;
import lombok.Getter;

/**
 * The type Artist.
 */
public final class Artist extends ContentCreator {
    private ArrayList<Album> albums;
    private ArrayList<Merchandise> merch;
    private ArrayList<Event> events;
    public double merchRevenue;
    public double songRevenue;

    @Getter
    public double ranking;
    public String mostProfitableSong;

    @Getter
    private ArrayList<Merchandise> buyedMerch;

    @Getter
    public double sales;

    /**
     *  Returns the list of subscribers for an artist.
     * @return the list of subscribers.
     */
    public ArrayList<UserAbstract> getSubscribedUsers() {
        if (subscribedUsers == null) {
            subscribedUsers = new ArrayList<>();
        }
        return subscribedUsers;
    }
    public void setRanking(final double ranking) {
        this.ranking = ranking;
    }

    private ArrayList<UserAbstract> subscribedUsers;

    /**
     * Instantiates a new Artist.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
        buyedMerch = new ArrayList<>();
        songRevenue = 0;
        sales = 0;
        mostProfitableSong = "N/A";

        super.setPage(new ArtistPage(this));
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * Gets merch.
     *
     * @return the merch
     */
    public ArrayList<Merchandise> getMerch() {
        return merch;
    }

    /**
     * Gets events.
     *
     * @return the events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Gets event.
     *
     * @param eventName the event name
     * @return the event
     */
    public Event getEvent(final String eventName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }

        return null;
    }

    /**
     * Gets album.
     *
     * @param albumName the album name
     * @return the album
     */
    public Album getAlbum(final String albumName) {
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }

        return null;
    }

    /**
     * Gets all songs.
     *
     * @return the all songs
     */
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        albums.forEach(album -> songs.addAll(album.getSongs()));

        return songs;
    }

    /**
     * Show albums array list.
     *
     * @return the array list
     */
    public ArrayList<AlbumOutput> showAlbums() {
        ArrayList<AlbumOutput> albumOutput = new ArrayList<>();
        for (Album album : albums) {
            albumOutput.add(new AlbumOutput(album));
        }

        return albumOutput;
    }

    /**
     * Get user type
     *
     * @return user type string
     */
    public String userType() {
        return "artist";
    }
    /**
     * Adds revenue to the total merchandise revenue of the artist.
     *
     * @param revenue The revenue.
     */
    public void addMerchRevenue(final double revenue) {
        this.merchRevenue += revenue;
    }

    /**
     * Gets the merchandise.
     * @param merchName The name of merchandise.
     * @return Merchandise
     */
    public Merchandise getMerchandise(final String merchName) {
        return merch.stream()
                .filter(merchandise -> merchandise.getName().equals(merchName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Increase the sales.
     */
    public void sales() {
        sales++;
    }
    public double getTotalRevenue() {
        return this.songRevenue + this.merchRevenue;
    }
}
