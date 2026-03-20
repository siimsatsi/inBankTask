# inBank Task

## Tech stack
- Spring Boot (Java 21) and Vue 3 + TypeScript

## Structure
- "backend/" - Spring Boot REST API
- "frontend/" - Vue 3 application, which will probably be only a single page
- might make a "docs/" module for a UML diagram or two if I have time, but not crucial for now

## Components
- "DecisionEngine" - For core loan decision logic
- "CreditRegistry" - Will map personal codes to credit profiles
- "LoanController" - Just for the single REST endpoint
- "GlobalExceptionHandler" - Add a little bit of error handling
- "Loan Constraint" - Will manage system boundaries like amounts or periods
