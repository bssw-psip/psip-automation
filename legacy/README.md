# PSIP Automation Site

![Build & Test](https://github.com/bssw-psip/psip-automation/workflows/Build%20&%20Test/badge.svg)

The PSIP Automation Site provides a way for teams to self assess their projects, generate progress tracking cards (PTCs), and integrate these PTCs with their development workflow.

## Running the Project in Development Mode

```
mvn clean install
mvn spring-boot:run
```

Note that the "install" target is required when building for the first
time in order to generate the required import file.

Wait for the application to start

Open http://localhost:5000/ to view the application.

