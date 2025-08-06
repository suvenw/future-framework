package com.suven.framework.sys.service;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.sys.dao.SysUserDepartDao;
import com.suven.framework.sys.dao.SysUserRoleDao;
import com.suven.framework.sys.entity.SysUserRole;
import com.suven.framework.sys.vo.request.AllStatusRequestVo;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.core.ObjectTrue;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.InputStream;


import com.suven.framework.sys.entity.SysUser;
import com.suven.framework.sys.dao.SysUserDao;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;


/**
 * ClassName: SysUserServiceImpl.java
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:37
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 RPC业务接口逻辑实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

@Service
public class SysUserServiceImpl implements SysUserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysUserDepartDao sysUserDepartDao;


    /**
     * 保存用户表同时更新数据库和缓存的实现方法
     *
     * @param sysUserRequestDto SysUserResponseDto
     * @return
     */
    @Override
    public SysUserResponseDto saveSysUser(SysUserRequestDto sysUserRequestDto) {
        if (sysUserRequestDto == null) {
            return null;
        }

        SysUser sysUser = SysUser.build().clone(sysUserRequestDto);
        sysUser.setSalt(CryptUtil.generateSalt(8));

        String userPassword = coverPassword(sysUser.getPassword(), sysUser.getSalt());
        sysUser.setPassword(userPassword);
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());
        boolean result = sysUserDao.save(sysUser);
        if (!result) {
            return null;
        }

        SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(sysUser);
        return sysUserResponseDto;


    }

    private static String coverPassword(String sourcePassword, String Salt) {
        String password = CryptUtil.md5(sourcePassword + Salt);
        return password;
    }

    /**
     * 保存用户表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     *
     * @param sysUserRequestDto SysUserResponseDto
     * @return
     */
    @Override
    public SysUserResponseDto saveIdSysUser(SysUserRequestDto sysUserRequestDto) {
        if (sysUserRequestDto == null) {
            return null;
        }
        SysUser sysUser = SysUser.build().clone(sysUserRequestDto);
        sysUser = sysUserDao.saveId(sysUser);
        if (null == sysUser) {
            return null;
        }
        SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(sysUser);
        return sysUserResponseDto;


    }

    /**
     * 保存用户表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     *
     * @param entityList SysUserRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysUser(Collection<SysUserRequestDto> entityList) {
        if (null == entityList) {
            return false;
        }
        List<SysUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUser.build().clone(e)));
        boolean result = sysUserDao.saveBatchId(list);
        return result;
    }

    /**
     * 批量保存用户表同时更新数据库和缓存的实现方法
     *
     * @param entityList SysUserRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysUser(Collection<SysUserRequestDto> entityList, int batchSize) {
        if (null == entityList || entityList.size() != batchSize) {
            return false;
        }
        List<SysUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUser.build().clone(e)));
        boolean result = sysUserDao.saveBatch(list, batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysUser(Collection<SysUserRequestDto> entityList, int batchSize) {
        if (null == entityList || entityList.size() != batchSize) {
            return false;
        }
        List<SysUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUser.build().clone(e)));
        boolean result = sysUserDao.saveOrUpdateBatch(list, batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysUserRequestDto> entityList, int batchSize) {
        if (null == entityList || entityList.size() != batchSize) {
            return false;
        }
        List<SysUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUser.build().clone(e)));
        boolean result = sysUserDao.updateBatchById(list, batchSize);
        return result;
    }

    /**
     * 更新用户表同时更新数据库和缓存的实现方法
     *
     * @param sysUserRequestDto SysUserResponseDto
     * @return
     */
    @Override
    public boolean updateSysUser(SysUserRequestDto sysUserRequestDto) {

        if (null == sysUserRequestDto) {
            return false;
        }
        SysUser getSysUser = sysUserDao.getById(sysUserRequestDto.getId());
        if(getSysUser == null){
            return false;
        }
        SysUser sysUser = SysUser.build().clone(sysUserRequestDto);
        sysUser.setPassword(getSysUser.getPassword());
        sysUser.setSalt(getSysUser.getSalt());
        sysUser.setUpdateTime(new Date());
        return sysUserDao.updateById(sysUser);


    }

    @Override
    public boolean updateSysUserPassWord(SysUserRequestDto sysUserRequestDto) {

        if (null == sysUserRequestDto) {
            return false;
        }
        SysUser sysUser = SysUser.build().clone(sysUserRequestDto);
        sysUser.setUpdateTime(new Date());
        return sysUserDao.updateById(sysUser);


    }


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     *
     * @param idList
     * @return
     */
    @Override
    public int delSysUserByIds(List<Long> idList) {
        boolean result = false;
        if (null == idList) {
            return ResultEnum.FAIL.id();
        }
        if (idList.size() == 1) {
            result = sysUserDao.removeById(idList.get(0));
        } else {
            result = sysUserDao.removeByIds(idList);
        }
        if (result) {
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象用户表实现缓存和数据库更新的方法
     *
     * @param sysUserId
     * @return
     */
    @Override
    public SysUserResponseDto getSysUserById(long sysUserId) {
        if (sysUserId < 0) {
            return null;
        }
        SysUser sysUser = sysUserDao.getById(sysUserId);
        if (sysUser == null) {
            return null;
        }
        SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(sysUser);

        return sysUserResponseDto;

    }

    /**
     * 通过参数limit0,1获取对象用户表的查询方法
     *
     * @param queryEnum
     * @return
     */
    @Override
    public SysUserResponseDto getSysUserByOne(SysUserQueryEnum queryEnum, SysUserRequestDto sysUserRequestDto) {
        if (sysUserRequestDto == null) {
            return null;
        }
        QueryWrapper<SysUser> queryWrapper = sysUserDao.builderQueryEnum(queryEnum, sysUserRequestDto);
        //分页对象        PageHelper
        Page<SysUser> iPage = new Page<>(0, 1);
        iPage.setSearchCount(false);
        List<SysUser> list = sysUserDao.getListByPage(iPage, queryWrapper);
        if (null == list || list.isEmpty()) {
            return null;
        }
        SysUser sysUser = list.get(0);
        SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(sysUser);

        return sysUserResponseDto;
    }


    /**
     * 通过条件查询SysUser信息列表,实现查找缓存和数据库的方法,但不统计总页数
     *
     * @param paramObject Object
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @Override
    public List<SysUserResponseDto> getSysUserListByQuery(Object paramObject, SysUserQueryEnum queryEnum) {

        QueryWrapper<SysUser> queryWrapper = sysUserDao.builderQueryEnum(queryEnum, paramObject);

        List<SysUser> list = sysUserDao.getListByQuery(queryWrapper);
        if (null == list) {
            list = new ArrayList<>();
        }
        List<SysUserResponseDto> resDtoList = IterableConvert.convertList(list, SysUserResponseDto.class);
        return resDtoList;

    }


    /**
     * 通过分页获取SysUser信息列表,实现查找缓存和数据库的方法,但不统计总页数
     *
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @Override
    public List<SysUserResponseDto> getSysUserListByPage(Pager page, SysUserQueryEnum queryEnum) {

        QueryWrapper<SysUser> queryWrapper = sysUserDao.builderQueryEnum(queryEnum, page.getParamObject());
        //分页对象        PageHelper
        Page<SysUser> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysUser> list = sysUserDao.getListByPage(iPage, queryWrapper);
        if (null == list) {
            list = new ArrayList<>();
        }
        List<SysUserResponseDto> resDtoList = IterableConvert.convertList(list, SysUserResponseDto.class);
        return resDtoList;

    }


    /**
     * 通过分页获取SysUser 用户表信息实现查找缓存和数据库的方法,不查总页数
     *
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @Override
    public ResponseResultPageVo<SysUserResponseDto> getSysUserByQueryPage(Pager page, SysUserQueryEnum queryEnum) {

        ResponseResultPageVo<SysUserResponseDto> ResponseResultPageVo = new ResponseResultPageVo<>();
        QueryWrapper<SysUser> queryWrapper = sysUserDao.builderQueryEnum(queryEnum, page.getParamObject());
        //分页对象        PageHelper
        Page<SysUser> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysUser> list = sysUserDao.getListByPage(iPage, queryWrapper);
        if (null == list) {
            list = new ArrayList<>();
        }
        List<SysUserResponseDto> resDtoList = IterableConvert.convertList(list, SysUserResponseDto.class);
        boolean isNext = page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList);
        return ResponseResultPageVo;
    }

    /**
     * 通过分页获取SysUser信息列表,实现查找缓存和数据库的方法,并且查询总页数
     *
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @Override
    public ResponseResultPageVo<SysUserResponseDto> getSysUserByNextPage(Pager page, SysUserQueryEnum queryEnum) {
        ResponseResultPageVo<SysUserResponseDto> ResponseResultPageVo = new ResponseResultPageVo<>();
        QueryWrapper<SysUser> queryWrapper = sysUserDao.builderQueryEnum(queryEnum, page.getParamObject());
        ;
        //分页对象        PageHelper
        Page<SysUser> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysUser> list = sysUserDao.getListByPage(iPage, queryWrapper);
        if (null == list) {
            list = new ArrayList<>();
        }
        List<SysUserResponseDto> resDtoList = IterableConvert.convertList(list, SysUserResponseDto.class);
        boolean isNext = page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList).toTotal((int) iPage.getTotal());
        return ResponseResultPageVo;

    }


    /**
     * 保存用户表同时更新数据库和缓存的实现方法
     *
     * @return
     */
    public SysUser setSysUser() {
        SysUser sysUser = new SysUser();
        /**
         //sysUser.setUsername (String username);
         //sysUser.setRealname (String realname);
         //sysUser.setPassword (String password);
         //sysUser.setSalt (String salt);
         //sysUser.setAvatar (String avatar);
         //sysUser.setBirthday (Date birthday);
         //sysUser.setSex (int sex);
         //sysUser.setEmail (String email);
         //sysUser.setPhone (String phone);
         //sysUser.setOrgCode (String orgCode);
         //sysUser.setStatus (int status);
         //sysUser.setDelFlag (int delFlag);
         //sysUser.setThirdId (String thirdId);
         //sysUser.setThirdType (String thirdType);
         //sysUser.setActivitiSync (int activitiSync);
         //sysUser.setWorkNo (String workNo);
         //sysUser.setPost (String post);
         //sysUser.setTelephone (String telephone);
         //sysUser.setCreateBy (String createBy);
         //sysUser.setCreateTime (Date createTime);
         //sysUser.setUpdateBy (String updateBy);
         //sysUser.setUpdateTime (Date updateTime);
         //sysUser.setUserIdentity (int userIdentity);
         //sysUser.setDepartIds (String departIds);
         //sysUser.setRelTenantIds (String relTenantIds);
         //sysUser.setClientId (String clientId);
         **/

        return sysUser;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream, sysUserDao, SysUser.class, 0);
    }

    @Override
    public boolean addSysUserRole(long roleId, List<Long> idList) {
        for (long sysUserId : idList) {
            SysUserRole sysUserRole = SysUserRole.build().toUserId(sysUserId).toRoleId(roleId);
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId).eq("user_id", sysUserId);
            SysUserRole one = sysUserRoleDao.getOne(queryWrapper);
            if (one == null) {
                sysUserRoleDao.save(sysUserRole);
            }
        }
        return true;
    }

    @Override
    public boolean deleteUserRole(long roleId, List<Long> idList) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId).in("user_id", idList);
        return sysUserRoleDao.remove(queryWrapper);
    }

    /**
     * @return
     * Title: 启用用户表信息
     * Description:
     * @throw
     * @author suven
     * date 2019-10-18 12:35:25
     */
    public boolean turnOn(List<Long> idList) {
        if (null == idList || idList.isEmpty()) {
            return false;
        }
        SysUser sysUser = sysUserDao.getById(idList.get(0));
        sysUser.toStatus(TbStatusEnum.ENABLE.index());
        sysUser.setUpdateTime(new Date());
        boolean result = sysUserDao.updateById(sysUser);
        return result;
    }

    /**
     * @return
     * Title: 禁用用户表信息
     * Description:
     * @throw
     * @author suven
     * date 2019-10-18 12:35:25
     */
    public boolean turnOff(List<Long> idList) {
        if (null == idList || idList.isEmpty()) {
            return false;
        }
        SysUser sysUser = sysUserDao.getById(idList.get(0));
        sysUser.toStatus(TbStatusEnum.DISABLE.index());
        sysUser.setUpdateTime(new Date());
        boolean result = sysUserDao.updateById(sysUser);
        return result;
    }


    /**
     * 冻结用户
     *
     * @param idList
     * @return
     */
    @Override
    public boolean frozenBatch(List<Long> idList, int status) {
        if (null == idList || idList.isEmpty()) {
            return false;
        }
        SysUser sysUser = SysUser.build().toStatus(status);
        //修改条件s
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", idList);

        boolean result = sysUserDao.update(sysUser, updateWrapper);
        return result;
    }

    /**
     * 处理禁言
     *
     * @param statusReqVo
     * @return
     */
    @Override
    public boolean handleMuted(AllStatusRequestVo statusReqVo) {
        if (statusReqVo.getId() <= 0) {
            return false;
        }
        SysUser sysUser = sysUserDao.getById(statusReqVo.getId());
        sysUser.setUpdateTime(new Date());
        boolean result = sysUserDao.updateById(sysUser);
        return result;
    }

    /**
     * 处理封号
     *
     * @param statusReqVo
     * @return
     */
    @Override
    public boolean handleBan(AllStatusRequestVo statusReqVo) {
        if (statusReqVo.getId() <= 0) {
            return false;
        }
        SysUser sysUser = sysUserDao.getById(statusReqVo.getId());
        sysUser.toStatus(statusReqVo.getBan());
        sysUser.setUpdateTime(new Date());

        boolean result = sysUserDao.updateById(sysUser);

        return result;
    }

    @Override
    public ResponseResultPageVo<SysUserResponseDto> getUserByDepIdPage(long depId) {
        ResponseResultPageVo<SysUserResponseDto> ResponseResultPageVo = new ResponseResultPageVo<>();
        List<SysUserResponseDto> resDtoList = new ArrayList<>();


        List<Long> userIds = sysUserDepartDao.getUserIdByDepId(depId);
        if (ObjectTrue.isEmpty(userIds)) {
            return ResponseResultPageVo.toTotal(0).toList(resDtoList);
        }
        Collection<SysUser> list = sysUserDao.getListByIds(userIds);
        resDtoList = IterableConvert.convertList(list, SysUserResponseDto.class);

        return ResponseResultPageVo.toTotal(list.size()).toList(resDtoList);
    }

    @Override
    public ResponseResultPageVo<SysUserResponseDto> getSysUserRoleId(Pager basePage, long roleId , String username) {
        ResponseResultPageVo<SysUserResponseDto> ResponseResultPageVo = new ResponseResultPageVo<>();
        ;
        //分页对象        PageHelper
        Page<SysUser> iPage = new Page<>(basePage.getPageNo(), basePage.getPageSize());
        iPage.setSearchCount(true);
        List<SysUser> list = sysUserDao.getSysUserRoleId(iPage, roleId, username );
        if (null == list) {
            list = new ArrayList<>();
        }
        List<SysUserResponseDto> resDtoList = IterableConvert.convertList(list, SysUserResponseDto.class);
        boolean isNext = basePage.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList).toTotal((int) iPage.getTotal());
        return ResponseResultPageVo;
    }

}
