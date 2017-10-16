package us.bojie.imbo.push.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import us.bojie.imbo.push.bean.api.account.RegisterModel;
import us.bojie.imbo.push.bean.card.UserCard;
import us.bojie.imbo.push.bean.db.User;
import us.bojie.imbo.push.factory.UserFactory;

// 127.0.0.1/api/account
@Path("/account")
public class AccountService {

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserCard register(RegisterModel model) {

        User user = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null) {
            UserCard card = new UserCard();
            card.setName("Existing account");
            return card;
        }

        user = UserFactory.findByName(model.getName().trim());
        if (user != null) {
            UserCard card = new UserCard();
            card.setName("Existing user name");
            return card;
        }

        user = UserFactory.register(model.getAccount(),
                model.getPassword(),
                model.getName());
        if (user != null) {
            UserCard card = new UserCard();
            card.setName(user.getName());
            card.setPhone(user.getPhone());
            card.setSex(user.getSex());
            card.setModifyAt(user.getUpdateAt());
            card.setFollow(true);
            return card;
        }
        return null;
    }
}
