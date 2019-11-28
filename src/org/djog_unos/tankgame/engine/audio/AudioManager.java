package org.djog_unos.tankgame.engine.audio;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.*;
import java.util.*;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.openal.*;


public final class AudioManager
{
    private static long device;

    private static long context;

    private static final List<SoundBuffer> soundBufferList;

    private static final Map<String, SoundSource> soundSourceMap;


    static {
        soundBufferList = new ArrayList<>();
        soundSourceMap = new HashMap<>();
    }

    public static void init() {
        try {
            device = alcOpenDevice((ByteBuffer) null);
            if (device == NULL) {
                throw new IllegalStateException("Failed to open the default OpenAL device.");
            }
            ALCCapabilities deviceCaps = ALC.createCapabilities(device);
            context = alcCreateContext(device, (IntBuffer) null);
            if (context == NULL) {
                throw new IllegalStateException("Failed to create OpenAL context.");
            }
            alcMakeContextCurrent(context);
            AL.createCapabilities(deviceCaps);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addSoundSource(String name, SoundSource soundSource) {
        soundSourceMap.put(name, soundSource);
    }

    public static SoundSource getSoundSource(String name) {
        return soundSourceMap.get(name);
    }

    public static void playSoundSource(String name) {
        SoundSource soundSource = soundSourceMap.get(name);
        if (soundSource != null && !soundSource.isPlaying()) {
            soundSource.play();
        }
    }

    public static void removeSoundSource(String name) {
        soundSourceMap.remove(name);
    }

    public static void addSoundBuffer(SoundBuffer soundBuffer) {
        soundBufferList.add(soundBuffer);
    }

    public static void setAttenuationModel(int model) {
        alDistanceModel(model);
    }
    
    public static void cleanup() {
        for (SoundSource soundSource : soundSourceMap.values()) {
            soundSource.cleanup();
        }
        soundSourceMap.clear();
        for (SoundBuffer soundBuffer : soundBufferList) {
            soundBuffer.cleanup();
        }
        soundBufferList.clear();
        if (context != NULL) {
            alcDestroyContext(context);
        }
        if (device != NULL) {
            alcCloseDevice(device);
        }
    }

    public static void setListenerPosition(Vector2f position)
    {
        alListener3f(AL_POSITION, position.x, position.y, 0);
    }

    public static void setListenerVelocity(Vector3f velocity)
    {
        alListener3f(AL_VELOCITY, velocity.x, velocity.y, velocity.z);
    }
}