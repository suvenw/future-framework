package com.suven.framework.sys.vo.response;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.data.entity.BaseBeanClone;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;

import java.io.Serializable;

/**
     * * 部门表 封装树结构的部门的名称的实体类
     */
    @ApiDoc(module = "部门名返回类")
    public  class DepartTreeResponseVo extends BaseBeanClone implements Serializable {
        private static final long serialVersionUID = 1L;

        // 主键ID
        @ApiDesc("部门表ID")
        private long key;

        // 主键ID
        @ApiDesc("部门表ID")
        private long value;

        // 部门名称
        @ApiDesc("部门名称")
        private String title;

        public static DepartTreeResponseVo build() {
            return new DepartTreeResponseVo();
        }

        /**
         * 该方法为用户部门的实现类所使用
         *
         * @param sysDepart
         * @return
         */
        public DepartTreeResponseVo convertByUserDepart(SysDepartResponseDto sysDepart) {
            this.key = sysDepart.getId();
            this.value = sysDepart.getId();
            this.title = sysDepart.getDepartName();
            return this;
        }


        public long getKey() {
            return key;
        }

        public DepartTreeResponseVo setKey(long key) {
            this.key = key;
            return this;
        }

        public long getValue() {
            return value;
        }

        public DepartTreeResponseVo setValue(long value) {
            this.value = value;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public DepartTreeResponseVo setTitle(String title) {
            this.title = title;
            return this;
        }
    }
