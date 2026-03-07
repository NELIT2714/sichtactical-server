package dev.nelit.server.security;

public final class HasPermission {

    public static final String CREATE_EVENT           = "hasAuthority('PERMISSION_CREATE_EVENT')";
    public static final String CREATE_NOTIFICATION    = "hasAuthority('PERMISSION_CREATE_NOTIFICATION')";
    public static final String DELETE_EVENT           = "hasAuthority('PERMISSION_DELETE_EVENT')";
    public static final String VIEW_EVENT_MEMBERS_LIST = "hasAuthority('PERMISSION_VIEW_EVENT_MEMBERS_LIST')";
    public static final String ADD_ADMIN              = "hasAuthority('PERMISSION_ADD_ADMIN')";
    public static final String REMOVE_ADMIN           = "hasAuthority('PERMISSION_REMOVE_ADMIN')";
    public static final String ADD_PERMISSION         = "hasAuthority('PERMISSION_ADD_PERMISSION')";
    public static final String REMOVE_PERMISSION      = "hasAuthority('PERMISSION_REMOVE_PERMISSION')";
    public static final String CHANGE_BALANCE         = "hasAuthority('PERMISSION_CHANGE_BALANCE')";

    private HasPermission() {}
}