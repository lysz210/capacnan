FROM busybox:latest

# Create the web root directory
WORKDIR /www

# Copy your Git-versioned configuration files into the web root
COPY ./blueprint/src/main/resources/ .

# Expose port 80 internally within the Swarm network
EXPOSE 80

# Run the built-in HTTP daemon:
# -f: run in the foreground (so the container doesn't exit)
# -p 80: listen on port 80
# -h /www: serve files out of the /www directory
CMD ["busybox", "httpd", "-f", "-p", "8000", "-h", "/www"]