package us.bojie.imbo.push.service;

import com.google.common.base.Strings;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import us.bojie.imbo.push.bean.api.base.ResponseModel;
import us.bojie.imbo.push.bean.api.user.UpdateInfoModel;
import us.bojie.imbo.push.bean.card.UserCard;
import us.bojie.imbo.push.bean.db.User;
import us.bojie.imbo.push.factory.UserFactory;

/**
 * 用户信息处理的Service
 */
// 127.0.0.1/api/user
@Path("/user")
public class UserService {

    @PUT
    //@Path("") //127.0.0.1/api/user 不需要写，就是当前目录
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(@HeaderParam("token") String token,
                                                      UpdateInfoModel model) {
        if (Strings.isNullOrEmpty(token) || !UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        // 拿到自己的个人信息
        User user = UserFactory.findByToken(token);
        if (user != null) {
            // 更新用户信息
            user = model.updateToUser(user);
            user = UserFactory.update(user);
            // 构架自己的用户信息
            UserCard card = new UserCard(user, true);
            // 返回
            return ResponseModel.buildOk(card);
        } else {
            // Token 失效，所有无法进行update
            return ResponseModel.buildAccountError();
        }

    }
}