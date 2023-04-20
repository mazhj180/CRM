package com.mazhj.crm.workbench.pojo;

public class ContactsActivityRelation {
    private String id;

    private String contactsId;

    private String activityId;

    public ContactsActivityRelation(String id, String contactsId, String activityId) {
        this.id = id;
        this.contactsId = contactsId;
        this.activityId = activityId;
    }

    public ContactsActivityRelation() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId == null ? null : contactsId.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }
}