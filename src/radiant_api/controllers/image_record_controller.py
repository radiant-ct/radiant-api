from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from radiant_api.core.database import get_db
from radiant_api.repositories.image_record_repository import ImageRecordRepository
from radiant_api.services.image_record_service import ImageRecordService
from radiant_api.schemas.image_record_schemas import ImageRecordCreate, ImageRecordSchema
from radiant_api.models.image_record import ImageRecord

router = APIRouter(prefix="/images", tags=["ImageRecords"])

@router.get("/", response_model=list[ImageRecordSchema])
def list_images(db: Session = Depends(get_db)) -> list[ImageRecordSchema]:
    service: ImageRecordService = ImageRecordService(ImageRecordRepository(db))
    images:list[ImageRecord] = service.list_images()
    images_schema: list[ImageRecordSchema] = list(map(ImageRecordSchema, images))
    return images_schema

@router.post("/", response_model=ImageRecordSchema)
def create_image(image_in: ImageRecordCreate, db: Session = Depends(get_db)) -> ImageRecordSchema:
    service: ImageRecordService = ImageRecordService(ImageRecordRepository(db))
    created_image: ImageRecord = service.create_image(image_in)
    image_schema:ImageRecordSchema = ImageRecordSchema(create_image)
    return image_schema
