package com.poly;

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

public class RegisterTestSelenium {
    WebDriver driver;
    Workbook workbook;
    Sheet sheet;
    int rowNum = 0;
    @BeforeClass
    public void beforeClass(){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("TestResult");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("STT");
        header.createCell(1).setCellValue("Test Case");
        header.createCell(2).setCellValue("Status");
    }
    @BeforeMethod
    public void setup(){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.get("http://localhost:8080/public/register");
    }
    @DataProvider(name = "registerData")
    public Object[][] getLoginData(){
        return new Object[][]{
                {"admin","Trần Hữu Lộc","123","123",false},
                {"user","Trần Hữu Lộc","123","123",false},
                {"student12","Trần Hữu Lộc","123","123",true},
                {"student1","Trần Hữu Lộc","123","12345",false},
        };
    }
    private void writeResult(String testCase, String status){
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(rowNum);
        row.createCell(1).setCellValue(testCase);
        row.createCell(2).setCellValue(status);
    }
    @Test(dataProvider = "registerData")
    public void testRegis(String username, String fullname, String password, String confirmpassword, boolean expectedSuccess){
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement fullnameField = driver.findElement(By.id("fullname"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement confirmpasswordField = driver.findElement(By.id("repassword"));
        WebElement registerButton = driver.findElement(By.cssSelector("button[id='submit']"));

        usernameField.clear();
        fullnameField.clear();
        passwordField.clear();
        confirmpasswordField.clear();

        usernameField.sendKeys(username);
        fullnameField.sendKeys(fullname);
        passwordField.sendKeys(password);
        confirmpasswordField.sendKeys(confirmpassword);
        registerButton.click();
        Assert.assertEquals(loginSuccessful(), expectedSuccess);
    }

    private boolean loginSuccessful() {
        try{
            driver.findElement(By.xpath("//p[contains(text(),' Đăng kí thành công !')]"));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        writeResult("testRegis", loginSuccessful() ? "Pass" : "Fail");
        driver.quit();
    }
    @AfterClass
    public void writeExcel() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("D:\\SPRING PRODUCTS\\ASM\\src\\test\\resources\\report\\register.xlsx");
        workbook.write(fileOut);
        workbook.close();
        fileOut.close();
    }
}


