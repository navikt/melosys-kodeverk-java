<#ftl output_format="undefined" encoding="utf-8" auto_esc=false>
package no.nav.melosys.domain.kodeverk;

import javax.persistence.Converter;

public enum  ${classNavn} implements InterntKodeverkTabell<${classNavn}> {

    ${enumVerdi}

    private String kode;
    private String beskrivelse;

    ${classNavn}(String kode, String beskrivelse) {
        this.kode = kode;
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String getKode() {
        return kode;
    }

    @Override
    public String getBeskrivelse() {
        return beskrivelse;
    }

    @Converter
    public static class DbKonverterer extends InterntKodeverkTabell.DbKonverterer<${classNavn}> {
        @Override
        protected ${classNavn}[] getLovligeVerdier() {
            return ${classNavn}.values();
        }
    }

}
