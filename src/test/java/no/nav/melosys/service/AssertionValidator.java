package no.nav.melosys.service;

import static org.junit.Assert.assertTrue;

class AssertionValidator {

    void validerEnumVerdier(String contents) {
        assertTrue(contents.contains("BRUKER"));
        assertTrue(contents.contains("ARBEIDSGIVER"));
        assertTrue(contents.contains("REPRESENTANT"));
        assertTrue(contents.contains("MYNDIGHET"));
    }
}
