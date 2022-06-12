package engine.audio;

import engine.utils.IOUtils;
import engine.utils.Range;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioClip {

    private final String filepath;

    private Clip rawClip;
    private FloatControl gainControl;

    public AudioClip(String filepath) {
        this.filepath = filepath;
    }

    public void Create() {
        try {
            InputStream inputStream = IOUtils.ReadAssetFileAsInputStream(filepath);
            AudioInputStream dais = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
            rawClip = AudioSystem.getClip();
            rawClip.open(dais);

            gainControl = (FloatControl) rawClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(0.0f);

            dais.close();
            inputStream.close();
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void Play() {
        Stop();

        rawClip.setFramePosition(0);

        while(!rawClip.isRunning())
            rawClip.start();
    }

    public void PlayContinuously() {
        Stop();

        rawClip.setFramePosition(0);

        while(!rawClip.isRunning())
            rawClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public boolean IsPlaying() { return rawClip.isRunning(); }

    public void Stop() {
        if(rawClip.isRunning()) rawClip.stop();
    }

    public void Destroy() {
        Stop();
        rawClip.flush();
    }

    public void SetGain(float gain) {
        gainControl.setValue(Range.Clip(gainControl.getMinimum(), gainControl.getMaximum(), gain));
    }

    public float GetGain() { return this.gainControl.getValue(); }
    public float GetMinimumGain() { return this.gainControl.getMinimum(); }
    public float GetMaximumGain() { return this.gainControl.getMaximum(); }

}
