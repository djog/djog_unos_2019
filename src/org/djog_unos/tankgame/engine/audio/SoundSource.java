package org.djog_unos.tankgame.engine.audio;

import static org.lwjgl.openal.AL10.*;

public class SoundSource {

    private final int sourceId;
    
    public SoundSource() {
        this(false, false);
    }
    
    public SoundSource(boolean loop, boolean relative) {
        sourceId = alGenSources();

        if (loop) {
            alSourcei(sourceId, AL_LOOPING, AL_TRUE);
        }
        if (relative) {
            alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_TRUE);
        }
    }

    public SoundSource(float x, float y, float gain, boolean loop) {
        this(loop, false);
        setPosition(x, y);
        setGain(gain);
    }

    public void setBuffer(int bufferId) {
        stop();
        alSourcei(sourceId, AL_BUFFER, bufferId);
    }

    public void setPosition(float x, float y) {
        alSource3f(sourceId, AL_POSITION, x, y, 0);
    }

    public void setGain(float gain) {
        alSourcef(sourceId, AL_GAIN, gain);
    }
    
    public void play() {
        alSourcePlay(sourceId);
    }

    public boolean isPlaying() { return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING; }

    public void pause() {
        alSourcePause(sourceId);
    }

    public void stop() {
        alSourceStop(sourceId);
    }

    public void cleanup() {
        stop();
        alDeleteSources(sourceId);
    }
}