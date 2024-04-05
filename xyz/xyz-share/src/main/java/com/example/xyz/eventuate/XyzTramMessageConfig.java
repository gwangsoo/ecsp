package com.example.xyz.eventuate;

public class XyzTramMessageConfig {
    public static final String serviceName = "xyzService";

    // event
    public static final String eventDispatcherId = serviceName + "_eventDispatcherId";

    // command
    public static final String commandChannel = serviceName + "_commandChannel";
    public static final String commandDispatcherId = serviceName + "_commandDispatcherId";
    public static final String replyChannel = serviceName + "_replyChannel";
}
