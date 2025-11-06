from pydantic import BaseModel
from radiant_api.models.image_record import ImageRecord

class ImageRecordCreate(BaseModel):
    name: str

class ImageRecordSchema(BaseModel):
    id: int
    name: str
    hasSegmentation: bool

    def __init__(self, image: ImageRecord):
        self.id = image.id
        self.name = image.name
        self.hasSegmentation = image.hasSegmentation
