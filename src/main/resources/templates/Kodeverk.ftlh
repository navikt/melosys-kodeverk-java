<#ftl output_format="undefined" encoding="utf-8" auto_esc=false>
package ${package};

import no.nav.melosys.domain.kodeverk.${parent};

public enum ${classNavn} implements ${parent} {

    ${enumVerdi}

    private String beskrivelse;

    private ${classNavn}(String beskrivelse) {
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
