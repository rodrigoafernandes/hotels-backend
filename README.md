python3 -m uvicorn main:app --port 8080 --reload

docker container run --rm --net=host -e SONAR_HOST_URL="https://sonar.app.cvc.com.br" -v ${PWD}:/usr/src sonarsource/sonar-scanner-cli
