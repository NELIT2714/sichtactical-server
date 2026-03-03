package dev.nelit.server.enums;

public enum AdminPermissions {
    CREATE_EVENT, CREATE_NOTIFICATION, DELETE_EVENT,
    VIEW_EVENT_MEMBERS_LIST,
    ADD_ADMIN, REMOVE_ADMIN, ADD_PERMISSION, REMOVE_PERMISSION,
    CHANGE_BALANCE;

    public String getI18nKey() {
        return "admin.permission." + this.name().toLowerCase();
    }
}
