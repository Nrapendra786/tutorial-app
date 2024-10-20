docker-compose down --rmi all
docker-compose down -v
docker system prune --volumes
docker stop $(docker ps -aq)                   # Stop all containers
docker rm $(docker ps -aq)                     # Remove all containers
docker rmi $(docker images -q)                  # Remove all images
docker volume rm $(docker volume ls -q)        # Remove all volumes
docker network rm $(docker network ls -q)      # Remove all custom networks
docker system prune -a --volumes 
docker container prune && docker network prune && docker volume prune  #If you want remove everything but keep images

