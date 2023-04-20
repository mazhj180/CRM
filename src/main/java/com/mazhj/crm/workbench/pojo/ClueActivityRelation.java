package com.mazhj.crm.workbench.pojo;

public class ClueActivityRelation {
    private String id;

    private String clueId;

    private String activityId;

    public ClueActivityRelation(String id, String clueId, String activityId) {
        this.id = id;
        this.clueId = clueId;
        this.activityId = activityId;
    }

    public ClueActivityRelation() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getClueId() {
        return clueId;
    }

    public void setClueId(String clueId) {
        this.clueId = clueId == null ? null : clueId.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }
}