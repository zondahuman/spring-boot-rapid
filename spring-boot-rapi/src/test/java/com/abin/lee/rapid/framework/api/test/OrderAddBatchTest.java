package com.abin.lee.rapid.framework.api.test;

import com.abin.lee.spring.boot.rapid.framework.common.util.HttpClientUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2018/1/15 11:36.
 * spring-boot-start2
 * com.abin.lee.spring.boot.mybatis.test
 */
public class OrderAddBatchTest {

    private static final String httpURL = "http://localhost:8099/order/insert";

    public static void main(String[] args) {
        OrderAddBatchTest orderAddBatchTest = new OrderAddBatchTest();
        for (int i = 0; i < 10000; i++) {
            orderAddBatchTest.testOrderAddBatch();

        }

    }

    @Test
    public void testOrderAddBatch() {
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            int id = (int)(Math.random()*10000000L);
            nvps.add(new BasicNameValuePair("id", id+""));
            int userId = (int)(Math.random()*100);
            nvps.add(new BasicNameValuePair("userId", userId+""));
            int orderNameId = (int)(Math.random()*100);
            nvps.add(new BasicNameValuePair("orderName", RandomStringUtils.randomGraph(10)));

            Long businessId = (long)(Math.random()*1000000000000000L);
            nvps.add(new BasicNameValuePair("businessId", businessId+""));
            HttpPost httpPost = new HttpPost(httpURL);
//            httpPost.setHeader("Cookie", getCookie());
//            httpPost.setHeader("Cookie", "JSESSIONID=7588C522A6900BFD581AA18FDA64D347");

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}
