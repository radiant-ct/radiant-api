from typing import Generic, TypeVar, Type, List, Optional
from sqlalchemy.orm import Session

ModelType = TypeVar("ModelType")

class BaseRepository(Generic[ModelType]):
    def __init__(self, model: Type[ModelType], db: Session):
        self.model = model
        self.db = db

    def get(self, id) -> Optional[ModelType]:
        return self.db.query(self.model).filter(self.model.id == id).first()

    def get_all(self) -> List[ModelType]:
        return self.db.query(self.model).all()

    def create(self, obj_in):
        obj = self.model(**obj_in.dict())
        self.db.add(obj)
        self.db.commit()
        self.db.refresh(obj)
        return obj
