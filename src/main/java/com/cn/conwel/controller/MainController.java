package com.cn.conwel.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.conwel.domain.FundBean;
import com.cn.conwel.request.HttpRequest;
import com.google.gson.Gson;

public class MainController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException { 
		doPost(request,response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        List<FundBean>  fundBeans = new ArrayList<FundBean>();
        String codeStr = req.getParameter("codes");
        String codes[] = codeStr.split(",");
        for (String code :codes){
            FundBean fundBean = HttpRequest.sendGet("http://www.howbuy.com/fund/ajax/gmfund/valuation/valuationnav.htm","jjdm="+code);
            fundBean.setCode(code);
            fundBeans.add(fundBean);
        }
        req.setAttribute("fundBeans",fundBeans);
        resp.setContentType("application/json;charset=UTF-8");
        resp.setHeader("pragma", "no-cache");
        resp.setHeader("cache-control", "no-cache");
        try {
            PrintWriter out = resp.getWriter();
            out.print(new Gson().toJson(fundBeans));
         } catch (IOException e) {
            e.printStackTrace();
         }
	}

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	} 
	
	

}
