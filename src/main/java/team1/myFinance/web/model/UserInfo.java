package team1.myFinance.web.model;

import team1.myFinance.data.model.SavedUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserInfo {

	public String id;
	public String alias;
	public String role;

	public String token;
	public AuthenticationType authenticationType = AuthenticationType.LOCAL;

	public static UserInfo parse(SavedUser u){
		UserInfo ui = new UserInfo();

		ui.id = Integer.toString(u.getId());
		ui.alias = u.getAlias();
		
		// fill user role
		switch (u.getRole()) {
			case 1:
				ui.role = "admin";			
				break;
			case 2:
				ui.role = "user";
				break;
			case 3:
				ui.role = "guest";			
				break;
		}

		return ui;
	}
	
	public static Collection<UserInfo> parse(Collection<SavedUser> users){

		List<UserInfo> info = new ArrayList<>();
		
		for(SavedUser u : users){
			info.add(UserInfo.parse(u));
		}
		return info;
		
	}
	
}
