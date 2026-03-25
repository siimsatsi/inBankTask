# inBank Task

## Tech stack
- Spring Boot (Java 21) and Vue 3 + TypeScript

## Structure
- `backend/` - Spring Boot REST API
- `frontend/` - Vue 3 single-page application

## Components
- `DecisionEngine` - core loan decision logic
- `CreditRegistry` - maps personal codes to credit profiles
- `LoanController` - single REST endpoint
- `GlobalExceptionHandler` - error handling
- `LoanConstraints` - system boundaries (amounts, periods)

### Backend
Requires Java 21.

cd backend
./gradlew clean build
find InBankTaskApplication in inBankTask/backend/src/main/java/InBankTaskApplication
run InBankTaskApplication

or

cd backend
./gradlew bootRun
Runs on http://localhost:8080

### Frontend
Requires Node.js.

cd frontend
npm install
npm run dev

Runs on http://localhost:5173
