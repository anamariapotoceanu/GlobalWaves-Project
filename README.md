# GlobalWaves-Project
**Potoceanu Ana-Maria**
**321CA**

 - The Admin Class is designed to be a Singleton. This ensures that there
  is only one instance of the class, which is obtained by calling
  getInstance(). In the Admin Class, I have stored all users and all audio
  files from the application.
- I employed the Factory design pattern for creating pages. This involves various page types
  (HomeFactory, LikedContentFactory, ArtistFactory, HostFactory), and a Page
  object is instantiated using the respective factory.

- Another design pattern employed is the Command Pattern. Two concrete classes,
  NextPage and PreviousPage, were created, implementing the Command interface.
  Each class holds a reference to an Admin object, responsible for executing the
  corresponding command.

- Within the project, I also applied the Observer pattern. Admin acts as an
  Observer, while UserAbstract serves as a Subject. Notifications regarding new
  albums, events, or merchandise are generated and broadcasted to all observers
  (users).
  
## Command: SEARCH
- I implemented a class with a search method for songs, podcasts, and playlists, storing the results in an Output class instance to ensure accurate recording of search outcomes.

## Command: SELECT
- The select command utilizes results from the previous method to determine whether to select a song, podcast, or playlist, handling potential error cases and storing results in an Output instance.

## Command: LOAD
- I implemented the load command in the same class as select, passing the last selection as a parameter and checking the source type while also initializing the "resumed" status.

## Command: PlayPause
- For the PlayPause command, I checked the audio source's state to pause playback if it was resumed or to start playback otherwise, considering the remaining time accordingly.

## Command: STATUS
- The status command sets relevant parameters, calculates the remaining time based on the current state of the song, and updates the pause/play status.

## Command: CreatePlaylist
- Using the PlaylistCommand class, I created new playlists with public visibility and zero followers, storing the results in a playlist list.

## Command: addRemoveInPlaylist
- This command checks if the selected song is in the playlist, adding it if not and removing it if it is, while tracking whether the Load command was previously executed.

## Command: Like
- For the like command, I maintained a list of appreciated songs, removing them if liked again and tracking user preferences in a formatted string array.

## Command: showPlaylists
- The command displays all created playlists by utilizing the results returned from the create command.

## Command: showPreferredSongs
- I displayed the list of liked songs using the keys established in the Like command to identify user preferences.

## Command: Follow
- The Follow command checks whether a user is followed and maintains a list of followers for each playlist to accurately display follower counts.

## Command: switchVisibility
- This command checks and modifies the visibility of a selected playlist, displaying a message based on the changes made.

## Command: Top5Playlist
- I created a list of public playlists, sorted them by follower count, ensured no duplicates, and presented them in descending order.

## Command: Top5Songs
- For this command, I tracked the likes for each song using keys and performed a descending sort based on the number of likes.

## Print
- I created separate methods in the Print class to display the outputs for each command.

 ## Command: switchConnectionStatus
  - For this command, I checked if the specified user exists and if it is a
  normal user. 
  - The user is switched from online to offline mode and vice versa. When
  switching to offline, the time is not counted, and the player corresponding 
  to the user is stopped, considering that they won't be able to call certain
  commands.

 ## Command: getOnlineUsers
  - I implemented in CommandInput a method that displays the list of all users
  who are online at that moment. For this command, I needed a method to
  determine whether the user is or isn't online: isOnline. I iterated through
  the list of all users in the application and checked for each whether they
  are online or not.

  ## Command: addUser
  - Adding a user to the application was done based on the given type. 
  Specifically, if it is a regular user, artist, or host. Depending on this,
  new users with their specific data were created, and they were added to the
  list.

  ## Command: addAlbum
  - To add an album for a given artist, I checked that the artist does not
  already have an album with the same name. Additionally, I used the verifyName
  method that checks if a song appears twice on the album we want to add. The
  new album is created with the necessary fields and added to the artist's
  album list. For each user, I kept albums in a list.

  ## Command: showAlbums
  - For displaying the albums of a specific artist, I created the AlbumOutput
  class to represent an output format for an album, including its name and a
  list of song names. The showAlbums method generates an ArrayList of
  AlbumOutput objects by iterating through existing albums and creating an
  AlbumOutput object for each.

  ## Command: printCurrentPage
  - To display the current page, I kept track of the page each user is on using
  the getChangedPage field. Initially, I checked if they are on a host's page
  and displayed information about the host's podcasts and announcements.
  If the user is on an artist's page, I display information about albums,
  events, and merchandise products of the selected artist. I iterated through
  each list and saved the results in new lists to be able to display them
  appropriately.
  - I proceeded similarly for the home page and the liked content page.

  ## Command: addMerch
  - For this command, I initially checked if the merchandise item could be 
  added.
  - I created the corresponding merchandise item and added it to the artist's
  merchandise list with all the items.

  ## Command: addEvent
  - For adding an event, I needed the validate method that checks if the event
  date is valid. To extract the day, month, and year, I used substring.

  ## Command: getAllUsers
  - To display all users in the application, I iterated through the list where
  - I kept all of them and created a list with all of them regardless of their
  type.

  ## Command: deleteUser
  - For deleting a user, I checked if any other user is currently listening to
  their audio source or if someone is on their page. The user is removed from
  the list of users, and the songs and albums are updated accordingly, 
  modifying likes and follower numbers for affected sources.

  ## Command: addPodcast
  - For adding a podcast, I initially checked if the operation is allowed. 
  Adding is only done by a host, and there should not be a podcast with the
  same name. The podcast is added to the host's podcast list.

  ## Command: addAnnouncement
  - Checks are made again if it can be added. The announcement is created, and
  it is added to the list.

  ## Command: removeAnnouncement
  - It is checked if the host can delete the announcement. If yes, it is
  removed from the list of announcements for each user who is a host.

  ## Command: showPodcasts
  - For displaying a host's podcasts, I created the PodcastOutput class to
  represent an output format for a podcast. It contains the name of the podcast
  and a list of episode names. The showPodcasts method generates an ArrayList
  of PodcastOutput objects by iterating through podcasts and creating a
  PodcastOutput object for each.

  ## Command: removePodcast
  - I checked that no other user has loaded any episodes from the podcast I
  want to delete. I did this by iterating through the list of users, checking
  the player and the current source.

  ## Command: getTop5Albums
  - The method calculates the cumulative likes for each song on each album. It
  sorts albums based on the total number of likes in descending order and then
  by album name. The top 5 albums are selected and returned as a list of 
  strings.

  ## Command: removeAlbum
  - For each user in the list, I checked if they have a song currently loaded
  that is contained on the album I want to delete. I used getSource to get what
  audio source is currently loaded for each user.

  ## Command: changePage
  - When changing the page, I record for each user on what page they will be.
  Also, I record if they will be on the page of a host and which host it is.

  ## Command: removeEvent
  - It is checked if the event can be deleted, and it is removed from the
  user's event list.

  ## Command: getTop5Artists
  - The method calculates the cumulative likes for each song associated with 
  each artist from all albums. I created a list of Artist objects to store the
  cumulative likes and the username of the artist. The list is sorted based on
  the total number of likes in descending order and then by the username of the
  artist. The usernames of the top 5 artists are selected and returned as a
  list of strings.

 - I implemented the command outputs in CommandRunner.

## Command subscribe
- For every user, I maintained a list of subscribed artists. Upon invoking the
  method, I checked whether the artist they wished to subscribe to was already
  on the list. Based on this, either a subscription or unsubscription operation
  was executed. I also considered whether the user was on the page of the
  artist/host they intended to subscribe to.

## Command getNotifications
- Notifications for each user were stored in an array. Whenever an album, event,
  or merchandise was created, the corresponding notification was added to the
  array for the current user.

## Command buyMerch
- The method identified the current user and searched the list of artists to
  find one offering the desired product. It verified whether the user was on the
  page of the corresponding artist. If affirmative, revenues from the purchase
  were added to the artist's earnings, and the bought product was appended to
  both the user's and artist's lists of purchased items.

## Command seeMerch
- This command displayed a list of names of merchandise products purchased by
  a user. Each user maintained a list of acquired products.

## Command updateRecommendations
- New recommendations were generated for a user, such as a list of songs or a
  playlist. I assumed each user possessed arrays of recommended songs and
  playlists for convenient access. To create a random song, I calculated a
  seed representing the user's listening progress in the current song.

## Command previousPage
- I stored an array of all pages accessed by the user to facilitate navigation.
  The last accessed page was removed, and the current page of the user was set as
  the penultimate page from the history. Given that pages were removed, I
  maintained an auxiliary history of all accessed pages, which aided in
  implementing nextPage.

## Command nextPage
- This command checked for at least one subsequent page in the auxiliary
  navigation history (historyPageAux). It also verified whether the user could
  transition to the next page. The current page of the user was then set as
  the next page from the auxiliary history.

## Command loadRecommendations
- Recommendations for each user were displayed. Every user possessed arrays
  containing all generated recommendations.

## Command wrapped
- The wrapped method was implemented specifically for hosts, concerning
  episodes. For each podcast, I iterated through each episode, setting the number
  of listeners. I created a list of loaded episodes, sorting it alphabetically
  based on episode names. For each podcast and user, I checked whether episodes
  loaded by the user coincided with the podcast's episodes. The user was
  subsequently added to the host's list of listeners.




