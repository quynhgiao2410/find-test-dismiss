import java.io.*
import java.util.regex.Pattern
import java.util.regex.Matcher
import java.io.*
import java.util.regex.Pattern
import java.util.regex.Matcher
import java.awt.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// Đường dẫn Python và script
Mobile.startApplication('C:\\Users\\trant\\Downloads\\FCdemo.apk', false)
String screenshotPath = "C:\\Users\\trant\\Katalon Studio\\checktextdismiss\\test.jpg"

// Chụp ảnh màn hình
Mobile.takeScreenshot(screenshotPath)
println("📸 Ảnh màn hình đã được lưu tại: " + screenshotPath)

//// Lưu ảnh màn hình vào vị trí đã chỉ định
//File screenshotFile = new File(screenshotPath)
//ImageIO.write(screenshot, "PNG", screenshotFile)
//println("📸 Ảnh màn hình đã được lưu tại: " + screenshotPath)

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

// Lấy giá trị x, y, width và height từ kết quả trả về
String result = output.toString()

// Sử dụng regular expression để tìm giá trị x, y, width, height
Pattern pattern = Pattern.compile("x=(\\d+), y=(\\d+), width=(\\d+), height=(\\d+)")
Matcher matcher = pattern.matcher(result)

if (matcher.find()) {
	String x = matcher.group(1)
	String y = matcher.group(2)
	String width = matcher.group(3)
	String height = matcher.group(4)
	println("🎯 Vị trí Dismiss - x: " + x + ", y: " + y + ", width: " + width + ", height: " + height)
	int a = Integer.parseInt(x)
	int b = Integer.parseInt(y)
	
	// Sử dụng tapAtPosition với tọa độ đã chuyển đổi
	Mobile.tapAtPosition(a, b)

} else {
	println("Không tìm thấy thông tin vị trí 'Dismiss'.")
}
