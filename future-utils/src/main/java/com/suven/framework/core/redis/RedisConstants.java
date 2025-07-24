package com.suven.framework.core.redis;


/**   
 * @Title: RedisConstants.java 
 * @author Joven.wang   
 * @date   2016年7月28日
 * @version V1.0  
 * Description: TODO(说明)
 */

public interface RedisConstants {

    public static boolean isOpenRedis_RW = true;
    public static boolean isOpenRedis_R = true;
    public static boolean isOpenRedis_W = true;

    public  final String SPRING_REDIS_ENABLED = "spring.redis.enabled";
    public  final String SPRING_REDIS_CONFIG = "spring.redis";
    public static final String REDIS_LOCK_PREFIX = "redis_lock:";


    public static int REDIS_LOCK_KEY_MILISECONDS  = 1000;

    public static final int REDIS_NONE =  -1;
    public static final int REDIS_ZERO =   0;
    public static final int ONE_SECOND =   1;
    public static final int THREE_SECOND = 3;
    public static final int TWO_SECOND = 2;
    public static final int FIVE_SECOND =  5;
    public static final int TEN_SECOND =  10;
    public static final int HALF_MINUTE = 30;
    public static final int ONE_MINUTE =  60;
    public static final int TWO_MINUTE =   2 * 60;
    public static final int THREE_MINUTE = 3 * 60;
    public static final int FIVE_MINUTE =  5 * 60;
    public static final int TEN_MINUTE =  10 * 60 ;
    public static final int HALF_HOUR =   30 * 60;
    public static final int ONE_HOUR =    60 * 60;
    public static final int HALF_DAY =    1 * 12 * 60 * 60;
    public static final int ONE_DAY =     1 * 24 * 60 * 60;
    public static final int TWO_DAY =     2 * 24 * 60 * 60;
    public static final int THREE_DAY =   3 * 24 * 60 * 60;
    public static final int FOUR_DAY =    4 * 24 * 60 * 60;
    public static final int FIVE_DAY =    5 * 24 * 60 * 60;
    public static final int SIX_DAY =     6 * 24 * 60 * 60;
    public static final int SEVEN_DAY =   7 * 24 * 60 * 60;
    public static final int TEN_DAY =    10 * 24 * 60 * 60;
    public static final int HALF_MONTH = 15 * 24 * 60 * 60;
    public static final int ONE_MONTH =  30 * 24 * 60 * 60;
    public static final int MAX_DAY =    Integer.MAX_VALUE;

    public static final int DEFAULT_REDIS_GROUP = -1;
    public static final int ONE_REDIS_GROUP = 1;
    public static final int TOW_REDIS_GROUP = 2;
    public static final int THREE_REDIS_GROUP = 3;
    public static final int FOUR_REDIS_GROUP = 4;
    public static final int FIVE_REDIS_GROUP = 5;
    public static final int SIX_REDIS_GROUP = 6;
    public static final int SEVEN_REDIS_GROUP = 7;
    public static final int EIGHT_REDIS_GROUP = 8;
    public static final int NINE_REDIS_GROUP = 9 ;
    public static final int TEN_REDIS_GROUP  = 10;

    public static final int ELEVEN_REDIS_GROUP = 11;
    public static final int TWELVE_REDIS_GROUP = 12;
    public static final int THIRTEEN_REDIS_GROUP = 13;
    public static final int FOURTEEN_REDIS_GROUP = 14;
    public static final int FIFTEEN_REDIS_GROUP = 15;
    public static final int SIXTEEN_REDIS_GROUP = 16;
    public static final int SEVENTEEN_REDIS_GROUP = 17;
    public static final int EIGHTEEN_REDIS_GROUP = 18;
    public static final int NINETEEN_REDIS_GROUP = 19 ;

    public static final int TWENTY_REDIS_GROUP  = 20;
    public static final int TWENTY_ONE_REDIS_GROUP = 21;
    public static final int TWENTY_TOW_REDIS_GROUP = 22;
    public static final int TWENTY_THREE_REDIS_GROUP = 23;
    public static final int TWENTY_FOUR_REDIS_GROUP = 24;
    public static final int TWENTY_FIX_REDIS_GROUP = 25;
    public static final int TWENTY_SIX_REDIS_GROUP = 26;
    public static final int TWENTY_SEVEN_REDIS_GROUP = 27;
    public static final int TWENTY_EIGHT_REDIS_GROUP = 28;
    public static final int TWENTY_NINE_REDIS_GROUP = 29 ;

    public static final int THIRTY_REDIS_GROUP  = 30;
    public static final int THIRTY_ONE_REDIS_GROUP = 31;
    public static final int THIRTY_TOW_REDIS_GROUP = 32;
    public static final int THIRTY_THREE_REDIS_GROUP = 33;
    public static final int THIRTY_FOUR_REDIS_GROUP = 34;
    public static final int THIRTY_FIX_REDIS_GROUP = 35;
    public static final int THIRTY_SIX_REDIS_GROUP = 36;
    public static final int THIRTY_SEVEN_REDIS_GROUP = 37;
    public static final int THIRTY_EIGHT_REDIS_GROUP = 38;
    public static final int THIRTY_NINE_REDIS_GROUP = 39 ;

    public static final int FORTY_REDIS_GROUP  = 40;
    public static final int FORTY_ONE_REDIS_GROUP = 41;
    public static final int FORTY_TOW_REDIS_GROUP = 42;
    public static final int FORTY_THREE_REDIS_GROUP = 43;
    public static final int FORTY_FOUR_REDIS_GROUP = 44;
    public static final int FORTY_FIX_REDIS_GROUP = 45;
    public static final int FORTY_SIX_REDIS_GROUP = 46;
    public static final int FORTY_SEVEN_REDIS_GROUP = 47;
    public static final int FORTY_EIGHT_REDIS_GROUP = 48;
    public static final int FORTY_NINE_REDIS_GROUP = 49 ;


    public static final int FIFTY_REDIS_GROUP  = 50;
    public static final int FIFTY_ONE_REDIS_GROUP = 51;
    public static final int FIFTY_TOW_REDIS_GROUP = 52;
    public static final int FIFTY_THREE_REDIS_GROUP = 53;
    public static final int FIFTY_FOUR_REDIS_GROUP = 54;
    public static final int FIFTY_FIX_REDIS_GROUP = 55;
    public static final int FIFTY_SIX_REDIS_GROUP = 56;
    public static final int FIFTY_SEVEN_REDIS_GROUP = 57;
    public static final int FIFTY_EIGHT_REDIS_GROUP = 58;
    public static final int FIFTY_NINE_REDIS_GROUP = 59 ;

    public static final int SIXTY_REDIS_GROUP  = 60;
    public static final int SIXTY_ONE_REDIS_GROUP = 61;
    public static final int SIXTY_TOW_REDIS_GROUP = 62;
    public static final int SIXTY_THREE_REDIS_GROUP = 63;
    public static final int SIXTY_FOUR_REDIS_GROUP = 64;
    public static final int SIXTY_FIX_REDIS_GROUP = 67;
    public static final int SIXTY_SIX_REDIS_GROUP = 66;
    public static final int SIXTY_SEVEN_REDIS_GROUP = 67;
    public static final int SIXTY_EIGHT_REDIS_GROUP = 68;
    public static final int SIXTY_NINE_REDIS_GROUP = 69 ;

    public static final int SEVENTY_REDIS_GROUP  = 70;
    public static final int SEVENTY_ONE_REDIS_GROUP = 71;
    public static final int SEVENTY_TOW_REDIS_GROUP = 72;
    public static final int SEVENTY_THREE_REDIS_GROUP = 73;
    public static final int SEVENTY_FOUR_REDIS_GROUP = 74;
    public static final int SEVENTY_FIX_REDIS_GROUP = 78;
    public static final int SEVENTY_SIX_REDIS_GROUP = 76;
    public static final int SEVENTY_SEVEN_REDIS_GROUP = 77;
    public static final int SEVENTY_EIGHT_REDIS_GROUP = 78;
    public static final int SEVENTY_NINE_REDIS_GROUP = 79 ;

    public static final int EIGHTY_REDIS_GROUP  = 80;
    public static final int EIGHTY_ONE_REDIS_GROUP = 81;
    public static final int EIGHTY_TOW_REDIS_GROUP = 82;
    public static final int EIGHTY_THREE_REDIS_GROUP = 83;
    public static final int EIGHTY_FOUR_REDIS_GROUP = 84;
    public static final int EIGHTY_FIX_REDIS_GROUP = 85;
    public static final int EIGHTY_SIX_REDIS_GROUP = 86;
    public static final int EIGHTY_SEVEN_REDIS_GROUP = 87;
    public static final int EIGHTY_EIGHT_REDIS_GROUP = 88;
    public static final int EIGHTY_NINE_REDIS_GROUP = 89 ;





}
