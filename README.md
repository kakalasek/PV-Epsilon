Description
===========
A very simple program which can convert csv to json array.
Possibly more formats will be supported. It uses a console
based interface.

Supported file examples
=======================

Csv
---

name,surname,number       
Sheldon,Trooper,773 123 123     
Yo,Moma,333 222 121     
Master,Oogway,221 123 554

Json
----

[       
{       
"name": "Sheldon",      
"surname": "Trooper",       
"number": "773 123 123"     
},      
{       
"name": "Yo",      
"surname": "Moma",       
"number": "333 222 121"     
},      
{       
"name": "Master",      
"surname": "Oogway",       
"number": "221 123 554"     
}       
]

How to use
==========

Clone the git repository with:

```shell
  git clone <URL of this repo>
```

or you can just download the zip file. Possibly even only the .jar file under *out/artifacts/PV_Epsilon_jar/*

After getting your hands on the jar file, make sure you have the right java version (**Java 21**) and execute this command:

```shell
    java --enable-preview -jar PV-Epsilon.jar
```

The CLI interface should appear. It is intuitive enough to not need further explanation.

Sources
=======
Jakob Jenkov - Java Virtual Threads - *https://www.youtube.com/watch?v=kirhhcFAGB4*     
Jakob Jenkov - The Java Memory Model Basics - *https://www.youtube.com/watch?v=LCSqZyjBwWA&t=1121s*         
Jakob Jenkov - Java ExecutorService part 1 - *https://www.youtube.com/watch?v=Nb85yJ1fPXM&t=285s*           
Geekific - A Guide To CompletableFuture In Java with Examples - *https://www.youtube.com/watch?v=xpjvY45Hbyg*           
Baeldung - Asynchronous Programming in Java - *https://www.baeldung.com/java-asynchronous-programming*          
Baeldung - Guide To CompletableFuture - *https://www.baeldung.com/java-completablefuture*           
Baeldung - A Guide to the Java ExecutorService - *https://www.baeldung.com/java-executor-service-tutorial*