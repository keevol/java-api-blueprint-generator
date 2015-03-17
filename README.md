# Java API Blueprint Generator

[![Build Status](https://travis-ci.org/fernandonogueira/java-api-blueprint-generator.svg?branch=master)](https://travis-ci.org/fernandonogueira/java-api-blueprint-generator)

This project has just been born.

Some basic functionality was implemented but it will be improved. Anyone is free to contribute.


# How to use it?

>
> Add annotations to your POJOs/Resources and Controllers
>

```java
@ApiResource(description = "This Resource/POJO contains information about users")
public class User {

    @ApiResourceAttr(description = "User birth date", mandatory = false)
    private Date birth;

    @ApiResourceAttr(description = "User's name", mandatory = true)
    private String name;

}
```

```java
@ApiController(
        description = "Describes requests to manipulate users",
        endPoint = "/user",
        resouceClass = "io.fernandonogueira.apitestproject.User")
public class UserController {

    @ApiRequest(endPoint = "/{id}", description = "Retrieves an user by id", method = ApiRequestMethod.GET)
    public User getUserById() {
        return null;
    }

}
```
>
> The result:
>

![](https://s3.amazonaws.com/publicimgs/java-api-blueprints-result.png)

# Add it to your project

>pom.xml

```xml
...

    <dependencies>
        <dependency>
            <groupId>japiblueprint</groupId>
            <artifactId>api-blueprint-generator</artifactId>
            <version>1.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
...

        <build>
            <plugins>
                <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.2</version>
                    <configuration>
                        <source>1.7</source>
                        <target>1.7</target>
                        <encoding>UTF-8</encoding>
                        <debug>true</debug>
                        <compilerArgs>
                            <arg>-AapiOutput=${basedir}/apiDoc</arg>
                        </compilerArgs>
                    </configuration>
                </plugin>
            </plugins>
        </build>
...

```
