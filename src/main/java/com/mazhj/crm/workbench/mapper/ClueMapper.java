package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.Clue;

import java.util.List;
import java.util.Map;

public interface ClueMapper {
    int deleteByPrimaryKey(String id);

    int insert(Clue record);

    int insertSelective(Clue record);

    Clue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Clue record);

    int updateByPrimaryKey(Clue record);

    /**
     * 保存创建的线索
     * @param clue
     * @return
     */
    int insertClue(Clue clue);

    /**
     * 根据id查询线索的明细信息
     * @param id
     * @return
     */
    Clue selectClueForDetailById(String id);

    /**
     * 根据id查询线索的信息
     * @param id
     * @return
     */
    Clue selectClueById(String id);

    /**
     * 根据id删除线索
     * @param id
     * @return
     */
    int deleteClueById(String id);

    /**
     * 分页查询
     * @param map
     * @return
     */
    List<Clue> selectClueByConditionForPage(Map<String,Object> map);

    /**
     * 线索总数
     * @param map
     * @return
     */
    Long selectClueCountOfByCondition(Map<String,Object> map);

}