package dev.nelit.server.enums;

public enum AdminPermissions {
    CREATE_EVENT, CREATE_NOTIFICATION, DELETE_EVENT,
    VIEW_EVENT_MEMBERS_LIST,
    MANAGE_ADMINS, VIEW_ADMINS,
    CHANGE_BALANCE;

    public String getI18nKey() {
        return "admin.permission." + this.name().toLowerCase();
    }
}
