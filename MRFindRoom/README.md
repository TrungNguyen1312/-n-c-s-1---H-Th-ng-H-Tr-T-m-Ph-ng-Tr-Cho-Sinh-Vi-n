# 🏠 MR-FindRoom Desktop

Ứng dụng desktop tìm phòng trọ sinh viên Đà Nẵng — chuyển từ web app (HTML/CSS/JS) sang **Java 21 + JavaFX**.

---

## 📁 Cấu trúc project

```
MRFindRoom/
├── pom.xml                          ← Maven build config
└── src/main/
    ├── java/com/mrfindroom/
    │   ├── MainApp.java             ← Entry point, navigation
    │   ├── model/
    │   │   ├── Room.java            ← Model phòng trọ
    │   │   └── SmartFilter.java     ← Kết quả filter từ AI
    │   ├── data/
    │   │   └── RoomData.java        ← 9 phòng trọ mẫu
    │   ├── service/
    │   │   ├── RoomFilterService.java ← Logic lọc, tìm kiếm, fuzzy search
    │   │   └── GeminiService.java   ← Gọi Gemini AI API
    │   ├── ui/
    │   │   ├── HomeView.java        ← Trang chủ (home.html)
    │   │   ├── MainView.java        ← Màn hình tìm phòng (timphong.html)
    │   │   ├── RoomCard.java        ← Card hiển thị 1 phòng
    │   │   └── SmartChatPanel.java  ← Chat AI sidebar
    │   └── util/
    │       └── TextNormalizer.java  ← Chuẩn hoá tiếng Việt
    └── resources/com/mrfindroom/css/
        └── dark-theme.css           ← Dark theme toàn bộ app
```

---

## ⚙️ Yêu cầu hệ thống

| Thứ | Phiên bản |
|-----|-----------|
| Java JDK | **21** trở lên |
| Maven | **3.8+** |
| Hệ điều hành | Windows / macOS / Linux |

---

## 🚀 Cách build & chạy

### 1. Cài Java 21

- **Windows/macOS**: Tải từ https://adoptium.net hoặc https://www.oracle.com/java/technologies/downloads/
- **Ubuntu/Debian**: `sudo apt install openjdk-21-jdk`

### 2. Cài Maven

- **Windows**: Tải từ https://maven.apache.org/download.cgi → giải nén → thêm vào PATH
- **macOS**: `brew install maven`
- **Ubuntu**: `sudo apt install maven`

### 3. Build project

```bash
cd MRFindRoom
mvn clean package -q
```

### 4. Chạy ứng dụng

```bash
mvn javafx:run
```

**Hoặc** chạy file JAR (sau khi build):

```bash
java --module-path <javafx-sdk>/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.web \
     -jar target/MRFindRoom-1.0.0.jar
```

> **Lưu ý**: JavaFX SDK có thể cần tải riêng từ https://gluonhq.com/products/javafx/ nếu dùng JDK không đi kèm.

---

## 🔑 Cấu hình Gemini AI

File `GeminiService.java` đã có sẵn API key demo. Để dùng key của bạn:

1. Truy cập https://aistudio.google.com → lấy API key miễn phí
2. Mở `src/main/java/com/mrfindroom/service/GeminiService.java`
3. Thay dòng:
   ```java
   private static final String GEMINI_API_KEY = "YOUR_KEY_HERE";
   ```

---

## ✨ Tính năng

| Tính năng | Web | Desktop |
|-----------|-----|---------|
| Tìm kiếm fuzzy | ✅ | ✅ |
| Lọc giá theo khoảng | ✅ | ✅ |
| Lọc theo tiện nghi | ✅ | ✅ |
| Gợi ý AI (Gemini) | ✅ | ✅ |
| Parser thủ công (offline) | ✅ | ✅ |
| Card phòng với ảnh | ✅ | ✅ |
| Gọi điện / copy SĐT | ✅ | ✅ |
| Trang chủ hero | ✅ | ✅ |
| Dark theme | ✅ | ✅ |
| Bottom navigation | ✅ | ✅ |

---

## 🛠 Mở rộng thêm

- **Thêm phòng**: Sửa file `RoomData.java`
- **Đổi màu theme**: Sửa `dark-theme.css`
- **Thêm màn hình**: Tạo View mới, đăng ký trong `MainApp.java`
- **Database thật**: Thay `RoomData.java` bằng JDBC + SQLite/MySQL

---

© 2025 MR-FindRoom — Nền tảng tìm phòng trọ dành cho sinh viên Đà Nẵng
