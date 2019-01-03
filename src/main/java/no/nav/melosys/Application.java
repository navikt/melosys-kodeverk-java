package no.nav.melosys;

import no.nav.melosys.service.KodeverkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;


@SpringBootApplication
public class Application implements CommandLineRunner {

    private final KodeverkService kodeverkService;

    @Autowired
    public Application(KodeverkService kodeverkService) {
        this.kodeverkService = kodeverkService;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        exit(0);

    }

    @Override
    public void run(String... args) throws Exception {
        kodeverkService.yamlTilJavaKildeFiler();
    }
}

