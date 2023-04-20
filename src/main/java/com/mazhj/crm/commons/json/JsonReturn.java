package com.mazhj.crm.commons.json;

import lombok.Data;

@Data
public class JsonReturn {
    public String state; //状态
    public String message; //响应信息
    public String importRet; //导入的数据
    public Object data;

}
