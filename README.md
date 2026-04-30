## How to Build & Test

### Prerequisites
- Java 17+
- Gradle 8+
- Python 3 + pytest (`pip install pytest`)

### Run Java unit tests
```bash
./gradlew test
```

### Run Python integration tests
```bash
cd tests
pytest integration_test.py -v
```

### View test report
```
build/reports/tests/test/index.html
```