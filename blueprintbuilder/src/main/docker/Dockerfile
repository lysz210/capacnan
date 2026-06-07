FROM python:3.12-alpine AS builder

# Install ONLY the jinja compiler utility
RUN pip install --no-cache-dir jinja2-cli

WORKDIR /build
COPY blueprintbuilder/build/generated/blueprint.json blueprint.json
COPY blueprintbuilder/src/main/docker/init.sh.j2 ./init.sh.j2

# Render the script
RUN jinja2 init.sh.j2 blueprint.json --trim-blocks --lstrip-blocks > init.sh


# ========================================================
# STAGE 2: The Production Layer
# ========================================================
FROM natsio/nats-box:latest

# Pluck the compiled script straight out of the python builder stage
COPY --from=builder /build/init.sh /usr/local/bin/init.sh

USER root
RUN chmod +x /usr/local/bin/init.sh

USER nats
ENTRYPOINT ["/usr/local/bin/init.sh"]