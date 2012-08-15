package com.gej.sound;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import com.gej.core.Global;

/**
 * Loads and plays wav files. To decrease the delay on loading of the files, you
 * are recommended to load them using the static loadSound() method. You can
 * play them again at any time by the invoking the play() method of the loaded
 * sound. Supports playing simultaneous files. Supports *.wav *.au *.aiff files.
 * Also you can use MIDI files which are detected and converted automatically.
 * But if you want to modify the MIDI data, use the MidiPlayer class instead.
 * 
 * @author Sri Harsha Chilakapati
 */
public class WavPlayer extends Thread {

    /**
     * Loads a sound from a file in the jar file to play them at any time.
     * Supports loading of .wav, .au, .aiff files. Also converts MIDI files on
     * the fly
     * 
     * @param s The path of the wav file.
     * @return The sound data loaded into the WavSound object
     */
    public static WavSound loadSound(String s){
        // Get an input stream
        InputStream is = WavPlayer.class.getClassLoader().getResourceAsStream(s);
        AudioInputStream audioStream;
        try {
            // Buffer the input stream
            BufferedInputStream bis = new BufferedInputStream(is);
            // Create the audio input stream and audio format
            audioStream = AudioSystem.getAudioInputStream(bis);
            AudioFormat format = audioStream.getFormat();
            // The length of the audio file
            int length = (int) (audioStream.getFrameLength() * format.getFrameSize());
            // The array to store the samples in
            byte[] samples = new byte[length];
            // Read the samples into array to reduce disk access
            // (fast-execution)
            DataInputStream dis = new DataInputStream(audioStream);
            dis.readFully(samples);
            // Create a sound container
            WavSound sound = new WavSound(samples, format, (int) audioStream.getFrameLength());
            // Don't start the sound on load
            sound.setState(SoundState.STATE_STOPPED);
            // Create a new player for each sound
            new WavPlayer(sound);
            return sound;
        } catch (Exception e) {
            // An error. Mustn't happen
        }
        return null;
    }

    // Private variables
    private WavSound sound = null;

    /**
     * Constructs a new player with a sound and with an optional looping
     * 
     * @param s The WavSound object
     */
    public WavPlayer(WavSound s) {
        sound = s;
        start();
    }

    /**
     * Runs the player in a separate thread
     */
    public void run(){
        // Get the byte samples from the container
        byte[] data = sound.getData();
        InputStream is = new ByteArrayInputStream(data);
        try {
            // Create a line for the required audio format
            SourceDataLine line = null;
            AudioFormat format = sound.getAudioFormat();
            // Calculate the buffer size and create the buffer
            int bufferSize = sound.getLength();
            // System.out.println(bufferSize);
            byte[] buffer = new byte[bufferSize];
            // Create a new data line to write the samples onto
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            // Open and start playing on the line
            try {
                if (!line.isOpen()) {
                    line.open();
                }
                line.start();
            } catch (Exception e){}
            // The total bytes read
            int numBytesRead = 0;
            boolean running = true;
            while (running) {
                // Destroy this player if the sound is destroyed
                if (sound.getState() == SoundState.STATE_DESTROYED) {
                    running = false;
                    // Release the line and release any resources used
                    line.drain();
                    line.close();
                }
                // Write the data only if the sound is playing or looping
                if ((sound.getState() == SoundState.STATE_PLAYING)
                        || (sound.getState() == SoundState.STATE_LOOPING)) {
                    numBytesRead = is.read(buffer, 0, buffer.length);
                    if (numBytesRead != -1) {
                        line.write(buffer, 0, numBytesRead);
                    } else {
                        // The samples are ended. So reset the position of the
                        // stream
                        is.reset();
                        // If the sound is not looping, stop it
                        if (sound.getState() == SoundState.STATE_PLAYING) {
                            sound.setState(SoundState.STATE_STOPPED);
                        }
                    }
                } else {
                    // Not playing. so wait for a few moments
                    Thread.sleep(Math.min(1000 / Global.FRAMES_PER_SECOND, 10));
                }
            }
        } catch (Exception e) {
            // Do nothing
        }
    }

}
