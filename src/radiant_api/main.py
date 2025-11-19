from fastapi import FastAPI
from radiant_api.core.database import Base, engine
from radiant_api.controllers import dataset_record_controller, health_controller

app = FastAPI(title="Radiant API")

# Create tables
Base.metadata.create_all(bind=engine)

app.include_router(dataset_record_controller.router)
app.include_router(health_controller.router)
