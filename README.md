## Relationship Diagram
```mermaid
erDiagram
    seq ||--o{ questions : "has"
    seq ||--o{ responses : "receives"
    questions ||--o{ question_options : "has"
    questions ||--o{ question_settings : "has"
    responses ||--o{ answer_values : "contains"
    question_options ||--o{ answer_values : "selected in"
    questions ||--o{ answer_values : "answered in"

    seq {
        int id PK
        string title
        string description
        string status
        datetime created_at
    }

    questions {
        int id PK
        int seq_id FK
        string title
        string type
        int page_number
        datetime created_at
    }

    responses {
        int id PK
        int seq_id FK
        string ip_address
        string user_agent
        datetime created_at
    }

    question_options {
        int id PK
        int question_id FK
        string option_text
        string option_value
        int order
        datetime created_at
    }

    question_settings {
        int id PK
        int question_id FK
        string setting_key
        string setting_value
        datetime created_at
    }

    answer_values {
        int id PK
        int response_id FK
        int question_id FK
        int option_id FK
        string text_value
        float numeric_value
    }
```

## STEP

```bash
mvn clean install
```
```bash
mvn spring-boot:run
```

#### When your follow top step Open http://localhost:3100/api/swagger-ui/index.html with your browser to see Doc-apis with swagger and http://localhost:3100/api/h2-console/ for h2database.

## docker-compose

```bash
docker compose up -d --build
```