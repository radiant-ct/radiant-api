from fastapi import Depends
from radiant_api.repositories.dataset_record_repository import DatasetRepository
from radiant_api.schemas.dataset_record_schemas import DatasetRecordCreate

class DatasetService:
    def __init__(self, repo: DatasetRepository = Depends()):
        self.repo = repo

    def create_dataset(self, data: DatasetRecordCreate):
        return self.repo.create(data)

    def list_datasets(self):
        return self.repo.get_all()
