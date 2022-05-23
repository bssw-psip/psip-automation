#!/bin/bash

REPO=jarrah/ryp

case $1 in
    "build")
    case $2 in
        "inspect")
        case $3 in
            "--env-cp")
            echo "Copying new environment file"
            cp ./environment.ts ./inspect/src/environments/environment.docker.prod.ts
            ;;
            *)
            ;;
        esac
        cd inspect
        docker build --no-cache -t $REPO:inspect .
	docker push $REPO:inspect
        cd ..
        ;;
        "chat")
        case $3 in
            "--env-cp")
            echo "Copying new environment file"
            cp ./environment.ts ./chat/src/environments/environment.docker.prod.ts
            ;;
            *)
            ;;
        esac
        cd chat
        docker build --no-cache -t $REPO:chat .
	docker push $REPO:chat
        cd ..
        ;;
        "create")
        case $3 in
            "--env-cp")
            echo "Copying new environment file"
            cp ./environment.ts ./create/src/environments/environment.docker.prod.ts
            ;;
            *)
            ;;
        esac
        cd create
        docker build --no-cache -t $REPO:create .
	docker push $REPO:create
        cd ..
        ;;
        "api")
        cd api
        docker build --no-cache -t $REPO:api .
	docker push $REPO:api
        cd ..
        ;;
        "web")
        cd web
        docker build --no-cache -t $REPO:web .
	docker push $REPO:web
        cd ..
        ;;
        "neo4j")
        cd neo4j
        docker build --no-cache -t $REPO:neo4j .
	docker push $REPO:neo4j
        cd ..
        ;;
        "legacy")
        cd legacy
	rm -rf node_modules # avoid issues with cross-platform builds
        docker build --no-cache -t $REPO:legacy .
	docker push $REPO:legacy
        cd ..
        ;;
	"reverse")
	cd reverse
        docker build --no-cache -t $REPO:reverse .
	docker push $REPO:reverse
        cd ..
	;;
        "--env-cp")
        echo "Copying new environment file"
        cp ./environment.ts ./create/src/environments/environment.docker.prod.ts
        cp ./environment.ts ./chat/src/environments/environment.docker.prod.ts
        cp ./environment.ts ./inspect/src/environments/environment.docker.prod.ts
        docker compose build --no-cache
        ;;
        *)
        docker compose build --no-cache
        ;;
    esac
    ;;
    "up")
    case $2 in
        "inspect")
        docker compose up -d --no-deps inspect
        ;;
        "chat")
        docker compose up -d --no-deps chat
        ;;
        "create")
        docker compose up -d --no-deps create
        ;;
        "api")
        docker compose up -d --no-deps api
        ;;
        *)
        docker compose up -d --force-recreate
        ;;
    esac
    ;;
    "stop")
    case $2 in
        "inspect")
        docker compose stop inspect
        ;;
        "chat")
        docker compose stop chat
        ;;
        "create")
        docker compose stop create
        ;;
        "api")
        docker compose stop api
        ;;
        *)
        docker compose down
        ;;
    esac
    ;;
    "help")
    echo -e "\nUsage: <command> [image] [options]"
    echo -e "\nCommands:  \n\t build \t\t Builds the selected image with the available 'environment.ts' file "
    echo -e "\t up \t\t Starts the selected container (all if no image is specified) "
    echo -e "\t stop \t\t Stops the selected container (all if no image is specified) \n "
    echo -e "Images:  \n\t api \t\t Coney's Application Programming Interface "
    echo -e "\t create \t Conversation editor"
    echo -e "\t chat \t\t User's chat endpoint"
    echo -e "\t inspect \t Realtime data visualization tool \n"
    echo -e "Options:  \n\t --env-cp \t Rewrites the environment.ts file in the Angular service(s)"
    ;;
    *)
    echo "Unknown command"    # unknown option
    ;;
esac
