

---

## Trade Enrichment Service

### How to Run the Service

1. **Clone the Repository:**
   ```sh
   git clone https://github.com/your-repo/trade-enrichment-task.git
   cd trade-enrichment-task
   ```

2. **Build the Project:**
   ```sh
   mvn clean install
   ```

3. **Run the Application:**
   ```sh
   mvn spring-boot:run
   ```

### How to Use the API

1. **Endpoint:** `/api/v1/enrich`

2. **Method:** `POST`

3. **Request:**
    - **Content-Type:** `multipart/form-data`
    - **Form Data:** `file` - The CSV file to be enriched

4. **Example cURL Request:**
   ```sh
   curl -X POST "http://localhost:8080/api/v1/enrich" \
        -H "Content-Type: multipart/form-data" \
        -F "file=@src/test/resources/trade.csv" \
        -o enriched.csv
   ```

### Limitations of the Code

- **Scalability:** The current implementation may struggle with extremely large files due to memory constraints.
- **Error Handling:** Limited error handling; invalid rows are logged but not reported back to the user.

### Discussion/Comment on the Design

The service is designed to read and enrich trade data by mapping product IDs to product names from a static file. The design leverages Spring Boot for RESTful services and OpenCSV for CSV parsing. While it serves the basic requirement efficiently, it could benefit from more robust error handling and reporting mechanisms.

### Ideas for Improvement

- **Scalability Enhancements:** Implement streaming processing to handle large files efficiently.
- **Improved Error Handling:** Provide detailed error responses for invalid inputs.
- **Concurrency:** Optimize for concurrent uploads using asynchronous processing or message queues.
- **Configuration Management:** Externalize configurations for better flexibility and maintainability.
- More unit and integration tests
---