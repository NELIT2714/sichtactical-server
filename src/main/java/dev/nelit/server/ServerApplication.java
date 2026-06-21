package dev.nelit.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication(exclude = {ReactiveUserDetailsServiceAutoConfiguration.class})
@EnableScheduling
public class ServerApplication {

    public static void main(String[] args) {
        String tz = System.getenv().getOrDefault("APP_TIMEZONE", "Europe/Warsaw");
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(tz)));
        SpringApplication.run(ServerApplication.class, args);
    }

}
