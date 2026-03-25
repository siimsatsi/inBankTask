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

OR

cd backend
./gradlew bootRun
Runs on http://localhost:8080

### Frontend
Requires Node.js.

cd frontend
npm install
npm run dev

Runs on http://localhost:5173

## Decision Algorithm
The engine evaluates loan applications using a credit score formula

- credit score = (creditModifier / loanAmount) * loanPeriod
 

A score of ≥ 1.0 is required for approval. The engine:
1. Rejects immediately if the applicant has existing debt
2. Searches for the maximum approvable amount at the requested period
3. If no valid amount exists, extends the period up to 60 months
4. Returns negative if no valid combination is found

## Design Decisions

The main goal for me during this task was to separate different bits of logic as much as possible to ensure the code is maintainable.

**CreditRegistry as an interface**
`HardcodedCreditRegistry` implements `CreditRegistry` rather than being used directly. This means the data source can be swapped for a real database or external API without touching `DecisionEngine` or any other class.

**Records for CreditProfile and LoanDecision**
Both are acting as data containers with no behavior, so records were the obvious choice here. They are immutable and there is no boilerplate.

**Loan search strategy**
When the requested amount is valid, the engine searches upward to find the maximum approvable amount rather than returning just what was requested. When invalid, it searches downward to find the highest amount that works. If no valid amount exists at the requested period, the period is extended up to 60 months. This prioritizes giving the applicant the best possible outcome.

**Reason field on LoanDecision**
A rejection without explanation is not useful. The reason field lets the frontend display why a loan was rejected. Existing debt vs no valid combination without exposing internal scoring details.

**GlobalExceptionHandler as a separate class**
Error handling is kept out of the controller so `LoanController` only deals with HTTP routing. A single `@ControllerAdvice` class handles all exceptions.

**CORS configuration**
CORS is configured to allow requests from `localhost:5173` only. This is intentional for the current task to allow frontend to communicate with the backend. In production this could be restricted to the actual deployment domain.

**Validation with @Valid annotations**
Input validation is handled with `@Min`, `@Max` and `@NotBlank` on the `LoanRequest` record. Invalid input is rejected before it reaches `DecisionEngine`, keeping business logic clean.


## Improvement suggestion
I get the task is supposed to be open-ended, allowing candidates to make their own design decisions. However, adding a few example scenarios with expected outputs would make it easier to evaluate whether an implementation is correct or not, without restricting the overall creativity a candidate is given. This approach would still keep the creative freedom for a candidate while making the evaluation more consistent. 