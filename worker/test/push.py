import redis
import json
import os

host = os.getenv("REDIS_HOST", "localhost")
port = int(os.getenv("REDIS_PORT", 6379))

r = redis.Redis(host=host, port=port, decode_responses=True)

job = {
    "task": "register_dataset",
    "dataset_id": "4564564564564"
}

r.lpush("queue", json.dumps(job))

print("Job pushed")