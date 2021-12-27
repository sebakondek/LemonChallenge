package org.lemon.configuration.module;

import com.google.inject.AbstractModule;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.lemon.configuration.util.DatabaseUtils;
import org.lemon.entrypoint.HealthCheckEntrypoint;
import org.lemon.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.RouteGroup;
import spark.Spark;
import spark.servlet.SparkApplication;

import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class Application extends AbstractModule implements SparkApplication, RouteGroup {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final ConcurrentMap<String, Injector> INJECTORS = new ConcurrentHashMap<>();

    private static final int DEFAULT_SERVER_PORT = 8080;
    private static final String DEFAULT_SERVER_ADDRESS = "127.0.0.1";
    private static final int DEFAULT_SERVER_BASE_THREADS = 2;
    private static final int DEFAULT_SERVER_REQUEST_TIMEOUT = 25000;

    /**
     * Injector name to add to Config.addInjector()
     */
    public static final String APP = "app";

    private int port;
    private String address;
    private int baseThreads;
    private int maxMultiplier;
    private int minMultiplier;
    private int timeout;
    private String basePath;

    public Application() {
        try {
            initializeWithDefaults();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        configureServer();
        dependencyInjectionConfiguration();
        createDBSchema();
        setUpRoutes();
        Spark.awaitInitialization();
    }

    protected void initializeWithDefaults() throws UnknownHostException {
        port = DEFAULT_SERVER_PORT;
        address = DEFAULT_SERVER_ADDRESS;

        baseThreads = DEFAULT_SERVER_BASE_THREADS;
        maxMultiplier = 2;
        minMultiplier = 1;

        timeout = DEFAULT_SERVER_REQUEST_TIMEOUT;
        basePath = "/";
    }

    protected void dependencyInjectionConfiguration() {
       addInjector(APP, Guice.createInjector(this,
               new UtilsModule(),
               new UseCaseModule(),
               new RepositoryModule(),
               new SQLModule()));
    }

    public static void addInjector(String name, Injector injector) {
        INJECTORS.put(name, injector);
    }

    public static Injector getInjector(String name) {
        return INJECTORS.get(name);
    }

    public static void clearInjectors() {
        INJECTORS.clear();
    }

    protected void configureServer() {
        final int processors = Runtime.getRuntime().availableProcessors();

        final int maxThreads = processors * maxMultiplier + baseThreads;
        final int minThreads = processors * minMultiplier + baseThreads;

        Spark.port(port);
        Spark.ipAddress(address);

        Spark.threadPool(maxThreads, minThreads, timeout);

        log.info("Listening in {}:{} using thread pool: [min:{} | max:{} | timeout:{}]", address, port, minThreads, maxThreads, timeout);
    }

    @Override
    public void destroy() {
        Spark.stop();
        clearInjectors();
    }

    private void setUpRoutes() {
        Spark.get("/ping", getInstance(HealthCheckEntrypoint.class));

        Spark.path(basePath, this);

        Objects.requireNonNull(getInstance(ExceptionHandler.class)).register();
    }

    public static <T> T getInstance(Class<T> clazz) {
        try {
            final Injector injector = getInjector(APP);

            if (injector == null) {
                return null;
            }

            return injector.getInstance(clazz);
        } catch (ConfigurationException ex) {
            return null;
        }
    }

    private void createDBSchema() {
        DatabaseUtils.createAllTables();
    }
}
