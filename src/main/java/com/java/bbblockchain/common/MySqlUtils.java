package com.java.bbblockchain.common;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.asyncsql.AsyncSQLClient;
import io.vertx.reactivex.ext.asyncsql.MySQLClient;
import io.vertx.reactivex.ext.sql.SQLClient;

/**
 * This is MySQL utility generic class
 * For all util method for mysql operation
 */
public class MySqlUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlUtils.class);

    static AsyncSQLClient createShared(Vertx vertx, JsonObject config) {
        return MySQLClient.createShared(vertx, config);

    }

    /**
     * This method is responsible for select query with parameter
     *
     * @param sqlClient      (This is sql client which is used for SQL connection )
     * @param sql            (This is sql query )
     * @param parameterArray (All parameters which required in query )
     * @return (Single < ResultSet >
     */
    public static Single<ResultSet> selectQueryWithParam(SQLClient sqlClient, String sql, JsonArray parameterArray) {
        LOGGER.info("[selectWithParam] running query: " + sql + " and parameters " + parameterArray);
        return sqlClient.rxGetConnection().flatMap(sqlConnection ->
                sqlConnection.rxQueryWithParams(sql, parameterArray)
                        .doAfterTerminate(sqlConnection::close)
                        .doOnError(error -> LOGGER.error("Getting error while running the query")));
    }

    /**
     * This method is responsible for select query without param
     *
     * @param sqlClient (This is sql client which is used for SQL connection )
     * @param sql       (This is sql query )
     * @return (Single < ResultSet >
     */
    @SuppressWarnings("unused")
    public static Single<ResultSet> selectQuery(SQLClient sqlClient, String sql) {
        LOGGER.info("[selectQuery] running query: " + sql);
        return sqlClient.rxGetConnection().flatMap(sqlConnection ->
                sqlConnection.rxQuery(sql)
                        .doAfterTerminate(sqlConnection::close)
                        .doOnError(error -> LOGGER.error("Getting error while running the query")));
    }

    /**
     * This method is responsible for Update and insert operation
     *
     * @param sqlClient      (This is sql client which is used for SQL connection )
     * @param sql            (This is sql query )
     * @param parameterArray (All parameters which required in query )
     * @return (Single < ResultSet >
     */
    @SuppressWarnings("unused")
    public static Single<UpdateResult> updateQuery(SQLClient sqlClient, String sql, JsonArray parameterArray) {
        LOGGER.info("[selectWithParam] running query: " + sql + " parameters :" + parameterArray);
        return sqlClient.rxGetConnection().flatMap(sqlConnection ->
                sqlConnection.rxUpdateWithParams(sql, parameterArray)
                        .doAfterTerminate(sqlConnection::close)
                        .doOnError(error -> LOGGER.error("Getting error while running the query")));
    }

}
