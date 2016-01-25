package team1.myFinance.web;

import org.apache.logging.log4j.LogManager;
import team1.myFinance.data.model.SavedUser;
import team1.myFinance.web.helper.JsonParser;
import team1.myFinance.web.model.Credentials;
import team1.myFinance.web.model.TokenWrapper;
import team1.myFinance.web.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import static javax.servlet.http.HttpServletResponse.*;

@Path("/users")
public class UserService extends ServiceBase {

    //constructor
    public UserService() {
        super();
    }

    @Override
    public void initializeLogger() {
        this.setLogger(LogManager.getLogger(UserService.class));
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public TokenWrapper login(String credstring, @Context HttpServletRequest request,
                              @Context HttpServletResponse response) {

        // TODO: remove
        this.setDataHandler(null);

        this.initialize();

        Credentials credentials = JsonParser.parse(credstring, Credentials.class);
        if (credentials == null) {
            logger.info("Could not parse user credentials: " + credstring);
            http.cancelRequest(response, SC_BAD_REQUEST);
            return null;
        }

        assert credentials != null;
        SavedUser user = dh.getUserLogin(credentials.username, credentials.hash);
        if (user == null) {
            http.cancelRequest(response, SC_UNAUTHORIZED);
            return null;
        }

        // Demodaten
//        SavedUser user = new SavedUser();
//        user.setAlias("Tom Riddle");
//        user.setId(1);
//        user.setRole(2);

        // Create auth tokenWrapper
        TokenWrapper tokenWrapper = new TokenWrapper();
        UserInfo userInfo = UserInfo.parse(user);
        tokenWrapper.token = this.auth.createAuthToken(userInfo);

        return tokenWrapper;
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo register(String credstring, @Context HttpServletRequest request,
                             @Context HttpServletResponse response) {

        this.initialize();

        Credentials credentials = JsonParser.parse(credstring, Credentials.class);
        if (credentials == null) {
            logger.info("Could not parse user credentials: " + credstring);
            http.cancelRequest(response, SC_BAD_REQUEST);
            return null;
        }

        assert credentials != null;

        //user already exists?
        if (dh.getUserByName(credentials.username) != null) {
            http.cancelRequest(response, SC_CONFLICT);
            return null;
        }

        SavedUser user = dh.createUser(credentials.username, credentials.hash, 2);

        // user create failed
        if (user == null) {
            http.cancelRequest(response, SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        assert user != null;
        response.setHeader("Location", "api/users/" + user.getId());
        response.setStatus(201);

        return null;
        //return UserInfo.parse(user);
    }


}
