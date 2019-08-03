package com.furtech.javautils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @des 发送 http request
 * you can encapsulate httpClient and set request body in json
 *
 * @author 719383495@qq.com | 719383495qq@gmail.com | 有问题可以邮箱或者github联系我
 * @date 2019/8/3 14:30
 */
public class HttpRequestUtil {

    public static void main(String[] args) {

        String url = "https://restapi.amap.com/v3/geocode/regeo?output=json&location=121.713683,31.379010&key=44d019ff91fd44b95e6bd251c7774673&radius=1000&extensions=all";
        String json = "";
        httpRequest0(url, json);
        httpRequest1(url, json);

    }

    /**
     * recommend using this
     * codeStatus
     * codeEntity
     *
     * @param url
     * @param json
     */
    private static void httpRequest1(String url, String json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            String content = EntityUtils.toString(entity);
            System.out.println(content + statusCode);
            JSONObject jsonObject = JSON.parseObject(content);
            System.out.println(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一种方式，只返回codeStatus
     *  200 true
     *  others false
     *
     * @param url
     * @param json
     */
    public static void httpRequest0(String url, String json) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        RequestEntity requestEntity = null;
        try {
            requestEntity = new StringRequestEntity(json, "application/json", "utf-8");
            postMethod.setRequestEntity(requestEntity);

            int i = httpClient.executeMethod(postMethod);
            System.out.println(i);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
