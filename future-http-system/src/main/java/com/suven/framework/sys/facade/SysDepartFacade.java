package com.suven.framework.sys.facade;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.sys.vo.response.DepartIdModelResponseVo;
import com.suven.framework.sys.vo.response.SysDepartTreeModelResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.sys.service.SysDepartService;
import com.suven.framework.sys.dto.request.SysDepartRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.vo.request.SysDepartRequestVo;
import com.suven.framework.sys.vo.response.SysDepartResponseVo;

import java.util.ArrayList;
import java.util.List;


/**
  * ClassName: SysDepartFacade.java
  *
  * @author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * CreateDate 创建时间: 2022-02-28 16:13:31
  * @version 版本: v1.0.0
  * <pre>
  *
  *  Description: 组织机构表 的业务综合处理门面实现逻辑类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * RequestMapping("/sys/sysDepart")
  * </pre>
  * Copyright: (c) 2021 gc by https://www.suven.top
  **/

@Component
public class SysDepartFacade {

	@Autowired
	private SysDepartService  sysDepartService;

	public List<SysDepartTreeModelResponseVo> queryTreeList() {
		List<SysDepartResponseDto> list = this.sysDepartService.getList();
		List<SysDepartTreeModelResponseVo> listResult = wrapTreeDataToTreeList(list);
		return listResult;
	}



	public static List<SysDepartTreeModelResponseVo> wrapTreeDataToTreeList(List<SysDepartResponseDto> recordList) {
		List<DepartIdModelResponseVo> idList = new ArrayList<>();
		List<SysDepartTreeModelResponseVo> records = new ArrayList<>();

        for (SysDepartResponseDto depart : recordList) {
            records.add(new SysDepartTreeModelResponseVo(depart));
        }

		List<SysDepartTreeModelResponseVo> tree = findChildren(records, idList);
		setEmptyChildrenAsNull(tree);
		return tree;
	}

	public static List<DepartIdModelResponseVo> wrapTreeDataToDepartIdTreeList(List<SysDepartResponseDto> recordList) {
		List<DepartIdModelResponseVo> idList = new ArrayList<>();
		List<SysDepartTreeModelResponseVo> records = new ArrayList<>();

		for(int i = 0; i < recordList.size(); ++i) {
			SysDepartResponseDto depart = (SysDepartResponseDto)recordList.get(i);
			records.add(new SysDepartTreeModelResponseVo(depart));
		}

		findChildren(records, idList);
		return idList;
	}

	private static List<SysDepartTreeModelResponseVo> findChildren(List<SysDepartTreeModelResponseVo> recordList, List<DepartIdModelResponseVo> departIdList) {
		List<SysDepartTreeModelResponseVo> treeList = new ArrayList<>();

        for (SysDepartTreeModelResponseVo branch : recordList) {
            if (branch.getParentId() <= 0L) {
                treeList.add(branch);
                DepartIdModelResponseVo departIdModel = (new DepartIdModelResponseVo()).convert(branch);
                departIdList.add(departIdModel);
            }
        }

		getGrandChildren(treeList, recordList, departIdList);
		return treeList;
	}

	private static void getGrandChildren(List<SysDepartTreeModelResponseVo> treeList, List<SysDepartTreeModelResponseVo> recordList, List<DepartIdModelResponseVo> idList) {
		for(int i = 0; i < treeList.size(); ++i) {
			SysDepartTreeModelResponseVo model = (SysDepartTreeModelResponseVo)treeList.get(i);
			DepartIdModelResponseVo idModel = (DepartIdModelResponseVo)idList.get(i);

            for (SysDepartTreeModelResponseVo m : recordList) {
                if (m.getParentId() > 0L && m.getParentId() == model.getId()) {
                    model.getChildren().add(m);
                    DepartIdModelResponseVo dim = (new DepartIdModelResponseVo()).convert(m);
                    idModel.getChildren().add(dim);
                }
            }

			getGrandChildren(((SysDepartTreeModelResponseVo)treeList.get(i)).getChildren(), recordList, ((DepartIdModelResponseVo)idList.get(i)).getChildren());
		}

	}

	private static void setEmptyChildrenAsNull(List<SysDepartTreeModelResponseVo> treeList) {
        for (SysDepartTreeModelResponseVo model : treeList) {
            if (ObjectTrue.isEmpty(model.getChildren())) {
                model.setChildren( null);
                model.setIsLeaf(true);
            } else {
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(false);
            }
        }

	}

//    public List<SysDepartTreeModelResponseVo> myDeptTreeList() {
//
//    }
}
