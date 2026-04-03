# Product Catalog API

REST API for managing a product catalog with multiple producers and dynamic product attributes.

## Requirements

- Java 21+
- Maven 3.9+

## Running
```bash
mvn spring-boot:run
```

App starts at `http://localhost:8080`. No database installation needed — H2 runs in-memory and is set up automatically on startup with sample data.

## Testing the API

Swagger UI is available at `http://localhost:8080/swagger-ui/index.html` — all endpoints are listed there and can be tested directly in the browser.

H2 database console at `http://localhost:8080/h2-console`:
- JDBC URL: `jdbc:h2:mem:productcatalog`
- Username: `sa`
- Password: *(leave empty)*

## Endpoints

| Method | URL                    | Description                                            |
|--------|------------------------|--------------------------------------------------------|
| GET    | `/api/products`        | List all products                                      |
| GET    | `/api/products/{id}`   | Get product by ID                                      |
| GET    | `/api/products/search` | Filter by `producerId`, `minPrice`, `maxPrice`, `name` |
| POST   | `/api/products`        | Create product                                         |
| PUT    | `/api/products/{id}`   | Update product                                         |
| DELETE | `/api/products/{id}`   | Delete product                                         |
| GET    | `/api/producers`       | List all producers                                     |
| GET    | `/api/producers/{id}`  | Get producer by ID                                     |
| POST   | `/api/producers`       | Create producer                                        |

## Design Decisions

H2 in-memory database was chosen over PostgreSQL for zero-infrastructure setup — the app runs with a single command and no external dependencies, making it easy to run and reproduce issues quickly.

Product attributes are stored as JSON in a CLOB column mapped to Map<String, Object> in Java via a JPA AttributeConverter. H2 internally maps TEXT to CLOB, so the entity annotation was aligned to match. This handles the 50-200 dynamic attributes per product without a rigid schema, while keeping queries and code simple.