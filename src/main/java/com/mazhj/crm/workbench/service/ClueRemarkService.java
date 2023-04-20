package com.mazhj.crm.workbench.service;

import com.mazhj.crm.workbench.pojo.ClueRemark;

import java.util.List;

public interface ClueRemarkService {
    List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId);

    int addClueRemark(ClueRemark remark);

    int removeClueRemarkById(String id);

    int editClueRemark(ClueRemark remark);
}
