from radiomics_extration import extract_radiomic_features
import os

import redis
import json

host = os.getenv("REDIS_HOST", "localhost")
port = int(os.getenv("REDIS_PORT", 6379))

r = redis.Redis(host=host, port=port, decode_responses=True)

print("Listening")
while True:
    _, data = r.brpop("queue")
    job = json.loads(data)

    print("Processing:", job)
