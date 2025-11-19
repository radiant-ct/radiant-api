from sqlalchemy import Column, String, Boolean, ForeignKey, Integer
from sqlalchemy.dialects.postgresql import UUID
from sqlalchemy.orm import relationship
from radiant_api.core.database import Base
import uuid

class ImageRecord(Base):
    __tablename__ = "images"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    has_segmentation = Column(Boolean, nullable=False, default=False)
    patient_age = Column(Integer, nullable=True)
    patient_sex = Column(String, nullable=True)
    cancer_type = Column(String, nullable=True)
    dataset_id = Column(
        UUID(as_uuid=True),
        ForeignKey("datasets.id", ondelete="CASCADE"),
        nullable=False
    )

    dataset = relationship("DatasetRecord", passive_deletes=True)
