# ATM Simulator - Complete Backend + Database

## Architecture Overview

This ATM Simulator consists of three layers:

1. **Backend (Java)** - REST API Server with business logic
2. **Database (Java)** - Persistent data storage with user accounts
3. **Frontend (Web)** - Modern UI with HTML, CSS, and JavaScript

## Files

### Backend Files
- **ATMServer.java** - HTTP Server with REST API endpoints (runs on port 8080)
- **ATMDatabase.java** - Database management with persistent storage

### Frontend Files
- **nervous/ATM.html** - Login & ATM interface
- **nervous/ATM.css** - Modern cyberpunk UI styling
- **nervous/ATM.js** - Frontend logic that communicates with backend

### Data Storage
- **atm_database.dat** - Persistent binary file storing user data (auto-created)

## How to Run

### Step 1: Compile Java Files
```bash
cd c:\Users\user\Desktop\projects
javac ATMDatabase.java
javac ATMServer.java
```

### Step 2: Start the Backend Server
```bash
java ATMServer
```

You should see:
```
================================
ATM Server Started
================================
Server running on http://localhost:8080

Default Test Users:
  User ID: user1, PIN: 1234
  User ID: user2, PIN: 5678
  User ID: user3, PIN: 9999

Press Ctrl+C to stop the server
```

### Step 3: Open the Frontend
Open the file in your browser:
```
c:\Users\user\Desktop\projects\nervous\ATM.html
```

## Test the Application

1. **Login** with one of the test credentials:
   - User ID: `user1`, PIN: `1234` (Balance: UgShs 1,000,000)
   - User ID: `user2`, PIN: `5678` (Balance: UgShs 500,000)
   - User ID: `user3`, PIN: `9999` (Balance: UgShs 750,000)

2. **Check Balance** - View your current account balance

3. **Deposit Money** - Add funds to your account
   - Enter amount manually or use quick buttons (50K, 100K, 500K)

4. **Withdraw Money** - Withdraw funds from your account
   - System prevents withdrawal if insufficient funds

5. **Logout** - Securely exit and return to login screen

## Backend API Endpoints

### 1. Authentication
```
POST /api/authenticate
Body: {"userId": "user1", "pin": "1234"}
Response: {"success": true, "sessionId": "session_xxx", "userId": "user1"}
```

### 2. Check Balance
```
GET /api/balance?sessionId=session_xxx
Response: {"success": true, "balance": 1000000, "userId": "user1"}
```

### 3. Deposit
```
POST /api/deposit
Body: {"sessionId": "session_xxx", "amount": "50000"}
Response: {"success": true, "message": "Deposit successful", "newBalance": 1050000}
```

### 4. Withdraw
```
POST /api/withdraw
Body: {"sessionId": "session_xxx", "amount": "50000"}
Response: {"success": true, "message": "Withdrawal successful", "newBalance": 950000}
```

### 5. Logout
```
POST /api/logout
Body: {"sessionId": "session_xxx"}
Response: {"success": true, "message": "Logged out successfully"}
```

## Database Features

- **Thread-safe operations** - Multiple concurrent users supported
- **Persistent storage** - Data saved to `atm_database.dat`
- **Session management** - Secure session-based authentication
- **Balance validation** - Prevents overdrafts
- **Data integrity** - Atomic transactions

## User Flow

```
1. User opens ATM.html
   ↓
2. Displays Login Screen
   ↓
3. User enters credentials → Sends to /api/authenticate
   ↓
4. Backend validates → Creates session
   ↓
5. ATM Main Menu displayed
   ↓
6. User selects transaction → Calls appropriate API endpoint
   ↓
7. Backend updates database
   ↓
8. Frontend shows confirmation
   ↓
9. User can logout → Session destroyed
```

## Key Features

✅ User authentication with PIN
✅ Real-time balance updates from backend
✅ Secure session management
✅ Persistent data storage
✅ Modern responsive UI
✅ Input validation (client & server)
✅ Transaction history tracking
✅ Insufficient funds protection
✅ CORS enabled for frontend communication
✅ RESTful API design

## Notes

- The server stores user data in `atm_database.dat`
- Sessions expire when the server restarts
- Make sure port 8080 is not in use
- Both Java and web components must be running together
