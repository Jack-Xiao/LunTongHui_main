/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.louie.luntonghui.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.LoginEvent;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.Login;
import com.louie.luntonghui.model.result.WxTokenResult;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.ui.register.RegisterStep3Activity;
import com.louie.luntonghui.ui.register.wx.WxUniteActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI api;
	public static final String WX_CODE = "wx_code";
	private String mac;
	private String openId;
	public static final String OPEN_ID = "open_id";
	public static final String ACCESS_TOKEN = "access_token";
	private String accessToken;
	/**
	 * 处理微信发出的向第三方应用请求app message
	 * <p>
	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
	 * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
	 * 做点其他的事情，包括根本不打开任何页面
	 */
	public void onGetMessageFromWXReq(WXMediaMessage msg) {
		Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
		startActivity(iLaunchMyself);
	}

	/**
	 * 处理微信向第三方应用发起的消息
	 * <p>
	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
	 * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
	 * 回调。
	 * <p>
	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
	 */
	public void onShowMessageFromWXReq(WXMediaMessage msg) {
		if (msg != null && msg.mediaObject != null
				&& (msg.mediaObject instanceof WXAppExtendObject)) {
			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wx_entry);
		api = WXAPIFactory.createWXAPI(this, Config.WX_APPID,true);
		api.handleIntent(getIntent(), this);
		mac = Config.getMacAddress(this);
	}

	@Override
	public void onReq(BaseReq baseReq) {
		ToastUtil.showShortToast(this, baseReq.openId + "");
	}

	@Override
	public void onResp(BaseResp baseResp) {
		//ToastUtil.showShortToast(this, baseResp.errCode + "");

		switch (baseResp.errCode){
			case BaseResp.ErrCode.ERR_SENT_FAILED:

				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:

				break;
			case BaseResp.ErrCode.ERR_COMM:


				break;
			case BaseResp.ErrCode.ERR_UNSUPPORT:

				break;
			case BaseResp.ErrCode.ERR_OK:

				String code = ((SendAuth.Resp) baseResp).code;

				String url = String.format(ConstantURL.WXAPI_GET_CODE,Config.WX_APPID,Config.WX_SECRET,code);

				RequestManager.addRequest(new GsonRequest(url, WxTokenResult.class, getUserInfo(), errorListener()), this);

				break;
			default:
				ToastUtil.showShortToast(this,"获取信息失败 "+baseResp.errCode);
		}
	}

	public Response.Listener<WxTokenResult> getUserInfo(){
		return new Response.Listener<WxTokenResult>() {
			@Override
			public void onResponse(WxTokenResult response) {
				accessToken = response.access_token;
				openId = response.openid;

				Log.d("openId...", " open Id:" +openId);
				String url = String.format(ConstantURL.WX_OPENID_CHECK,openId,mac);

				RequestManager.addRequest(new GsonRequest(url,
						Login.class, onCheckUserInfo(), errorListener()),this);
			}
		};
	}

	public Response.Listener<Login> onCheckUserInfo(){
		return new Response.Listener<Login>(){
			@Override
			public void onResponse(final Login login) {
				if (!login.rsgcode.equals(BaseNormalActivity.SUCCESSCODE)) {

					Bundle bundle = new Bundle();
					bundle.putString(OPEN_ID,openId);
					bundle.putString(ACCESS_TOKEN,accessToken);
					bundle.putString(WxUniteActivity.TYPE,WxUniteActivity.TYPE_WX);
					IntentUtil.startActivity(WXEntryActivity.this, WxUniteActivity.class,bundle);

				} else {

					TaskUtils.executeAsyncTask(new AsyncTask<Void, Void, Void>() {
						@Override
						protected void onPreExecute() {


						}

						@Override
						protected Void doInBackground(Void... params) {
							User user;

							String phoneNumber = login.mobile_phone;
							new Delete()
									.from(User.class)
									.where("mobile_phone = ?", phoneNumber).execute();

								User curuser = new User();
								curuser.uid = login.userid;
								curuser.username = login.user_name;
								curuser.email = login.email;
								curuser.isSupplier = login.gysa;
								curuser.superiorSupplier = login.gys;
								curuser.superiorSupplierInviteCode = login.yqm;
								curuser.integral = login.jif;
								curuser.mobilePhone = login.mobile_phone;
								curuser.rankName = login.rank_name;
								curuser.verification = login.verification;
								curuser.wechatBd = login.wxch_bd;
								curuser.regTime = login.reg_time;
								curuser.place = login.display;
								curuser.type = login.type;
								curuser.save();
							return null;
						}

						@Override
						protected void onPostExecute(Void aVoid) {
							DefaultShared.putInt(RegisterLogin.LOGIN_IN, RegisterLogin.HASLOGIN);
							DefaultShared.putString(RegisterLogin.USER_TYPE, login.type);
							DefaultShared.putString(RegisterLogin.USERUID, login.userid);
							DefaultShared.putLong(Config.LAST_SING_IN_TIME, Config.CLEAR_SIGN_IN);
							DefaultShared.putString(User.IS_EMPLOYEE, login.personnel);
							App.getBusInstance().post(new LoginEvent());
							Bundle bundle = new Bundle();
							bundle.putInt(RegisterStep3Activity.INIT_TYPE, 2);
							IntentUtil.startActivity(WXEntryActivity.this, MainActivity.class, bundle);
							WXEntryActivity.this.finish();
						}
					});
				}
			}
		};
	}


	protected com.android.volley.Response.ErrorListener errorListener() {
		return new com.android.volley.Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				ToastUtil.showLongToast(WXEntryActivity.this, R.string.network_connect_fail);
			}
		};
	}
}
