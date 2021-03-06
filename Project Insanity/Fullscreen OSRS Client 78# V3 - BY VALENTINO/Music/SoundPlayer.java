/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Music;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

/**
 *
 * @author amaco
 */
public class SoundPlayer {

    public void PlaySound(String source) {
        try {
            // From file
            Sequence sequence = MidiSystem.getSequence(new File(".\\Music\\" + source));
            // From URL
            System.out.println(source);
            File file;
            //file = new File(getClass().getResource("/Music" + source).toURI());
            //Sequence sequence = MidiSystem.getSequence(file);

            // Create a sequencer for the sequence
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);

            // Start playing
            sequencer.start();
            if (sequencer instanceof Synthesizer) {
                Synthesizer synthesizer = (Synthesizer) sequencer;
                MidiChannel[] channels = synthesizer.getChannels();

                // gain is a value between 0 and 1 (loudest)
                double gain = 0.9D;
                for (int i = 0; i < channels.length; i++) {
                    channels[i].controlChange(7, (int) (gain * 25.0));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.getMessage();
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
}
