package no.nav.melosys.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

public class KildeCodeGeneratorServiceTest extends AssertionValidator {

    @Test
    public void genererEnumKildeKodeForInterntKodeverkTabell_classNavnOgEnumInformasjonFinnes_genererKildeKoden() throws Exception {

        List<String> interntkodeverktabellEnumFiler = new LinkedList<>();
        List<String> LovvalgBestemmelseEnumFiler = new LinkedList<>();

        KildeCodeGeneratorService kildeCodeGeneratorService = Mockito.spy(new KildeCodeGeneratorService(
            "templates\\",
            interntkodeverktabellEnumFiler,
            LovvalgBestemmelseEnumFiler));

        Map<String, Object> kodeTermer = new HashMap<>();
        kodeTermer.put("BRUKER", "BRUKER");
        kodeTermer.put("ARBEIDSGIVER", "ARBEIDSGIVER");
        kodeTermer.put("REPRESENTANT", "REPRESENTANT");
        kodeTermer.put("MYNDIGHET", "MYNDIGHET");

        String kildeKode = kildeCodeGeneratorService.genererEnumKildeKode("RolleType", kodeTermer);
        validerEnumVerdier(kildeKode);

    }

    @Test
    public void velgTemplateType_classNavnFinnesList_velgRiktigTemplate() throws Exception {

        List<String> interntkodeverktabellEnumFiler = new LinkedList<>();
        interntkodeverktabellEnumFiler.add("Aktoerroller");

        List<String> LovvalgBestemmelseEnumFiler = new LinkedList<>();
        LovvalgBestemmelseEnumFiler.add("Forordning_883_2004");

        KildeCodeGeneratorService kildeCodeGeneratorService = Mockito.spy(new KildeCodeGeneratorService(
            "templates\\",
            interntkodeverktabellEnumFiler,
            LovvalgBestemmelseEnumFiler));

        Map<String, Object> kodeTermer = new HashMap<>();
        String kildeKode = kildeCodeGeneratorService.genererEnumKildeKode("Aktoerroller", kodeTermer);
        verify(kildeCodeGeneratorService, org.mockito.Mockito.times(1)).velgTemplate(anyString());
        assertTrue(kildeKode.contains("implements InterntKodeverkTabell<Aktoerroller>"));

        kildeKode = kildeCodeGeneratorService.genererEnumKildeKode("Forordning_883_2004", kodeTermer);
        assertTrue(kildeKode.contains("implements LovvalgBestemmelse"));

        kildeKode = kildeCodeGeneratorService.genererEnumKildeKode("DokumentTittel", kodeTermer);
        assertTrue(kildeKode.contains("implements Kodeverk"));

    }
}