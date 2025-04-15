THÔNG TIN SINH VIÊN
------------------
Họ và tên: TRẦN VĂN PHƯƠNG
MSSV: 21120534
Khoa: KỸ THUẬT PHẦN MỀM
Trường: ĐẠI HỌC SƯ PHẠM KỸ THUẬT TP.HCM
Email: 21120534@student.hcmus.edu.vn
SDT: 0399784975

HƯỚNG DẪN BIÊN DỊCH VÀ CHẠY CHƯƠNG TRÌNH
----------------------------------------

1. Yêu cầu hệ thống:
   - Java Development Kit (JDK) 8 trở lên
   - Windows 10/11 hoặc Linux/MacOS

2. Cấu trúc thư mục:
   - Source/: Chứa mã nguồn chương trình
   - Release/: Chứa file thực thi và dữ liệu
   - Libs/: Chứa các thư viện cần thiết

3. Cách biên dịch và chạy chương trình:

   a) Sử dụng Command Line:
      - Mở Command Prompt hoặc PowerShell
      - Di chuyển đến thư mục gốc của project
      - Chạy lệnh: .\build.bat
      - Chương trình sẽ tự động biên dịch và chạy

   b) Sử dụng IDE:
      - Mở project trong IDE (Eclipse, IntelliJ IDEA, VS Code)
      - Tìm file Main.java trong thư mục Source
      - Click chuột phải và chọn "Run Main.main()"

   c) Dùng jar
      click DictionaryApp.jar để chạy

4. Các file dữ liệu:
   - anh_viet.xml: Từ điển Anh-Việt
   - viet_anh.xml: Từ điển Việt-Anh
   - favorites.txt: Danh sách từ yêu thích

5. Lưu ý:
   - Đảm bảo các file dữ liệu đã được copy vào thư mục bin/data/
   - Nếu có lỗi, kiểm tra console để xem thông báo lỗi
   - Đảm bảo biến môi trường JAVA_HOME đã được cấu hình đúng

6. Chức năng chính:
   - Tra cứu từ điển Anh-Việt và Việt-Anh
   - Thêm/xóa từ mới
   - Quản lý từ yêu thích
   - Xem thống kê tra cứu

Liên hệ hỗ trợ: 21120534@student.hcmus.edu.vn

LƯU Ý
-----
- Đảm bảo các file XML trong thư mục data không bị xóa hoặc di chuyển
- Nếu gặp lỗi khi biên dịch, hãy kiểm tra:
  + JDK đã được cài đặt đúng cách
  + Biến môi trường JAVA_HOME đã được cấu hình
  + Đường dẫn đến thư mục chứa ứng dụng không chứa ký tự đặc biệt
- Nếu gặp lỗi "File not found" khi chạy ứng dụng:
  + Đảm bảo bạn đang chạy lệnh từ thư mục gốc của project
  + Kiểm tra xem thư mục bin/data và các file XML đã được tạo chưa
  + Thử chạy lại file build.bat để copy lại các file XML
- Nếu gặp lỗi "Class not found":
  + Kiểm tra xem đã biên dịch thành công chưa
  + Kiểm tra đường dẫn classpath (-cp) có chính xác không
  + Thử xóa thư mục bin và biên dịch lại 



Tự Chấm Điểm 
  ----

(1.0đ) Giao diện đồ hoạ hợp lý, đúng yêu cầu.
(0.5đ) Chuyển đổi ngôn ngữ tra cứu.
(2.0đ)Tra cứu từ và hiển thị nghĩa của từ.
(2.0đ) Thêm từ mới (cùng với nghĩa) vào từ điển đã chọn.
(0.5đ) Xóa một từ (cùng với nghĩa) ra khỏi từ điển đã chọn.
(2.0đ) Lưu lại từ yêu thích. Sắp xếp danh sách từ yêu thích theo thứ
tự A-Z hoặc Z-A.
2.0đ) Thống kê tần suất tra cứu các từ đã tra từ ngày Date1 đến
ngày Date2.

Em thấy mình làm cũng rất cẩn thận hết yêu cầu
nên em tự đánh giá 10 điểm ạ