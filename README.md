# Rate Your Project

[RateYourProject.org](https://rateyourproject.org) (RYP) is a self assessment tool that works by guiding the user through a series of common development practices 
and allows the user to choose a response that most closely matches their current practice level. By assessing their own practices, the tool enables software
teams to more easily plan how to improve the way they develop software.

The original RYP application was built on Vaadin and Spring Boot. It is currently being transitioned to a microservices architecture based on the [Coney 
Conversational Survey](https://coney.cefriel.com) and utilizing the [Survey Ontology](https://cefriel.github.io/survey-ontology/docs/index.html).

## Structure

RYP is currently structured into seven services as follows:

- ryp-legacy: this is the legacy Vaadin Spring Boot application that will be replaced some time in the future
- ryp-api: a Spring Boot application that provides the main microservices API
- ryp-chat: an Angular application that provides the conversational UI
- ryp-create: an Angular application that provides a web UI to build surveys using [Rete.js](https://rete.js.org)
- ryp-inspect: an Angular application that visualizes the survey results
- ryp-reverse: a reverse proxy to route URL paths to the different applications
- ryp-neo4j: A [neo4j](https://neo4j.com) graph database used to store surveys and responses

## Deployment

RYP is deployed on AWS ECS using Docker compose. A good reference for this can be found [here](https://docs.docker.com/cloud/ecs-integration/). 
The main deployment script is `docker-compose.yml` which contains the definitions of the services. Currently, 
images are pulled from docker.io using the tag `jarrah/ryp:<service>`. There are two EFS volumes that have been created, `ryp-data` which is used to persist 
the Rete definitions of surveys, and  `ryp-db` which is used to persist the neo4j database.

Deployment requires a recent enough version of Docker that supports the `docker compose` command (not `docker-compose`.)

In order to deploy manually (a GitHub deployment action will be added at some point), you must first create an AWS context using the command:

```
docker context create ecs <context_name>
```

where `<context_name>` is any name you want to give the context. You will need to provide an [AWS access key ID and a secret access key](https://docs.aws.amazon.com/general/latest/gr/aws-sec-cred-types.html#access-keys-and-secret-access-keys)
that you set up through the AWS console.

The services are then deployed (or re-deployed) using the following command:

```
docker --context=<context_name> compose up
```

Note that for re-deployment this is *usually* smart enough to realize that only some of the services have changed and only update those ones. Sometimes you need
to take all the services down and then back up again for everything to reset correctly.

Since Docker compose is not currently smart enough to handle loadbalancer configuration, this needs to be manually configured using a CloudFormation overlay 
to handle HTTP->HTTPS redirection which has been added to the end of the `docker-compose.yml` file. More details can be found 
[here](https://techsparx.com/software-development/docker/docker-ecs/load-balancer/https.html). Not that this also requires an application loadbalancer to 
be created manually also and a reference added to `docker-compose.yml`.

## EFS Volumes

Docker compose with AWS does not allow the use of bind mounts, so everything needs to be set up using EFS volumes. I was unable to get existing, manually
created, volumes to mount however (see https://github.com/docker/compose-cli/issues/1739), so had to allow `compose` to create the initial volumes. 
There are a variety of ways to mount the
volumes to initialize them, but I found the easiest was to spin up an EC2 instance with the volumes mounted, then scp the files over.

## LoadBalancer

An Application LoadBalancer is required to redirect HTTP traffic to HTTPS, however `compose` requires a Network LoadBalancer if you try to expose any ports
other than 80 or 443, for example the neo4j ports. However I haven't been able to work out how to use a Network LoadBalancer to do the HTTP redirection, so if
anyone knows please let me know!

## Reverse Proxy

We provide a reverse proxy to perform path routing using [NGINX](https://www.nginx.com). It's probably possible to do this with a LoadBalancer, but I think it's 
better to have a more generic solution. Currently the routing is as follows:

- rateyourproject.org -> ryp-legacy
- rateyourproject.org/ryp -> static landing page
- rateyourproject.org/ryp/chat -> ryp-chat
- rateyourproject.org/ryp/inspect -> ryp-inspect
- rateyourproject.org/ryp/create -> ryp-create

## Domain Name

The rateyourproject.org domain name is registered with [domain.com](https://domain.com) and is reserved for this project. For more information please contact the
author. Nameservers for the domain name are provided using AWS Route 53.
