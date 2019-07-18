package no.nav.melosys.service;

import java.io.IOException;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import static org.junit.Assert.assertTrue;

public class KildekodeGeneratorServiceTest extends AssertionValidator {

    private static final String PACKAGE_PATH = "no.nav.melosys.domain.kodeverk";
    private FreeMarkerTemplateService freeMarkerTemplateService;

    private KildekodeGeneratorService kildekodeGeneratorService;

    @Before
    public void setUp() throws IOException {
        freeMarkerTemplateService = new FreeMarkerTemplateService("templates");
        freeMarkerTemplateService.getCfg().setDirectoryForTemplateLoading(new ClassPathResource("templates").getFile());
    }

    @Test
    public void genererEnumKildeKodeForInterntKodeverkTabell_classNavnOgEnumInformasjonFinnes_kildeKodenErGenerert() {
        List<String> LovvalgBestemmelseEnumFiler = new LinkedList<>();

        kildekodeGeneratorService = new KildekodeGeneratorService(
            LovvalgBestemmelseEnumFiler,
            freeMarkerTemplateService);

        List<Map<String, Object>> kodeTermer = Arrays.asList(
                lagMapForKodeTerm("BRUKER", "BRUKER"),
                lagMapForKodeTerm("ARBEIDSGIVER", "ARBEIDSGIVER"),
                lagMapForKodeTerm("REPRESENTANT", "REPRESENTANT"),
                lagMapForKodeTerm("MYNDIGHET", "MYNDIGHET")
                
        );

        String kildeKode = kildekodeGeneratorService.genererEnumKildeKode("Aktoerroller", PACKAGE_PATH, kodeTermer);
        validerEnumVerdier(kildeKode);
    }
    
    @Test
    public void genererEnumKildeKode_valueIMapErNull_genererKildeKoden() {
        List<String> LovvalgBestemmelseEnumFiler = new LinkedList<>();

        kildekodeGeneratorService = new KildekodeGeneratorService(
            LovvalgBestemmelseEnumFiler,
            freeMarkerTemplateService);

        List<Map<String, Object>> kodeTermer = Arrays.asList(
                lagMapForKodeTerm("BRUKER", null),
                lagMapForKodeTerm("ARBEIDSGIVER", null),
                lagMapForKodeTerm("REPRESENTANT", null),    
                lagMapForKodeTerm("MYNDIGHET", null)
        );

        String kildeKode = kildekodeGeneratorService.genererEnumKildeKode("Aktoerroller", PACKAGE_PATH, kodeTermer);
        validerEnumVerdier(kildeKode);

    }

    @Test
    public void velgTemplateType_classNavnFinnesList_velgRiktigTemplate() {
        List<String> LovvalgBestemmelseEnumFiler = new LinkedList<>();
        LovvalgBestemmelseEnumFiler.add("Forordning_883_2004");

        kildekodeGeneratorService = Mockito.spy(new KildekodeGeneratorService(
            LovvalgBestemmelseEnumFiler,
            freeMarkerTemplateService));

        List<Map<String, Object>> kodeTermer = Arrays.asList(lagMapForKodeTerm("term", "beskrivelse"));
        String kildeKode = kildekodeGeneratorService.genererEnumKildeKode("Aktoerroller", PACKAGE_PATH, kodeTermer);
        assertTrue(kildeKode.contains("implements Kodeverk"));

        kildeKode = kildekodeGeneratorService.genererEnumKildeKode("Forordning_883_2004", PACKAGE_PATH, kodeTermer);
        assertTrue(kildeKode.contains("implements LovvalgBestemmelse"));
    }

    private Map<String, Object> lagMapForKodeTerm(String kode, String term) {
        Map<String, Object> map = new HashMap<>();
        map.put("kode", kode);
        map.put("term", term);
        return map;
    }
}