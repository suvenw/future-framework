package com.suven.framework.sys.facade;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.suven.framework.common.constants.CommonConstant;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.common.enums.SystemMsgCodeEnum;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.validator.JwtUtil;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.service.SysUserService;
import com.suven.framework.sys.vo.request.TokenRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionTreeRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionTreeResponseVo;
import com.suven.framework.sys.vo.response.TreeModelResponseVo;
import com.suven.framework.util.crypt.CryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.sys.service.SysPermissionService;
import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.vo.request.SysPermissionRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionResponseVo;

import java.util.*;
import java.util.stream.Collectors;


/**
  * ClassName: SysPermissionFacade.java
  *
  * @author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * CreateDate 创建时间: 2022-02-28 16:10:30
  * @version 版本: v1.0.0
  * <pre>
  *
  *  Description: 菜单权限表 的业务综合处理门面实现逻辑类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * RequestMapping("/sys/sysPermission")
  * </pre>
  * Copyright: (c) 2021 gc by https://www.suven.top
  **/

@Component
public class SysPermissionFacade {

	@Autowired
	private SysPermissionService  sysPermissionService;

	@Autowired
	private SysUserService sysUserService;





	public List<SysPermissionTreeResponseVo> getSysPermissionByList(SysPermissionRequestVo sysPermissionRequestVo) {
		List<SysPermissionTreeResponseVo> listVo = new ArrayList<>();
		SysPermissionRequestDto dto = SysPermissionRequestDto.build().clone(sysPermissionRequestVo);
		dto.toMenuType(CommonConstant.MENU_TYPE_0).toDelFlag(CommonConstant.DEL_FLAG_0);
		List<SysPermissionResponseDto> resultList = sysPermissionService.getSysPermissionList(dto);

		if (null == resultList) {
			return listVo;
		}
		resultList.forEach(e -> listVo.add(new SysPermissionTreeResponseVo(e)));
		return listVo;
	}

//	public SysPermissionTreeRequestVo queryTreeList() {
//
//		List<Long> ids = new ArrayList<>();
//		SysPermissionRequestDto dto = SysPermissionRequestDto.build();
//		dto.toDelFlag(CommonConstant.DEL_FLAG_0).toMenuType(-1);
//		List<SysPermissionResponseDto> list = sysPermissionService.getSysPermissionList(dto);
//		list.forEach(e -> ids.add(e.getId()));
//		List<TreeModelResponseVo> treeList = new ArrayList<>();
//		getTreeModelList(treeList, list, null);
//		SysPermissionTreeRequestVo resMap = SysPermissionTreeRequestVo.build();
//		resMap.setTreeList( treeList); // 全部树节点数据
//		resMap.setIds(ids);// 全部树ids
//		return resMap;
//	}

	public Map<String, Object> queryTreeList() {

		SysPermissionRequestDto dto = SysPermissionRequestDto.build();
		dto.toDelFlag(0).toMenuType(-1).toParentId(-1);
		List<SysPermissionResponseDto> resultList = sysPermissionService.getSysPermissionList(dto);
		List<String> ids = resultList.stream().map(r ->
				String.valueOf(r.getId())).collect(Collectors.toList()
		);
		List<TreeModelResponseVo> treeList = new ArrayList<>();
		getTreeModelList(treeList, resultList,null);
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("treeList", treeList); //全部树节点数据
		resMap.put("ids", ids);//全部树ids
		return resMap;
	}




	private void getTreeModelList(List<TreeModelResponseVo> treeList, List<SysPermissionResponseDto> metaList, TreeModelResponseVo temp) {
		metaList.forEach(m ->{
			String  tempPid = String.valueOf(m.getParentId());
			TreeModelResponseVo tree = new TreeModelResponseVo(String.valueOf(m.getId()), String.valueOf(m.getParentId()), m.getName(),
					m.getRuleFlag(), m.getIsLeaf() ==1?true:false);
			if(temp==null && tempPid.equals("0")) {
				treeList.add(tree);
				if(!tree.getIsLeaf()) {
					getTreeModelList(treeList, metaList, tree);
				}
			}else if(temp!=null && tempPid!=null && tempPid.equals(temp.getKey())){
				temp.getChildren().add(tree);
				if(!tree.getIsLeaf()) {
					getTreeModelList(treeList, metaList, tree);
				}
			}
		});
	}


//	private void getTreeModelList(List<TreeModelResponseVo> treeList, List<SysPermissionResponseDto> metaList, TreeModelResponseVo temp) {
//		Iterator var4 = metaList.iterator();
//
//		while(true) {
//			while(var4.hasNext()) {
//				SysPermissionResponseDto permission = (SysPermissionResponseDto)var4.next();
//				long tempPid = permission.getParentId();
//				TreeModelResponseVo tree = new TreeModelResponseVo(permission);
//				if (temp == null && tempPid >= 0L) {
//					treeList.add(tree);
//					if (!tree.getIsLeaf()) {
//						this.getTreeModelList(treeList, metaList, tree);
//					}
//				} else if (temp != null && tempPid >= 0L && temp.getKey().equals(String.valueOf(tempPid))) {
//					temp.getChildren().add(tree);
//					if (!tree.getIsLeaf()) {
//						this.getTreeModelList(treeList, metaList, tree);
//					}
//				}
//			}
//
//			return;
//		}
//	}



	public boolean addPermission(SysPermissionRequestVo requestVo) {
		SysPermissionRequestDto dto = SysPermissionRequestDto.build().clone(requestVo);
		dto = intelligentProcessData(dto);
		SysPermissionResponseDto responseDto = sysPermissionService.saveSysPermission(dto);
		if (null == responseDto) {
			return false;
		}
		return true;
	}

	public boolean editPermission(SysPermissionRequestVo requestVo) {
		SysPermissionRequestDto dto = SysPermissionRequestDto.build().clone(requestVo);
		dto = intelligentProcessData(dto);
		return sysPermissionService.updateSysPermission(dto);
	}


	public List<SysPermissionTreeResponseVo> getSystemSubmenu(SysPermissionRequestVo requestVo) {

		List<SysPermissionTreeResponseVo> listVo = new ArrayList<>();
		SysPermissionRequestDto dto = SysPermissionRequestDto.build().clone(requestVo);
		dto.toDelFlag(CommonConstant.DEL_FLAG_0).toMenuType(-1).toParentId(requestVo.getParentId());
		List<SysPermissionResponseDto> resultList = sysPermissionService.getSysPermissionList(dto);
		if (null == resultList) {
			return listVo;
		}
		resultList.forEach(e -> listVo.add(new SysPermissionTreeResponseVo(e)));

		return listVo;

	}


	public Object getUserPermissionByToken(TokenRequestVo tokenRequestVo) {
		if (null == tokenRequestVo.getToken()) {
			return SysResultCodeEnum.SYS_AUTH_REFRES_TOKEN_FAIL;
		}
		String obj = JwtUtil.getUserInfo(tokenRequestVo.getToken());
		if(tokenRequestVo.getUserId()<= 0) {
			return SysResultCodeEnum.SYS_USER_CODE_NOT;
		}
//		SysUserResponseDto dto = JSONObject.parseObject(obj,SysUserResponseDto.class);
//		SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().toUsername(dto.getPhone());
		SysUserResponseDto sysUserResponseDto = sysUserService.getSysUserById(tokenRequestVo.getUserId());
		if(sysUserResponseDto == null){
			throw new SystemRuntimeException( SystemMsgCodeEnum.SYS_USER_FAIL);
		}
		List<SysPermissionResponseDto> metaList = sysPermissionService.queryByUser(sysUserResponseDto.getId());
		addIndexPage(metaList);
		Map<String,Object> map = new HashMap<>();
		JSONArray menujsonArray = new JSONArray();
		getPermissionJsonArray(menujsonArray, metaList, null);
		JSONArray authjsonArray = new JSONArray();
		getAuthJsonArray(authjsonArray, metaList);
		List<SysPermissionResponseDto> allAuthList = sysPermissionService.getAuthListByType(CommonConstant.MENU_TYPE_2);
		JSONArray allauthjsonArray = new JSONArray();
		getAllAuthJsonArray(allauthjsonArray, allAuthList);
		//路由菜单
		map.put("menu", menujsonArray);
		//按钮权限
		map.put("auth", authjsonArray);
		//全部权限配置（按钮权限，访问权限）
		map.put("allAuth", allauthjsonArray);
		return map;

	}

	/**
	 * 如果没有index页面 需要new 一个放到list中
	 * @param metaList
	 */
	private void addIndexPage(List<SysPermissionResponseDto> metaList) {
		boolean hasIndexMenu = false;
		for (SysPermissionResponseDto sysPermission : metaList) {
			if("首页".equals(sysPermission.getName())) {
				hasIndexMenu = true;
				break;
			}
		}
		if(!hasIndexMenu) {
			metaList.add(0,SysPermissionResponseDto.buildIndex());
		}
	}


	private SysPermissionRequestDto intelligentProcessData(SysPermissionRequestDto permission) {
		if (permission == null) {
			return null;
		}

		// 组件
		if (StringUtils.isNotEmpty(permission.getComponent())) {
			String component = permission.getComponent();
			if (component.startsWith("/")) {
				component = component.substring(1);
			}
			if (component.startsWith("views/")) {
				component = component.replaceFirst("views/", "");
			}
			if (component.startsWith("src/views/")) {
				component = component.replaceFirst("src/views/", "");
			}
			if (component.endsWith(".vue")) {
				component = component.replace(".vue", "");
			}
			permission.setComponent(component);
		}

		// 请求URL
		if (StringUtils.isNotEmpty(permission.getUrl())) {
			String url = permission.getUrl();
			if (url.endsWith(".vue")) {
				url = url.replace(".vue", "");
			}
			if (!url.startsWith("http") && !url.startsWith("/")&&!url.trim().startsWith("{{")) {
				url = "/" + url;
			}
			permission.setUrl(url);
		}

		// 一级菜单默认组件
		if (0 == permission.getMenuType() && StringUtils.isEmpty(permission.getComponent())) {
			// 一级菜单默认组件
			permission.setComponent("layouts/RouteView");
		}
		return permission;
	}


//	private void getTreeModelList(List<TreeModelResponseVo> treeList, List<SysPermissionResponseDto> metaList, TreeModelResponseVo temp) {
//		for (SysPermissionResponseDto permission : metaList) {
//			long tempPid = permission.getParentId();
//			TreeModelResponseVo tree = new TreeModelResponseVo(permission);
//			if (temp == null && tempPid >= 0) {
//				treeList.add(tree);
//				if (!tree.getIsLeaf()) {
//					getTreeModelList(treeList, metaList, tree);
//				}
//			} else if (temp != null && tempPid >= 0 && temp.getKey().equals(tempPid)) {
//				temp.getChildren().add(tree);
//				if (!tree.getIsLeaf()) {
//					getTreeModelList(treeList, metaList, tree);
//				}
//			}
//
//		}
//	}


//	public IResultCodeEnum deleteBatch(HttpRequestByIdListVo idListVo) {
//		IResultCodeEnum sysMsgEnum =  sysPermissionService.deleteBatchByIds(idListVo.getIdList());
//		return sysMsgEnum;
//
//	}


	private void getAllAuthJsonArray(JSONArray jsonArray, List<SysPermissionResponseDto> allList) {
		JSONObject json = null;
		for (SysPermissionResponseDto permission : allList) {
			json = new JSONObject();
			json.put("action", permission.getPerms());
			json.put("status", permission.getStatus());
			json.put("type", permission.getPermsType());
			json.put("describe", permission.getName());
			jsonArray.add(json);
		}
	}

	private void getAuthJsonArray(JSONArray jsonArray, List<SysPermissionResponseDto> metaList) {
		for (SysPermissionResponseDto permission : metaList) {
			if(permission.getMenuType() < 0) {
				continue;
			}
			JSONObject json = null;
			if(permission.getMenuType() == CommonConstant.MENU_TYPE_2 && CommonConstant.STATUS_1.equals(String.valueOf(permission.getStatus()))) {
				json = new JSONObject();
				json.put("action", permission.getPerms());
				json.put("type", permission.getPermsType());
				json.put("describe", permission.getName());
				jsonArray.add(json);
			}
		}
	}

	private void getPermissionJsonArray(JSONArray jsonArray, List<SysPermissionResponseDto> metaList, JSONObject parentJson) {
		for (SysPermissionResponseDto permission : metaList) {
			if (permission.getMenuType() < 0) {
				continue;
			}
			long tempPid = permission.getParentId();
			JSONObject json = getPermissionJsonObject(permission);
			if(json==null) {
				continue;
			}
			if (parentJson == null && tempPid <= 0) {
				jsonArray.add(json);
				if (permission.getIsLeaf() != 1) {
					getPermissionJsonArray(jsonArray, metaList, json);
				}
			} else if (parentJson != null && tempPid > 0 && tempPid == parentJson.getLong("id")) {
				// 类型( 0：一级菜单 1：子菜单 2：按钮 )
				if (permission.getMenuType() == CommonConstant.MENU_TYPE_2) {
					JSONObject metaJson = parentJson.getJSONObject("meta");
					if (metaJson.containsKey("permissionList")) {
						metaJson.getJSONArray("permissionList").add(json);
					} else {
						JSONArray permissionList = new JSONArray();
						permissionList.add(json);
						metaJson.put("permissionList", permissionList);
					}
					// 类型( 0：一级菜单 1：子菜单 2：按钮 )
				} else if (permission.getMenuType() == CommonConstant.MENU_TYPE_1 || permission.getMenuType() == CommonConstant.MENU_TYPE_0) {
					if (parentJson.containsKey("children")) {
						parentJson.getJSONArray("children").add(json);
					} else {
						JSONArray children = new JSONArray();
						children.add(json);
						parentJson.put("children", children);
					}

					if (permission.getIsLeaf() != 1) {
						getPermissionJsonArray(jsonArray, metaList, json);
					}
				}
			}

		}
	}

	private JSONObject getPermissionJsonObject(SysPermissionResponseDto permission) {
		JSONObject json = new JSONObject();
		// 类型(0：一级菜单 1：子菜单 2：按钮)
		if (permission.getMenuType() == CommonConstant.MENU_TYPE_2) {
			return null;
		} else if (permission.getMenuType() == CommonConstant.MENU_TYPE_0 || permission.getMenuType() == CommonConstant.MENU_TYPE_1) {
			json.put("id", permission.getId());
			if (permission.getIsRoute() == 1) {
				json.put("route", "1");// 表示生成路由
			} else {
				json.put("route", "0");// 表示不生成路由
			}

			if (isWWWHttpUrl(permission.getUrl())) {
				//Md5Utils.getMD5(permission.getUrl(), "utf-8")
				json.put("path", CryptUtil.md5(permission.getUrl()));
			} else {
				json.put("path", permission.getUrl());
			}

			// 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
			if (StringUtils.isNotEmpty(permission.getComponentName())) {
				json.put("name", permission.getComponentName());
			} else {
				json.put("name", urlToRouteName(permission.getUrl()));
			}

			// 是否隐藏路由，默认都是显示的
			if (permission.getHidden() != 0) {
				json.put("hidden", true);
			}
			// 聚合路由
			if (permission.getAlwaysShow() != 0) {
				json.put("alwaysShow", true);
			}
			json.put("component", permission.getComponent());
			JSONObject meta = new JSONObject();
			// 由用户设置是否缓存页面 用布尔值
			if (permission.getKeepAlive() != 0) {
				meta.put("keepAlive", true);
			} else {
				meta.put("keepAlive", false);
			}

			/*update_begin author:wuxianquan date:20190908 for:往菜单信息里添加外链菜单打开方式 */
			//外链菜单打开方式
			meta.put("internalOrExternal", false);
//            if (permission.isInternalOrExternal()) {
//                meta.put("internalOrExternal", true);
//            } else {
//                meta.put("internalOrExternal", false);
//            }
			/* update_end author:wuxianquan date:20190908 for: 往菜单信息里添加外链菜单打开方式*/

			meta.put("title", permission.getName());
			if (permission.getParentId() <= 0) {
				// 一级菜单跳转地址
				json.put("redirect", permission.getRedirect());
				if (StringUtils.isNotEmpty(permission.getIcon())) {
					meta.put("icon", permission.getIcon());
				}
			} else {
				if (StringUtils.isNotEmpty(permission.getIcon())) {
					meta.put("icon", permission.getIcon());
				}
			}
			if (isWWWHttpUrl(permission.getUrl())) {
				meta.put("url", permission.getUrl());
			}
			json.put("meta", meta);
		}

		return json;
	}

	/**
	 * 判断是否外网URL 例如： http://localhost:8080/jeecg-boot/swagger-ui.html#/ 支持特殊格式： {{
	 * window._CONFIG['domianURL'] }}/druid/ {{ JS代码片段 }}，前台解析会自动执行JS代码片段
	 *
	 * @return
	 */
	private boolean isWWWHttpUrl(String url) {
		if (url != null && (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("{{"))) {
			return true;
		}
		return false;
	}

	/**
	 * 通过URL生成路由name（去掉URL前缀斜杠，替换内容中的斜杠‘/’为-） 举例： URL = /isystem/role RouteName =
	 * isystem-role
	 * @return
	 */
	private String urlToRouteName(String url) {
		if (StringUtils.isNotEmpty(url)) {
			if (url.startsWith("/")) {
				url = url.substring(1);
			}
			url = url.replace("/", "-");

			// 特殊标记
			url = url.replace(":", "@");
			return url;
		} else {
			return null;
		}
	}


	public Map<String, Object> queryPermissionTreeList() {
		List<Long> ids = new ArrayList<>();
		SysPermissionRequestDto dto = SysPermissionRequestDto.build();
		dto.toDelFlag(CommonConstant.DEL_FLAG_0).toMenuType(-2);
		List<SysPermissionResponseDto> list = this.sysPermissionService.getSysPermissionList(dto);
		list.forEach((e) -> {
			ids.add(e.getId());
		});
		List<TreeModelResponseVo> treeList = new ArrayList<>();
		this.getPermissionTreeModelList(treeList, list, (TreeModelResponseVo)null);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("treeList", treeList);
		resMap.put("ids", ids);
		return resMap;
	}

	private void getPermissionTreeModelList(List<TreeModelResponseVo> treeList, List<SysPermissionResponseDto> metaList, TreeModelResponseVo temp) {
		Iterator var4 = metaList.iterator();

		while (true) {
			while (var4.hasNext()) {
				SysPermissionResponseDto permission = (SysPermissionResponseDto) var4.next();
				long tempPid = permission.getParentId();
				TreeModelResponseVo tree = new TreeModelResponseVo(permission ,true);
				if (temp == null && tempPid >= 0L) {
					treeList.add(tree);
					if (!tree.getIsLeaf()) {
						this.getPermissionTreeModelList(treeList, metaList, tree);
					}
				} else if (temp != null && tempPid >= 0L && temp.getKey().equals(tempPid)) {
					temp.getChildren().add(tree);
					if (!tree.getIsLeaf()) {
						this.getPermissionTreeModelList(treeList, metaList, tree);
					}
				}
			}

			return;
		}
	}
}
