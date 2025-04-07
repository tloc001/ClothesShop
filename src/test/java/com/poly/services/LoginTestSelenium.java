package com.poly.services;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class LoginTestSelenium {
    WebDriver driver;
    Workbook workbook;
    Sheet sheet;
    int rowNum = 0;
    @BeforeClass
    public void beforeClass(){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("TestResult");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Test Case");
        header.createCell(1).setCellValue("Status");
    }

    @BeforeMethod
    public void setup(){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.get("https://practicetestautomation.com/practice-test-login/");
    }
    @DataProvider(name = "loginData")
    public Object[][] getLoginData(){
        return new Object[][]{
                {"student", "Password123", true},   // Đúng
                {"wrongUser", "wrongPass", false}, // Sai user & pass
                {"student", "", false},  // Thiếu mật khẩu,
                {"", "Password123", false}  , // Thiếu tên đăng nhập,
                {"","",false}
        };
    }
    private void writeResult(String testCase, String status){
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(testCase);
        row.createCell(1).setCellValue(status);
    }
    @Test(dataProvider = "loginData")
    public void testLoginById(String username, String password, boolean expectedSuccess){
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("submit"));

        passwordField.clear();
        usernameField.clear();

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
        boolean LoginSuccess = loginSuccessful();
        Assert.assertEquals(LoginSuccess, expectedSuccess, "Kết quả đăng nhập không đúng!");
        writeResult("Test login by id", LoginSuccess == expectedSuccess ? "PASS" : "FAIL");

    }

    @Test(dataProvider = "loginData")
    public void TestloginByClassname (String username, String password, boolean expectedSuccess){
            WebElement usernameField = driver.findElement(By.name("username"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement loginButton = driver.findElement(By.id("submit"));

            usernameField.clear();
            passwordField.clear();

            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            loginButton.click();

            boolean LoginSuccess = loginSuccessful();
            Assert.assertEquals(LoginSuccess, expectedSuccess, "Kết quả đăng nhập không đúng!");
            writeResult("Test login by class name", LoginSuccess == expectedSuccess ? "PASS" : "FAIL");
    }
    @Test(dataProvider = "loginData")
    public void TestloginCssSelector (String username, String password, boolean expectedSuccess){
        WebElement usernameField = driver.findElement(By.cssSelector("input[id='username']"));
        WebElement passwordField = driver.findElement(By.cssSelector("input[id='password']"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[id='submit']"));

        usernameField.clear();
        passwordField.clear();

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        boolean LoginSuccess = loginSuccessful();
        Assert.assertEquals(LoginSuccess, expectedSuccess, "Kết quả đăng nhập không đúng!");
        writeResult("Test login by class name", LoginSuccess == expectedSuccess ? "PASS" : "FAIL");
    }





    private boolean loginSuccessful() {
        try{
            driver.findElement(By.xpath("//h1[contains(text(),'Logged In Successfully')]"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }
    @AfterClass
    public void writeExcel() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("D:\\SPRING PRODUCTS\\Celenium\\src\\test\\java\\com\\poly\\Celenium\\report\\report.xlsx");
        workbook.write(fileOut);
        workbook.close();
        fileOut.close();
    }
}


