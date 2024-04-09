package com.example.orders.eventuate;

public class OrdersTramMessageConfig {
    public static final String serviceName = "ordersService";

    // event
    public static final String eventDispatcherId = serviceName + "_eventDispatcherId";

    // command
    public static final String commandChannel = serviceName + "_commandChannel";
    public static final String commandDispatcherId = serviceName + "_commandDispatcherId";
    public static final String replyChannel = serviceName + "_replyChannel";
}
