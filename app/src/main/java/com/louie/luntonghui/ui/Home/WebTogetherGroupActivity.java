package com.louie.luntonghui.ui.Home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.event.WxChatPay;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.MD5;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.util.Util;
import com.squareup.otto.Subscribe;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jack on 15/11/18.
 */
public class WebTogetherGroupActivity extends AppCompatActivity {
    private WebView browser;
    private String userId;
    private String URL;
    public static final String TOGETHER_GROUP = "together_group";
    public static final String SUCCESS = "SUCCESS";
    private PayReq req;
    private IWXAPI mWXAPI;
    public static final String PAY_RESULT = "pay_result";
    public static final String PREPAY_ID = "prepay_id";

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOL0/Hu+AkZnM" +
            "8xOa9xtD4jVY7NGBfiR72ODqRiDwLzXTNi+aO0yzrLjxCj1jLxymDkElVl2zkNhGz4oFj7CVpP0xXFegjjrnaEUNeFveeNzIo" +
            "cBdR2d7BBzZpIIDJorHqJtZ4trfg31QVvTUS4p00e4oG1aVuR3Dc0WS7FuJNaHAgMBAAECgYEAiYYp+FGKz1555LMgaNEtOJ0Vb" +
            "Oxhcd38UTDWpiQwBj3wMuYuboz68ytREgC45vgJhYNhfHShR/LvrxpbYGwiSSEeOtnlAXrhPe7nDx+EZl3WSojjhoSSEOdNvCw21" +
            "gCaVHE91FzlVrBcdy7AFJwiG6mlnbZz6pdfqi6R5qGO7AECQQD5a71lncAHZDWaxdvyuSVLuYoX4Q+KbtaMSkRLMMU9wgmI6fy3Q" +
            "s138fDAFUpLEycpdo5/dhrSwD0pWbWSpsiXAkEA6PGOEQddQADxqXhJG0KqIT47oOATLlqjW3bXKaeglf9tSrCuF+hnwAXunOHtl" +
            "wXnjHk+LyKV/2o50BcvTaKvkQJBAPSGzxEfnhLhCLirQB5vq4PY6+zLRgwm7ApwCRJaRxVr9SMYmx0jzgLI3InElaiSp3M7+yo1JMj" +
            "3lT7D54JWJxMCQGNfmRGeqKNDQ9TeIaHlgJqJ5/orXUNrG0FLo2J7xj/3JXK1iK9eT9RVM4PtxeOlezAsEOEeygqaEGu7WEywWGECQE" +
            "fXUB5ndNErQsAB85+GVMb5GakUvWP+vdF5DfMe9FYfF+N9etcP+yzQpZGmR2E8UM+7q37X/w9bq7EOh5xWsEg=";


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_togethergroup);

        App.getBusInstance().register(this);

        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        browser =(WebView)findViewById(R.id.web_view);
        URL = String.format(ConstantURL.TOGETHER_GROUP_LINK, userId);

        initWxChat();
        initWeb();
    }

    private void initWxChat() {
        req = new PayReq();
        mWXAPI = WXAPIFactory.createWXAPI(WebTogetherGroupActivity.this, null);
        mWXAPI.registerApp(Config.WX_APPID);
    }

    private void initWeb() {

        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);
        browser.addJavascriptInterface(new TogetherGroupWebView(this), TOGETHER_GROUP);

        //browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //缓存
        browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //不使用缓存

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        browser.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        browser.loadUrl(URL);

    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Config.WX_API_KEY);

        //sb.append("sign str\n" + sb.toString() + "\n\n");

        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();

        Log.e("orion", appSign);

        return appSign;
    }


    final public class TogetherGroupWebView{
        private Context mContext;

        public TogetherGroupWebView(WebTogetherGroupActivity mActivity){
            this.mContext = mActivity;
        }
        //0 成功，-2 取消支付，-1 其他错误
        private Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((String) msg.obj);

                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultInfo = payResult.getResult();

                        String resultStatus = payResult.getResultStatus();

                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            Toast.makeText(WebTogetherGroupActivity.this, "支付成功",
                                    Toast.LENGTH_SHORT).show();


                            final int result = 0;
                            browser.post(new Runnable() {
                                @Override
                                public void run() {
                                    browser.loadUrl("javascript:prepay_callback('" + result + "')");
                                }
                            });
                        } else {
                            int curResult = -1;
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                Toast.makeText(WebTogetherGroupActivity.this, "支付结果确认中",
                                        Toast.LENGTH_SHORT).show();
                                curResult = -1;
                            } else if(TextUtils.equals(resultStatus,"6001")) {
                                curResult = -2;

                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                Toast.makeText(WebTogetherGroupActivity.this, "支付失败",
                                        Toast.LENGTH_SHORT).show();

                            }else{
                                curResult = -1;
                            }

                            final int result = curResult;
                            browser.post(new Runnable() {
                                @Override
                                public void run() {
                                    browser.loadUrl("javascript:prepay_callback('" + result + "')");
                                }
                            });
                        }
                        break;
                    }
                    case SDK_CHECK_FLAG: {
                        Toast.makeText(WebTogetherGroupActivity.this, "检查结果为：" + msg.obj,
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                        break;
                }
            };
        };

        @JavascriptInterface
        public void alipay_prepay(String jsonObject){

            PayTask aliPay = new PayTask(WebTogetherGroupActivity.this);
            String orderInfo = "";
            String sign= "";
            String notifyurl = "";
            try {
                JSONObject object = new JSONObject(jsonObject);
                String orderId = object.getString("out_trade_no");
                String productName = object.getString("product_name");
                String productDetail = object.getString("product_name");
                String price = object.getString("product_price");
                notifyurl = object.getString("notifyurl");

                orderInfo = getOrderInfo(orderId, productName, productDetail, price,notifyurl);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try{
                sign = sign(orderInfo);
                sign = URLEncoder.encode(sign, "UTF-8");
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                    + getSignType();

            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(WebTogetherGroupActivity.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(payInfo);

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();

        }

        public String sign(String content) {
            return Util.sign(content, RSA_PRIVATE);
        }

        public String getSignType() {
            return "sign_type=\"RSA\"";
        }

        public String getOrderInfo(String orderId,String productName,String productDetail,String price,String notifyurl){
            // 签约合作者身份ID
            String orderInfo = "partner=" + "\"" + Config.ALI_PID + "\"";

            // 签约卖家支付宝账号
            orderInfo += "&seller_id=" + "\"" + Config.ALI_PID + "\"";

            // 商户网站唯一订单号
            orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

            // 商品名称
            orderInfo += "&subject=" + "\"" + productName + "\"";

            // 商品详情
            orderInfo += "&body=" + "\"" + productDetail + "\"";

            // 商品金额
            orderInfo += "&total_fee=" + "\"" + price + "\"";

            // 服务器异步通知页面路径
            orderInfo += "&notify_url=" + "\"" + notifyurl
                    + "\"";

            // 服务接口名称， 固定值
            orderInfo += "&service=\"mobile.securitypay.pay\"";

            // 支付类型， 固定值
            orderInfo += "&payment_type=\"1\"";

            // 参数编码， 固定值
            orderInfo += "&_input_charset=\"utf-8\"";

            // 设置未付款交易的超时时间
            // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
            // 取值范围：1m～15d。
            // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
            // 该参数数值不接受小数点，如1.5h，可转换为90m。
            orderInfo += "&it_b_pay=\"30m\"";

            // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
            // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

            //  支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空

            //orderInfo += "&return_url=\"m.alipay.com\"";

            // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
            // orderInfo += "&paymethod=\"expressGateway\"";

            return orderInfo;
        }

        //传入预支付Id 等信息
        @JavascriptInterface
        public void wechat_prepay(String jsonObject){
            try {
                JSONObject object = new JSONObject(jsonObject);
                String result = object.getString("return_code");
                if(result.equals(SUCCESS)){
                    req.appId = Config.WX_APPID;
                    req.partnerId = Config.WX_MCH_ID;
                    req.prepayId = object.getString("prepay_id");
                    req.packageValue = "Sign=WXPay";
                    req.nonceStr = genNonceStr();
                    req.timeStamp = String.valueOf(genTimeStamp());

                    List<NameValuePair> signParams = new LinkedList<NameValuePair>();
                    signParams.add(new BasicNameValuePair("appid", req.appId));
                    signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
                    signParams.add(new BasicNameValuePair("package", req.packageValue));
                    signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
                    signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
                    signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

                    String beforeSign = genAppSign(signParams);
                    req.sign = beforeSign;
                    sendPayReq();
                }else{
                    ToastUtil.showLongToast(WebTogetherGroupActivity.this, object.getString("return_msg"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void group(){
            WebTogetherGroupActivity.this.finish();
        }

        public void sendPayReq(){
            mWXAPI.registerApp(Config.WX_APPID);
            mWXAPI.sendReq(req);
        }

        private String genNonceStr() {
            Random random = new Random();
            return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
        }

        private long genTimeStamp() {
            return System.currentTimeMillis() / 1000;
        }

        /*//0 -1, -2.
        @JavascriptInterface
        public int prepay_callback(){
            int RESULT = 0;

            return RESULT;
        }*/
    }

    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onWxChatPay(WxChatPay pay) {
        final int result = pay.getResult();
        browser.post(new Runnable() {
            @Override
            public void run() {
                browser.loadUrl("javascript:prepay_callback('" + result + "')");
            }
        });
    }


    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
}
