package no.nav.melosys.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class KodeverkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KodeverkService.class);

    private static final String STANDARD_TEMPLATE_MAPPE = "standard";

    private final String internKodeverkYaml;
    private final String enumKildeKodeMappe;
    private final List<String> standardEnumFiler;

    private final KildekodeGeneratorService kildekodeGeneratorService;
    private static final String BASE_PACKAGE = "no.nav.melosys.domain.kodeverk";

    @Autowired
    public KodeverkService(@Value("${intern.kodeverk.yaml}") String internKodeverkYaml,
                           @Value("${enum.kilde.kode.mappe}") String enumKildeKodeMappe,
                           @Value("#{'${standard.enum.filer}'.split(',')}") List<String> standardEnumFiler,
                           KildekodeGeneratorService kildekodeGeneratorService) {
        this.internKodeverkYaml = internKodeverkYaml;
        this.enumKildeKodeMappe = enumKildeKodeMappe;
        this.standardEnumFiler = standardEnumFiler;
        this.kildekodeGeneratorService = kildekodeGeneratorService;
    }

    public void yamlTilJavaKildeFiler() throws IOException {
        opprettStandardFiler();
        traverserStruktur(lesKodeverkStrukturFraYaml(), BASE_PACKAGE);
    }

    void opprettStandardFiler() {
        FileService.opprettMappe(filstiForJavaPakke(BASE_PACKAGE));
        FileService.kopierFilTilMappe(new File(STANDARD_TEMPLATE_MAPPE, "pom.xml"), new File("melosys-kodeverk", "pom.xml"));
        standardEnumFiler.forEach(s -> FileService.kopierFilTilMappe(new File(STANDARD_TEMPLATE_MAPPE, s), new File(filstiForJavaPakke(BASE_PACKAGE), s + ".java")));
    }

    HashMap<String, Object> lesKodeverkStrukturFraYaml() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        return mapper.readValue(new String(FileService.lesFilTilByteArray(internKodeverkYaml)), HashMap.class);
    }

    private void traverserStruktur(HashMap<String, Object> map, String javaPakke) {
        map.forEach((key, value) -> {
                if (erEnum(value)) {
                    opprettJavaEnumKildekode(key, javaPakke, (List<Map<String, Object>>) value);
                } else if (harUnderstruktur(value)) {
                    traverserStruktur((HashMap<String, Object>) value, javaPakke + "." + key);
                } else {
                    throw new RuntimeException("Uventet type for key " + javaPakke + ":" + key + " med value " + value);
                }
            }
        );
    }

    private void opprettJavaEnumKildekode(String enumNavn, String javaPakke, List<Map<String, Object>> enumVerdier) {
        enumNavn = StringUtils.capitalize(enumNavn);
        File sourceFile = FileService.opprettJavaKildeFil(filstiForJavaPakke(javaPakke), enumNavn);
        String sourceCode = kildekodeGeneratorService.genererEnumKildeKode(enumNavn, javaPakke, enumVerdier);
        FileService.skrivKildeKode(sourceFile, sourceCode);
    }

    private boolean harUnderstruktur(Object value) {
        return value instanceof HashMap;
    }

    private boolean erEnum(Object value) {
        return value instanceof List;
    }

    private String filstiForJavaPakke(String javaPakke) {
        return enumKildeKodeMappe + javaPakke.replace(".", "/");
    }
}
