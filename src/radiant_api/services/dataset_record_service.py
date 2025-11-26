from fastapi import Depends, UploadFile
from radiant_api.repositories.dataset_record_repository import DatasetRepository
from radiant_api.schemas.dataset_record_schemas import DatasetRecordCreate
from pathlib import Path
import shutil
from radiant_api.core.config import settings

class DatasetService:
    def __init__(self, repo: DatasetRepository = Depends()):
        self.dataset_repository = repo

    def create_dataset(self, data: DatasetRecordCreate, file: UploadFile):

        dataset = self.dataset_repository.create(data)

        save_dir = Path(settings.TMP_VOLUME_PATH)
        save_dir.mkdir(parents=True, exist_ok=True)

        filename = f"{dataset.id}.tar.gz"
        file_path = save_dir / filename

        with file_path.open("wb") as buffer:
            shutil.copyfileobj(file.file, buffer)

        return dataset

    def list_datasets(self):
        return self.dataset_repository.get_all()
