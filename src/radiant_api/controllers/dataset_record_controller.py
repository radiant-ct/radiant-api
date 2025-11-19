from fastapi import APIRouter, Depends
from radiant_api.schemas.dataset_record_schemas import (
    DatasetRecordCreate,
    DatasetRecordRead,
)
from radiant_api.services.dataset_record_service import DatasetService

router = APIRouter(prefix="/datasets", tags=["datasets"])

@router.post("/", response_model=DatasetRecordRead)
def create_dataset(
    data: DatasetRecordCreate,
    service: DatasetService = Depends(),
):
    return service.create_dataset(data)


@router.get("/", response_model=list[DatasetRecordRead])
def list_datasets(
    service: DatasetService = Depends(),
):
    return service.list_datasets()
