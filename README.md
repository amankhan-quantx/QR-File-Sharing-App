# 📁 QR File Sharing App

A full-stack file sharing application built with **Spring Boot**, **React**, and **PostgreSQL**. The application allows users to upload files, generate QR codes for quick sharing, search uploaded files, download files, delete individual or all files, and monitor storage usage through an interactive dashboard.

---

## 🚀 Features

- 📤 Upload Files
- 📥 Download Files
- 📱 Generate QR Codes for File Sharing
- 🔍 Search Files by Name
- 🗑 Delete Individual Files
- 🗑 Delete All Files
- 📊 View Storage Statistics
- 🔄 Automatic Dashboard Refresh
- 💻 Responsive Dashboard
- ⚠ Global Exception Handling
- 📦 RESTful API Architecture

---

## 🌟 Key Highlights

- Full-Stack Application
- RESTful API Development
- Layered Spring Boot Architecture
- QR Code Generation using ZXing
- Responsive React Dashboard
- PostgreSQL Integration
- Clean Code Structure
- Service Layer Architecture

---

## 🛠 Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- ZXing (QR Code Generator)

### Frontend

- React
- Vite
- Material UI
- Axios
- React Router DOM

### Tools

- Git
- GitHub
- VS Code
- Postman

---

## 📂 Project Structure

```text
QR-File-Sharing-App
│
├── qr-file-sharing
│   ├── src
│   ├── pom.xml
│   ├── mvnw
│   └── ...
│
├── qr-file-sharing-frontend
│   ├── src
│   ├── package.json
│   ├── public
│   └── ...
│
└── README.md
```

---

## 📸 Screenshots

### Dashboard
<img width="1920" height="1020" alt="Screenshot 2026-07-04 001027" src="https://github.com/user-attachments/assets/3fd6fea2-2b49-4651-bdef-5f75fc9c4e0a" />


### Upload File
<img width="1920" height="1020" alt="Screenshot 2026-07-04 001153" src="https://github.com/user-attachments/assets/af5e5974-6c1e-4cd9-9f83-92b6f2cdd075" />
<img width="1920" height="1020" alt="Screenshot 2026-07-04 001259" src="https://github.com/user-attachments/assets/9622e4d9-e37a-4fdc-9a05-e2630affc7a1" />
<img width="1920" height="1020" alt="Screenshot 2026-07-04 001405" src="https://github.com/user-attachments/assets/bf5c1ec4-170f-429e-a346-9c7f185c8c0b" />




### QR Code
<img width="1920" height="1020" alt="Screenshot 2026-07-04 001450" src="https://github.com/user-attachments/assets/5fa7c1c5-f915-4264-875d-0cdd06b41510" />
<img width="561" height="535" alt="Screenshot 2026-07-04 001944" src="https://github.com/user-attachments/assets/ddc14354-3ff8-4240-8830-1ab4b66a2935" />
<img width="1920" height="1020" alt="Screenshot 2026-07-04 002134" src="https://github.com/user-attachments/assets/3b461644-ba6c-4fe5-8da9-f0d33ebbdf4e" />



### Search Files
<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/68ac970b-b59a-4ae2-ab58-3003b8096ddc" />


---

## ⚙ Installation

### Clone Repository

```bash
git clone https://github.com/amankhan-quantx/QR-File-Sharing-App.git
```

Move into the project folder:

```bash
cd QR-File-Sharing-App
```

---

## 🔧 Backend Setup

Move into the backend project:

```bash
cd qr-file-sharing
```

Configure your PostgreSQL database in:

```text
src/main/resources/application.yaml
```

Run the backend:

```bash
mvn spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

---

## 💻 Frontend Setup

Move into the frontend project:

```bash
cd qr-file-sharing-frontend
```

Install dependencies:

```bash
npm install
```

Run the frontend:

```bash
npm run dev
```

Frontend runs at:

```text
http://localhost:5173
```

---

## 📡 REST API Endpoints

| Method | Endpoint | Description |
|:------:|----------|-------------|
| GET | `/api/files` | Get all uploaded files |
| POST | `/api/files/upload` | Upload a file |
| GET | `/api/files/download/{id}` | Download a file |
| GET | `/api/files/qr/{id}` | View QR Code |
| DELETE | `/api/files/{id}` | Delete a file |
| DELETE | `/api/files` | Delete all files |
| GET | `/api/files/stats` | Retrieve storage statistics |

---

## 📈 Future Improvements

- 🔐 JWT Authentication & Authorization
- 📄 Server-side Pagination
- 🖱 Drag & Drop File Upload
- ☁ Cloud Storage Integration (AWS S3 / Azure Blob)
- 🌙 Dark Mode
- 📅 File Expiration Notifications

---

## 👨‍💻 Author

**Aman Khan**

- B.Tech in Computer Science & Engineering (ITER)
- BS in Data Science and Applications (IIT Madras)

### Areas of Interest

- Backend Development
- Java & Spring Boot
- Python
- Machine Learning
- Quantitative Finance
- Distributed Systems

---

## ⭐ Support

If you found this project useful, consider giving it a **⭐ Star** on GitHub.

It helps others discover the project and motivates further improvements.
