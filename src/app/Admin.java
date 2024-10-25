package app;

import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.pages.ArtistFactory;
import app.pages.ArtistPage;
import app.pages.HomeFactory;
import app.pages.HostFactory;
import app.pages.HostPage;
import app.pages.LikedContentFactory;
import app.pages.Page;
import app.pages.PageFactory;
import app.player.Player;
import app.user.Announcement;
import app.user.Artist;
import app.user.Event;
import app.user.Host;
import app.user.Merchandise;
import app.user.Notification;
import app.user.Observer;
import app.user.User;
import app.user.UserAbstract;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Random;

/**
 * The type Admin.
 */
public final class Admin implements Observer {
    private List<UserAbstract> usersAdmin = new ArrayList<>();

    /**
     * Adds a user to the list.
     *
     * @param user The user.
     */
    public void addObserver(final UserAbstract user) {
        usersAdmin.add(user);
    }
    /**
     *  Informs the observer about a new notification.
     *
     * @param notification The notification.
     */
    @Override
    public void update(final Notification notification) {
        System.out.println("New notification: " + notification.getDescription());
    }
    /**
     * Informs all users about a new notification.
     *
     * @param notification The notification.
     */
    public void notifyUsers(final Notification notification) {
        for (UserAbstract user : users) {
            user.updateNotifications();
        }
    }

    @Getter
    private List<User> users = new ArrayList<>();
    @Getter
    private List<Artist> artists = new ArrayList<>();
    @Getter
    private List<Host> hosts = new ArrayList<>();
    private List<Song> songs = new ArrayList<>();
    private List<Podcast> podcasts = new ArrayList<>();
    private int timestamp = 0;
    private final int limit = 5;
    private final int dateStringLength = 10;
    private final int dateFormatSize = 3;
    private final int dateYearLowerLimit = 1900;
    private final int dateYearHigherLimit = 2023;
    private final int dateMonthLowerLimit = 1;
    private final int dateMonthHigherLimit = 12;
    private final int dateDayLowerLimit = 1;
    private final int dateDayHigherLimit = 31;
    private final int dateFebHigherLimit = 28;

    public  List<Artist> getArtistsAux() {
        return artistsAux;
    }

    @Getter
    private static List<Artist> artistsAux = new ArrayList<>();

    public void setNotifications(final List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Getter
    private List<Notification> notifications = new ArrayList<>();
    private static Admin instance;

    private Admin() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    /**
     * Reset instance.
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public void setUsers(final List<UserInput> userInputList) {
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public void setSongs(final List<SongInput> songInputList) {
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public void setPodcasts(final List<PodcastInput> podcastInputList) {
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                                         episodeInput.getDuration(),
                                         episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public List<Playlist> getPlaylists() {
        return users.stream()
                    .flatMap(user -> user.getPlaylists().stream())
                    .collect(Collectors.toList());
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public List<Album> getAlbums() {
        return artists.stream()
                      .flatMap(artist -> artist.getAlbums().stream())
                      .collect(Collectors.toList());
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List<String> getAllUsers() {
        List<String> allUsers = new ArrayList<>();

        allUsers.addAll(users.stream().map(UserAbstract::getUsername).toList());
        allUsers.addAll(artists.stream().map(UserAbstract::getUsername).toList());
        allUsers.addAll(hosts.stream().map(UserAbstract::getUsername).toList());

        return allUsers;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public User getUser(final String username) {
        return users.stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
    }

    /**
     * Gets artist.
     *
     * @param username the username
     * @return the artist
     */
    public Artist getArtist(final String username) {
        return artists.stream()
                .filter(artist -> artist.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets host.
     *
     * @param username the username
     * @return the host
     */
    public Host getHost(final String username) {
        return hosts.stream()
                .filter(artist -> artist.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;

        if (elapsed == 0) {
            return;
        } else if (elapsed < 0) {
            throw new IllegalArgumentException("Invalid timestamp" + newTimestamp);
        }

        users.forEach(user -> user.simulateTime(elapsed));
    }

    /**
     * Get the user.
     * @param username The username
     * @return the abstract user
     */
public UserAbstract getAbstractUser(final String username) {
    ArrayList<UserAbstract> allUsers = new ArrayList<>();

    allUsers.addAll(users);
    allUsers.addAll(artists);
    allUsers.addAll(hosts);

    return allUsers.stream()
            .filter(userPlatform -> userPlatform.getUsername().equals(username))
            .findFirst()
            .orElse(null);
}

    /**
     * Add new user string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addNewUser(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String type = commandInput.getType();
        int age = commandInput.getAge();
        String city = commandInput.getCity();

        UserAbstract currentUser = getAbstractUser(username);
        if (currentUser != null) {
            return "The username %s is already taken.".formatted(username);
        }
        UserAbstract newUser;
        if (type.equals("user")) {
            newUser = new User(username, age, city);
            users.add(new User(username, age, city));
        } else if (type.equals("artist")) {
            newUser = new Artist(username, age, city);
            artists.add(new Artist(username, age, city));
        } else {
            newUser = new Host(username, age, city);
            hosts.add(new Host(username, age, city));
        }
        Admin admin = Admin.getInstance();
        admin.addObserver(newUser);
        return "The username %s has been added successfully.".formatted(username);
    }

    /**
     * Delete user string.
     *
     * @param username the username
     * @return the string
     */
    public String deleteUser(final String username) {
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        }

        if (currentUser.userType().equals("user")) {
            return deleteNormalUser((User) currentUser);
        }

        if (currentUser.userType().equals("host")) {
            return deleteHost((Host) currentUser);
        }

        return deleteArtist((Artist) currentUser);
    }

    private String deleteNormalUser(final User user) {
        if (user.getPlaylists().stream().anyMatch(playlist -> users.stream().map(User::getPlayer)
                .filter(player -> player != user.getPlayer())
                .map(Player::getCurrentAudioCollection)
                .filter(Objects::nonNull)
                .anyMatch(collection -> collection == playlist))) {
            return "%s can't be deleted.".formatted(user.getUsername());
        }

        user.getLikedSongs().forEach(Song::dislike);
        user.getFollowedPlaylists().forEach(Playlist::decreaseFollowers);

        users.stream().filter(otherUser -> otherUser != user)
             .forEach(otherUser -> otherUser.getFollowedPlaylists()
                                            .removeAll(user.getPlaylists()));

        users.remove(user);
        return "%s was successfully deleted.".formatted(user.getUsername());
    }

    private String deleteHost(final Host host) {
        if (host.getPodcasts().stream().anyMatch(podcast -> getAudioCollectionsStream()
                .anyMatch(collection -> collection == podcast))
                || users.stream().anyMatch(user -> user.getCurrentPage() == host.getPage())) {
            return "%s can't be deleted.".formatted(host.getUsername());
        }

        host.getPodcasts().forEach(podcast -> podcasts.remove(podcast));
        hosts.remove(host);

        return "%s was successfully deleted.".formatted(host.getUsername());
    }

    private String deleteArtist(final Artist artist) {
        if (artist.getAlbums().stream().anyMatch(album -> album.getSongs().stream()
            .anyMatch(song -> getAudioFilesStream().anyMatch(audioFile -> audioFile == song))
            || getAudioCollectionsStream().anyMatch(collection -> collection == album))
            || users.stream().anyMatch(user -> user.getCurrentPage() == artist.getPage())) {
            return "%s can't be deleted.".formatted(artist.getUsername());
        }

        users.forEach(user -> artist.getAlbums().forEach(album -> album.getSongs().forEach(song -> {
            user.getLikedSongs().remove(song);
            user.getPlaylists().forEach(playlist -> playlist.removeSong(song));
        })));

        songs.removeAll(artist.getAllSongs());
        artists.remove(artist);
        return "%s was successfully deleted.".formatted(artist.getUsername());
    }

    /**
     * Add album string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addAlbum(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String albumName = commandInput.getName();
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("artist")) {
            return "%s is not an artist.".formatted(username);
        }

        Artist currentArtist = (Artist) currentUser;
        if (currentArtist.getAlbums().stream()
            .anyMatch(album -> album.getName().equals(albumName))) {
            return "%s has another album with the same name.".formatted(username);
        }

        List<Song> newSongs = commandInput.getSongs().stream()
                                       .map(songInput -> new Song(songInput.getName(),
                                                                  songInput.getDuration(),
                                                                  albumName,
                                                                  songInput.getTags(),
                                                                  songInput.getLyrics(),
                                                                  songInput.getGenre(),
                                                                  songInput.getReleaseYear(),
                                                                  currentArtist.getUsername()))
                                       .toList();

        Set<String> songNames = new HashSet<>();
        if (!newSongs.stream().filter(song -> !songNames.add(song.getName()))
                  .collect(Collectors.toSet()).isEmpty()) {
            return "%s has the same song at least twice in this album.".formatted(username);
        }

        songs.addAll(newSongs);
        currentArtist.getAlbums().add(new Album(albumName,
                                                commandInput.getDescription(),
                                                username,
                                                newSongs,
                                                commandInput.getReleaseYear()));

        for (UserAbstract userAbstract : currentArtist.getSubscribedUsers()) {
            String description = null;
            description = "New Album from " + currentArtist.getUsername() + ".";
            userAbstract.getNotifications().add(new Notification("New Album", description));
        }
        String description = null;
        description = "New Album from " + currentArtist.getUsername() + ".";
        notifyUsers(new Notification("New Album", description));
        return "%s has added new album successfully.".formatted(username);
    }

    /**
     * Remove album string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String removeAlbum(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String albumName = commandInput.getName();

        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("artist")) {
            return "%s is not an artist.".formatted(username);
        }

        Artist currentArtist = (Artist) currentUser;
        Album searchedAlbum = currentArtist.getAlbum(albumName);
        if (searchedAlbum == null) {
            return "%s doesn't have an album with the given name.".formatted(username);
        }

        if (getAudioCollectionsStream().anyMatch(collection -> collection == searchedAlbum)) {
            return "%s can't delete this album.".formatted(username);
        }

        for (Song song : searchedAlbum.getSongs()) {
            if (getAudioCollectionsStream().anyMatch(collection -> collection.containsTrack(song))
                || getAudioFilesStream().anyMatch(audioFile -> audioFile == song)) {
                return "%s can't delete this album.".formatted(username);
            }
        }

        for (Song song: searchedAlbum.getSongs()) {
            users.forEach(user -> {
                user.getLikedSongs().remove(song);
                user.getPlaylists().forEach(playlist -> playlist.removeSong(song));
            });
            songs.remove(song);
        }

        currentArtist.getAlbums().remove(searchedAlbum);
        return "%s deleted the album successfully.".formatted(username);
    }

    /**
     * Add podcast string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addPodcast(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String podcastName = commandInput.getName();
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("host")) {
            return "%s is not a host.".formatted(username);
        }

        Host currentHost = (Host) currentUser;
        if (currentHost.getPodcasts().stream()
            .anyMatch(podcast -> podcast.getName().equals(podcastName))) {
            return "%s has another podcast with the same name.".formatted(username);
        }

        List<Episode> episodes = commandInput.getEpisodes().stream()
                                             .map(episodeInput ->
                                                     new Episode(episodeInput.getName(),
                                                                 episodeInput.getDuration(),
                                                                 episodeInput.getDescription()))
                                             .collect(Collectors.toList());

        Set<String> episodeNames = new HashSet<>();
        if (!episodes.stream().filter(episode -> !episodeNames.add(episode.getName()))
                     .collect(Collectors.toSet()).isEmpty()) {
            return "%s has the same episode in this podcast.".formatted(username);
        }

        Podcast newPodcast = new Podcast(podcastName, username, episodes);
        currentHost.getPodcasts().add(newPodcast);
        podcasts.add(newPodcast);

        return "%s has added new podcast successfully.".formatted(username);
    }


    /**
     * Remove podcast string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String removePodcast(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String podcastName = commandInput.getName();
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("host")) {
            return "%s is not a host.".formatted(username);
        }

        Host currentHost = (Host) currentUser;
        Podcast searchedPodcast = currentHost.getPodcast(podcastName);

        if (searchedPodcast == null) {
            return "%s doesn't have a podcast with the given name.".formatted(username);
        }

        if (getAudioCollectionsStream().anyMatch(collection -> collection == searchedPodcast)) {
            return "%s can't delete this podcast.".formatted(username);
        }

        currentHost.getPodcasts().remove(searchedPodcast);
        podcasts.remove(searchedPodcast);
        return "%s deleted the podcast successfully.".formatted(username);
    }

    /**
     * Add event string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addEvent(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String eventName = commandInput.getName();

        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("artist")) {
            return "%s is not an artist.".formatted(username);
        }

        Artist currentArtist = (Artist) currentUser;
        if (currentArtist.getEvent(eventName) != null) {
            return "%s has another event with the same name.".formatted(username);
        }

        String date = commandInput.getDate();

        if (!checkDate(date)) {
            return "Event for %s does not have a valid date.".formatted(username);
        }

        currentArtist.getEvents().add(new Event(eventName,
                                                commandInput.getDescription(),
                                                commandInput.getDate()));
        for (UserAbstract userAbstract : currentArtist.getSubscribedUsers()) {
            String description = null;
            description = "New Event from " + currentArtist.getUsername() + ".";
            userAbstract.getNotifications().add(new Notification("New Event", description));
        }
        String description = null;
        description = "New Event from " + currentArtist.getUsername() + ".";
        notifyUsers(new Notification("New Event", description));
        return "%s has added new event successfully.".formatted(username);
    }

    /**
     * Remove event string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String removeEvent(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String eventName = commandInput.getName();

        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("artist")) {
            return "%s is not an artist.".formatted(username);
        }

        Artist currentArtist = (Artist) currentUser;
        Event searchedEvent = currentArtist.getEvent(eventName);
        if (searchedEvent == null) {
            return "%s doesn't have an event with the given name.".formatted(username);
        }

        currentArtist.getEvents().remove(searchedEvent);
        return "%s deleted the event successfully.".formatted(username);
    }

    private boolean checkDate(final String date) {
        if (date.length() != dateStringLength) {
            return false;
        }

        List<String> dateElements = Arrays.stream(date.split("-", dateFormatSize)).toList();

        if (dateElements.size() != dateFormatSize) {
            return false;
        }

        int day = Integer.parseInt(dateElements.get(0));
        int month = Integer.parseInt(dateElements.get(1));
        int year = Integer.parseInt(dateElements.get(2));

        if (day < dateDayLowerLimit
            || (month == 2 && day > dateFebHigherLimit)
            || day > dateDayHigherLimit
            || month < dateMonthLowerLimit || month > dateMonthHigherLimit
            || year < dateYearLowerLimit || year > dateYearHigherLimit) {
            return false;
        }

        return true;
    }

    /**
     * Add merch string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addMerch(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("artist")) {
            return "%s is not an artist.".formatted(username);
        }

        Artist currentArtist = (Artist) currentUser;
        if (currentArtist.getMerch().stream()
                         .anyMatch(merch -> merch.getName().equals(commandInput.getName()))) {
            return "%s has merchandise with the same name.".formatted(currentArtist.getUsername());
        } else if (commandInput.getPrice() < 0) {
            return "Price for merchandise can not be negative.";
        }

        currentArtist.getMerch().add(new Merchandise(commandInput.getName(),
                                                     commandInput.getDescription(),
                                                     commandInput.getPrice()));
        for (UserAbstract userAbstract : currentArtist.getSubscribedUsers()) {
            String description = null;
            description = "New Merchandise from " + currentArtist.getUsername() + ".";
            userAbstract.getNotifications().add(new Notification("New Merchandise", description));
        }
        String description = null;
        description = "New Merchandise from " + currentArtist.getUsername() + ".";
        notifyUsers(new Notification("New Merchandise", description));
        return "%s has added new merchandise successfully.".formatted(username);
    }

    /**
     * Add announcement string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String addAnnouncement(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String announcementName = commandInput.getName();
        String announcementDescription = commandInput.getDescription();

        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("host")) {
            return "%s is not a host.".formatted(username);
        }

        Host currentHost = (Host) currentUser;
        Announcement searchedAnnouncement = currentHost.getAnnouncement(announcementName);
        if (searchedAnnouncement != null) {
            return "%s has already added an announcement with this name.";
        }

        currentHost.getAnnouncements().add(new Announcement(announcementName,
                                                            announcementDescription));
        return "%s has successfully added new announcement.".formatted(username);
    }

    /**
     * Remove announcement string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String removeAnnouncement(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String announcementName = commandInput.getName();

        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("host")) {
            return "%s is not a host.".formatted(username);
        }

        Host currentHost = (Host) currentUser;
        Announcement searchAnnouncement = currentHost.getAnnouncement(announcementName);
        if (searchAnnouncement == null) {
            return "%s has no announcement with the given name.".formatted(username);
        }

        currentHost.getAnnouncements().remove(searchAnnouncement);
        return "%s has successfully deleted the announcement.".formatted(username);
    }

    /**
     * Change page string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String changePage(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String nextPage = commandInput.getNextPage();

        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("user")) {
            return "%s is not a normal user.".formatted(username);
        }

        User user = (User) currentUser;
        Artist currentArtist = null;
        Host currentHost = null;

        for (Host host : hosts) {
            if (user.getPlayer().getSource() != null) {
                AudioCollection audioCollection = user.getPlayer().getSource().getAudioCollection();
                if (audioCollection != null && host.getPodcasts().stream()
                        .anyMatch(podcast -> podcast.getName().equals(audioCollection.getName()))) {
                    currentHost = host;
                }
            }
        }
        if (currentHost != null) {
            user.setHostPage(new HostPage(currentHost));
        }
        for (Artist artist : artists) {
            if (user.getPlayer().getSource() != null) {
                AudioFile audioFile = user.getPlayer().getSource().getAudioFile();
                if (audioFile != null && artist.getAllSongs().stream()
                        .anyMatch(song -> song.getName()
                                .equals(user.getPlayer().getSource().getAudioFile().getName()))) {
                    currentArtist = artist;
                }
            }
        }
        if (currentArtist != null) {
            user.setArtistPage(new ArtistPage(currentArtist));
        }
        if (!user.isStatus()) {
            return "%s is offline.".formatted(user.getUsername());
        }
        user.setPreviousPage(user.getCurrentPage());
        PageFactory pageFactory = null;
        switch (nextPage) {
            case "Home" -> {
                pageFactory = new HomeFactory();
                Page currentPage = pageFactory.createPage(user);
                user.setCurrentPage(currentPage);
            }
            case "LikedContent" -> {
                pageFactory = new LikedContentFactory();
                Page currentPage = pageFactory.createPage(user);
                user.setCurrentPage(currentPage);
            }
            case "Artist" -> {
                pageFactory = new ArtistFactory();
                Page currentPage = pageFactory.createPage(currentArtist);
                user.setCurrentPage(currentPage);
            }
            case "Host" -> {
                pageFactory = new HostFactory();
                Page currentPage = pageFactory.createPage(currentHost);
                user.setCurrentPage(currentPage);
            }
            default -> {
                return "%s is trying to access a non-existent page.".formatted(username);
            }
        }
        Page currentPage = user.getCurrentPage();
        if (!user.getHistoryPageAux().contains(currentPage)) {
            user.getHistoryPageAux().add(currentPage);
        }
        if (!user.getHistoryPage().contains(currentPage)) {
            user.getHistoryPage().add(currentPage);
        }
        return "%s accessed %s successfully.".formatted(username, nextPage);
    }

    /**
     * Print current page string.
     *
     * @param commandInput the command input
     * @return the string
     */
    public String printCurrentPage(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("user")) {
            return "%s is not a normal user.".formatted(username);
        }

        User user = (User) currentUser;
        if (!user.isStatus()) {
            return "%s is offline.".formatted(user.getUsername());
        }
        if (user.getCurrentPage() != null) {
            return user.getCurrentPage().printCurrentPage();
        }
        return null;
    }

    /**
     * Switch status string.
     *
     * @param username the username
     * @return the string
     */
    public String switchStatus(final String username) {
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        }

        if (currentUser.userType().equals("user")) {
            ((User) currentUser).switchStatus();
            return username + " has changed status successfully.";
        } else {
            return username + " is not a normal user.";
        }
    }

    /**
     * Gets online users.
     *
     * @return the online users
     */
    public List<String> getOnlineUsers() {
        return users.stream().filter(User::isStatus).map(User::getUsername).toList();
    }

    private Stream<AudioCollection> getAudioCollectionsStream() {
        return users.stream().map(User::getPlayer)
                    .map(Player::getCurrentAudioCollection).filter(Objects::nonNull);
    }

    private Stream<AudioFile> getAudioFilesStream() {
        return users.stream().map(User::getPlayer)
                    .map(Player::getCurrentAudioFile).filter(Objects::nonNull);
    }

    /**
     * Gets top 5 album list.
     *
     * @return the top 5 album list
     */
    public List<String> getTop5AlbumList() {
        List<Album> albums = artists.stream().map(Artist::getAlbums)
                                    .flatMap(List::stream).toList();

        final Map<Album, Integer> albumLikes = new HashMap<>();
        albums.forEach(album -> albumLikes.put(album, album.getSongs().stream()
                                          .map(Song::getLikes).reduce(0, Integer::sum)));

        return albums.stream().sorted((o1, o2) -> {
            if ((int) albumLikes.get(o1) == albumLikes.get(o2)) {
                return o1.getName().compareTo(o2.getName());
            }
            return albumLikes.get(o2) - albumLikes.get(o1);
        }).limit(limit).map(Album::getName).toList();
    }

    /**
     * Gets top 5 artist list.
     *
     * @return the top 5 artist list
     */
    public List<String> getTop5ArtistList() {
        final Map<Artist, Integer> artistLikes = new HashMap<>();
        artists.forEach(artist -> artistLikes.put(artist, artist.getAllSongs().stream()
                                              .map(Song::getLikes).reduce(0, Integer::sum)));

        return artists.stream().sorted(Comparator.comparingInt(artistLikes::get).reversed())
                               .limit(limit).map(Artist::getUsername).toList();
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= limit) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= limit) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Subscribes or unsubscribes a user from an artist or host.
     * @param commandInput The commandInput
     * @return The message corresponding to the command.
     */
    public String subscribe(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        }

        User user = (User) currentUser;

        for (Artist artist : artists) {
            if (artist.getPage() == user.getCurrentPage()) {
                if (user.getSubscribedArtists().contains(artist)) {
                    user.getSubscribedArtists().remove(artist);
                    artist.getSubscribedUsers().remove(currentUser);
                    return commandInput.getUsername() + " unsubscribed"
                            + " from " + artist.getUsername() + " successfully.";
                } else {
                    user.getSubscribedArtists().add(artist);
                    artist.getSubscribedUsers().add(currentUser);
                    return commandInput.getUsername() + " subscribed"
                            + " to " + artist.getUsername() + " successfully.";
                }
            }
        }

        return "To subscribe you need to be on the page of an artist or host.";
    }

    /**
     * A user purchases merchandise from an artist,
     * and the funds are credited to the artist's account.
     * @param commandInput The CommandInput.
     * @return The message corresponding to the command.
     */
    public String buyMerch(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        }
        Artist currentArtist = null;
        Merchandise currentMerch = null;
        for (Artist artist : artists) {
            if (artist.getMerch().stream()
                    .anyMatch(merch -> merch.getName().equals(commandInput.getName()))) {
                currentArtist = artist;

            }
        }

        if (currentArtist == null) {
            return "The merch " + commandInput.getName() +  " doesn't exist.";
        }
        User user = (User) currentUser;
        if (user.getCurrentPage() != currentArtist.getPage()) {
            return "Cannot buy merch from this page.";
        }
        currentMerch = currentArtist.getMerchandise(commandInput.getName());
        currentArtist.addMerchRevenue(currentMerch.getPrice());
        user.getBuyedMerch().add(currentMerch);
        currentArtist.getBuyedMerch().add(currentMerch);
        currentArtist.sales();
        return "%s has added new merch successfully.".formatted(username);
    }

    /**
     * Displays all items purchased by the user in chronological
     * order based on their purchase time.
     * @param commandInput The commandInput.
     * @return A list containing all merchandise items.
     */
    public List<String> seeMerch(final CommandInput commandInput) {
        List<String> merchandisesName = new ArrayList<>();
        UserAbstract currentUser = getAbstractUser(commandInput.getUsername());
        User user = (User) currentUser;
        List<Merchandise> merchandises = user.getBuyedMerch();
        for (Merchandise merchandise : merchandises) {
            merchandisesName.add(merchandise.getName());
        }
        return merchandisesName;
    }

    /**
     * Adds recommendations for a user, either a random song or a random playlist.
     * @param commandInput The commandInput.
     * @param songs A list containing all songs.
     * @return The message corresponding to the command.
     */
    public String updateRecommendations(final CommandInput commandInput,
                                        final List<Song> songs) {
        String username = commandInput.getUsername();
        UserAbstract currentUser = getAbstractUser(username);

        if (currentUser == null) {
            return "The username %s doesn't exist.".formatted(username);
        } else if (!currentUser.userType().equals("user")) {
            return "%s is not a normal user.".formatted(username);
        }
        switch (commandInput.getRecommendationType()) {
            case "random_song" -> {
                User user = (User) currentUser;
                Song songCurrent = (Song) user.getPlayer().getCurrentAudioFile();
                int time = 0;
                time = user.getPlayer().getSource().getAudioFile().getDuration()
                        - user.getPlayer().getStats().getRemainedTime();
                List<Song> songsGenre = songs.stream().filter(song -> song.getGenre()
                        .equals(songCurrent.getGenre())).collect(Collectors.toList());
                long seed = time;
                Random random;
                random = new Random(seed);
                Song randomSong = songsGenre.get(random.nextInt(songsGenre.size()));
                user.setLastSongRecommendation(randomSong);
                user.getSongRecommendations().add(randomSong);
            }
            case "random_playlist" -> {
                User user = (User) currentUser;
                String namePlaylist = commandInput.getUsername() + "'s recommendations";
                user.setLastPlaylistRecommendation(
                        new Playlist(namePlaylist, commandInput.getUsername()));
                user.getPlaylistRecommendations().add(
                        new Playlist(namePlaylist, commandInput.getUsername()));
            }
            case "fans_playlist" -> {
                User user = (User) currentUser;
                String artistName = null;
                for (Song song : songs) {
                    if (song.getName()
                            .equals(user.getPlayer().getSource().getAudioFile().getName())) {
                        artistName = song.getArtist();
                    }
                }
                String namePlaylist = artistName + " Fan Club recommendations";
                user.setLastPlaylistRecommendation(
                        new Playlist(namePlaylist, commandInput.getUsername()));
                user.getPlaylistRecommendations().add(
                        new Playlist(namePlaylist, commandInput.getUsername()));

            }
            default -> throw new IllegalStateException(
                    "Unexpected value: " + commandInput.getRecommendationType());
        }


        return ("The recommendations for user %s"
               + " have been updated successfully.").formatted(username);
    }

    /**
     Loads a list of artists based on the songs uploaded by users.
     * @return A list of artists.
     */
    public List<Artist> artistsLoad() {
        List<String> artistsName = new ArrayList<>();
        List<Song> loadedSongs = new ArrayList<>();

        for (User user : users) {
            UserAbstract userAbstract = getAbstractUser(user.getUsername());
            User userCurrent = (User) userAbstract;
            List<String> loadedSongNames = userCurrent.getLoadedSong();

            loadedSongs.addAll(
                    songs.stream()
                            .filter(song -> loadedSongNames.contains(song.getName()))
                            .collect(Collectors.toList())
            );
        }

        for (Song song : loadedSongs) {
            artistsName.add(song.getArtist());
        }

        List<Artist> artistsAux = artists.stream()
                .filter(artist -> artistsName.contains(artist.getUsername()))
                .collect(Collectors.toList());

        return artistsAux;
    }

    /**
     * The user will navigate to the previous page if it exists.
     * @param commandInput The commandInput.
     * @return The message corresponding to the command.
     */
    public String previousPage(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        UserAbstract currentUser = getAbstractUser(username);
        User user = (User) currentUser;

        if (user.getHistoryPage().size() <= 1) {
            return "There are no pages left to go back.";
        }

        user.getHistoryPage().remove(user.getHistoryPage().size() - 1);

        Page previousPage = user.getHistoryPage().get(user.getHistoryPage().size() - 1);

        user.setCurrentPage(previousPage);
        return "The user %s has navigated successfully to the previous page."
                .formatted(commandInput.getUsername());
    }
    /**
     * The user will navigate to the next page if it exists.
     * @param commandInput The commandInput.
     * @return The message corresponding to the command.
     */
    public String nextPage(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        UserAbstract currentUser = getAbstractUser(username);
        User user = (User) currentUser;
        int currentPageIndex = user.getHistoryPageAux().indexOf(user.getCurrentPage());
        if (currentPageIndex == user.getHistoryPageAux().size() - 1) {
            return "There are no pages left to go forward.";
        }
        user.setCurrentPage(user.getHistoryPageAux().get(currentPageIndex + 1));
        return "The user %s has navigated successfully to the next page."
                .formatted(commandInput.getUsername());
    }

    /**
     * All user recommendations will be loaded.
     * @param commandInput The commandInput.
     * @return The message corresponding to the command.
     */
    public String loadRecommendations(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        UserAbstract currentUser = getAbstractUser(username);
        User user = (User) currentUser;

        if (!user.isStatus()) {
            return "%s is offline.".formatted(commandInput.getUsername());
        }

        if (user.getLastSongRecommendation() == null
        && user.getPlaylistRecommendations() == null) {
            return "No recommendations available.";
        }

        if (user.getLastSongRecommendation() != null) {
            user.getPlayer().setSource(user.getLastSongRecommendation(), "song");
        }
        if (user.getLastPlaylistRecommendation() != null) {
            user.getPlayer().setSource(user.getLastPlaylistRecommendation(), "playlist");
        }

        user.getLoadedSong().add(user.getLastSongRecommendation().getName());
        user.getPlayer().pause();

        return "Playback loaded successfully.";
    }

    /**
     * Counts the number of listeners for each episode,
     * based on the users who have loaded that episode.
     * Updates the top episodes and user listeners for the hosts of podcasts.
     * @param commandInput The commandInput.
     */
    void wrapped(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        for (Podcast podcast : podcasts) {
            for (Episode episode : podcast.getEpisodes()) {
                episode.resetListeners();
            }
        }

        for (Podcast podcast : podcasts) {
            for (Episode episode : podcast.getEpisodes()) {
                for (User user : users) {
                    if (user.getLoadedEpisode() != null) {
                        for (Episode episodeLoad : user.getLoadedEpisode()) {
                            if (episode.getName().equals(episodeLoad.getName())) {
                                episode.listernesNr();
                            }
                        }
                    }
                }
            }
        }
        for (User user : users) {
            if (user.getLoadedEpisode() != null) {
                user.getLoadedEpisode().sort(Comparator.comparing(Episode::getName));
            }
        }
        for (User user : users) {
            if (user.getLoadedEpisode() != null) {
                user.setTopEpisodes(user.getLoadedEpisode());
            }
        }
        for (User user : users) {
            if (user.getTopEpisodes() != null) {
                ArrayList<Episode> episodes = new ArrayList<>(user.getTopEpisodes().stream()
                        .distinct()
                        .collect(Collectors.toList()));
                user.setTopEpisodes(episodes);
            }
        }
        for (Podcast podcast : podcasts) {
            for (User user : users) {
                for (Episode episodeLoad : user.getLoadedEpisode()) {
                    if (podcast.getEpisodes().stream().anyMatch(episode -> episode
                            .getName().equals(episodeLoad.getName()))) {
                        Host currentHost;
                        if (hosts.stream().anyMatch(host -> host.getUsername()
                                .equals(podcast.getOwner()))) {
                            currentHost = hosts.stream()
                                    .filter(host -> host.getUsername().equals(podcast.getOwner()))
                                    .findFirst()
                                    .orElse(null);
                            if (!currentHost.getTopEpisodes()
                                    .stream().anyMatch(episodee -> episodee.getName()
                                    .equals(episodeLoad.getName()))) {
                                currentHost.getTopEpisodes().add(episodeLoad);
                            }
                            if (!currentHost.getUserListeners()
                                    .stream().anyMatch(userr -> userr.getUsername()
                                    .equals(user.getUsername()))) {
                                currentHost.getUserListeners().add(user);
                            }

                        }

                    }
                }
            }
        }

    }



}
