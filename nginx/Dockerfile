# Base Image
FROM nginx:alpine

# MAINTAINER of the Dockerfile
MAINTAINER Ajay <trivajay259@gmail.com>

COPY nginx.conf /etc/nginx/nginx.conf

#Expose Nginx Port
EXPOSE 80

#Start NginxService
CMD ["nginx", "-g", "daemon off;"]