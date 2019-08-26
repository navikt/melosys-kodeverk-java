# Melosys-kodeverk-java
This is a standalone application which generates Java enum classes based on the information 
provided by a yaml file and a version file.

A `mvn install` will build **melosys-kodeverk-generator**, which will in turn be used to 
generate files for **melosys-internt-kodeverk**, which will then be built as the final step.

This project/application can be used in three different ways, depending on what the goal is:
* Standalone development/changes to the application itself. By default, the application will 
generate the enums based on the `kodemap.yml` located in 
`melosys-kodeverk-generator/src/test/resources/dist`. The file is simply meant for test
purposes during development, and is not intended to be kept up to date.
* As a local build tool during development of **melosys-kodeverk**. You can specify 
`MELOSYS_KODEVERK_FRONTEND` either as an environment variable (probably easiest) or a system
property (e.g. `-DMELOSYS_KODEVERK_FRONTEND=latest`). The value can be either `latest`,
which will call npm to download the latest available artifact, and generate with that as a 
basis, or the path to
your checked out **melosys-kodeverk** directory (absolute or relative to
**melosys-kodeverk-java**). Other than that, it works as the previous option.
* As a continuous integration tool for automatically releasing Java enums matching what is 
defined in **navikt/melosys-kodeverk** frontend. This is achieved by building and releasing
this application as a Docker image, that is in turn called by the CI server when building 
**melosys-kodeverk**. The Maven artifact version will be given a build number matching the 
one in the npm build it is based on. For example `5.8.0:58` becomes `5.8.0-58`. Check the 
CircleCI-config in these two projects to see how it is set up. Note that **melosys-kodeverk** 
uses a specific version of this Docker image, not *latest* or similar, so after updating and 
releasing **melosys-kodeverk-java**, you need to manually update the CircleCI config in 
**melosys-kodeverk** with the reference to the new Docker image.

## Documentation
Internal documentation here: [Confluence](https://confluence.adeo.no/display/TEESSI). 

## Inquiries
Questions related to the application should be routed to 
@navikt/melosys-java

## For NAV-Employees
Internal resposes can be sent on Slack "#melosys-utvikling"

