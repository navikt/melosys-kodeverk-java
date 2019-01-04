<#ftl output_format="undefined" auto_esc=false>
package no.nav.melosys.domain.kodeverk;

public enum ${classNavn} implements LovvalgBestemmelse {

    ${enumVerdi}

    private String beskrivelse;

    ${classNavn}(String beskrivelse) {
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
}
