package com.mazhj.crm.settings.service.impl;

import com.mazhj.crm.settings.mapper.DicValueMapper;
import com.mazhj.crm.settings.pojo.DicValue;
import com.mazhj.crm.settings.service.DicValueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicValueServiceImpl implements DicValueService {

    private DicValueMapper dicValueMapper;

    public DicValueServiceImpl(DicValueMapper dicValueMapper) {
        this.dicValueMapper = dicValueMapper;
    }

    @Override
    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        List<DicValue> dicValues = dicValueMapper.selectDicValueByTypeCode(typeCode);
        return dicValues;
    }
}
