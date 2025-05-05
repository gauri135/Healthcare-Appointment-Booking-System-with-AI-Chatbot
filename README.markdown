# Healthcare Appointment Booking System with AI Chatbot

## Description
This project is a backend-only healthcare application that enables patients to register, book appointments with doctors, manage their appointments, and interact with an AI chatbot through API endpoints. The chatbot, powered by the tinyllama model via Ollama, assists with appointment scheduling, symptom assessment, and answering FAQs. Built with Spring Boot, the application is secured with JWT authentication and uses AES-256 encryption for HIPAA-inspired data protection. It is deployed on Heroku with a PostgreSQL database, and users can interact with it via RESTful API endpoints (e.g., using Postman or cURL). The project aims to streamline healthcare appointment management while providing AI-driven patient support.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Installation
Step-by-step instructions to get the project up and running locally.

```bash
# Clone the repository
git clone https://github.com/gauri135/healthcare-appointment-booking.git
cd healthcare-appointment-booking

# Set up PostgreSQL
# Install PostgreSQL locally (https://www.postgresql.org/download/)
# Create a database named healthcare_app
psql -U postgres -c "CREATE DATABASE healthcare_app;"

# Update src/main/resources/application.properties with your database credentials
# Example:
# spring.datasource.url=jdbc:postgresql://localhost:5432/healthcare_app
# spring.datasource.username=postgres
# spring.datasource.password=your_password
# spring.jpa.hibernate.ddl-auto=update
# spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Set up Ollama for the AI chatbot
# Follow the Ollama setup for Windows (experimental binary, no Docker/WSL, as per setup on May 2, 2025)
# Run the tinyllama model
ollama run tinyllama
# Ensure the Ollama server is accessible at http://localhost:11434
curl http://localhost:11434/api/generate -d '{"model": "tinyllama", "prompt": "Hello"}'

# Set the encryption key for AES-256 encryption
export ENCRYPTION_KEY="Xn2k9pL5qW8tY3vR7mN4bJ0hG2dF6cI="
# On Windows (if using Command Prompt):
# set ENCRYPTION_KEY=Xn2k9pL5qW8tY3vR7mN4bJ0hG2dF6cI=

# Run the backend using Maven
mvn spring-boot:run
```

### Prerequisites
- Java 17
- PostgreSQL (local or Heroku PostgreSQL)
- Ollama (with LLaMA 3.2 model)
- Maven
- Git
- Postman (for testing API endpoints)

## Usage
Instructions on how to use the project via API endpoints, with examples.

The application runs on `http://localhost:8080` by default. Use Postman or cURL to interact with the API endpoints. API documentation is available via Swagger UI at `http://localhost:8080/swagger-ui.html`.

### Example API Usage
1. **Register a User**:
   ```bash
   curl -X POST http://localhost:8080/api/auth/register \
   -H "Content-Type: application/json" \
   -d '{"username":"patient1","password":"password","email":"patient1@example.com","role":"PATIENT"}'
   ```
   - Response: Returns the registered user with an encrypted email.

2. **Log In**:
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
   -H "Content-Type: application/json" \
   -d '{"username":"patient1","password":"password"}'
   ```
   - Response: Returns a JWT token (e.g., `eyJhbGciOiJIUzUxMiJ9...`). Save this token for authenticated requests.

3. **Book an Appointment**:
   ```bash
   curl -X POST http://localhost:8080/api/appointments \
   -H "Content-Type: application/json" \
   -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..." \
   -d '{"patient":{"id":1},"doctor":{"id":1},"appointmentTime":"2025-05-06T10:00:00","status":"SCHEDULED"}'
   ```
   - Response: Returns the booked appointment details.

4. **Interact with the AI Chatbot**:
   ```bash
   curl -X POST http://localhost:8080/api/chat \
   -H "Content-Type: application/json" \
   -d '{"message":"I have a headache"}'
   ```
   - Response: Returns the chatbot’s response (e.g., `{"response":"Rest and hydrate, see a doctor if it persists."}`).

5. **View User Profile**:
   ```bash
   curl -X GET http://localhost:8080/api/users/1 \
   -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
   ```
   - Response: Returns the user’s profile with a decrypted email.

### Deployment on Heroku
1. Create a Heroku app:
   ```bash
   heroku create healthcare-booking-app
   ```
2. Add Heroku PostgreSQL:
   ```bash
   heroku addons:create heroku-postgresql:hobby-dev -a healthcare-booking-app
   ```
3. Set the encryption key:
   ```bash
   heroku config:set ENCRYPTION_KEY="Xn2k9pL5qW8tY3vR7mN4bJ0hG2dF6cI=" -a healthcare-booking-app
   ```
4. Deploy:
   ```bash
   git add .
   git commit -m "Deploy backend to Heroku"
   heroku git:remote -a healthcare-booking-app
   git push heroku main
   ```
5. Access the deployed backend at `https://healthcare-booking-app.herokuapp.com`.

## Contributing
Guidelines for contributing to the project:

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Add some feature'
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a Pull Request.

Please ensure your code follows the project’s coding standards, includes appropriate tests, and passes all existing tests (`mvn test`).

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
