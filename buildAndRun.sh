#Rebuild a project
./gradlew clean build

#Build a docker image
docker build --tag=product-discount:latest .

#Run docker image
docker run -p8887:8080 product-discount:latest