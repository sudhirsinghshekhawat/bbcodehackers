package com.java.bbblockchain.common;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

public class ResponseUtils {
    public static void response(HttpResponseStatus responseStatus, RoutingContext routingContext) {
        routingContext.response().setStatusCode(responseStatus.code())
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .putHeader(BlockchainConstant.X_TRACKER,BlockchainUtils.getXTrackerId(routingContext.request()))
                .end(new JsonObject().put("status", responseStatus.code()).put("message", responseStatus.reasonPhrase())
                        .encodePrettily());
    }

    public static void response(HttpResponseStatus responseStatus, RoutingContext routingContext, String message) {
        routingContext.response().setStatusCode(responseStatus.code())
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .putHeader(BlockchainConstant.X_TRACKER,BlockchainUtils.getXTrackerId(routingContext.request()))
                .end(new JsonObject().put("status", responseStatus.code()).put("message", message)
                        .encodePrettily());
    }

    public static void response(HttpResponseStatus responseStatus, RoutingContext routingContext, JsonObject jsonObject) {
        routingContext.response().setStatusCode(responseStatus.code())
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .putHeader(BlockchainConstant.X_TRACKER,BlockchainUtils.getXTrackerId(routingContext.request()))
                .end(new JsonObject().put("status", responseStatus.code()).put("message", responseStatus.reasonPhrase())
                        .put("response", jsonObject)
                        .encodePrettily());
    }

    public static void response(HttpResponseStatus responseStatus, RoutingContext routingContext, JsonObject jsonObject, String message) {
        routingContext.response().setStatusCode(responseStatus.code())
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .putHeader(BlockchainConstant.X_TRACKER,BlockchainUtils.getXTrackerId(routingContext.request()))
                .end(new JsonObject().put("status", responseStatus.code()).put("message", message)
                        .put("response", jsonObject)
                        .encodePrettily());
    }

    public static void response(HttpResponseStatus responseStatus,String message,RoutingContext routingContext,String contextId){
        routingContext.response().setStatusCode(responseStatus.code())
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .putHeader(BlockchainConstant.X_TRACKER,contextId)
                .end(new JsonObject().put("status", responseStatus.code()).put("message", message)
                        .encodePrettily());
    }

    public static void response(RoutingContext routingContext){
        routingContext.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code())
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .putHeader(BlockchainConstant.X_TRACKER,BlockchainUtils.getXTrackerId(routingContext.request()))
                .end(new JsonObject().put("status", HttpResponseStatus.BAD_REQUEST.code()).put("message", HttpResponseStatus.BAD_REQUEST.reasonPhrase())
                        .encodePrettily());
    }
}


