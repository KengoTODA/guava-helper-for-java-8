# Guava Migration Helper SonarQube Plugin

This is a SonarQube plugin that helps developer to find remaining tasks for migration.
About the provided rules, see [README.md](../README.md#spotbugs-plugin) in this project root.

## Compatibility

|Guava Migration Helper Version|SonarQube Version|SonarJava Version|sonar-findbugs Version|
|----|----|----|----|
|v1.2.0|v7.9.0|v5.13.1.18282|v3.11|
|v1.0.5|v6.7.4|v5.1.0.13090|v3.5|
|v1.0.4|v5.6.6|v4.0|v3.5|

## How to test

This project provides a `Dockerfile` to launch SonarQube with necessary plugins, please run following commands then you can visit SonarQube at http://localhost:9000/

```sh
$ docker build -t sonarqube-with-plugin .
$ docker run -it -p 9000:9000 -p 9092:9092 sonarqube-with-plugin
```

For test, please do not forget to enable rules in your quality profile.
