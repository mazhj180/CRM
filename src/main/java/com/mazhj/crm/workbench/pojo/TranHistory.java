package com.mazhj.crm.workbench.pojo;

public class TranHistory {
    private String id;

    private String stage;

    private String money;

    private String expectedDate;

    private String createTime;

    private String createBy;

    private String tranId;

    public TranHistory(String id, String stage, String money, String expectedDate, String createTime, String createBy, String tranId) {
        this.id = id;
        this.stage = stage;
        this.money = money;
        this.expectedDate = expectedDate;
        this.createTime = createTime;
        this.createBy = createBy;
        this.tranId = tranId;
    }

    public TranHistory() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage == null ? null : stage.trim();
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate == null ? null : expectedDate.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId == null ? null : tranId.trim();
    }
}