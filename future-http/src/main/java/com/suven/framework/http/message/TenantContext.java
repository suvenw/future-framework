package com.suven.framework.http.message;

public class TenantContext {
    public  static final String tenantId = "tenantId";
    public static final String TENANT_ID = "tenant_Id";
    public static final String TENANT__ID = "tenant-Id";

    private static final ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();
           
    public static String getCurrentTenant() {
        return currentTenant.get();
    }
           
    public static void setCurrentTenant(String tenantId) {
        currentTenant.set(tenantId);
    }
               
    public static void clear() {
        currentTenant.remove();
    }
}