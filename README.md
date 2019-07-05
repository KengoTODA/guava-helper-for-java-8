Migrate your code from legacy Guava to Java 8
=============================================

[![Travis CI Build Status](https://travis-ci.org/KengoTODA/guava-helper-for-java-8.svg)](https://travis-ci.org/KengoTODA/guava-helper-for-java-8)
[![Javadocs](http://javadoc.io/badge/jp.skypencil.guava/helper.svg)](http://javadoc.io/doc/jp.skypencil.guava/helper)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=jp.skypencil.guava%3Aguava-helper-for-java-8&metric=alert_status)](https://sonarcloud.io/dashboard?id=jp.skypencil.guava%3Aguava-helper-for-java-8)

Guava was great library for Java 7 users. But now we have Java 8 and it has many useful features like `Optional`, `Stream` and `Lambda`. So some features in Guava should be replaced with them.

Other parts in Guava are useful even for Java 8, especially collections. But Guava is designed for Java 6+, so it does not provide `java.util.stream.Collector` for Guava collections. So when you want to collect stream to Guava collections, you need some hack which makes your code nonintuitive.

This toolset solves these two problems. For former problem, this toolset provides SpotBugs plugin which reports dependency on deprecated Guava classes. For latter problem, this toolset provides `GuavaCollectors` class which provides many `java.util.stream.Collector` instances for Guava collections.

This toolset will help your development with Java 8 and good parts of Guava library.

SpotBugs plugin
---------------

SpotBugs plugin helps developer to find dependency on deprecated guava classes, which should be replaced with Java 8.
List of deprecated guava classes is in [DependencyOnDeprecatedGuavaClassDetector.java](spotbugs-plugin/src/main/java/jp/skypencil/guava/DependencyOnDeprecatedGuavaClassDetector.java).

To use this module in your Maven project, please add following configuration to your `pom.xml`.

```xml
<plugin>
  <groupId>com.github.spotbugs</groupId>
  <artifactId>spotbugs-maven-plugin</artifactId>
  <version>3.1.11</version>
  <configuration>
    <plugins>
      <plugin>
        <groupId>jp.skypencil.guava</groupId>
        <artifactId>spotbugs-plugin</artifactId>
        <version>1.2.0</version>
      </plugin>
    </plugins>
  </configuration>
</plugin>
```

Helper module
-------------

Helper module provides collectors which is useful to use Guava with Java 8.  

```java
ImmutableMap<String, Integer> map = Stream.of(1, 2)
        .collect(GuavaCollectors.toImmutableMap(Object::toString, UnaryOperator.identity()));
```

See [GuavaCollectors.java](helper/src/main/java/jp/skypencil/guava/stream/GuavaCollectors.java) and [its test cases](helper/src/test/java/jp/skypencil/guava/stream/GuavaCollectorsTest.java) for detail usage.
To use this module in your Maven project, please add following configuration to your `pom.xml`.

```xml
<dependency>
  <groupId>jp.skypencil.guava</groupId>
  <artifactId>helper</artifactId>
  <version>1.2.0</version>
</dependency>
```

SonarQube plugin
----------------

See [sonarqube-plugin/README.md](sonarqube-plugin/README.md) for detail.

Change set
----------

Please visit [release page](https://github.com/KengoTODA/guava-helper-for-java-8/releases).

Copyright and license
---------------------

    Copyright 2015-2019 Kengo TODA

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
