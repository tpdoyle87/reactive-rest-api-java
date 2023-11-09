# Makefile
APP_NAME=spring-boot-reactive-app
PORT=8080

.PHONY: build run clean

build:
	@echo "Building the Docker image..."
	docker build -t $(APP_NAME) .

run:
	@echo "Running the Docker container..."
	docker run -p $(PORT):$(PORT) $(APP_NAME)

stop:
	@echo "Stopping the Docker container..."
	@docker stop $(APP_NAME) || true
	@docker rm $(APP_NAME) || true

clean:
	@echo "Cleaning up Docker images and containers..."
	docker system prune -af

all: build run

reset: clean build run

default: all
