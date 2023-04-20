package com.mazhj.crm.workbench.service.impl;

import com.mazhj.crm.workbench.mapper.ClueRemarkMapper;
import com.mazhj.crm.workbench.pojo.ClueRemark;
import com.mazhj.crm.workbench.service.ClueRemarkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {

    private ClueRemarkMapper clueRemarkMapper;

    public ClueRemarkServiceImpl(ClueRemarkMapper clueRemarkMapper) {
        this.clueRemarkMapper = clueRemarkMapper;
    }

    @Override
    public List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId) {
        return clueRemarkMapper.selectClueRemarkForDetailByClueId(clueId);
    }

    @Override
    public int addClueRemark(ClueRemark remark) {
        int res = clueRemarkMapper.insertClueRemark(remark);
        return res;
    }

    @Override
    public int removeClueRemarkById(String id) {
        int res = clueRemarkMapper.deleteClueRemarkById(id);
        return res;
    }

    @Override
    public int editClueRemark(ClueRemark remark) {
        int res = clueRemarkMapper.updateClueRemark(remark);
        return res;
    }

}
