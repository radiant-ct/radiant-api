from fastapi import FastAPI

app = FastAPI(title="FastAPI + PostgreSQL")

@app.get("/")
def read_root():
    return {"message": "Hello from FastAPI"}
