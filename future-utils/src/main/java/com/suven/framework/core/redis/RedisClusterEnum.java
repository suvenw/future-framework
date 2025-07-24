package com.suven.framework.core.redis;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * redis Cluster enum 类实现
 * 
 * @author joven
 *
 *
 */
public enum RedisClusterEnum  implements RedisGroupEnumInterface,RedisConstants{



	REDIS_GROUP_DEFAULT(DEFAULT_REDIS_GROUP,""),
	/** 1-9 **/

	REDIS_GROUP_ONE          (       ONE_REDIS_GROUP       ,"one"),
	REDIS_GROUP_TOW          (       TOW_REDIS_GROUP       ,"tow"),
	REDIS_GROUP_THREE        (       THREE_REDIS_GROUP     ,"three"),
	REDIS_GROUP_FOUR         (       FOUR_REDIS_GROUP      ,"four"),
	REDIS_GROUP_FIVE         (       FIVE_REDIS_GROUP      ,"five"),
	REDIS_GROUP_SIX          (       SIX_REDIS_GROUP       ,"six"),
	REDIS_GROUP_SEVEN        (       SEVEN_REDIS_GROUP     ,"seven"),
	REDIS_GROUP_EIGHT        (       EIGHT_REDIS_GROUP     ,"eight"),
	REDIS_GROUP_NINE         (       NINE_REDIS_GROUP      ,"nine"),


//	/** 10-19 **/
//	REDIS_GROUP_TEN           (       TEN_REDIS_GROUP           ,"ten"),
//	REDIS_GROUP_ELEVEN        (       ELEVEN_REDIS_GROUP        ,"eleven"),
//	REDIS_GROUP_TWELVE        (       TWELVE_REDIS_GROUP        ,"twelve"),
//	REDIS_GROUP_THIRTEEN      (       THIRTEEN_REDIS_GROUP      ,"thirteen"),
//	REDIS_GROUP_FOURTEEN      (       FOURTEEN_REDIS_GROUP      ,"fourteen"),
//	REDIS_GROUP_FIFTEEN       (       FIFTEEN_REDIS_GROUP       ,"fifteen"),
//	REDIS_GROUP_SIXTEEN       (       SIXTEEN_REDIS_GROUP       ,"sixteen"),
//	REDIS_GROUP_SEVENTEEN     (       SEVENTEEN_REDIS_GROUP     ,"seventeen"),
//	REDIS_GROUP_EIGHTEEN      (       EIGHTEEN_REDIS_GROUP      ,"eighteen"),
//	REDIS_GROUP_NINETEEN      (       NINETEEN_REDIS_GROUP      ,"nineteen"),

//	/** 20-29 **/
//	REDIS_GROUP_TWENTY        (       TWENTY_REDIS_GROUP           ,"twenty"),
//	REDIS_GROUP_TWENTY_ONE    (       TWENTY_ONE_REDIS_GROUP       ,"twenty.one"),
//	REDIS_GROUP_TWENTY_TOW    (       TWENTY_TOW_REDIS_GROUP       ,"twenty.tow"),
//	REDIS_GROUP_TWENTY_THREE  (       TWENTY_THREE_REDIS_GROUP     ,"twenty.three"),
//	REDIS_GROUP_TWENTY_FOUR   (       TWENTY_FOUR_REDIS_GROUP      ,"twenty.four"),
//	REDIS_GROUP_TWENTY_FIX    (       TWENTY_FIX_REDIS_GROUP       ,"twenty.fix"),
//	REDIS_GROUP_TWENTY_SIX    (       TWENTY_SIX_REDIS_GROUP       ,"twenty.six"),
//	REDIS_GROUP_TWENTY_SEVEN  (       TWENTY_SEVEN_REDIS_GROUP     ,"twenty.seven"),
//	REDIS_GROUP_TWENTY_EIGHT  (       TWENTY_EIGHT_REDIS_GROUP     ,"twenty.eight"),
//	REDIS_GROUP_TWENTY_NINE   (       TWENTY_NINE_REDIS_GROUP      ,"twenty.nine"),
//
//
//
//	/** 30-39 **/
//	REDIS_GROUP_THIRTY         (       THIRTY_REDIS_GROUP           ,"thirty"),
//	REDIS_GROUP_THIRTY_ONE     (       THIRTY_ONE_REDIS_GROUP       ,"thirty.one"),
//	REDIS_GROUP_THIRTY_TOW     (       THIRTY_TOW_REDIS_GROUP       ,"thirty.tow"),
//	REDIS_GROUP_THIRTY_THREE   (       THIRTY_THREE_REDIS_GROUP     ,"thirty.three"),
//	REDIS_GROUP_THIRTY_FOUR    (       THIRTY_FOUR_REDIS_GROUP      ,"thirty.four"),
//	REDIS_GROUP_THIRTY_FIX     (       THIRTY_FIX_REDIS_GROUP       ,"thirty.fix"),
//	REDIS_GROUP_THIRTY_SIX     (       THIRTY_SIX_REDIS_GROUP       ,"thirty.six"),
//	REDIS_GROUP_THIRTY_SEVEN   (       THIRTY_SEVEN_REDIS_GROUP     ,"thirty.seven"),
//	REDIS_GROUP_THIRTY_EIGHT   (       THIRTY_EIGHT_REDIS_GROUP     ,"thirty.eight"),
//	REDIS_GROUP_THIRTY_NINE    (       THIRTY_NINE_REDIS_GROUP      ,"thirty.nine"),
//
//	/** 40-49 **/
//	REDIS_GROUP_FORTY          (       FORTY_REDIS_GROUP           ,"forty"),
//	REDIS_GROUP_FORTY_ONE      (       FORTY_ONE_REDIS_GROUP       ,"forty.one"),
//	REDIS_GROUP_FORTY_TOW      (       FORTY_TOW_REDIS_GROUP       ,"forty.tow"),
//	REDIS_GROUP_FORTY_THREE    (       FORTY_THREE_REDIS_GROUP     ,"forty.three"),
//	REDIS_GROUP_FORTY_FOUR     (       FORTY_FOUR_REDIS_GROUP      ,"forty.four"),
//	REDIS_GROUP_FORTY_FIX      (       FORTY_FIX_REDIS_GROUP       ,"forty.fix"),
//	REDIS_GROUP_FORTY_SIX      (       FORTY_SIX_REDIS_GROUP       ,"forty.six"),
//	REDIS_GROUP_FORTY_SEVEN    (       FORTY_SEVEN_REDIS_GROUP     ,"forty.seven"),
//	REDIS_GROUP_FORTY_EIGHT    (       FORTY_EIGHT_REDIS_GROUP     ,"forty.eight"),
//	REDIS_GROUP_FORTY_NINE     (       FORTY_NINE_REDIS_GROUP      ,"forty.nine"),
//
//
//	/** 50-59 **/
//	REDIS_GROUP_FIFTY          (       FIFTY_REDIS_GROUP           ,"fifty"),
//	REDIS_GROUP_FIFTY_ONE      (       FIFTY_ONE_REDIS_GROUP       ,"fifty.one"),
//	REDIS_GROUP_FIFTY_TOW      (       FIFTY_TOW_REDIS_GROUP       ,"fifty.tow"),
//	REDIS_GROUP_FIFTY_THREE    (       FIFTY_THREE_REDIS_GROUP     ,"fifty.three"),
//	REDIS_GROUP_FIFTY_FOUR     (       FIFTY_FOUR_REDIS_GROUP      ,"fifty.four"),
//	REDIS_GROUP_FIFTY_FIX      (       FIFTY_FIX_REDIS_GROUP       ,"fifty.fix"),
//	REDIS_GROUP_FIFTY_SIX      (       FIFTY_SIX_REDIS_GROUP       ,"fifty.six"),
//	REDIS_GROUP_FIFTY_SEVEN    (       FIFTY_SEVEN_REDIS_GROUP     ,"fifty.seven"),
//	REDIS_GROUP_FIFTY_EIGHT    (       FIFTY_EIGHT_REDIS_GROUP     ,"fifty.eight"),
//	REDIS_GROUP_FIFTY_NINE     (       FIFTY_NINE_REDIS_GROUP      ,"fifty.nine"),
//
//
//
//	/** 60-69 **/
//	REDIS_GROUP_SIXTY            (       SIXTY_REDIS_GROUP           ,"sixty"),
//	REDIS_GROUP_SIXTY_ONE        (       SIXTY_ONE_REDIS_GROUP       ,"sixty.one"),
//	REDIS_GROUP_SIXTY_TOW        (       SIXTY_TOW_REDIS_GROUP       ,"sixty.tow"),
//	REDIS_GROUP_SIXTY_THREE      (       SIXTY_THREE_REDIS_GROUP     ,"sixty.three"),
//	REDIS_GROUP_SIXTY_FOUR       (       SIXTY_FOUR_REDIS_GROUP      ,"sixty.four"),
//	REDIS_GROUP_SIXTY_FIX        (       SIXTY_FIX_REDIS_GROUP       ,"sixty.fix"),
//	REDIS_GROUP_SIXTY_SIX        (       SIXTY_SIX_REDIS_GROUP       ,"sixty.six"),
//	REDIS_GROUP_SIXTY_SEVEN      (       SIXTY_SEVEN_REDIS_GROUP     ,"sixty.seven"),
//	REDIS_GROUP_SIXTY_EIGHT      (       SIXTY_EIGHT_REDIS_GROUP     ,"sixty.eight"),
//	REDIS_GROUP_SIXTY_NINE       (       SIXTY_NINE_REDIS_GROUP      ,"sixty.nine"),
//
//
//	/** 70-79 **/
//	REDIS_GROUP_SEVENTY          (       SEVENTY_REDIS_GROUP           ,"seventy"),
//	REDIS_GROUP_SEVENTY_ONE      (       SEVENTY_ONE_REDIS_GROUP       ,"seventy.one"),
//	REDIS_GROUP_SEVENTY_TOW      (       SEVENTY_TOW_REDIS_GROUP       ,"seventy.tow"),
//	REDIS_GROUP_SEVENTY_THREE    (       SEVENTY_THREE_REDIS_GROUP     ,"seventy.three"),
//	REDIS_GROUP_SEVENTY_FOUR     (       SEVENTY_FOUR_REDIS_GROUP      ,"seventy.four"),
//	REDIS_GROUP_SEVENTY_FIX      (       SEVENTY_FIX_REDIS_GROUP       ,"seventy.fix"),
//	REDIS_GROUP_SEVENTY_SIX      (       SEVENTY_SIX_REDIS_GROUP       ,"seventy.six"),
//	REDIS_GROUP_SEVENTY_SEVEN    (       SEVENTY_SEVEN_REDIS_GROUP     ,"seventy.seven"),
//	REDIS_GROUP_SEVENTY_EIGHT    (       SEVENTY_EIGHT_REDIS_GROUP     ,"seventy.eight"),
//	REDIS_GROUP_SEVENTY_NINE     (       SEVENTY_NINE_REDIS_GROUP      ,"seventy.nine"),
//
//	/** 80-89 **/
//	REDIS_GROUP_EIGHTY          (       EIGHTY_REDIS_GROUP           ,"eighty"),
//	REDIS_GROUP_EIGHTY_ONE      (       EIGHTY_ONE_REDIS_GROUP       ,"eighty.one"),
//	REDIS_GROUP_EIGHTY_TOW      (       EIGHTY_TOW_REDIS_GROUP       ,"eighty.tow"),
//	REDIS_GROUP_EIGHTY_THREE    (       EIGHTY_THREE_REDIS_GROUP     ,"eighty.three"),
//	REDIS_GROUP_EIGHTY_FOUR     (       EIGHTY_FOUR_REDIS_GROUP      ,"eighty.four"),
//	REDIS_GROUP_EIGHTY_FIX      (       EIGHTY_FIX_REDIS_GROUP       ,"eighty.fix"),
//	REDIS_GROUP_EIGHTY_SIX      (       EIGHTY_SIX_REDIS_GROUP       ,"eighty.six"),
//	REDIS_GROUP_EIGHTY_SEVEN    (       EIGHTY_SEVEN_REDIS_GROUP     ,"eighty.seven"),
//	REDIS_GROUP_EIGHTY_EIGHT    (       EIGHTY_EIGHT_REDIS_GROUP     ,"eighty.eight"),
//	REDIS_GROUP_EIGHTY_NINE     (       EIGHTY_NINE_REDIS_GROUP      ,"eighty.nine"),



	;


	
	private static Map<String, RedisClusterEnum> nameMap = new LinkedHashMap<>();
	private static Map<Integer, RedisClusterEnum> indexMap = new LinkedHashMap<>();

	static {

		for (RedisClusterEnum type : values()) {
			nameMap.put(type.name(), type);
			indexMap.put(type.id, type);
		}
	}

	private final int id;
	private final String config;
	

	RedisClusterEnum(int id, String config) {
		this.id = id;
		this.config = config;
	}

	public final int getGroup() {
		return id;
	}

	@Override
	public final String getGroupName() {
		return name().toLowerCase();
	}
	@Override
	public String getConfig() {
		return config;
	}



	public static Collection<RedisClusterEnum>  getRedisClusters(){
		if(nameMap == null || nameMap.isEmpty()){
			for (RedisClusterEnum type : values()) {
				nameMap.put(type.name(), type);
				indexMap.put(type.getGroup(), type);
			}
		}
		return nameMap.values();
	}
	
	
	public static RedisClusterEnum findByNumType(int numType) {
		RedisClusterEnum rc = indexMap.get(numType);
		if(null != rc ){
			return rc;
		}
		return REDIS_GROUP_DEFAULT;
		
	}


}
