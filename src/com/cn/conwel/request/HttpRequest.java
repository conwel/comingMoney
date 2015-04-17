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
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static FundBean sendGet(String url, String param) {
    	FundBean fundBean = new FundBean();
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
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
        String netWorth = result.substring(result.indexOf("con_value_up")+14, result.indexOf("con_ratio_red")-21); 
        fundBean.setNetWorth(netWorth);
        System.out.println("*********************************");
        System.out.println("当前净值: "+netWorth);
        
        String increase = result.substring(result.indexOf("con_ratio_up")+14, result.indexOf("con_ratio_red\"")-21);
        fundBean.setIncrease(increase);
        System.out.println("*********************************");
        System.out.println("当前涨幅: "+increase);
        
        String interest = result.substring(result.indexOf("con_ratio_red\"")+15, result.indexOf("tips_icon_con")-21);
        fundBean.setInterest(interest);
        System.out.println("*********************************");
        System.out.println("当前利率: "+interest);
        
        String time = result.substring(result.indexOf("tips_icon_con")+16, result.indexOf("con_tips_c")-21);
        time = time.substring(5);
        fundBean.setTime(time);
        System.out.println("*********************************");
        System.out.println("截止时间: "+time);

        double rich = 100 * Double.parseDouble(interest);
        fundBean.setRich(String.valueOf(rich));
        System.out.println("*********************************");
        System.out.println("当前盈利: "+rich);
        return fundBean;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static FundBean sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        FundBean fundBean = new FundBean();
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        if("".equals(result)){
            return new FundBean();
        }

        result = new StringBuffer(result.toString().trim().replace(" ",""));
        String netWorth = result.substring(result.indexOf("con_value_up")+14, result.indexOf("con_ratio_red")-21); 
        fundBean.setNetWorth(netWorth);
        System.out.println("*********************************");
        System.out.println("当前净值: "+netWorth);
        
        String increase = result.substring(result.indexOf("con_ratio_up")+14, result.indexOf("con_ratio_red\"")-21);
        fundBean.setIncrease(increase);
        System.out.println("*********************************");
        System.out.println("当前涨幅: "+increase);
        
        String interest = result.substring(result.indexOf("con_ratio_red\"")+15, result.indexOf("tips_icon_con")-21);
        fundBean.setInterest(interest);
        System.out.println("*********************************");
        System.out.println("当前利率: "+interest);
        
        String time = result.substring(result.indexOf("tips_icon_con")+15, result.indexOf("con_tips_c")-20);
        time = time.substring(0, 5)+" "+time.substring(5);
        fundBean.setTime(time);
        System.out.println("*********************************");
        System.out.println("截止时间: "+time);

        double rich = 100 * Double.parseDouble(interest);
        fundBean.setRich(String.valueOf(rich));
        System.out.println("*********************************");
        System.out.println("当前盈利: "+rich);

        return fundBean;
    }    
}