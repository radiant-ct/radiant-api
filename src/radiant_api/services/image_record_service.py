from radiant_api.repositories.image_record_repository import ImageRecordRepository
from radiant_api.schemas.image_record_schemas import ImageRecordCreate
from radiant_api.models.image_record import ImageRecord

class ImageRecordService:
    def __init__(self, repo: ImageRecordRepository):
        self.repo = repo

    def create_image(self, image_in: ImageRecordCreate) -> ImageRecord:
        return self.repo.create(image_in)
