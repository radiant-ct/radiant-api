from sqlalchemy import Column, String, Boolean, ForeignKey, Integer, Float, Text
from sqlalchemy.dialects.postgresql import UUID
from radiant_api.core.database import Base
import uuid

class DatasetRecord(Base):
    __tablename__ = "datasets"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    name = Column(String, nullable=False)
    description = Column(Text, nullable=True)
    credits = Column(String, nullable=True)
