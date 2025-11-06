from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    DATABASE_URL: str = "sqlite:///./radiant.db"
    # For prod: "postgresql+psycopg2://user:password@host:port/dbname"

    class Config:
        env_file = ".env"


settings = Settings()
