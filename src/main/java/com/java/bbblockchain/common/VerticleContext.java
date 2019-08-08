package com.java.bbblockchain.common;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.sql.SQLClient;

/**
 * This is verticle context ,which is initiating the all configuration initialization
 * which is responsible for all required object initialization
 */


public class VerticleContext {

    private Vertx vertx;
    private SQLClient sqlClient;

    private JsonObject config;

    private final Logger LOGGER = LoggerFactory.getLogger(VerticleContext.class);


    public VerticleContext() {
    }

    public void initVerticleContext(Vertx vertx, JsonObject config) {
        if (config != null) {
            this.config = config;
        }

        JsonObject mysqlConfig = new JsonObject()
                .put("host", config.getString("MYSQL_URL"))
                .put("port", config.getInteger("MYSQL_PORT"))
                .put("username", config.getString("MYSQL_USERNAME"))
                .put("password", config.getString("MYSQL_PASSWORD"))
                .put("database", config.getString("MYSQL_DATABASE"))
                .put("max_pool_size", config.getInteger("MYSQL_MAX_POOL_SIZE", 500));

        LOGGER.info("[initVerticleContext] Mysql config "+mysqlConfig);

        sqlClient = MySqlUtils.createShared(vertx, mysqlConfig);


        if (this.vertx == null) {
            this.vertx = vertx;
        }


//        if (memberWebClient == null) {
//            memberWebClient = new MemberWebClient(vertx, config);
//        }
    }

    public Vertx getVertx() {
        return vertx;
    }


    public SQLClient getSqlClient() {
        return sqlClient;
    }

//    public MemberWebClient getMemberWebClient() {
//        return memberWebClient;
//    }

    public JsonObject getConfig() {
        return config;
    }
}