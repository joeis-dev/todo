#!/bin/bash

# Todo Application Orchestrator

COMMAND=$1
ENV=${2:-dev}

if [ "$ENV" == "prod" ]; then
    COMPOSE_FILE="docker-compose.prod.yml"
else
    COMPOSE_FILE="docker-compose.yml"
fi

case $COMMAND in
    "up")
        docker compose -f $COMPOSE_FILE up -d --build
        ;;
    "down")
        docker compose -f $COMPOSE_FILE down
        ;;
    "logs")
        docker compose -f $COMPOSE_FILE logs -f
        ;;
    "restart")
        docker compose -f $COMPOSE_FILE restart
        ;;
    "ps")
        docker compose -f $COMPOSE_FILE ps
        ;;
    "rebuild")
        #docker system prune -f
        docker compose -d --build --remove-orphans --no-cache        
        ;;
    *)
        echo "Usage: ./manage.sh {up|down|logs|restart|ps|rebuild} [dev|prod]"
        exit 1
        ;;
esac
