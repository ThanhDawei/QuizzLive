# Quizzlive - Realtime Network Quiz Application

Một phần mềm ứng dụng mạng thời gian thực (Realtime Network Application) được xây dựng trên ngôn ngữ **Java** với kiến trúc **Maven Multi-Module**, hỗ trợ giao tiếp Socket TCP/UDP, mã hóa SSL/TLS và sẵn sàng triển khai Container Docker lên Internet.

---

## 🏗️ Cấu trúc dự án (Project Architecture)

Dự án được chia làm 3 module chính:

```text
Quizzlive/
├── pom.xml                                    # Parent POM
├── docs/                                      # Tài liệu giao thức mạng (Protocol Spec)
├── quizzlive-shared/                          # Module dùng chung (OpCodes, Packets, DTOs)
├── quizzlive-server/                          # Module Server (SocketServer, ThreadPool, SSL, Docker)
└── quizzlive-client/                          # Module Client (SocketClient, Event Listeners)
```

---

## 🚀 Hướng dẫn khởi chạy (Getting Started)

### 1. Biên dịch dự án bằng Maven
```bash
mvn clean package
```

### 2. Khởi chạy Server
```bash
java -jar quizzlive-server/target/quizzlive-server-1.0.0-SNAPSHOT.jar
```

### 3. Khởi chạy Client
```bash
java -jar quizzlive-client/target/quizzlive-client-1.0.0-SNAPSHOT.jar
```

---

## 🐳 Triển khai nhanh với Docker (Production Deployment)

Sử dụng Docker Compose để khởi tạo ứng dụng Server trên VPS / Cloud:

```bash
docker-compose up -d --build
```
