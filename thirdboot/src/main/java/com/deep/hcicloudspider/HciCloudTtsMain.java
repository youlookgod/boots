package com.deep.hcicloudspider;

import com.sinovoice.hcicloudsdk.common.HciErrorCode;
import com.sinovoice.hcicloudsdk.common.tts.TtsConfig;

import java.io.*;

/**
 * @author sinovoice
 */
public class HciCloudTtsMain {
    private HciCloudSysHelper mHciCloudSysHelper;
    private HciCloudTtsHelper mHciCloudTtsHelper;

    public HciCloudTtsMain() {
        init();
    }

    public int generateTxtToMp3(String txt, String filePath, TtsConfig ttsConfig) {
        int nRet = mHciCloudTtsHelper.synth(txt, ttsConfig);
        if (nRet == HciErrorCode.HCI_ERR_NONE) {
            byte[] audioByte = mHciCloudTtsHelper.getResultByte();
            if (audioByte != null) {
                FileOutputStream mFos = initFileOutputStream(filePath);
                try {
                    mFos.write(audioByte);
                    mFos.flush();
                    mFos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mHciCloudTtsHelper.setResultByte();//清空byte
        }
        return nRet;
    }

    public void generateFileToMp3(File file, String filePath) throws FileNotFoundException {
        TtsConfig ttsConfig = setTtsConfig();
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String str = null;
            byte[] fullByte = null;
            while ((str = bf.readLine()) != null) {
                str = str.trim().replace(" ", "");
                int length = str.length();
                if (str == null || str.equals("")) {
                    continue;
                }
                mHciCloudTtsHelper.synth(str, ttsConfig);
                byte[] audioByte = mHciCloudTtsHelper.getResultByte();
                if (fullByte == null) {
                    fullByte = audioByte;
                } else {
                    fullByte = addByte(fullByte, audioByte);
                }
            }

            if (fullByte != null) {
                FileOutputStream mFos = initFileOutputStream(filePath);
                mFos.write(fullByte);
                mFos.flush();
                mFos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        HciCloudSysHelper mHciCloudSysHelper = HciCloudSysHelper.getInstance();
        HciCloudTtsHelper mHciCloudTtsHelper = HciCloudTtsHelper.getInstance();
        this.mHciCloudSysHelper = mHciCloudSysHelper;
        this.mHciCloudTtsHelper = mHciCloudTtsHelper;

        // 此方法是线程阻塞的，当且仅当有结果返回才会继续向下执行。
        // 此处只是演示合成能力用法，没有对耗时操作进行处理。需要开发者放入后台线程进行初始化操作
        // 必须首先调用HciCloudSys的初始化方法
        int sysInitResult = mHciCloudSysHelper.init();
        if (sysInitResult != HciErrorCode.HCI_ERR_NONE) {
            System.out.println("hci init error, error code = " + sysInitResult);
            return;
        } else {
            System.out.println("hci init success");
        }

        // 此方法是线程阻塞的，当且仅当有结果返回才会继续向下执行。
        // 此处只是演示合成能力用法，没有对耗时操作进行处理。需要开发者放入后台线程进行初始化操作
        // 只有HciCloudSys初始化成功后，才能调用asr的初始化方法
        int ttsInitResult = mHciCloudTtsHelper.init();
        if (ttsInitResult != HciErrorCode.HCI_ERR_NONE) {
            System.out.println("tts init error " + ttsInitResult);
            return;
        } else {
            System.out.println("tts init success");
        }
    }

    public void release() {
        // -------------反初始化------------------------------------------
        // 终止TTs能力
        int ttsReleaseResult = mHciCloudTtsHelper.release();
        if (ttsReleaseResult != HciErrorCode.HCI_ERR_NONE) {
            System.out.println("hciTtsRelease failed:" + ttsReleaseResult);
            mHciCloudSysHelper.release();
            return;
        } else {
            System.out.println("hciTtsRelease success");
        }

        // 终止 灵云 系统
        int sysReleaseRet = mHciCloudSysHelper.release();
        if (HciErrorCode.HCI_ERR_NONE != sysReleaseRet) {
            System.out.println("hciRelease failed:" + sysReleaseRet);
        }
        System.out.println("hciRelease Success");
    }

    /**
     * 功能描述:合并2个byte
     *
     * @param byte1 数组1
     * @param byte2 数组2
     */
    private static byte[] addByte(byte[] byte1, byte[] byte2) {
        byte[] byte3 = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, byte3, 0, byte1.length);
        System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
        return byte3;
    }

    private static FileOutputStream initFileOutputStream(String filePath) {
        FileOutputStream mFos = null;
        try {
            File file = new File(filePath);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
            mFos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mFos;
    }

    public static TtsConfig setTtsConfig() {
        TtsConfig ttsConfig = new TtsConfig();
        ttsConfig.addParam("capKey", HciCloudSysHelper.capKey);
        //ttsConfig.addParam(" puncmode", "on_rtn");//标点符号读法
        ttsConfig.addParam("audioformat", "mp3");//音频格式
        ttsConfig.addParam(" tagmode", "auto");//标注处理方式
        ttsConfig.addParam(" engmode", "english");//英文阅读方式
        ttsConfig.addParam("digitmode", "auto_number");//数字阅读方式
        ttsConfig.addParam("speed", "4");//语速
        ttsConfig.addParam("volume", "6");//音量
        ttsConfig.addParam("pitch", "5");//声音基频
        ttsConfig.addParam("voicestyle", "normal");//朗读风格
        return ttsConfig;
    }
}
