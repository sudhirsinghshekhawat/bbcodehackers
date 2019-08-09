package com.java.bbblockchain.common;

import com.java.bbblockchain.models.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.sql.SQLClient;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is verticle context ,which is initiating the all configuration initialization
 * which is responsible for all required object initialization
 */


public class VerticleContext {

    private Vertx vertx;
    private SQLClient sqlClient;

    public static HashMap<Integer, Farmer> farmerHashMap  = new HashMap<>();
    public static HashMap<Integer, CollectionCenter> collectionCenterHashMap  = new HashMap<>();
    public static HashMap<Integer, DistributionCenter> distributionCenterHashMap  = new HashMap<>();
    public static HashMap<Integer, Customer> customerHashMap  = new HashMap<>();
    public static HashMap<Integer, Order_items> order_itemsHashMap  = new HashMap<>();

    public static ArrayList<Block> blockChainArrayList = new ArrayList<>();


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

        VerticleContext.loadFarmersData();


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


    public static void loadFarmersData()
    {
        Farmer farmer1 = new Farmer(1,"Mysore","Ram Singh","127 railway layout Mysore","http://","1234567890",true);
        farmerHashMap.put(1,farmer1);
        Block farmerBlock = new Block("",farmer1.getHash(),farmer1);
        blockChainArrayList.add(farmerBlock);
        System.out.println(blockChainArrayList.size());
    }
}