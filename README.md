A tool set to help developer to use Guava with Java 8
=====================================================

[![Build Status](https://travis-ci.org/KengoTODA/guava-helper-for-java-8.svg)](https://travis-ci.org/KengoTODA/guava-helper-for-java-8)

Helper module
-------------

Helper module provides collectors which is useful to use Guava with Java 8.  
See [GuavaCollectors.java](helper/src/main/java/jp/skypencil/guava/stream/GuavaCollectors.java) and [its test cases](helper/src/test/java/jp/skypencil/guava/stream/GuavaCollectorsTest.java) for detail.

To use this module in your Maven project, please add following configuration to your `pom.xml`.

```xml
<dependency>
  <groupId>jp.skypencil.guava</groupId>
  <artifactId>helper</artifactId>
  <version>1.0.0</version>
</dependency>
```


Findbugs plugin
---------------

Findbugs plugin helps developer to find dependency on deprecated guava classes, which should be replaced with Java 8.
List of deprecated guava classes is in [DependencyOnDeprecatedGuavaClassDetector.java](findbugs-plugin/src/main/java/jp/skypencil/guava/DependencyOnDeprecatedGuavaClassDetector.java).

To use this module in your Maven project, please add following configuration to your `pom.xml`.

```xml
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>findbugs-maven-plugin</artifactId>
  <version>3.0.1</version>
  <configuration>
    <plugins>
      <plugin>
        <groupId>jp.skypencil.guava</groupId>
        <artifactId>findbugs-plugin</artifactId>
        <version>1.0.0</version>
      </plugin>
    </plugins>
  </configuration>
</plugin>
```


Copyright and license
---------------------

    Copyright 2015 Kengo TODA

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
