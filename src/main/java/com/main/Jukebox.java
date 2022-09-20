package com.main;

import com.dao.ArtistDAO;
import com.dao.GenreDAO;
import com.dao.PlaylistDAO;
import com.dao.SongsDAO;
import com.data.Playlist;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static com.dao.PlaylistDAO.displayPlaylists;

public class Jukebox {
    public static void main(String[] args) throws NullPointerException, SQLException {
        ArtistDAO artistDAO = new ArtistDAO();
        GenreDAO genreDAO = new GenreDAO();
        SongsDAO songsDAO = new SongsDAO();
        PlaylistDAO playlistDAO = new PlaylistDAO();
        JukeboxPlayer jukeboxPlayer = new JukeboxPlayer();

        Scanner scanner = new Scanner(System.in);


        int repeat;
        do {
            System.out.println("*****************************************Welcome to JUKEBOX******************************************");
            System.out.format("\t\t\t\t\t\t\t\t\t\t-*Select Songs By*-\n\t\t\t\t\t\t\t\t\t\t1. Songs\n\t\t\t\t\t\t\t\t\t\t" +
                    "2. Jukebox Library\n\t\t\t\t\t\t\t\t\t\t3. Playlist");
            System.out.println();
            System.out.println("*****************************************************************************************************");

            System.out.println();
            int choice = scanner.nextInt();
            if (choice == 1) {
                songsDAO.getAllSongs();
                jukeboxPlayer.SongPlayFromDB();
                jukeboxPlayer.SongsPlay();
            } else if (choice == 2) {
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println("\t\t\t\t\t\t\t\t\t\tSearch song by\n\t\t\t\t\t\t\t\t\t\t1. Artist\n\t\t\t\t\t\t\t\t\t\t2. Genre\n\t\t\t\t\t\t\t\t\t\t3. Song Name");
                System.out.println("----------------------------------------------------------------------------------------------");
                System.out.println();
                int searchSong = scanner.nextInt();
                if (searchSong == 1) {
                    artistDAO.displayArtist();
                    artistDAO.searchArtist();
                    System.out.println("Select the artist and enter the id ");
                    int artistID = scanner.nextInt();
                    artistDAO.displayArtistSongs(artistID);
                    jukeboxPlayer.SongPlayFromDB();
                    jukeboxPlayer.SongsPlay();


                } else if (searchSong == 2) {
                    genreDAO.displayGenre();
                    genreDAO.searchGenre();
                    System.out.println("Select the genre and enter the id");
                    int genreID = scanner.nextInt();
                    genreDAO.displayGenreSongs(genreID);
                    jukeboxPlayer.SongPlayFromDB();
                    jukeboxPlayer.SongsPlay();

                } else if (searchSong == 3) {
                    System.out.println("Enter the Name of song");
                    scanner.nextLine();
                    String sName = scanner.nextLine();
                    songsDAO.displaySearchByNameSongs(sName);
                    jukeboxPlayer.SongPlayFromDB();
                    jukeboxPlayer.SongsPlay();

                }


            } else if (choice == 3) {
                int checkRepeat = 1;
                while (checkRepeat == 1) {
                    System.out.println("----------------------------------------------------------------------------------------------");
                    System.out.println("\t\t\t\t\t\t\t\t\t\t1. Create playlist\n\t\t\t\t\t\t\t\t\t\t2. Display playlist" +
                            "\n\t\t\t\t\t\t\t\t\t\t3. Add in playlist");
                    System.out.println("----------------------------------------------------------------------------------------------");
                    int playlistOption = scanner.nextInt();

                    if (playlistOption == 1) {
                        System.out.println("\t\t\t\t\t\t\t\t\t\t Create songs playlist");
                        playlistDAO.createPlaylist();
                        System.out.println("Your All playlist");
                        playlistDAO.displaySongsPlaylists();
                        break;


                    } else if (playlistOption == 2) {
                        System.out.println("\t\t\t\t\t\t\t\t\t\tDisplay songs playlist");
                        List<Playlist> playlists = playlistDAO.displaySongsPlaylists();
                        displayPlaylists(playlists);
                        System.out.println("Enter the playlist id");
                        int playlistId = scanner.nextInt();
                        playlistDAO.displayPlaylistSongs(playlistId);
                        jukeboxPlayer.SongPlayFromDB();
                        jukeboxPlayer.SongsPlay();


                    } else if (playlistOption == 3) {
                        System.out.println("\t\t\t\t\t\t\t\t\t\tAdd songs to the playlist\n\t\t\t\t\t\t\t\t\t\t\tTo add enter 1");
                        int add = scanner.nextInt();
                        if (add == 1) {
                            List<Playlist> playlists = playlistDAO.displaySongsPlaylists();
                            displayPlaylists(playlists);
                            System.out.println("\t\t\t\t\t\t\t\t\t\tEnter the playlist Id");
                            int playlistID = scanner.nextInt();
                            int stop = 0;
                            do {
                                songsDAO.getAllSongs();
                                System.out.println("\t\t\t\t\t\t\t\t\t\tEnter the song Id");
                                int playSongID = scanner.nextInt();
                                playlistDAO.addSongInPlaylist(playlistID, playSongID);
                                System.out.println("\t\t\t\t\t\t\t\t\t\tDo you want to add more songs\n\t\t\t\t\t\t\t\t\t\t\t\t" +
                                        "1. yes\n\t\t\t\t\t\t\t\t\t\t\t\t2. No");
                                stop = scanner.nextInt();
                                if (stop == 2) {
                                    break;
                                }
                            } while (stop == 1);
                        }
                    }

                    System.out.println("\t\t\t\t\t\t\t\t\t\tDo you want to continue with playlist\n\t\t\t\t\t\t\t\t\t\t1. Yes\n\t\t\t\t\t\t\t\t\t\t2. No");
                    checkRepeat = scanner.nextInt();
                    if (checkRepeat == 2) {
                        System.out.println("\t\t\t\t\t\t\t\t\t\t\t-*Thank you*-");
                        break;
                    }
                }

            }
            System.out.println("\t\t\t\t\t\t\t\t\t\tDo you want to continue with the Jukebox\n\t\t\t\t\t\t\t\t\t\t1. Yes\n\t\t\t\t\t\t\t\t\t\t2. No");
            repeat = scanner.nextInt();
            if (repeat == 2) {
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t-*Thank you*-");
                break;
            }

        } while (repeat == 1);
    }

}
