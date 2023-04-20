package com.mazhj.crm.settings.service;

import com.mazhj.crm.settings.pojo.DicValue;

import java.util.List;

public interface DicValueService {
    List<DicValue> queryDicValueByTypeCode(String typeCode);
}
