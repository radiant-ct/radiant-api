from sqlalchemy import Column, Integer, String, Boolean
from radiant_api.core.database import Base

class ImageRecord(Base):
    __tablename__ = "images"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, nullable=False)
    hasSegmentation = Column(Boolean, nullable=False, default=False)
