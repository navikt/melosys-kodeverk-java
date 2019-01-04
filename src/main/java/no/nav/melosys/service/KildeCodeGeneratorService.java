package no.nav.melosys.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KildeCodeGeneratorService {

    private final static String INTERNKODEVERKTABELL_TEMPLATE = "InterntKodeverkTabell.ftlh";

    private final static String LOVVALGBESTEMMELSE_TEMPLATE = "LovvlagBestemmelse.ftlh";

    private final static String KODEVERK_TEMPLATE = "Kodeverk.ftlh";

    private final FreeMarkerTemplateService freeMarkerTemplateService;

    private List<String> interntKodeverkTabellEnumFiler;

    private List<String> lovvalgBestemmelseEnumFiler;

    @Autowired
    public KildeCodeGeneratorService(@Value("#{'${interntkodeverktabell.enum.filer}'.split(',')}") List<String> interntkodeverktabellEnumFiler,
                                     @Value("#{'${lovvalgBestemmelse.enum.filer}'.split(',')}") List<String> lovvalgBestemmelseEnumFiler,
                                     FreeMarkerTemplateService freeMarkerTemplateService) {
        this.interntKodeverkTabellEnumFiler = interntkodeverktabellEnumFiler;
        this.lovvalgBestemmelseEnumFiler = lovvalgBestemmelseEnumFiler;
        this.freeMarkerTemplateService = freeMarkerTemplateService;
    }

    public String genererEnumKildeKode(String classNavn, Map<String, Object> enumMap) {
        Map root = new HashMap();
        root.put("classNavn", classNavn);

        String enumList = enumMap.entrySet().stream()
            .map((kode) -> kode.getKey() + "(\"" + kode.getKey() + "\", \"" + kode.getValue() + "\")")
            .collect(Collectors.joining(",\n\t"))
            .concat(";");

        root.put("enumVerdi", enumList);

        try {
            return freeMarkerTemplateService.generereKildeKodeFraTemplate(root, velgTemplate(classNavn));
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Lagring av Java fil feilet for classNavn " + classNavn + " " + e.getMessage());
        }
    }

    public String velgTemplate(String classNavn) {
        if (interntKodeverkTabellEnumFiler.contains(classNavn)) {
            return INTERNKODEVERKTABELL_TEMPLATE;
        } else if (lovvalgBestemmelseEnumFiler.contains(classNavn)) {
            return LOVVALGBESTEMMELSE_TEMPLATE;
        } else {
            return KODEVERK_TEMPLATE;
        }
    }
}
