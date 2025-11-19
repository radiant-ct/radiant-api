from sqlalchemy.orm import Session
from radiant_api.models.image_record import ImageRecord
from radiant_api.schemas.image_record_schemas import ImageRecordCreate

class ImageRecordRepository:
    def __init__(self, db: Session):
        self.db = db

    def get_all(self) -> list[ImageRecord]:
        return self.db.query(ImageRecord).all()

    def get_by_id(self, image_id) -> ImageRecord | None:
        return self.db.query(ImageRecord).filter(ImageRecord.id == image_id).first()

    def get_by_dataset(self, dataset_id) -> list[ImageRecord]:
        return self.db.query(ImageRecord).filter(ImageRecord.dataset_id == dataset_id).all()

    def create(self, image_in: ImageRecordCreate) -> ImageRecord:
        image_record = ImageRecord(**image_in.model_dump())
        self.db.add(image_record)
        self.db.commit()
        self.db.refresh(image_record)
        return image_record

    def delete(self, image_id) -> bool:
        image = self.get_by_id(image_id)
        if not image:
            return False
        self.db.delete(image)
        self.db.commit()
        return True
