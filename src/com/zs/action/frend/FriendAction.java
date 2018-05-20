package com.zs.action.frend;

import java.util.ArrayList;
import java.util.List;

import io.rong.models.FormatType;
import io.rong.models.Message;
import io.rong.models.TxtMessage;
import io.rong.utils.ApiHttpClient;
import io.rong.utils.GsonUtil;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.jboss.logging.Message.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zs.action.frend.model.RequestFriend1;
import com.zs.po.Friend;
import com.zs.po.User;
import com.zs.service.IFriendService;
import com.zs.service.IUserService;
import com.zs.util.Contents;

@Controller
public class FriendAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IFriendService friendService;
	@Autowired
	private IUserService userService;

	private String data;

	/**
	 * 处理好友邀请
	 */
	@Action("/process_request_friend")
	public void process_request_friend() {

	}

	/**
	 * 发好友邀请
	 */
	@Action("/request_friend")
	public void request_friend() {
		HttpServletResponse response = ServletActionContext.getResponse();
		System.out.println("------" + data);
		String code = "";
		try {
			if (data != null && "".equals(data)) {
				RequestFriend1 reqfriend1 = (RequestFriend1) GsonUtil.fromJson(
						data, RequestFriend1.class);
				// 1.获取好友的用户ID
				User user = userService.findUserForUserNameOrEmail(reqfriend1
						.getFriendEmailOrUserName());
				if (user != null) {
					Integer friendId = user.getId();
					// status : 1 好友, 2 请求添加, 3 请求被添加, 4 请求被拒绝, 5 我被对方删除
					Friend my_friend = friendService.findFriendShip(Integer
							.parseInt(reqfriend1.getUserID()), friendId);
					Friend other_friend = friendService.findFriendShip(
							friendId, Integer.parseInt(reqfriend1.getUserID()));
					if(my_friend !=null && other_friend !=null){

						if(my_friend.getStatus()==3){//如果对方也加过我，则直接成为好友
							
						}else { //重复请求或已经是好友
							code =  "301";
						}
						
					}else if(my_friend!=null){
						if (my_friend.getStatus() == 1) { //已经是好友
							code ="302";
						} else if(my_friend.getStatus() == 4) { //请求被拒绝时，重新发情请求
							
						} else { 
							code = "303";//未知错误
						}
					}else if(other_friend!=null){
						if (other_friend.getStatus() == 1) {//之前成为过好友，则直接成为好友
							//$db->exec('INSERT INTO `friend` (`user_id`, `friend_id`, `status`) VALUES (?,?,1);', $currentUserId, $id);
						} else if(other_friend.getStatus() == 4){ //之前被我拒绝过，则直接成为好友
							//$db->exec('INSERT INTO `friend` (`user_id`, `friend_id`, `status`) VALUES (?,?,1);', $currentUserId, $id);
							//$db->exec('UPDATE `friend` SET `status` = 1 WHERE `user_id`=? AND `friend_id`=?;', $id, $currentUserId);
						
						} else { 
							code = "304";//未知错误
						}
					}else { //发出好友请求
						//$db->exec('INSERT INTO `friend` (`user_id`, `friend_id`, `status`) VALUES (?,?,2);', $currentUserId, $id);
						//$db->exec('INSERT INTO `friend` (`user_id`, `friend_id`, `status`) VALUES (?,?,3);', $id, $currentUserId);
						//向融云IM server发送消息
						//ServerAPI::getInstance()->messageSystemPublish('10000',array($id->val),'RC:ContactNtf',
						//	'{"sourceUserId":"'.$currentUserId.'","targetUserId":"'.$id.'","operation":"Request","message":"'.$message.'"}',
						//	'你收到一条好友邀请');
						List<String> toUserIds = new ArrayList<String>();
						toUserIds.add(friendId.toString());
						Message message = new TxtMessage("txtMessagehaha");
						ApiHttpClient.publishSystemMessage(Contents.key, Contents.secret, reqfriend1.getUserID(), toUserIds, message , reqfriend1.getPushContent(), "你收到一条好友邀请", FormatType.json);
						
					}
					
				}
			} else {
				code = "4000";// 请求数据有误
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
