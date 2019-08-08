package com.java.bbblockchain.common;

import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.ext.web.client.HttpRequest;

import java.rmi.server.UID;
import java.util.UUID;

@SuppressWarnings("ALL")
public class BlockchainUtils {
    /**
     * This method will be used for generating UID
     *
     * @return String
     */
    public static String getUID() {
        UID uid = new UID();
        return uid.toString().substring(9, 19);
    }


    public static String getXTrackerId(HttpServerRequest request) {
        if (request.getHeader(BlockchainConstant.X_TRACKER) != null) {
            return request.getHeader(BlockchainConstant.X_TRACKER);
        } else {
            UUID uid = UUID.randomUUID();
            String tracker = "bb-" + uid.toString();
            request.headers().add(BlockchainConstant.X_TRACKER,tracker);
            return tracker;
        }
    }

    public static String getXTrackerId() {
            UUID uid = UUID.randomUUID();
            String tracker = "bbd-" + uid.toString();
            return tracker;
        }
    }

