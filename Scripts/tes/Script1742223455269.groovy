import java.io.*

// Đường dẫn Python và script
String pythonPath = "C:\\Users\\trant\\AppData\\Local\\Programs\\Python\\Python313\\python.exe"
String scriptPath = "close_dialog_dismiss.py"

// Tạo process chạy Python
ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, scriptPath)
processBuilder.redirectErrorStream(true)  // Gộp stderr vào stdout
Process process = processBuilder.start()

// Đọc toàn bộ output từ Python
BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))
StringBuilder output = new StringBuilder()
String line

while ((line = reader.readLine()) != null) {
    output.append(line).append("\n")  // Lưu toàn bộ kết quả đầu ra
}

// Chờ script chạy xong
int exitCode = process.waitFor()
println("⚡ Script Python kết thúc với mã: " + exitCode)

// Hiển thị toàn bộ kết quả đầu ra trong Katalon
println("📜 Toàn bộ kết quả từ Python:\n" + output.toString())
