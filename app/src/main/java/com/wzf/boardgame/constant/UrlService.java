package com.wzf.boardgame.constant;

import com.wzf.boardgame.function.http.OkHttpUtils;
import com.wzf.boardgame.function.http.dto.response.BaseResponse;
import com.wzf.boardgame.function.http.dto.response.CommentListResDto;
import com.wzf.boardgame.function.http.dto.response.CommunityListResDto;
import com.wzf.boardgame.function.http.dto.response.GameListResDto;
import com.wzf.boardgame.function.http.dto.response.LoginResDto;
import com.wzf.boardgame.function.http.dto.response.MainBannerResDto;
import com.wzf.boardgame.function.http.dto.response.PostDetailResDto;
import com.wzf.boardgame.function.http.dto.response.QiNiuTokenResDto;
import com.wzf.boardgame.function.http.dto.response.UserInfoResDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangzhenfei on 2017/2/28.
 */

public interface UrlService {
    boolean DEBUG = true;
    String BASE_RESOURCE = "http://192.168.2.202:8081/boxingmanager/";
    String BASE_URL = "http://106.14.215.117/BRPG/";

    UrlService SERVICE = OkHttpUtils.getInstance().getUrlService(UrlService.class);

    //1.get查询参数的设置@Query
    @GET("ServerInfo_2.1.3")
    Call<ResponseBody> testDouqu(@Query("name") String name);

    //2.POST请求体的方式向服务器传入json字符串@Body
    @POST("add")
    Call<ResponseBody> addUser(@Body String user);

    //3.表单的方式传递键值对@FormUrlEncoded
    //os,deviceModel,deviceId,ip,mobile,pass


    //获取验证码
    @POST("user/getSmsCode")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> smsCode(@Field("params") String params);

    //注册
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> register(@Field("params") String params);

    //登录
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginResDto>> login(@Field("params") String params);

    //获取首页的banner
    @POST("community/getHomeCarousel")
    @FormUrlEncoded
    Observable<BaseResponse<MainBannerResDto>> getBanner(@Field("params") String params);
    //获取/搜索社区列表接口
    @POST("community/getPostList")
    @FormUrlEncoded
    Observable<BaseResponse<CommunityListResDto>> communityList(@Field("params") String params);

    //获取瀑布流信息
    @POST("boardGame/getWaterfallList")
    @FormUrlEncoded
    Observable<BaseResponse<GameListResDto>> getWaterfallList(@Field("params") String params);

    //获取用户信息
    @POST("user/getUserInformation")
    @FormUrlEncoded
    Observable<BaseResponse<UserInfoResDto>> getUserInformation(@Field("params") String params);

    //获取用户信息
    @POST("user/changeUserInformation")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> changeUserInformation(@Field("params") String params);

    //获取七牛云的token
    @POST("system/getQiniuToken")
    @FormUrlEncoded
    Observable<BaseResponse<QiNiuTokenResDto>> getQiniuToken(@Field("params") String params);

    //发帖接口
    @POST("community/sendPost")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> sendPost(@Field("params") String params);

    //获取帖子详情接口
    @POST("community/getPostInformation")
    @FormUrlEncoded
    Observable<BaseResponse<PostDetailResDto>> getPostInformation(@Field("params") String params);


    //收藏帖子接口
    @POST("community/collectPost")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> collectPost(@Field("params") String params);

    //取消收藏帖子接口
    @POST("community/cancelCollectPost")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> cancelCollectPost(@Field("params") String params);


    //评论主贴接口
    @POST("community/commentPost")
    @FormUrlEncoded
    Observable<BaseResponse<Object>> commentPost(@Field("params") String params);


    //获取回帖列表（楼层列表）接口
    @POST("community/getPostReplyList")
    @FormUrlEncoded
    Observable<BaseResponse<CommentListResDto>> getPostReplyList(@Field("params") String params);



//
//    //注销
//    @POST("logout")
//    @FormUrlEncoded
//    Call<ResponseBody> logout(@Field("token") String token);
//
//    //确认订单列表
//    @POST("order/confirmOrderList")
//    Call<ResponseBody> confirmOrderList(@Body ConfirmOrderListParam confirmOrderListParam);
//
//    //确认下单
//    @POST("order/confirmOrder")
//    Call<ResponseBody> confirmOrder(@Body ConfirmOrderParam confirmOrderParam);
//
//    //取消订单
//    @POST("order/cancelOrder")
//    Call<ResponseBody> cancelOrder(@Body CancelOrderParam cancelOrderParam);
//
//    //删除菜品
//    @POST("order/deleteVegetables")
//    Call<ResponseBody> deleteDish(@Body DeleteDishParam deleteDishParam);
//
//    //确认付款列表
//    @POST("order/confirmPayList")
//    Call<ResponseBody> confirmPayList(@Body ConfirmPayListParam confirmPayList);
//
//
//    //确认付款
//    @POST("order/confirmPay")
//    Call<ResponseBody> confirmPay(@Body ConfirmPayParam confirmPayParam);
//
//    //获取老板页面数据
//    @POST("extract/bossPage")
//    Call<ResponseBody> getBossPageArg(@Body PostParam dto);
//
//    //验证原支付密码
//    @POST("wallet/checkPayPassword")
//    Call<ResponseBody> checkPassword(@Body SetPasswordParam setPassword);
//
//    //设置支付密码
//    @POST("wallet/setPayPassword")
//    Call<ResponseBody> setPassword(@Body SetPasswordParam setPassword);
//
//    //获取最近一次提现的账户
//    @POST("extract/account/extractConfig")
//    Call<ResponseBody> extractConfig(@Body PostParam dto);
//
//    //获取绑定的账户列表
//    @POST("extract/account/getAccountList")
//    Call<ResponseBody> getAccountList(@Body PostParam dto);
//
//    //添加绑定账户
//    @POST("extract/account/bind")
//    Call<ResponseBody> bindAccount(@Body ShopBindAccountRequestDto dto);
//
//    //提现申请
//    @POST("extract/alipay/apply")
//    Call<ResponseBody> apply(@Body ExtractApplyRequestDto dto);
//
//    //提现申请
//    @POST("extract/recordList")
//    Call<ResponseBody> getRecordList(@Body PostParam dto);
//
//    //获取交易流水列表
//    @POST("wallet/getWalletFlowList")
//    Call<ResponseBody> getWalletFlowList(@Body GetWalletFlowListRequestDto dto);
//
//
//     @Multipart
//     @POST("video/addVideo")
//    Call<ResponseBody> upload(@PartMap Map<String, RequestBody> file,
//                              @Part("message") RequestBody message);


}

