<#ftl output_format="undefined" encoding="utf-8" auto_esc=false>
package no.nav.melosys.domain.kodeverk;

public enum ${classNavn} implements Kodeverk {

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
}
