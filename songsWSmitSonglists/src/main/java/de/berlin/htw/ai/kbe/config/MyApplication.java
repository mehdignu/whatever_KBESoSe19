package de.berlin.htw.ai.kbe.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {
    public MyApplication() {
        register(new DependencyBinder());
        packages(true, "de.berlin.htw.ai.kbe.resource");
        packages(true, "de.berlin.htw.ai.kbe.authentication");
    }
}
