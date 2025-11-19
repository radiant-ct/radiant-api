from pydantic import BaseModel
from typing import Optional
from uuid import UUID

class DatasetRecordBase(BaseModel):
    name: str
    description: Optional[str] = None
    credits: Optional[str] = None

class DatasetRecordCreate(DatasetRecordBase):
    pass

class DatasetRecordRead(DatasetRecordBase):
    id: UUID

    class Config:
        orm_mode = True
