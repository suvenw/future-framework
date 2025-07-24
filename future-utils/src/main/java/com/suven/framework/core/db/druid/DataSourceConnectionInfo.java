package com.suven.framework.core.db.druid;

public class DataSourceConnectionInfo {
        String url;
        String username;
        String password;
        String passwordKey;
        String driverClassName;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPasswordKey() {
            return passwordKey;
        }

        public void setPasswordKey(String passwordKey) {
            this.passwordKey = passwordKey;
        }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}