# Food-Order-System
Create a food order system like a swigyy


1) implement a User-service:-port 8080
   Require :Db Config ,KeyCLoak Db Config

   KeyCloak :  first command :-docker run -d -p 8072:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:24.0.3 start-dev
               Second Command for keycloak db:- docker run -d --name keycloakdb -e POSTGRES_PASSWORD=root123 -p 5432:5432 -e POSTGRES_DB=keyclaokdb -d postgres
   
   Db Config:- docker run -d --name userservicedb -e POSTGRES_PASSWORD=root123 -p 5433:5432 -e POSTGRES_DB=userservicedb -d postgres

2)Config server:-
         port:8071
           Require Config server -add dependancy
           write all configration in this file
           
3)Eureka-sever:-
                 port:-8073
                 this is service registry ,register all microservice in this code

4)RabbitMq:- this rabbit mq is use for spring bus refresh a configration from github  port:-15672
                 
