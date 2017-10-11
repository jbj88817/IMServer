package us.bojie.imbo.push.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import us.bojie.imbo.push.bean.api.account.RegisterModel;
import us.bojie.imbo.push.bean.card.UserCard;

// 127.0.0.1/api/account
@Path("/account")
public class AccountService {

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserCard register(RegisterModel model) {
        UserCard card = new UserCard();
        card.setName(model.getName());
        card.setFollow(true);
        return card;
    }
}
