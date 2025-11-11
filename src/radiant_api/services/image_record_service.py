from radiant_api.repositories.image_record_repository import ImageRecordRepository
from radiant_api.schemas.image_record_schemas import ImageRecordCreate
from radiant_api.models.image_record import ImageRecord
class ImageRecordService:
    def __init__(self, repo: ImageRecordRepository):
        self.repo = repo

    def list_images(self) -> list[ImageRecord]:
      return self.repo.get_all()

    def create_image(self, user_in: ImageRecordCreate) -> ImageRecord:
        return self.repo.create(user_in)

    def get_image_by_id(self, id: int):
        return self.repo.get_by_id(id)
