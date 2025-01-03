Description
===========
A very simple program which can convert csv to json array.
Possibly more formats will be supported. It uses a console
based interface.        
It was created primarily to showcase the use of asynchronous methods and cooperative multitasking, although the latter
is not really present. The file conversions can be big and take a long time, so for smooth experience this program
starts conversion on a different java virtual thread, thus asynchronously. Like this, you are able to start multiple 
file conversions at once. You can look at their status. The list of statuses is ordered based on the time you started 
the conversions.            
I am purposefully using virtual threads, because, in theory, they kinda use cooperative multitasking in the background. 
I was 2/3 into this project, when I realized, that asyncio in Python would be a much better example, so I at least 
finished this.          
When entering file paths, I strongly advice to use absolute paths, since I am unsure if the underlying functions work 
with relative ones and I haven't had time to test it.           
Also, this program is not tested on Windows, because feck Windows, that why. Use Unix.


Supported file examples
=======================

The default CSV delimiter is set to "," (a comma). You can change it in settings. Note that if you change it, the change will only last until you shut down the program.

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
