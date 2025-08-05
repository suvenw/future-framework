package com.suven.framework.util.ids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Title: GlobalId.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 雪花算法实现全局唯一ID生成器
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class GlobalId {

    private long workerId;//最大到32
    private long datacenterId;//最大到32
    private long sequence = 0L;
    private long twepoch = 1288834974657L; //Thu, 04 Nov 2010 01:42:54 GMT
    private long workerIdBits = 5L; //节点ID长度
    private long datacenterIdBits = 5L; //数据中心ID长度
    private long maxWorkerId = -1L ^ (-1L << workerIdBits); //最大支持机器节点数0~31，一共32个
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); //最大支持数据中心节点数0~31，一共32个
    private long sequenceBits = 12L; //序列号12位
    private long workerIdShift = sequenceBits; //机器节点左移12位
    private long datacenterIdShift = sequenceBits + workerIdBits; //数据中心节点左移17位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; //时间毫秒数左移22位
    private long sequenceMask = -1L ^ (-1L << sequenceBits); //4095
     private long lastTimestamp = -1L;

    private static Map<Integer, GlobalId> map = new HashMap();
    
    private static class IdGenHolder {
        private static final GlobalId instance = new GlobalId();
    }

  
    public static GlobalId get(){
        return IdGenHolder.instance;
    }

    public static GlobalId get(IGlobalId idEnum){
        GlobalId idGenerator = map.get(idEnum.getWorkerId());
        if(null == idGenerator){
            long datacenterId = idEnum.getDataCenterId();
//            if( datacenterId == 1){
////                datacenterId = RandomUtil.nextInt(UUIDGenerator.getUUID().hashCode());
//            }
//            datacenterId = 31;
            idGenerator = new GlobalId(idEnum.getWorkerId(), datacenterId);
            map.put(idEnum.getWorkerId(), idGenerator);
        }
      return idGenerator;
    }
    
    public GlobalId() {
        this(0L, 0L);
    }

    public GlobalId(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    
    public synchronized long nextId() {
        long timestamp = timeGen(); //获取当前毫秒数
        //如果服务器时间有问题(时钟后退) 报错。
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp); //自旋等待到下一毫秒
            }
        } else {
            sequence = 0L; //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
        }
        lastTimestamp = timestamp;
        // 最后按照规则拼出ID。
        // 000000000000000000000000000000000000000000  00000            00000       000000000000
        // time                                                               datacenterId   workerId    sequence
         return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen(){
        long timestamp  =System.currentTimeMillis();
        if (timestamp < lastTimestamp) {

            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    //时间偏差大小小于5ms，则等待两倍时间
                    wait(offset << 1);//wait
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        //还是小于，抛异常并上报
                        throw new RuntimeException(String.valueOf(timestamp));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
//                    throw  e;
                }
            } else {
                //throw
                throw new RuntimeException(String.valueOf(timestamp));
            }
        }
        return timestamp;
    }

    public List<Long> nextIds(int count) {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ids.add(nextId());
        }
        return ids;
    }

    public static void main(String[] args) {
		System.out.println( GlobalId.get(GlobalIdEnum.USER).nextId());
	}
}