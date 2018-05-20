package com.zs.action.user;


import io.rong.utils.GsonUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zs.action.user.model.RequestInfo1;
import com.zs.po.User;
import com.zs.service.ILoginService;
import com.zs.service.IUserService;
import com.zs.util.JsonUtil;

@Controller
public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 5033459742031477727L;
	private static final Logger log = LoggerFactory.getLogger(UserAction.class);
	
	@Autowired
	private IUserService userService;
	private String data;

	@Action("/reg")
	public void register() {
		HttpServletResponse response = ServletActionContext.getResponse();
		System.out.println("------" + data);
		String code = "";
		if (data != null || "".equals(data)) {
			RequestInfo1 req = new RequestInfo1();
			req = 	(RequestInfo1) GsonUtil.fromJson(data, RequestInfo1.class);
			if (req.getEmail() != null || "".equals(req.getEmail())) {
				try {
					User user = userService.findUserForID(req.getEmail());
					if (user != null) {
						code = "该邮箱已经被注册";
					} else {
						User user2 = new User();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
						String date = format.format(System.currentTimeMillis());
						user2.setCreatedTime(Timestamp.valueOf(date));
						user2.setEmail(req.getEmail());
						user2.setPasswd(req.getPassword());
						user2.setUsername(req.getUsername());
						boolean saveUser = userService.saveUser(user2);
						if (saveUser == true) {
							code = "注册成功";
						} else {
							code = "注册失败";
						}
					}
					response.setContentType("text/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(code);
					System.out.println("查询任务输出：  " + code);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
