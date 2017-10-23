package us.bojie.imbo.push.service;

import com.google.common.base.Strings;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
public class AccountService extends BaseService {

    // 登录
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountResponseModel> login(LoginModel model) {
        if (!LoginModel.check(model)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.login(model.getAccount(), model.getPassword());
        if (user != null) {
            // 如果有携带PushId
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                return bind(user, model.getPushId());
            }

            // 返回当前的账户
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

        // Check
        if (!RegisterModel.check(model)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }

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
            // 如果有携带PushId
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                return bind(user, model.getPushId());
            }

            // 返回当前的账户
            AccountResponseModel responseModel = new AccountResponseModel(user);
            return ResponseModel.buildOk(responseModel);
        } else {
            // 注册异常
            return ResponseModel.buildRegisterError();
        }
    }

    // 绑定设备Id
    @POST
    @Path("/bind/{pushId}")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 从请求头中获取token字段
    // pushId从url地址中获取
    public ResponseModel<AccountResponseModel> bind(@PathParam("pushId") String pushId) {
        if (Strings.isNullOrEmpty(pushId)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }

        User user = getSelf();
        return bind(user, pushId);
    }

    /**
     * 绑定的操作
     *
     * @param self   自己
     * @param pushId PushId
     * @return User
     */
    private ResponseModel<AccountResponseModel> bind(User self, String pushId) {
        // 进行设备Id绑定的操作
        User user = UserFactory.bindPushId(self, pushId);
        if (user == null) {
            // 绑定失败则是服务器异常
            return ResponseModel.buildServiceError();
        }

        // 返回当前的账户, 并且已经绑定了
        AccountResponseModel responseModel = new AccountResponseModel(user, true);
        return ResponseModel.buildOk(responseModel);
    }
}
