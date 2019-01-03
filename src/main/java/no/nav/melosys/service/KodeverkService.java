package no.nav.melosys.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KodeverkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KodeverkService.class);

    private final String internKodeverkYaml;
    private final String enumKildeKodeMappe;
    private final FileService fileService;
    private final List<String> standardEnumFiler;

    private final KildeCodeGeneratorService kildeCodeGeneratorService;

    @Autowired
    public KodeverkService(@Value("${intern.kodeverk.yaml}") String internKodeverkYaml,
                           @Value("${enum.kilde.kode.mappe}") String enumKildeKodeMappe,
                           @Value("#{'${standard.enum.filer}'.split(',')}") List<String> standardEnumFiler,
                           FileService fileService,
                           KildeCodeGeneratorService kildeCodeGeneratorService) {
        this.internKodeverkYaml = internKodeverkYaml;
        this.enumKildeKodeMappe = enumKildeKodeMappe;
        this.fileService = fileService;
        this.standardEnumFiler = standardEnumFiler;
        this.kildeCodeGeneratorService = kildeCodeGeneratorService;
    }

    public HashMap<String, Object> yamlTilMelosysInternKodeverkObject() throws IOException {
        File file = fileService.lesFil(internKodeverkYaml);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(file, HashMap.class);
    }

    public void yamlTilJavaKildeFiler() throws IOException {
        kopiStandardJavaFiler();
        fileService.lagJavaPackageMapper(enumKildeKodeMappe);
        HashMap<String, Object> melosysInternKodeverk = yamlTilMelosysInternKodeverkObject();
        traverserHashMap("", melosysInternKodeverk, new HashSet<>());
    }

    public void kopiStandardJavaFiler() {
        standardEnumFiler.forEach(s -> fileService.kopiFilTilMappe(new File("standard", s), new File(enumKildeKodeMappe, s)));
    }

    private void traverserHashMap(String classNavn, HashMap<String, Object> map, Set<String> seenKey) {
        map.forEach((key, value) -> {
                if (value instanceof String) {
                    if (!seenKey.contains(classNavn)) {
                        LOGGER.info("Generer java class for : {}", classNavn);
                        objectTilJavaKildeKode(StringUtils.capitalize(classNavn), map);
                        seenKey.add(classNavn);
                    }
                } else if (value instanceof HashMap) {
                    traverserHashMap(key, (HashMap<String, Object>) value, seenKey);
                }
            }
        );
    }

    private void objectTilJavaKildeKode(String classNavn, Map<String, Object> enumVerdier) {
        File sourceFile = fileService.lagJavaKildeFil(enumKildeKodeMappe, classNavn);
        String sourceCode = kildeCodeGeneratorService.genererEnumKildeKode(classNavn, enumVerdier);
        fileService.skriveKildeKode(sourceFile, sourceCode);
    }
}
