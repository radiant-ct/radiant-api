from fastapi import APIRouter, Depends, UploadFile, File, Form
from radiant_api.schemas.dataset_record_schemas import (
    DatasetRecordCreate,
    DatasetRecordRead,
)
from radiant_api.services.dataset_record_service import DatasetService
import json

router = APIRouter(prefix="/datasets", tags=["datasets"])

@router.post("/", response_model=DatasetRecordRead)
async def create_dataset(
    data: str = Form(...),
    file: UploadFile = File(...),
    service: DatasetService = Depends(),
):
    parsed = DatasetRecordCreate(**json.loads(data))
    return service.create_dataset(parsed, file)

@router.get("/", response_model=list[DatasetRecordRead])
def list_datasets(
    service: DatasetService = Depends(),
):
    return service.list_datasets()
