package com.java.bbblockchain.verticle;


import com.google.gson.GsonBuilder;
import com.java.bbblockchain.common.ResponseUtils;
import com.java.bbblockchain.common.VerticleContext;
import com.java.bbblockchain.models.Block;
import com.java.bbblockchain.models.Customer;
import com.java.bbblockchain.models.DistributionCenter;
import com.java.bbblockchain.models.Order_items;
import com.java.bbblockchain.service.BlockchainService;
import com.java.bbblockchain.serviceimpl.BlockchainServiceImpl;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

import static com.java.bbblockchain.common.BlockchainConstant.WELCOME_MESSAGE;


/**
 * This is Member verticle class
 * Responsible for Member service routing.
 * All api endpoints defined here
 */


public class BlockChainVerticle extends AbstractVerticle {
    private VerticleContext verticleContext;
    private BlockchainService blockchainService;


    private final Logger LOGGER = LoggerFactory.getLogger(BlockChainVerticle.class);

    public BlockChainVerticle() {
        super();
        verticleContext = new VerticleContext();
    }

    /**
     * This is the default start method of verticle
     *
     * @param startFuture (StartFuture Object )
     * @throws Exception throwing exception
     */
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        verticleContext.initVerticleContext(vertx, config());
        blockchainService = new BlockchainServiceImpl(verticleContext);


        Router router = Router.router(vertx);

        router.errorHandler(HttpResponseStatus.BAD_REQUEST.code(), ResponseUtils::response);
        router.route().handler(BodyHandler.create());



        router.get(config().getString("ROOT_API")).handler(this::rootApi);
        router.get(config().getString("FARMER")).handler(this::getFarmer);

        router.get(config().getString("DC")).handler(this::getDc);
        router.get(config().getString("COLLECTION")).handler(this::getCollection);
        router.get(config().getString("CUSTOMER")).handler(this::getCustomer);
        router.get(config().getString("ORDER_ITEMS")).handler(this::getOrderItems);
        router.get(config().getString("BLOCKCHAIN")).handler(this::getBlocBlockchain);


        router.post(config().getString("COLLECTION")).handler(this::addCollectionCenter);
        router.post(config().getString("DC")).handler(this::addDC);
        router.post(config().getString("CUSTOMER")).handler(this::addCustomer);
        router.post(config().getString("ORDER_ITEMS")).handler(this::addOrderItems);


        vertx.createHttpServer().requestHandler(router)
                .listen(config().getInteger("APP_PORT"), result -> {
                    if (result.succeeded()) {
                        LOGGER.info("[start] Blockchain verticle deployed successfully");
                        startFuture.complete();
                    } else {
                        LOGGER.error("[start] Blockchain verticle deployment failed", result.cause());
                        startFuture.fail(result.cause());
                    }
                });
    }


    private void getFarmer(RoutingContext rc)
    {
        ResponseUtils.response(HttpResponseStatus.OK, rc,new GsonBuilder().create().toJson(VerticleContext.farmerHashMap));
    }

    private void getDc(RoutingContext rc)
    {
        ResponseUtils.response(HttpResponseStatus.OK, rc,new GsonBuilder().create().toJson(VerticleContext.distributionCenterHashMap));

    }

    private void getCustomer(RoutingContext rc)
    {
        ResponseUtils.response(HttpResponseStatus.OK, rc,new GsonBuilder().create().toJson(VerticleContext.customerHashMap));

    }

    private void getCollection(RoutingContext rc)
    {
        ResponseUtils.response(HttpResponseStatus.OK, rc,new GsonBuilder().create().toJson(VerticleContext.collectionCenterHashMap));

    }


    private void getOrderItems(RoutingContext rc)
    {
        ResponseUtils.response(HttpResponseStatus.OK, rc,new GsonBuilder().create().toJson(VerticleContext.order_itemsHashMap));
    }

    private  void addCollectionCenter(RoutingContext rc)
    {
        JsonObject jsonObject = rc.getBodyAsJson();
        System.out.println("collection :"+jsonObject);
        blockchainService.addCollection(jsonObject.getInteger("skuId"),jsonObject.getInteger("quantity"));
        ResponseUtils.response(HttpResponseStatus.OK, rc,new GsonBuilder().create().toJson(VerticleContext.collectionCenterHashMap));
    }

    private  void  addDC(RoutingContext rc)
    {
        JsonObject jsonObject = rc.getBodyAsJson();
        System.out.println("dc :"+jsonObject);
        DistributionCenter dc = DistributionCenter.fromMemberRequestJsonObject(jsonObject);
        blockchainService.addDC(dc);
        ResponseUtils.response(HttpResponseStatus.OK, rc,new GsonBuilder().create().toJson(VerticleContext.distributionCenterHashMap));
    }

    private  void  addCustomer(RoutingContext rc)
    {
        JsonObject jsonObject = rc.getBodyAsJson();
        System.out.println("customer :"+jsonObject);
        Customer customer = Customer.fromMemberRequestJsonObject(jsonObject);
        blockchainService.addCustomer(customer);
        ResponseUtils.response(HttpResponseStatus.OK, rc,new GsonBuilder().create().toJson(VerticleContext.customerHashMap));
    }


    private  void  addOrderItems(RoutingContext rc)
    {
        JsonObject jsonObject = rc.getBodyAsJson();
        Order_items order_items = Order_items.fromMemberRequestJsonObject(jsonObject);
        System.out.println("Order_Items "+order_items);
        blockchainService.addOrder_Items(order_items);
        ResponseUtils.response(HttpResponseStatus.OK, rc,new GsonBuilder().create().toJson(VerticleContext.order_itemsHashMap));

    }


    public  void getBlocBlockchain(RoutingContext rc)
    {
        String blockJson = new GsonBuilder().create().toJson(VerticleContext.blockChainArrayList);
        ResponseUtils.response(HttpResponseStatus.OK, rc,blockJson);
    }

 private void rootApi(RoutingContext rc) {
        ResponseUtils.response(HttpResponseStatus.OK, rc, WELCOME_MESSAGE);
   }


//    public static Boolean isChainValid() {
//        Block currentBlock;
//        Block previousBlock;
//
//        //loop through blockchain to check hashes:
//        for(int i=1; i < VerticleContext.blockChainArrayList.size(); i++) {
//            currentBlock = VerticleContext.blockChainArrayList.get(i);
//            previousBlock = VerticleContext.blockChainArrayList.get(i-1);
//            //compare registered hash and calculated hash:
//            if(!currentBlock.getCurrHash().equals(currentBlock.getObject().calculateHash()) ){
//                System.out.println("Current Hashes not equal");
//                return false;
//            }
//            //compare previous hash and registered previous hash
//            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
//                System.out.println("Previous Hashes not equal");
//                return false;
//            }
//        }
//        return true;
//    }
}

