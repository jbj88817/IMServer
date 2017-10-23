package us.bojie.imbo.push;

import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

import us.bojie.imbo.push.provider.AuthRequestFilter;
import us.bojie.imbo.push.provider.GsonProvider;
import us.bojie.imbo.push.service.AccountService;

public class Application extends ResourceConfig {
    public Application() {
        // 注册逻辑处理的包名
        packages(AccountService.class.getPackage().getName());

        // 注册我们的全局请求拦截器
        register(AuthRequestFilter.class);

        // 注册Json解析器
//        register(JacksonJsonProvider.class);
        // 替换解析器为Gson
        register(GsonProvider.class);

        // 注册日志打印输出
        register(Logger.class);
    }
}
