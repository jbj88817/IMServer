package us.bojie.imbo.push.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import us.bojie.imbo.push.bean.db.User;

// 127.0.0.1/api/account
@Path("/account")
public class AccountService {

    // 127.0.0.1/api/account/login
    @GET
    @Path("/login")
    public String get() {
        return "You get the login";
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User post() {
        User user = new User();
        user.setName("Bojie");
        user.setSex(1);
        return user;
    }
}
