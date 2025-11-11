from fastapi import APIRouter

router = APIRouter(prefix="/health", tags=["HealthCheck"])

@router.get("/hello")
def hello():
    return {"message": "The app is up!"}
