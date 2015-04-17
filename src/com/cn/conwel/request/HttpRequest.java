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
     * ��ָ��URL����GET����������
     * 
     * @param url
     *            ���������URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return URL ������Զ����Դ����Ӧ���
     */
    public static FundBean sendGet(String url, String param) {
    	FundBean fundBean = new FundBean();
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
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
        System.out.println("��ǰ��ֵ: "+netWorth);
        
        String increase = result.substring(result.indexOf("con_ratio_up")+14, result.indexOf("con_ratio_red\"")-21);
        fundBean.setIncrease(increase);
        System.out.println("*********************************");
        System.out.println("��ǰ�Ƿ�: "+increase);
        
        String interest = result.substring(result.indexOf("con_ratio_red\"")+15, result.indexOf("tips_icon_con")-21);
        fundBean.setInterest(interest);
        System.out.println("*********************************");
        System.out.println("��ǰ����: "+interest);
        
        String time = result.substring(result.indexOf("tips_icon_con")+16, result.indexOf("con_tips_c")-21);
        time = time.substring(5);
        fundBean.setTime(time);
        System.out.println("*********************************");
        System.out.println("��ֹʱ��: "+time);

        double rich = 100 * Double.parseDouble(interest);
        fundBean.setRich(String.valueOf(rich));
        System.out.println("*********************************");
        System.out.println("��ǰӯ��: "+rich);
        return fundBean;
    }

    /**
     * ��ָ�� URL ����POST����������
     * 
     * @param url
     *            ��������� URL
     * @param param
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return ������Զ����Դ����Ӧ���
     */
    public static FundBean sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        FundBean fundBean = new FundBean();
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
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
        System.out.println("��ǰ��ֵ: "+netWorth);
        
        String increase = result.substring(result.indexOf("con_ratio_up")+14, result.indexOf("con_ratio_red\"")-21);
        fundBean.setIncrease(increase);
        System.out.println("*********************************");
        System.out.println("��ǰ�Ƿ�: "+increase);
        
        String interest = result.substring(result.indexOf("con_ratio_red\"")+15, result.indexOf("tips_icon_con")-21);
        fundBean.setInterest(interest);
        System.out.println("*********************************");
        System.out.println("��ǰ����: "+interest);
        
        String time = result.substring(result.indexOf("tips_icon_con")+15, result.indexOf("con_tips_c")-20);
        time = time.substring(0, 5)+" "+time.substring(5);
        fundBean.setTime(time);
        System.out.println("*********************************");
        System.out.println("��ֹʱ��: "+time);

        double rich = 100 * Double.parseDouble(interest);
        fundBean.setRich(String.valueOf(rich));
        System.out.println("*********************************");
        System.out.println("��ǰӯ��: "+rich);

        return fundBean;
    }    
}