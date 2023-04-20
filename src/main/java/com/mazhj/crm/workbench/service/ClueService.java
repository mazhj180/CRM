package com.mazhj.crm.workbench.service;

import com.mazhj.crm.workbench.pojo.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {
    int saveCreateClue(Clue clue);

    Clue queryClueForDetailById(String id);

    void saveConvertClue(Map<String,Object> map);

    List<Clue> queryClueByConditionForPage(Map<String,Object> map);

    Long queryClueCountOfByCondition(Map<String,Object> map);
}
