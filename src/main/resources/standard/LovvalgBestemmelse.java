package no.nav.melosys.domain;

public interface LovvalgBestemmelse extends no.nav.melosys.domain.Kodeverk {

    String name();

    // Til Hibernate https://hibernate.atlassian.net/browse/HHH-10858
    boolean equals(Object other);

    // Til Hibernate https://hibernate.atlassian.net/browse/HHH-10858
    int hashCode();
}
