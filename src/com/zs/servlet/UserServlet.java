package com.zs.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zs.servlet.bean.UserBean;

//
public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("login".equals(method)) {// 登录
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if (username == null || "".equals(username.trim())
					|| password == null || "".equals(password.trim())) {
				System.out.println("用户名或密码不能为空！");
				response.sendRedirect("login.jsp");
				return;
			}
			UserBean userBean = new UserBean();
			// boolean isValid = userBean.valid(username,password);
			boolean isValid = true;// TODO 0520
			if (isValid) {
				System.out.println("登录成功！");
				request.getSession().setAttribute("username", username);
				response.sendRedirect("welcome.jsp");
				return;
			} else {
				System.out.println("用户名或密码错误，登录失败！");
				response.sendRedirect("login.jsp");
				return;
			}
		} else if ("logout".equals(method)) {// 退出登录
			System.out.println("退出登录！");
			request.getSession().removeAttribute("username");
			response.sendRedirect("login.jsp");
			return;
		} else if ("register".equals(method)) {// 注册
			String username = request.getParameter("username");
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");
			String email = request.getParameter("email");
			if (username == null || "".equals(username.trim())
					|| password1 == null || "".equals(password1.trim())
					|| password2 == null || "".equals(password2.trim())
					|| !password1.equals(password2)) {
				System.out.println("用户名或密码不能为空！");
				response.sendRedirect("register.jsp");
				return;
			}
			UserBean userBean = new UserBean();
			// boolean isExit = userBean.isExist(username);
			boolean isExit = true;// TODO 0520
			if (!isExit) {
				// userBean.add(username, password1, email); //TODO 0520
				System.out.println("注册成功，请登录！");
				response.sendRedirect("login.jsp");
				return;
			} else {
				System.out.println("用户名已存在！");
				response.sendRedirect("register.jsp");
				return;
			}
		}

	}

}
