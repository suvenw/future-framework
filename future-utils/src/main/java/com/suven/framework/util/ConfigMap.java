package com.suven.framework.util;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joven on 2017/5/19.
 */
public class ConfigMap {

    private static Map<String,Map<String,Config>> configMap = new HashMap<>();
//    private static final int initValue = 100;
    private static final String prefixKey = "111";
    
    //初始化map数据；
    public static  boolean initMap(List<Config> list){
        if(null != list && !list.isEmpty()){
            for (Config config : list){
                if(configMap.containsKey(config.getPrefixKey())){
                    Map<String,Config> map = configMap.get(config.getPrefixKey());
                    map.put(config.getKey(),config);
                }else {
                    Map<String,Config> map = new HashMap<>();
                    map.put(config.getKey(),config);
                    configMap.put(config.getPrefixKey(),map);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 验证map是否已初始化；
     * @return
     */
    public static boolean checkMapIsEmpty(String prefixKey){
        if(configMap.isEmpty()){
            return true;
        }else {
            Map<String,Config> map = configMap.get(prefixKey);
            return null == map || map.isEmpty();
        }
    }
    
    public static Config updateKey(String prefixKey, long coinValue, long time){

       
        Map<String,Config> map = configMap.get(prefixKey);
        if(null == map || map.isEmpty()){
            return null;
        }
        long  initValue = 0;
        Config config = map.get(String.valueOf(initValue));
        if(null == config || config.getValue() == 0){
            return null;
        }
        initValue = config.getValue();
        long coin  = coinValue / initValue * initValue;
        String key = String.valueOf(coin);
        config = map.get(key);
        if(null != config && config.getValue() == 0){
            config.setValue(time);
            map.put(key,config);
            configMap.put(prefixKey,map);
            return config;
        }
        return null;
    }
    



   
//    public static void main(String[] agr){
//        String prefixKey = "111";
//        List<Config> list = new ArrayList<>();
//        list.add(new Config(3,prefixKey,"0",100));
//        list.add(new Config(1,prefixKey,"100",0));
//        list.add(new Config(2,prefixKey,"200",0));
//        boolean is = ConfigMap.checkMapIsEmpty(prefixKey);
//        if(is){
//            is = ConfigMap.initMap(list);
//        }
//        Config config = ConfigMap.updateKey(prefixKey,111,100);
//        System.out.print(config.toString());
//
//    }

    public static class Config{

        public Config(){}
        public Config(long id, String prefixKey, String key, long value) {
            this.id = id;
            this.prefixKey = prefixKey;
            this.key = key;
            this.value = value;
        }

        private long id;
        private String prefixKey;
        private String key;
        private long value;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public String getPrefixKey() {
            return prefixKey;
        }

        public void setPrefixKey(String prefixKey) {
            this.prefixKey = prefixKey;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "id=" + id +
                    ", prefixKey='" + prefixKey + '\'' +
                    ", key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}



