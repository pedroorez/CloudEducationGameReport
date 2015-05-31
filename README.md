# Cloud Game Report 

If you desire to learn more or contribute to this project, contact: pedroorez@poli.ufrj.br, under supervision of sbVB (sbvillasboas@gmail.com). www.sbvb.com.br

# About:
Cloud Game Report is a cloud based tool with the objective to compile results of Educational Games in a single platform creating the possibility of analysis not able before, such as reports of students, classes, etc.

# Content:
This project of 3 componentes. The CloudGameReport and 2 games developed to prove the concept

* **Cloud Game Report (Web Only)**: A cloud solution to acquire informations about educational games and generate reports. It's have a web application where the professor can manager his classes and create reports.

The following games were made on Unity4.6. Each one also have a web application where it's possible to create your own iteration of the game with ease. After creation the game is available to be played.

* **ESIa - Education Space Invaders Advanced (Mobile + Web):** A mobile "Space Invaders" Cloned with an education implementation. Several GameModes availables (WIP)

* **QuizQuest (Mobile + Web):** Educational quiz based game with RPG-like components creating an addictive way of learning.


# Seting up Project:

## Server Side:

All the server-side logic is made using Java runing on a Tomcat Server, although it can also run using GlassFish Server from Oracle.

This project use Netbeans to unify the libraries used such as SpringMVC, Jackson, SimpleUploadLib, Hibernate and the PostgreSQL JDBC Driver. 

All Netbeans projects use hibernate and postgresql, so it's only nescessary to install postgresql and configure it to use the the database. All tables will be created whe you startup the Tomcat Server.

I case you want to use another IDE you will need to reimport the JAR files witch are located on the /JARs folder. 

## Mobile Apps

Both Games were develop using Unity3D 4.6 beta. Since it uses the new UI system you will need any version from unity newer than the 4.6 version


