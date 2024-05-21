# Food-Order-System

Create a food order system like a swiggy

1) implement a User-service:-port 8080
   Require :Db Config ,KeyCloak Db Config

   KeyCloak :  first command :-docker run -d -p 8072:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin
   quay.io/keycloak/keycloak:24.0.3 start-dev
   Second Command for keycloak db:- docker run -d --name keycloakdb -e POSTGRES_PASSWORD=root123 -p 5432:5432 -e
   POSTGRES_DB=keyclaokdb -d postgres

Db Config:- docker run -d --name userservicedb -e POSTGRES_PASSWORD=root123 -p 5433:5432 -e POSTGRES_DB=userservicedb -d
postgres

2)Config server:-
port:8071
Require Config server -add dependency
write all configuration in this file

3)Eureka-sever:-
port:-8073
this is service registry ,register all microservice in this code

4)RabbitMq:- this rabbit mq is use for spring bus refresh a configuration from github port:-15672

command:- docker run -d -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management

5)Address Management sevice:-
Db Config:-
command docker run -d --name addressservicedb -e POSTGRES_PASSWORD=root123 -p 5434:5432 -e POSTGRES_DB=addressservicedb
-d postgres

create image :
mvn clean: Cleans the project directory.
mvn package: Builds the project and creates a package (JAR or WAR).
mvn install: Builds the project and installs it into your local Maven repository.
mvn test: Runs the project's tests.

add dependency

	<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-bindings</artifactId>
			<version>2.0.2</version>
		</dependency>

google jib

<plugin>
				<groupId>com.google.cloud.tools</groupId>
				<artifactId>jib-maven-plugin</artifactId>
				<version>2.8.0</version>
				<configuration>
					<from>
						<image>openjdk:${java.version}-slim</image>
					</from>
					<to>
						<image>${DockerHubUsername}/eureka-server</image>
						<tags>latest</tags>
					</to>
				</configuration>
			</plugin>


command-generate image-:-mvn package jib:build
run image:-docker run -p 8080:8080 my-demo-app:latest

apt-get -y update; apt-get -y install curl  for curl command


helm upgrade --install kubernetes-dashboard kubernetes-dashboard/kubernetes-dashboard --create-namespace --namespace kubernetes-dashboard
kubectl apply -f dashboard-rolebinding.yaml

kubectl -n kubernetes-dashboard create token admin-user


kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath={".data.token"} | base64 -d
kubectl get deployments
kubectl get services
kubectl get replicaset
