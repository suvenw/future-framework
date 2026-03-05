/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.inters;

import com.suven.framework.common.constants.ReflectionsScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * 
 * @author suvenw@163.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 *     编码规范说明: 1,1 01 001 错误编码的意思,第一位(1)代码是系统,第2位是功能码
 *      第3，4位( eg: 01用户信息模块, 02礼物模块),第5，6，7号(该模块用到的返回提示用号的编码code)
 *      1. 1 00 01 - 100 99(共99个系统级别) 是手端(看7.0 app 2.0) 应用后台系统级别返回来的统一规范编码
 *      1.第1位表示软件平台：1
 *      2.第2位表示功能类型：1.弹窗提示，2.软接连，3.跳转url
 *      3.第3.4位表示模块分类名称：00系统 01 用户 02 验证 03 资源 04 支付 05 资产 06 设备 07 订单 08 活动 09 桌面 10 配置 11 MQ 12 定时 13 第三方 14 应用市场
 *      4.第5，6，7位表示错误序列号；
 *      
 */
public interface IResultCodeEnum {
	Logger logger = LoggerFactory.getLogger(IResultCodeEnum.class);
	/**
	 * 系统响应码
	 *
	 * @return code
	 */

	int getCode();

	/**
	 * 默认系统响应提示，code=0时此处为空
	 *
	 * @return msg
	 */
	String getMsg();

	default public IResultCodeEnum clone(String msg) {
		IResultCodeEnum msgEnum  = MsgEnumType.clone(this,msg);
		return msgEnum;

	}

	default public IResultCodeEnum format(String msgContent) {
		IResultCodeEnum msgEnum = MsgEnumType.format(this,msgContent);
		return msgEnum;
	}

	default public IResultCodeEnum format(String ... msgContent) {
		IResultCodeEnum msgEnum = MsgEnumType.format(this,msgContent);
		return msgEnum;
	}

	static class MsgEnumType implements IResultCodeEnum {

		private int errCode = 1100001;
		private String errMsg = "操作失败!";

		private MsgEnumType(int errCode,String errMsg) {
			this.errCode = errCode;
			this.errMsg = errMsg;
		}

		public static IResultCodeEnum format(IResultCodeEnum enumType, String... msgContent){
			String msg =  String.format(enumType.getMsg(), new Object[] { msgContent });
			return new MsgEnumType(enumType.getCode(),msg);

		}
		public static IResultCodeEnum clone(IResultCodeEnum enumType, String msgContent){
			return new MsgEnumType(enumType.getCode(),msgContent);

		}
		@Override
		public int getCode() {
			return errCode;
		}
		@Override
		public String getMsg() {
			return errMsg;
		}

		public static Map<Integer, IResultCodeEnum> getMsgTypeMap() {
			return msgTypeMap;
		}

		private static Map<Integer, IResultCodeEnum> msgTypeMap = new TreeMap<>();
		private static Set<Integer> checkCodeSet = new HashSet<>();
		static {
			List<IResultCodeEnum> list  =  getIMsgEnum();
			if(list != null && !list.isEmpty()){
				for(IResultCodeEnum imsg : list){
					msgTypeMap.put(imsg.getCode(), imsg);
					if(checkCodeSet.contains(imsg.getCode())){
						logger.error(" IMsgEnum is type double code :[{}] and Msg :[{}] is exist ",imsg.getCode(),imsg.getMsg());
						throw new RuntimeException(" IMsgEnum is type double :" +imsg.getCode()+" and "+ imsg.getMsg() +"is exist ");
					}
					checkCodeSet.add(imsg.getCode());
				}
				checkCodeSet = null;
			}

		}

		@SuppressWarnings("unchecked")
		private static List<IResultCodeEnum> getIMsgEnum() {
			return ReflectionsScan.reflections.getSubTypesOf(IResultCodeEnum.class)
					.stream()
					.filter(Class::isEnum)
					.flatMap(clazz -> {
						try {
							return Stream.of(((Class<? extends Enum<?>>) clazz).getEnumConstants())
									.filter(IResultCodeEnum.class::isInstance)
									.map(IResultCodeEnum.class::cast);
						} catch (Exception e) {
							e.printStackTrace();
							return Stream.empty();
						}
					})
					.collect(Collectors.toList());
		}
		@Override
		public String toString() {
			return "MsgEnumType{" +
					"errCode=" + errCode +
					", errMsg='" + errMsg + '\'' +
					'}';
		}
	}


}
