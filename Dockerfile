# builder
FROM vdisk/maven-java21-graalvm:latest AS builder
ARG BUILDER_ROOTFS="/builder-rootfs"
WORKDIR /build
COPY ./pom.xml /build/pom.xml
COPY ./src/main/ /build/src/main/
RUN \
    --mount=type=cache,sharing=locked,target=/root/.m2/repository \
    set -eux; \
    ls -lh /build/; \
    ls -lh /root/.m2/repository/; \
    mvn -Pnative native:compile -DskipTests;
RUN ls -lh /build/target/;
RUN mkdir -v -p "${BUILDER_ROOTFS}/usr/local/bin"
RUN cp "/build/target/http-java" "${BUILDER_ROOTFS}/usr/local/bin/http-java";

# image
FROM vdisk/base-debian12:latest AS image
ARG BUILDER_ROOTFS="/builder-rootfs"
COPY --from=builder "${BUILDER_ROOTFS}" "/"
ENTRYPOINT []
CMD ["/usr/local/bin/http-java"]
