package no.nav.melosys.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class KildekodeGeneratorServiceTest extends AssertionValidator {

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

        Map<String, Object> kodeTermer = new HashMap<>();
        kodeTermer.put("BRUKER", "BRUKER");
        kodeTermer.put("ARBEIDSGIVER", "ARBEIDSGIVER");
        kodeTermer.put("REPRESENTANT", "REPRESENTANT");
        kodeTermer.put("MYNDIGHET", "MYNDIGHET");

//        String kildeKode = kildeCodeGeneratorService.genererEnumKildeKode("Aktoerroller", kodeTermer);
//        validerEnumVerdier(kildeKode);

    }

    @Test
    public void genererEnumKildeKode_valueIMapErNull_genererKildeKoden() {

        List<String> interntkodeverktabellEnumFiler = new LinkedList<>();
        List<String> LovvalgBestemmelseEnumFiler = new LinkedList<>();

        kildekodeGeneratorService = new KildekodeGeneratorService(
            LovvalgBestemmelseEnumFiler,
            freeMarkerTemplateService);

        Map<String, Object> kodeTermer = new HashMap<>();
        kodeTermer.put("BRUKER", null);
        kodeTermer.put("ARBEIDSGIVER", null);
        kodeTermer.put("REPRESENTANT", null);
        kodeTermer.put("MYNDIGHET", null);

//        String kildeKode = kildeCodeGeneratorService.genererEnumKildeKode("Aktoerroller", kodeTermer);
//        validerEnumVerdier(kildeKode);

    }

    @Test
    public void velgTemplateType_classNavnFinnesList_velgRiktigTemplate() {

        List<String> LovvalgBestemmelseEnumFiler = new LinkedList<>();
        LovvalgBestemmelseEnumFiler.add("Forordning_883_2004");

        kildekodeGeneratorService = Mockito.spy(new KildekodeGeneratorService(
            LovvalgBestemmelseEnumFiler,
            freeMarkerTemplateService));

        Map<String, Object> kodeTermer = new HashMap<>();
//        String kildeKode = kildeCodeGeneratorService.genererEnumKildeKode("Aktoerroller", kodeTermer);
//        verify(kildeCodeGeneratorService, org.mockito.Mockito.times(1)).velgTemplate(anyString());
//        assertTrue(kildeKode.contains("implements InterntKodeverkTabell<Aktoerroller>"));

//        kildeKode = kildeCodeGeneratorService.genererEnumKildeKode("Forordning_883_2004", kodeTermer);
//        assertTrue(kildeKode.contains("implements LovvalgBestemmelse"));

//        kildeKode = kildeCodeGeneratorService.genererEnumKildeKode("DokumentTittel", kodeTermer);
//        assertTrue(kildeKode.contains("implements Kodeverk"));

    }
}