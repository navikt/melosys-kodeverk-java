package no.nav.melosys.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

@Component
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

     File lesFil(String path) {
        try {
            return new ClassPathResource(path).getFile();
        } catch (IOException e) {
            throw new RuntimeException("Fil lesing feilet :" + e.getMessage());
        }
    }

    byte[] lesFilTilByteArray(String path) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            return FileCopyUtils.copyToByteArray(classPathResource.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException("Fil lesing feilet :" + e.getMessage());
        }
    }

    File lagJavaKildeFil(String mappe, String navn) {
        try {
            if (StringUtils.isEmpty(navn)) {
                throw new RuntimeException("Lagring av java klass fil feilet fordi klass navn er null ellers tom");
            }
            File sourceFile = new File(mappe, navn + ".java");
            sourceFile.createNewFile();
            return sourceFile;
        } catch (IOException e) {
            throw new RuntimeException("Lagring av java fil feilet : " + e.getMessage());
        }
    }

    void kopiFilTilMappe(File filIn, File filUt) {
        try {
            FileCopyUtils.copy(lesFilTilByteArray(filIn.toString()), filUt);
        } catch (IOException e) {
            throw new RuntimeException("Lagring av java fil feilet : " + e.getMessage());
        }
    }

    void lagJavaPackageMapper(String mappe) {
        if (StringUtils.isEmpty(mappe)) {
            throw new RuntimeException("Kan ikke lagre mappe fordi mappe navn er null ellers tom");
        }
        File sourceFile = new File(mappe);
        sourceFile.mkdirs();
    }

    void skriveKildeKode(File javaSourceFil, String sourceCode) {
        try {
            if (!(javaSourceFil.exists() && javaSourceFil.canWrite())) {
                throw new RuntimeException("Lagring av java klass fil feilet fordi java klass filen ikke eksisterer ellers ikke skrivebart ");
            }

            LOGGER.info("Skriver i fil {}", javaSourceFil.getAbsolutePath());
            FileWriter writer = new FileWriter(javaSourceFil);
            writer.write(sourceCode);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Lagring av kildekoden feilet" + e.getMessage());
        }
    }
}
