package us.bojie.imbo.push;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

import us.bojie.imbo.push.service.AccountService;

public class Application extends ResourceConfig {
    public Application() {
        packages(AccountService.class.getPackage().getName());
        register(JacksonJsonProvider.class);
        register(Logger.class);
    }
}
