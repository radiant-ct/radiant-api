from fastapi import Depends
from sqlalchemy.orm import Session

from radiant_api.core.database import get_db
from radiant_api.models.dataset_record import DatasetRecord
from radiant_api.repositories.base_repository import BaseRepository

class DatasetRepository(BaseRepository[DatasetRecord]):
    def __init__(self, db: Session = Depends(get_db)):
        super().__init__(DatasetRecord, db)
