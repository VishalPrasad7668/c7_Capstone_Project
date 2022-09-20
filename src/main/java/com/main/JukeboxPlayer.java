package com.main;

import com.util.DbConnection;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JukeboxPlayer {
    static String songsFromDB;
    static int songId;
    static String filePath;
    Timer t;
    // to store current position
    Long currentFrame;
    Clip clip;

    // current status of clip
    String status;

    AudioInputStream audioInputStream;

    public void SongPlayFromDB() {

        try {
            Connection connection = DbConnection.getConnection();
            String path = "C:\\Users\\VISHAL PRASAD\\Desktop\\Java\\Course7\\c7_Capstone_Project\\c7_Capstone_Project\\src\\main\\resources\\Project Jukebox Songs\\";
            String sql = "select resource_path from songs_tbl where songs_id=?";
            Scanner scanner = new Scanner(System.in);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            System.out.println("Enter Song ID");
            songId = scanner.nextInt();


            preparedStatement.setInt(1, songId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                songsFromDB = path + rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Clip playClip() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        filePath = songsFromDB;
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
        int delay = 1000;

        long length = (clip.getMicrosecondLength() / 1000);
        t = new Timer(delay, new ActionListener() {
            private long time = length;

            public void actionPerformed(ActionEvent e) {
                if (time >= 0) {
                    long s = ((time / 1000) % 60);
                    long m = ((time / 1000) / 60 % 60);
                    long h = ((((time / 1000) / 60) / 60) % 60);
                    System.out.print("\r" + h + ":" + m + ":" + s);
                    time -= 1000;
                }
            }
        });
        return clip;
    }

    public void SongsPlay() {
        try {
            JukeboxPlayer audioPlayer = new JukeboxPlayer();
            Clip clip = audioPlayer.playClip();
            audioPlayer.play(clip);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("1. pause");
                System.out.println("2. resume");
                System.out.println("3. restart");
                System.out.println("4. stop");
                System.out.println("5. Next");
                System.out.println("6. previous");
                int choose = sc.nextInt();
                audioPlayer.gotoChoice(choose);
                if (choose == 4)
                    break;
            }

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }

    // Work as the user enters his choice

    private void gotoChoice(int c) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        switch (c) {
            case 1: {
                pause();
                break;
            }
            case 2: {
                resumeAudio();
                break;
            }
            case 3: {
                restart();
                break;
            }
            case 4: {
                stop();
                break;
            }
            case 7: {
                stop();
                SongPlayFromDB();
                playClip();
                break;
            }
            case 5: {
                Next();
                break;
            }
            case 6: {
                previous();
            }
        }

    }

    public void Next() {
        int id = songId;
        clip.close();
        t.stop();
        songId = id + 1;

    }

    public void previous() {
        int id = songId;
        clip.close();
        t.stop();
        songId = id - 1;

    }

    // Method to play the audio
    public void play(Clip clip) {
        //start the clip

        clip.start();
        t.start();
        status = "play";
        System.out.println("Status after play" + status);
    }

    // Method to pause the audio
    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        t.stop();
        status = "paused";
        System.out.println("Status after play" + status);
    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        if (status.equals("play")) {
            System.out.println("Audio is already " +
                    "being played");
            return;
        }
        clip.close();
        t.stop();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play(clip);
    }

    // Method to restart the audio
    public void restart() throws IOException, LineUnavailableException,
            UnsupportedAudioFileException {
        clip.stop();
        clip.close();
        t.stop();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play(clip);
    }

    // Method to stop the audio
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        t.stop();
        clip.close();
    }

    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        filePath = songsFromDB;
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
