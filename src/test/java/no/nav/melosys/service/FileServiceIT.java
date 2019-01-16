package no.nav.melosys.service;

import java.io.File;

import no.nav.melosys.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class})
@ContextConfiguration(classes = {FileService.class})

public class FileServiceIT {

    @Autowired
    private FileService fileService;

    @Test
    public void lesYamlFil_finnesFil_kanLese() {
        File yamlFile = fileService.lesFil("kodeset_yaml.yaml");
        assertTrue(yamlFile.canRead());
    }

    @Test
    public void lagJavaPackageMapper_forGittSti_mappenErOpprettet() throws Exception {
        fileService.lagJavaPackageMapper("melosys-kodeverk/src/main/java/no/nav/melosys/domain/kodeverk");
        assertTrue(new File("melosys-kodeverk/src/main/java/no/nav/melosys/domain/kodeverk").exists());
    }

}