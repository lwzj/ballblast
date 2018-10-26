package com.lsj.ballblast.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lsj.ballblast.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/8/28 14:10
 */
@Log4j2
public class App {
    public static final String APP_ID = "";
    public static final String APP_SECRET = "";
    public static final String GAME_NAME = "ballblast";
    public static final String GAME_TYPE = "mini";
    public static final String LOGIN_API = "https://api.weixin.qq.com/sns/jscode2session";
    private static String version = "v1.1";


    public static final ConcurrentHashMap<Integer, Map<String, Object>> LEVEL_MAP = new ConcurrentHashMap(700);
    //    public static final List<PetSkin> CAN_GET_NORMAL_PET_SKIN_LIST = Collections.synchronizedList(new ArrayList<>());
//    public static final List<PetSkin> CAN_GET_SUPER_PET_SKIN_LIST = Collections.synchronizedList(new ArrayList<>());
//    public static final ConcurrentHashMap<String, PetSkin> OPEN_BOX_REWARD_MAP = new ConcurrentHashMap<>(4);
    public static final ConcurrentHashMap<Integer, Long> MOUSE_LIST = new ConcurrentHashMap(35);


    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        App.version = version;
    }

    /**
     * 初始化游戏关卡
     */
    public static void loadGameLevel() {
        log.info("加载游戏关卡配置...");
        JSONObject jsonObject = loadJSONObject("classpath:config/gameInfo.json").getJSONObject("gameInfo");
        for (String level : jsonObject.keySet()) {
            JSONObject jsonObject1 = jsonObject.getJSONObject(level);
            Map<String, Object> levelTempMap = new HashMap(20);
            for (Map.Entry<String, Object> stringObjectEntry : jsonObject1.entrySet()) {
                levelTempMap.put(stringObjectEntry.getKey(), stringObjectEntry.getValue());
            }
            LEVEL_MAP.put(Integer.parseInt(level), levelTempMap);
        }
    }


    private static JSONObject loadJSONObject(String path) {
        File file;
        BufferedReader in = null;
        try {
            file = ResourceUtils.getFile(path);
            if (file.exists()) {
                in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StringUtil.UTF8));
                StringBuffer str = new StringBuffer();
                String line;
                while ((line = in.readLine()) != null) {
                    str.append(line);
                }
                return JSON.parseObject(str.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
