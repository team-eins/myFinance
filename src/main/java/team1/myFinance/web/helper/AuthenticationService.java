package team1.myFinance.web.helper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import team1.myFinance.contracts.IAuthenticationService;
import team1.myFinance.web.ServiceBase;
import team1.myFinance.web.model.AuthenticationType;
import team1.myFinance.web.model.UserInfo;

public class AuthenticationService extends ServiceBase implements IAuthenticationService {

    private final String TOKEN_REGEX = "^Bearer ([a-zA-Z0-9\\-_]+\\.[a-zA-Z0-9\\-_]+\\.[a-zA-Z0-9\\-_]+)$";
    private final String SECRET = "MY_SECRET";

    @Override
    public UserInfo getUserInfo(HttpServletRequest request)
            throws NotAuthorizedException, IllegalArgumentException {

        this.initialize();

        String authorizationHeader = request.getHeader("Authorization");

        // Check exitence of token
        if (authorizationHeader == null) {
            throw new NotAuthorizedException("Authorization header is missing");
        }

        // Verify syntax of token
        if (!authorizationHeader.matches(TOKEN_REGEX)) {
            throw new IllegalArgumentException("Token has an invalid format");
        }

        // Extract userdata form token
        String token = authorizationHeader.replaceAll(TOKEN_REGEX, "$1");
        UserInfo userInfo = parseAuthToken(token);
        if (userInfo == null) {
            return null;
        }

        // Create userInfo
        switch (userInfo.authenticationType) {
            case LOCAL:
                userInfo = UserInfo.parse(dh.getUserByID(Integer.valueOf(userInfo.id)));
                break;
            case OAUTH_GITHUB:
                userInfo = getGitHubUserInfo(userInfo.id);
                break;
        }

        userInfo.token = token;

        return userInfo;
    }


    
    


    public String createAuthToken(UserInfo userInfo) {
        JWTSigner signer = new JWTSigner(SECRET);
        final HashMap<String, Object> claims = new HashMap<>(10);
        claims.put("iss", "https://webinfo-myshop.herokuapp.com");
        claims.put("sub", userInfo.id);
        claims.put("exp", LocalDateTime.now().plusHours(10).toEpochSecond(ZoneOffset.UTC));
        claims.put("iat", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        claims.put("alias", userInfo.alias);
        claims.put("role", userInfo.role);
        claims.put("auth_type", userInfo.authenticationType);

        return signer.sign(claims);
    }

    private UserInfo parseAuthToken(String token) {

        UserInfo userInfo = null;
        JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            Map<String, Object> payload = verifier.verify(token);

            userInfo = new UserInfo();
            userInfo.id = (String) payload.get("sub");
            userInfo.alias = (String) payload.get("alias");
            userInfo.role = (String) payload.get("role");
            userInfo.authenticationType = AuthenticationType.valueOf((String) payload.get("auth_type"));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (JWTVerifyException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public UserInfo getGitHubUserInfo(String token) {
        this.initialize();

        String url = "https://api.github.com/user";
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token " + token);
        String userdata = http.get(url, params, headers);

        UserInfo userInfo = new UserInfo();
        userInfo.alias = JsonParser.get(userdata, "login");
        userInfo.id = token;
        userInfo.role = "author";
        userInfo.authenticationType = AuthenticationType.OAUTH_GITHUB;

        return userInfo;
    }

    public String getGitHubToken(HttpServletRequest request) {

        this.initialize();

        String url = "https://github.com/login/oauth/access_token";

        Map<String, String> params = new HashMap<>();
        params.put("client_id", "2cd8ce35fb2392ce6d04");
        params.put("client_secret", "ef073352dc0ea7cb169ea75e8393d3f5dfa4a51e");
        params.put("code", request.getParameter("code"));
        params.put("state", request.getParameter("state"));

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", MediaType.APPLICATION_JSON);

        String response = http.post(url, params, headers);
        return JsonParser.get(response, "access_token");
    }

    @Override
    public void initializeLogger() {
        this.setLogger(LogManager.getLogger(AuthenticationService.class));
    }
}
