package no.nav.melosys.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class})
@ContextConfiguration(classes = {KodeverkService.class, FileService.class, KildeCodeGeneratorService.class, FreeMarkerTemplateService.class})
@PropertySource("classpath:application.properties")

public class KodeverkServiceIT extends AssertionValidator {

    @Autowired
    private KodeverkService kodeverkService;

    @Test
    public void lagJavaClass_objectIkkeNull_generereJavaSourceFil() throws IOException {

        HashMap<String, Object> melosysInternKodeverkMap = kodeverkService.yamlTilMelosysInternKodeverkObject();

        assertNotNull(melosysInternKodeverkMap);
    }

    @Test
    public void lagSourceCode_sourceFilIkkeNull_skrivJavaSourceCode() throws IOException {

        kodeverkService.yamlTilJavaKildeFiler();
        File aktoerRoller = new File("melosys-kodeverk\\src\\main\\java\\no\\nav\\melosys\\domain\\kodeverk\\Aktoerroller.java");
        assertTrue(aktoerRoller.exists());
        String contents = new String(Files.readAllBytes(Paths.get(aktoerRoller.toString())));
        validerEnumVerdier(contents);
    }

    @Test
    public void kopiStandardJavaFiler_fraResources_kopiererFiler() throws IOException {
        kodeverkService.kopiStandardJavaFiler();
        assertTrue(new File("melosys-kodeverk\\src\\main\\java\\no\\nav\\melosys\\domain\\kodeverk\\Kodeverk.java").exists());
    }
}
