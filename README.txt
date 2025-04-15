THÔNG TIN SINH VIÊN
------------------
Họ và tên: TRẦN VĂN PHƯƠNG
MSSV: 21120534
Lớp: [Tên lớp của bạn]
Khoa: KỸ THUẬT PHẦN MỀM
Email: 21120534@student.hcmus.edu.vn
SDT: 0399784975

HƯỚNG DẪN BIÊN DỊCH VÀ CHẠY ỨNG DỤNG
------------------------------------

1. Yêu cầu hệ thống:
   - Java Development Kit (JDK) phiên bản 8 trở lên
   - Hệ điều hành Windows

2. Cấu trúc thư mục:
   /src
      Main.java    - Mã nguồn chính của ứng dụng
   /data
      anh_viet.xml               - Từ điển Anh-Việt
      viet_anh.xml               - Từ điển Việt-Anh
   /bin
      /data
         anh_viet.xml           - Bản sao của từ điển Anh-Việt
         viet_anh.xml           - Bản sao của từ điển Việt-Anh
   build.bat                     - Script biên dịch
   README.txt                    - File hướng dẫn này

3. Các bước biên dịch và chạy:

   Cách 1: Sử dụng build.bat (Khuyến nghị)
   ----------------------------------------
   - Mở Command Prompt (cmd)
   - Di chuyển đến thư mục chứa ứng dụng: cd đường_dẫn_đến_thư_mục
   - Chạy lệnh: build.bat
   - Script sẽ tự động biên dịch và chạy ứng dụng

   Cách 2: Biên dịch và chạy thủ công
   ---------------------------------
   - Mở Command Prompt (cmd)
   - Di chuyển đến thư mục chứa ứng dụng: cd đường_dẫn_đến_thư_mục
   - Biên dịch: javac -d bin src/Main.java
   - Copy file XML: xcopy /Y data\*.xml bin\data\
   - Chạy ứng dụng: java -cp bin src.Main

   Cách 3: Chạy từ file JAR
   ------------------------
   - Mở Command Prompt (cmd)
   - Di chuyển đến thư mục chứa file JAR: cd đường_dẫn_đến_thư_mục
   - Chạy lệnh: java -jar DictionaryApp.jar

4. Sử dụng ứng dụng:
   - Giao diện đồ họa sẽ hiển thị sau khi chạy thành công
   - Sử dụng các nút và ô nhập liệu để tìm kiếm từ
   - Chuyển đổi giữa từ điển Anh-Việt và Việt-Anh

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