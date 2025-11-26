from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    DATABASE_URL: str = "sqlite:///./radiant.db"
    TMP_VOLUME_PATH: str = "/app/tmp"

    class Config:
        env_file = ".env"


settings = Settings()
