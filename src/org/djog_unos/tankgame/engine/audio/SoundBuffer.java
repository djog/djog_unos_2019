package org.djog_unos.tankgame.engine.audio;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.*;

import org.djog_unos.tankgame.engine.utils.IOUtil;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class SoundBuffer {

    private final int bufferId;
    private ShortBuffer pcm = null;

    private ByteBuffer vorbis = null;

    public SoundBuffer(final String fileName) {
        String path = "./assets/audio/" + fileName;
        bufferId = alGenBuffers();
        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            ShortBuffer pcm = readVorbis(path, 256 * 1024, info);

            // Copy to buffer
            alBufferData(bufferId, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getBufferId() {
        return this.bufferId;
    }

    public void cleanup() {
        alDeleteBuffers(this.bufferId);
    }

    private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws Exception {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            vorbis = IOUtil.ioResourceToByteBuffer(resource, bufferSize);
            IntBuffer error = stack.mallocInt(1);
            long decoder = stb_vorbis_open_memory(vorbis, error, null);
            if (decoder == NULL) {
                throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
            }

            stb_vorbis_get_info(decoder, info);

            int channels = info.channels();

            int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

            pcm = MemoryUtil.memAllocShort(lengthSamples);

            pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
            stb_vorbis_close(decoder);

            return pcm;
        }
    }
}