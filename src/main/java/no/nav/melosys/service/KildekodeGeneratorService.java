package no.nav.melosys.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.joining;

@Component
public class KildekodeGeneratorService {

    private final static String KODEVERK_TEMPLATE = "Kodeverk.ftlh";

    private final FreeMarkerTemplateService freeMarkerTemplateService;

    private final List<String> lovvalgBestemmelseEnumFiler;

    @Autowired
    public KildekodeGeneratorService(@Value("#{'${lovvalgBestemmelse.enum.filer}'.split(',')}") List<String> lovvalgBestemmelseEnumFiler,
                                     FreeMarkerTemplateService freeMarkerTemplateService) {
        this.lovvalgBestemmelseEnumFiler = lovvalgBestemmelseEnumFiler;
        this.freeMarkerTemplateService = freeMarkerTemplateService;
    }

    String genererEnumKildeKode(String classNavn, String packagePath, List<Map<String, Object>> listEnumMap) {
        Map root = new HashMap();

        String enumList = listEnumMap.stream()
            .map((kode) -> formaterEnumVerdi(kode))
            .collect(joining(",\n\t"))
            .concat(";");

        root.put("enumVerdi", enumList);
        root.put("classNavn", classNavn);
        root.put("package", packagePath);
        root.put("parent", lovvalgBestemmelseEnumFiler.contains(classNavn) ? "LovvalgBestemmelse" : "Kodeverk");

        try {
            return freeMarkerTemplateService.generereKildeKodeFraTemplate(root, KODEVERK_TEMPLATE);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Lagring av Java-fil feilet for classNavn " + classNavn, e);
        }
    }

    private String formaterEnumVerdi(Map<String, Object> map) {
        return String.format("%s1(\"%s2\")", map.get("kode"), map.get("term"));
    }
}
