<#ftl output_format="undefined" encoding="utf-8" auto_esc=false>
package no.nav.melosys.domain.kodeverk;

public enum ${classNavn} implements LovvalgBestemmelse {

    ${enumVerdi}

    private String beskrivelse;

    ${classNavn}(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String getKode() {
        return name();
    }

    @Override
    public String getBeskrivelse() {
        return beskrivelse;
    }
}
