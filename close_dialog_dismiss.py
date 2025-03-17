import cv2
import pytesseract

# Cấu hình đường dẫn đến tesseract.exe
pytesseract.pytesseract.tesseract_cmd = r"F:\Tesseract\tesseract.exe"

# Đường dẫn đến file hình ảnh
# image_path = "test.jpg"
image_path = "test.jpg"

# Load hình ảnh
image = cv2.imread(image_path)

# Chuyển đổi hình ảnh sang grayscale
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# Tăng cường chất lượng ảnh (nếu cần)
gray = cv2.resize(gray, None, fx=2, fy=2, interpolation=cv2.INTER_CUBIC)  # Phóng to ảnh
_, binary = cv2.threshold(gray, 128, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)  # Chuyển thành ảnh đen trắng

# Thiết lập cấu hình cho Tesseract OCR
custom_config = r'--oem 3 --psm 11'  # OEM 3: Default engine, PSM 11: Nhận diện từ đơn lẻ

# Chạy OCR trên ảnh
data = pytesseract.image_to_data(binary, config=custom_config, output_type=pytesseract.Output.DICT)

# Tìm text "Dismiss"
found = False
for i, text in enumerate(data["text"]):
    if "Dismiss" in text:
        x, y, w, h = data["left"][i], data["top"][i], data["width"][i], data["height"][i]
        print(f"'Dismiss found at': x={x}, y={y}, width={w}, height={h}")

        # Vẽ bounding box xung quanh text "Dismiss" (nếu cần)
        cv2.rectangle(image, (x, y), (x + w, y + h), (0, 255, 0), 2)
        found = True
        break

if not found:
    print("Không tìm thấy text 'Dismiss' trong hình ảnh!")

# Hiển thị hình ảnh đã xử lý (nếu bounding box được vẽ)

cv2.waitKey(0)
cv2.destroyAllWindows()
