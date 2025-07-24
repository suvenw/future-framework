package com.suven.framework.util.random;

/**
 * HashHNV算法
 */
public class HashAlgorithms {

    /**   
     * 改进的32位FNV算法1
     *  
     * @param data 数组   
     * @return int值   
     */   
    public static int fnvHash(byte[] data) {
        final int seed = 16777619;//67331902;//16777619;    
        int hash = (int) 2166136261L;//166796261;   
        for(byte b: data) {
        	hash = (hash ^ b) * seed; 
        }
        
        hash += hash << 7;    
        hash ^= hash >> 6;    
        hash += hash << 3;    
        hash ^= hash >> 8;    
        hash += hash << 1;   
        return hash;    
    }
}
