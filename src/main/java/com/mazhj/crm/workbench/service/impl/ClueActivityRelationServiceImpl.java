package com.mazhj.crm.workbench.service.impl;

import com.mazhj.crm.workbench.mapper.ClueActivityRelationMapper;
import com.mazhj.crm.workbench.pojo.ClueActivityRelation;
import com.mazhj.crm.workbench.service.ClueActivityRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    private ClueActivityRelationMapper clueActivityRelationMapper;

    public ClueActivityRelationServiceImpl(ClueActivityRelationMapper clueActivityRelationMapper) {
        this.clueActivityRelationMapper = clueActivityRelationMapper;
    }
    @Override
    public int saveCreateClueActivityRelationByList(List<ClueActivityRelation> list) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(list);
    }

    @Override
    public int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdActivityId(relation);
    }

}
