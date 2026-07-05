# BILL-NOW

A full-stack billing and point-of-sale (POS) application for managing categories, items, orders, and payments. Built with Spring Boot and React.

**Repository:** [github.com/vallabhvy/BILL-NOW](https://github.com/vallabhvy/BILL-NOW)

---

## Features

- **Dashboard** — today's sales, order count, and recent orders
- **Explore / POS** — browse items, add to cart, and checkout
- **Order history** — view and manage past orders
- **Category & item management** — admin CRUD with image uploads
- **User management** — admin-only user registration and deletion
- **Payments** — cash checkout and UPI via Razorpay
- **Receipt printing** — printable order receipts
- **JWT authentication** — role-based access (`ROLE_ADMIN`, `ROLE_USER`)

---

## Tech Stack

| Layer | Technologies |
|-------|--------------|
| Backend | Java 21, Spring Boot 3.4, Spring Security, JWT, JPA/Hibernate |
| Frontend | React 19, Vite, React Router, Axios, Bootstrap |
| Database | PostgreSQL |
| Payments | Razorpay |
| Storage | Local file uploads (`billingsoftware/uploads/`) |

---

## Project Structure

```
Billing-Software/
├── billingsoftware/     # Spring Boot backend (API)
├── client/              # React frontend
└── billing_app.sql      # Legacy MySQL schema reference
```

---

## Prerequisites

- **Java 21**
- **Maven 3.9+**
- **Node.js 18+** and npm
- **PostgreSQL 14+**
- **Razorpay account** (optional, for UPI payments)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/vallabhvy/BILL-NOW.git
cd BILL-NOW
```

### 2. Set up PostgreSQL

Create a database named `billing_app`:

```sql
CREATE DATABASE billing_app;
```

Tables are created automatically by Hibernate on first run (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Configure the backend

Edit `billingsoftware/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/billing_app
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

jwt.secret.key=your_secure_jwt_secret

razorpay.key.id=your_razorpay_key_id
razorpay.key.secret=your_razorpay_secret
```

Also update the Razorpay key in `client/src/util/constants.js`:

```js
export const AppConstants = {
    RAZORPAY_KEY_ID: "your_razorpay_key_id"
}
```

> **Note:** Do not commit real secrets to version control. Use environment variables or a local config file for production.

### 4. Create the first admin user

Start the backend (step 5), then generate a bcrypt password hash:

```bash
curl -X POST http://localhost:8080/api/v1.0/encode \
  -H "Content-Type: application/json" \
  -d "{\"password\": \"your_password\"}"
```

Insert an admin user into PostgreSQL (replace values as needed):

```sql
INSERT INTO tbl_users (user_id, email, password, role, name, created_at, updated_at)
VALUES (
  'admin-001',
  'admin@example.com',
  '<bcrypt_hash_from_encode_endpoint>',
  'ROLE_ADMIN',
  'Admin',
  NOW(),
  NOW()
);
```

Additional users can be created from the **Manage Users** page once logged in as admin.

### 5. Run the backend

```bash
cd billingsoftware
mvn spring-boot:run
```

API base URL: `http://localhost:8080/api/v1.0`

### 6. Run the frontend

```bash
cd client
npm install
npm run dev
```

Frontend URL: `http://localhost:5173`

---

## API Overview

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/login` | Authenticate and receive JWT | Public |
| POST | `/encode` | Generate bcrypt password hash | Public |
| GET | `/dashboard` | Dashboard stats | User, Admin |
| GET | `/categories` | List categories | User, Admin |
| GET | `/items` | List items | User, Admin |
| POST | `/orders` | Create order | User, Admin |
| GET | `/orders/latest` | Order history | User, Admin |
| POST | `/payments/create-order` | Create Razorpay order | User, Admin |
| POST | `/payments/verify` | Verify UPI payment | User, Admin |
| POST | `/admin/register` | Register user | Admin |
| POST | `/admin/categories` | Add category | Admin |
| POST | `/admin/items` | Add item | Admin |

All protected routes require a Bearer token:

```
Authorization: Bearer <jwt_token>
```

---

## Application Routes

| Route | Page | Role |
|-------|------|------|
| `/login` | Login | Public |
| `/dashboard` | Dashboard | Authenticated |
| `/explore` | POS / billing | Authenticated |
| `/orders` | Order history | Authenticated |
| `/category` | Manage categories | Admin |
| `/items` | Manage items | Admin |
| `/users` | Manage users | Admin |

---

## Build for Production

**Backend:**

```bash
cd billingsoftware
mvn clean package
java -jar target/billingsoftware-0.0.1-SNAPSHOT.jar
```

**Frontend:**

```bash
cd client
npm run build
```

Output is in `client/dist/`.

---

## Author

**Vallabh** — [vallabhvy](https://github.com/vallabhvy)

---

## License

This project is open source. Add a license file if you plan to distribute it publicly.
