package team1.myFinance.contracts;

import team1.myFinance.web.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;


public interface IAuthenticationService {

	UserInfo getUserInfo(HttpServletRequest request) throws NotAuthorizedException, IllegalArgumentException;

	String createAuthToken(UserInfo userInfo);

    String getGitHubToken(HttpServletRequest request);
    UserInfo getGitHubUserInfo(String token);

}
