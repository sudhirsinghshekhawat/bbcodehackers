package com.java.bbblockchain.verticle;


import com.java.bbblockchain.common.ResponseUtils;
import com.java.bbblockchain.common.VerticleContext;
import com.java.bbblockchain.service.BlockchainService;
import com.java.bbblockchain.serviceimpl.BlockchainServiceImpl;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;

import  static  com.java.bbblockchain.common.BlockchainConstant.*;


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



        router.get(config().getString("ROOT_API")).handler(this::rootApi);
//        router.get(config().getString("HEALTH_CHECK_API")).handler(this::healthCheck);
//
//        router.get(config().getString("MEMBER_BY_ID_API"))
//                .handler(io.vertx.reactivex.ext.web.api.validation.HTTPRequestValidationHandler.create().addPathParam("id", ParameterType.INT.INT))
//                .handler(this::getMemberById);
//
//        router.get(config().getString("MEMBER_BY_UID_MOBILE")).handler(this::getMemberByUidMobile);
//
//        router.post(config().getString("SEND_OTP_API")).handler(this::sendOTP);
//
//        router.post(config().getString("VERIFY_OTP_API")).handler(this::verifyOTP);
//
//        router.post(config().getString("MEMBER_DETAILS_API")).handler(this::getMemberDetails);
//
//        router.put(config().getString("MEMBER_UPDATE_API")).handler(this::updateMemberDetails);
//
//
//        router.route().handler(BodyHandler.create());
//
//        router.post(config().getString("KAPTURE_DETAILS_API"))
//                .handler(routingContext -> BBSignUtils.validate("KAPTURE_DETAILS_API", config(), routingContext)).handler(this::getKaptureMemberDetails);
//
//
//
//        router.post(config().getString("BBSIGN_GENERATE")).handler(this::getBBSign);



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

//    private void getBBSign(RoutingContext routingContext) {
//        String requestId = MemberUtils.getXTrackerId(routingContext.request());
//        Single<String> bbSign = BBSignUtils.getBBSign(config(), routingContext);
//        bbSign.subscribe(success -> {
//            LOGGER.info("[getBBSign] request_id: " + requestId + " Result: " + success);
//            ResponseUtils.response(HttpResponseStatus.OK, routingContext, new JsonObject().put("bbsign", success));
//        }, failure -> {
//            LOGGER.error("[getBBSign] request_id: " + requestId + " Error: " + failure);
//            ResponseUtils.response(HttpResponseStatus.BAD_REQUEST, routingContext);
//        });
//
//    }
//
//
//    @Override
//    public void stop(Future<Void> stopFuture) throws Exception {
//        super.stop(stopFuture);
//    }
//
//
//    /**
//     * This method is mainly used for updating the member details
//     *
//     * @param rc Routing context
//     */
//    private void updateMemberDetails(RoutingContext rc) {
//
//        rc.request().setExpectMultipart(true).bodyHandler(req -> {
//            MultiMap multiMap = rc.request().formAttributes();
//
//            Single<UpdateResult> updateResultSingle = memberService.updateMemberDetails(multiMap);
//
//            updateResultSingle.subscribe(success -> {
//                ResponseUtils.response(HttpResponseStatus.OK, rc, "update consumer successful");
//            }, throwable -> {
//                ResponseUtils.response(HttpResponseStatus.BAD_REQUEST, rc, throwable.getMessage());
//            });
//        });
//    }
//
//    /**
//     * @param rc (Routing Context)
//     */
//    private void getMemberByUidMobile(RoutingContext rc) {
//
//        String id = rc.pathParam("uid");
//        String mobileNo = rc.pathParam("mobile");
//
//        Single<ResultSet> memberResultSetSingle = memberService.getMemberDetailsByUidMobile(id, mobileNo);
//        memberResultSetSingle.subscribe(memberResultSet -> {
//                    if (memberResultSet.getNumRows() > 0) {
//                        ResponseUtils.response(HttpResponseStatus.OK, rc, memberResultSet.getRows().get(0), SUCCESS);
//                    } else {
//                        ResponseUtils.response(HttpResponseStatus.NOT_FOUND, rc, new JsonObject(), MEMBER_NOT_FOUND_MESSAGE);
//                    }
//                },
//                throwable -> {
//                    ResponseUtils.response(HttpResponseStatus.BAD_REQUEST, rc, BAD_REQUEST_RESPONSE);
//                });
//    }
//
//
//    /**
//     * This method is mainly used for getting the kapture member details
//     *
//     * @param rc Which accepts the KaptureMemberRequest param
//     */
//    private void getKaptureMemberDetails(RoutingContext rc) {
//        String requestId = MemberUtils.getXTrackerId(rc.request());
//        LOGGER.info("requestId: " + requestId);
//        JsonObject postBody = rc.getBodyAsJson();
//        LOGGER.info("[getKaptureMemberDetails] request_id : " + requestId + " request : " + postBody);
//        Maybe<KaptureMemReqResp> kaptureMemberDetails = memberService.getKaptureMemberDetails(postBody, requestId);
//        kaptureMemberDetails.subscribe(kaptureMember ->
//                {
//                    ResponseUtils.response(HttpResponseStatus.OK, rc, kaptureMember.toJsonObject(), SUCCESS);
//
//                }, throwable -> {
//                    ResponseUtils.response(HttpResponseStatus.BAD_REQUEST, rc, BAD_REQUEST_RESPONSE);
//                },
//                () -> {
//                    ResponseUtils.response(HttpResponseStatus.NOT_FOUND, rc, MEMBER_NOT_FOUND_MESSAGE);
//                });
//    }
//
//    /**
//     * Method responsible for get member details
//     *
//     * @param rc Routing context having Member request object(mobile, email,customerId)
//     */
//    private void getMemberDetails(RoutingContext rc) {
//        rc.request().bodyHandler(postBody -> {
//            Single<ResultSet> members = memberService.getMemberDetails(postBody);
//            members.subscribe(resultSet -> {
//                if (resultSet.getNumRows() > 0) {
//                    ResponseUtils.response(HttpResponseStatus.OK, rc, resultSet.getRows().get(0));
//                } else {
//                    ResponseUtils.response(HttpResponseStatus.NOT_FOUND, rc, new JsonObject(), MEMBER_NOT_FOUND_MESSAGE);
//                }
//            }, throwable ->
//            {
//                ResponseUtils.response(HttpResponseStatus.BAD_REQUEST, rc, BAD_REQUEST_RESPONSE);
//            });
//        });
//    }
//
//    /**
//     * Method is responsible for verify otp
//     *
//     * @param rc Request param expecting (OTP code and mobile no)
//     */
//    private void verifyOTP(RoutingContext rc) {
//        rc.request().bodyHandler(postBody -> {
//            Single<JsonObject> verifyOTPSingle = memberService.verifyOTP(postBody.toJsonObject());
//            verifyOTPSingle.subscribe(verifyOTPJson -> {
//                ResponseUtils.response(HttpResponseStatus.OK, rc, verifyOTPJson);
//            }, throwable -> {
//                ResponseUtils.response(HttpResponseStatus.BAD_REQUEST, rc, BAD_REQUEST_RESPONSE);
//            });
//        });
//    }
//
//    /**
//     * Responsible for send otp
//     *
//     * @param rc (Routing context having mobile no)
//     */
//    private void sendOTP(RoutingContext rc) {
//        rc.request().bodyHandler(postBody -> {
//            Single<JsonObject> sendOTPSingle = memberService.sendOTP(postBody.toJsonObject());
//            sendOTPSingle.subscribe(sendOTPJson -> {
//                ResponseUtils.response(HttpResponseStatus.OK, rc, sendOTPJson, SUCCESS);
//            }, throwable -> {
//                ResponseUtils.response(HttpResponseStatus.BAD_REQUEST, rc, BAD_REQUEST_RESPONSE);
//            });
//        });
//    }
//
//    /**
//     * This method for getting member details by member id
//     *
//     * @param rc Routing context having member id as path param
//     */
//
//    private void getMemberById(RoutingContext rc) {
//
//        int id = Integer.parseInt(rc.request().getParam("id"));
//        Single<ResultSet> members = memberService.getMemberDetailsById(id);
//
//        members.subscribe(resultSet -> {
//            if (resultSet.getNumRows() > 0) {
//                ResponseUtils.response(HttpResponseStatus.OK, rc, resultSet.getRows().get(0));
//            } else {
//                ResponseUtils.response(HttpResponseStatus.NOT_FOUND, rc, new JsonObject(), MEMBER_NOT_FOUND_MESSAGE);
//            }
//        }, throwable -> {
//            ResponseUtils.response(HttpResponseStatus.BAD_REQUEST, rc, BAD_REQUEST_RESPONSE);
//        });
//    }
//
//
//    /**
//     * This is basic root api for testing
//     *
//     * @param rc (Routing Context Object)
//     */
//
//
 private void rootApi(RoutingContext rc) {
        ResponseUtils.response(HttpResponseStatus.OK, rc, WELCOME_MESSAGE);
   }
//
//
//    /**
//     * This method responsible for basic health check
//     *
//     * @param rc (Routing Context Object)
//     */
//
//
//    private void healthCheck(RoutingContext rc) {
//        ResponseUtils.response(HttpResponseStatus.OK, rc, HEALTH_CHECK_MESSAGE + config().getInteger("APP_PORT"));
//    }
//
//
//    private void callMemberCommunicationService(RoutingContext routingContext) {
//
//            JsonObject requestJson = routingContext.getBodyAsJson();
//            Single<JsonObject> response= memberCommunicationService.getCommunicationRequest(requestJson);
//
//            response.subscribe(result->{
//                if(!result.getBoolean("status")){
//                    ResponseUtils.response(HttpResponseStatus.BAD_REQUEST,result.getString(MESSAGE),routingContext,requestJson.getString(CONTEXT_ID));
//                }
//                else{
//                    ResponseUtils.response(HttpResponseStatus.OK,SUCCESS,routingContext,requestJson.getString(CONTEXT_ID));
//                }
//        },throwable -> {
//                ResponseUtils.response(HttpResponseStatus.BAD_REQUEST,throwable.getMessage(),routingContext,requestJson.getString(CONTEXT_ID));
//            });
//
//    }
}

