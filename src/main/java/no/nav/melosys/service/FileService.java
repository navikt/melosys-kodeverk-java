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

public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    public static File lesFil(String path) {
        try {
            return new ClassPathResource(path).getFile();
        } catch (IOException e) {
            throw new RuntimeException("Fillesing feilet", e);
        }
    }

    public static byte[] lesFilTilByteArray(String path) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            return FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Fillesing feilet", e);
        }
    }

    public static File opprettJavaKildeFil(String mappe, String navn) {
        try {
            if (StringUtils.isEmpty(navn)) {
                throw new RuntimeException("Lagring av java-fil feilet fordi klassenavn er null eller tomt");
            }
            sjekkMappe(mappe);
            File sourceFile = new File(mappe, navn + ".java");
            sourceFile.createNewFile();
            return sourceFile;
        } catch (IOException e) {
            LOGGER.info("Mappe: {}", mappe);
            throw new RuntimeException("Lagring av java-fil feilet", e);
        }
    }

    private static void sjekkMappe(String mappe) {
        File file = new File(mappe);
        if (!file.exists()) {
            opprettMappe(mappe);
        }
    }

    public static void kopierFilTilMappe(File filIn, File filUt) {
        try {
            FileCopyUtils.copy(lesFilTilByteArray(filIn.toString()), filUt);
        } catch (IOException e) {
            throw new RuntimeException("Lagring av java-fil feilet", e);
        }
    }

    public static void opprettMappe(String mappe) {
        if (StringUtils.isEmpty(mappe)) {
            throw new RuntimeException("Kan ikke opprette mappe fordi mappenavn er null eller tomt");
        }
        File sourceFile = new File(mappe);
        sourceFile.mkdirs();
    }

    public static void skrivKildeKode(File javaSourceFil, String sourceCode) {
        try {
            if (!(javaSourceFil.exists() && javaSourceFil.canWrite())) {
                throw new RuntimeException("Lagring av java-fil feilet fordi java-filen ikke eksisterer eller ikke er skrivbar");
            }

            LOGGER.info("Skriver i fil {}", javaSourceFil.getAbsolutePath());
            FileWriter writer = new FileWriter(javaSourceFil);
            writer.write(sourceCode);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Lagring av kildekoden feilet", e);
        }
    }
}
