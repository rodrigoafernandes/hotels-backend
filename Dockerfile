FROM tiangolo/uvicorn-gunicorn-fastapi:python3.8

ENV PORT="8080"
ENV WORKERS_PER_CORE="20"

EXPOSE 8080

COPY . /app

RUN python3 -m pip install -r /app/requirements.txt