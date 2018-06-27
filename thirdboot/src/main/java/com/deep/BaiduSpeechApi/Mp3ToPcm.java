package com.deep.BaiduSpeechApi;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Mp3ToPcm {
    /**
     * mp3转pcm
     * @param mp3FilePath MP3文件
     * @param pcmFilePath 转换的pcm保存目录，注意目录一定要存在，不然会报FileNotFoundError
     * @return
     */
    public static boolean covertMp3ToPcm(String mp3FilePath, String pcmFilePath) {
        try {
            AudioInputStream audioInputStream = getPcmAudioInputStream(mp3FilePath);
            AudioSystem.write(audioInputStream,AudioFileFormat.Type.WAVE,new File(pcmFilePath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * MP3转成AudioInputStream
     * @param mp3FilePath
     * @return
     */
    private static AudioInputStream getPcmAudioInputStream(String mp3FilePath) {
        File file = new File(mp3FilePath);
        AudioInputStream audioInputStream = null;
        AudioFormat audioFormat = null;
        try {
            AudioInputStream in = null;
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(file);
            AudioFormat format = in.getFormat();
            audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,format.getSampleRate(),16,format.getChannels(),
                    format.getChannels()*2,format.getSampleRate(),false);
            audioInputStream = AudioSystem.getAudioInputStream(audioFormat,in);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  audioInputStream;
    }
}
