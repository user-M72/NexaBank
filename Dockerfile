# syntax=docker/dockerfile:1

################################################################################
# Stage 1 — build
FROM eclipse-temurin:21-jdk-jammy as build

WORKDIR /build

# копируем gradle wrapper
COPY --chmod=0755 gradlew gradlew
COPY gradle/ gradle/

# копируем gradle файлы
COPY build.gradle settings.gradle ./

# скачиваем зависимости
RUN ./gradlew dependencies --no-daemon

# копируем исходники
COPY src src

# собираем jar
RUN ./gradlew build -x test --no-daemon

################################################################################
# Stage 2 — runtime
FROM eclipse-temurin:21-jre-jammy

ARG UID=10001

RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser

WORKDIR /app

# копируем jar
COPY --from=build /build/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]