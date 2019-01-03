<#ftl output_format="undefined" auto_esc=false>
package no.nav.melosys.domain;

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
