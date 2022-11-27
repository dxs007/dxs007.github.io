package com.bwie.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

/**
 * @author dxs
 * @Classname TelMsg
 * @Description TODO
 * @Date 2022/3/11 15:58
 */
@Log4j2
public class TelSmsUtils {

    /**
     * 阿里云主账号AccessKey，accessKeySecret拥有所有API的访问权限
     */
    private static String accessKeyId = "LTAI5t8ScPwrW7a9aRu8wnds";
    private static String accessKeySecret = "arXqs8zVJ8ZylR5pT0V8jqtFkJUVNm";

    /**
     * 短信访问域名
     */
    private static String endpoint = "dysmsapi.aliyuncs.com";

    /**
     * 短信签名
     */
    private static String signName = "登录验证";

    /**
     * 实例化短信对象
     */
    private static Client client;

    static {
        log.info("初始化短信服务开始");
        long startTime = System.currentTimeMillis();
        try {
            client = initClient();
            log.info("初始化短信成功：{}",signName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("初始化短信服务结束：耗时：{}MS",(System.currentTimeMillis()-startTime));
    }

    /**
     * 初始化短信对象
     * @return
     * @throws Exception
     */
    private static Client initClient() throws Exception{
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = endpoint;
        return new Client(config);
    }

    /**
     * 发送单条短信
     * @param tel
     * @param templateCode  SMS_153991546
     * @param sendDataMap
     */
    public static String sendSms(String tel , String templateCode , Map<String,String> sendDataMap){
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(tel)
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setTemplateParam(JSONObject.toJSONString(sendDataMap));
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(sendSmsResponse.getBody());
    }

}
