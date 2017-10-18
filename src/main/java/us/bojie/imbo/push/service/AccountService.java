package us.bojie.imbo.push.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import us.bojie.imbo.push.bean.api.account.AccountResponseModel;
import us.bojie.imbo.push.bean.api.account.LoginModel;
import us.bojie.imbo.push.bean.api.account.RegisterModel;
import us.bojie.imbo.push.bean.api.base.ResponseModel;
import us.bojie.imbo.push.bean.db.User;
import us.bojie.imbo.push.factory.UserFactory;

// 127.0.0.1/api/account
@Path("/account")
public class AccountService {

    // 登录
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountResponseModel> login(LoginModel model) {
        User user = UserFactory.login(model.getAccount(), model.getPassword());
        if (user != null) {
            AccountResponseModel responseModel = new AccountResponseModel(user);
            return ResponseModel.buildOk(responseModel);
        } else {
            return ResponseModel.buildLoginError();
        }
    }

    // 注册
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountResponseModel> register(RegisterModel model) {

        // 已有账户
        User user = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null) {
            return ResponseModel.buildHaveAccountError();
        }

        // 已有用户名
        user = UserFactory.findByName(model.getName().trim());
        if (user != null) {
            return ResponseModel.buildHaveNameError();
        }

        // 开始注册逻辑
        user = UserFactory.register(model.getAccount(),
                model.getPassword(),
                model.getName());
        if (user != null) {
            AccountResponseModel responseModel = new AccountResponseModel(user);
            return ResponseModel.buildOk(responseModel);
        } else {
            // 注册异常
            return ResponseModel.buildRegisterError();
        }
    }
}
