FROM ubuntu:18.04

COPY . /app

WORKDIR /app

RUN apt-get update && apt-get upgrade -y && apt-get install python3-pip -y && apt-get install python3-distutils -y && python3 -m pip install -r requirements.txt

EXPOSE 8080

CMD ["python3", "-m", "uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8080"]
