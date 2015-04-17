package com.cn.conwel.request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.cn.conwel.domain.FundBean;


public class HttpRequest {


    public static boolean isNum(String str){
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    public static  String findNext(String str){
        StringBuffer resultBuffer = new StringBuffer();
        for(int i=1;i < str.length(); i++){
            String next = str.substring(i,i+1);
            if(isNum(next)){
                resultBuffer.append(next);
            }else{
                break;
            }
        }
        return resultBuffer.toString();
    }

    public static String findPer(String str){
        StringBuffer resultBuffer = new StringBuffer();
        for(int i=str.length();i>0; i--){
            String next = str.substring(i-1,i);
            if(isNum(next)){
                resultBuffer.append(next);
            }else{
                break;
            }
        }
        return resultBuffer.toString();
    }

    public static FundBean sendGet(String url, String param) {
        FundBean fundBean = new FundBean();
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("send get exception" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        if(result != null && "".equals(result)){
            return new FundBean();
        }

        result = new StringBuffer(result.toString().trim().replace(" ",""));

        String head =  result.substring(0, result.indexOf("."));
        String next =  result.substring(result.indexOf("."), result.length());
        String netWorth = findPer(head) + "."  + findNext(next);
        fundBean.setNetWorth(netWorth);
        System.out.println("*********************************");
        System.out.println("netWorth: "+netWorth);

        next = next.substring(1, next.length());
        head =  next.substring(0, next.indexOf("."));
        next =  next.substring(next.indexOf("."), next.length());
        String increase =  findPer(head) + "."  + findNext(next);
        if(increase.indexOf("-") != -1){
            increase = increase.replaceAll("-","");
            increase = "-"+increase;
        }

        fundBean.setIncrease(increase);
        System.out.println("*********************************");
        System.out.println("increase: "+increase);


        next = next.substring(1, next.length());
        head =  next.substring(0, next.indexOf("."));
        next =  next.substring(next.indexOf("."), next.length());
        String interest = findPer(head) + "."  + findNext(next);
        if(interest.indexOf("-") != -1){
            interest = interest.replaceAll("-","");
            interest = "-"+interest;
        }
        fundBean.setInterest(interest);
        System.out.println("*********************************");
        System.out.println("interest: "+interest);

        double rich = 100 * Double.parseDouble(interest);
        fundBean.setRich(String.valueOf(rich));
        System.out.println("*********************************");
        System.out.println("rich: "+rich);
        return fundBean;
    }
}