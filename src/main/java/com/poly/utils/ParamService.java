package com.poly.utils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;
	
	/**
	 * Đọc chuỗi giá trị của tham số
	 * @param name tên tham số
	 * @param defaultValue giá trị mặc định
	 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
	 */
	public String getString(String name, String defaultValue){
		String value = request.getParameter(name);
		return value != null ? value : defaultValue;
	}
	/**
	 * Đọc số nguyên giá trị của tham số
	 * @param name tên tham số
	 * @param defaultValue giá trị mặc định
	 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
	 */
	public int getInt(String name, int defaultValue){
		String value = getString(name, String.valueOf(defaultValue));
		return Integer.parseInt(value);
	}
	/**
	 * Đọc số thực giá trị của tham số
	 * @param name tên tham số
	 * @param defaultValue giá trị mặc định
	 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
	 */
	public double getDouble(String name, double defaultValue){
		String value = getString(name, String.valueOf(defaultValue));
		return Double.parseDouble(value);
	}
	/**
	 * Đọc giá trị boolean của tham số
	 * @param name tên tham số
	 * @param defaultValue giá trị mặc định
	 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
	 */
	public boolean getBoolean(String name, boolean defaultValue){
		String value = getString(name, String.valueOf(defaultValue));
		return Boolean.parseBoolean(value);
	}
	/**
	 * Đọc giá trị thời gian của tham số
	 * @param name tên tham số
	 * @param pattern là định dạng thời gian
	 * @return giá trị tham số hoặc null nếu không tồn tại
	 * @throws RuntimeException nếu không đúng định dạng
	 */
	public Date getDate(String name, String pattern){
		String value = getString(name, "");
		try {
			return new SimpleDateFormat(pattern).parse(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Lưu file upload vào thư mục
	 * @param file chứa file upload từ client
	 * @param path đường dẫn tính từ webroot
	 * @return đối tượng chứa file đã lưu hoặc null nếu không có file upload
	 * @throws RuntimeException lỗi lưu file
	 */
	public String save(MultipartFile file, String path) {
	    if (!file.isEmpty()) {
	        // Lấy đường dẫn thực tế của static
	        String uploadDir = new File("src/main/resources/static" + path).getAbsolutePath();
	        
	        File dir = new File(uploadDir);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	        try {
	            File savedFile = new File(dir, file.getOriginalFilename());
	            file.transferTo(savedFile.getAbsoluteFile());
	            return path + "/" + file.getOriginalFilename();
	        } catch (Exception e) {
	            throw new RuntimeException("Lỗi khi lưu tệp: " + e.getMessage());
	        }
	    }
	    return null;
	}




}