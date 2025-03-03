package com.poly.controllers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.poly.models.Attribute;
import com.poly.models.Category;
import com.poly.models.CustomUserDetails;
import com.poly.models.Product;
import com.poly.models.ProductValue;
import com.poly.models.ProductVariantValue;
import com.poly.models.Variant;
import com.poly.models.VariantValue;
import com.poly.services.AttributeService;
import com.poly.services.CartItemService;
import com.poly.services.CategoryService;
import com.poly.services.ProductService;
import com.poly.services.ProductValueService;
import com.poly.services.ProductVariantValueService;
import com.poly.services.SessionCartService;
import com.poly.services.VariantService;
import com.poly.services.VariantValueService;
import com.poly.utils.ParamService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/admin")
public class CrudProductController {
	@Autowired
	ParamService paramService;
	@Autowired
	ProductValueService productValueService;
	@Autowired
	AttributeService attributeService;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductVariantValueService productVariantValueService;
	@Autowired
	SessionCartService sessionCartService;
	@Autowired
	VariantService variantService;
	@Autowired
	VariantValueService variantValueService;
	@Autowired
	CartItemService cartItemService;
	
	@GetMapping("/products/create")
	public String formCreate(Model model, @ModelAttribute("product") Product product) {
		model.addAttribute("listCategory", categoryService.findAllCategory());
		product.setName("Áo thun form boxy");
		product.setDescription("Áo thun hiệu con bò, chất liệu cao cấp");
		List<Variant> v = variantService.findAllVariant();
		model.addAttribute("listVariant", v);
		System.err.println(v);
		return "/admin/form-crud-product(goc)";
	}

	@PostMapping("/products/create/confirm")
	public String postMethodName(Model model, @ModelAttribute("product") Product product,
			@RequestParam("categoryy") String category, @RequestParam("stock") int stock,
			@RequestPart("imagee") MultipartFile image,
			@RequestParam("price") Float price, @RequestParam("oldprice") Float oldPrice,
			@RequestParam(value = "attribute", required = false) String[] variants,
			@RequestParam(value = "valueAtt", required = false) String[] valueAtt) {
		// Danh sách chứa các nhóm sau khi tách
		List<List<String>> result = new ArrayList<>();

		// Tạo nhóm đầu tiên
		List<String> currentGroup = new ArrayList<>();

		// Duyệt qua mảng và tách các phần tử dựa trên "-----"
		for (String value : valueAtt) {
			if (value.equals("-----")) {
				// Nếu gặp "-----", thêm nhóm hiện tại vào kết quả (nếu không rỗng)
				if (!currentGroup.isEmpty()) {
					result.add(new ArrayList<>(currentGroup));
					currentGroup.clear(); // Xóa các phần tử trong nhóm hiện tại để chuẩn bị cho nhóm mới
				}
			} else {
				// Nếu không phải "-----", thêm giá trị vào nhóm hiện tại
				currentGroup.add(value);
			}
		}

		// Đảm bảo nhóm cuối cùng được thêm vào nếu không có "-----" ở cuối
		if (!currentGroup.isEmpty()) {
			result.add(currentGroup);
		}
		// Tạo kết quả nối từ các nhóm, ghép các thuộc tính từ các nhóm liên tiếp
		List<String> finalResult = new ArrayList<>();

		// Kiểm tra số lượng nhóm và ghép các giá trị
		if (result.size() == 2) { // Nếu chỉ có 2 nhóm (màu sắc và size)
			List<String> colors = result.get(0); // Nhóm màu sắc
			List<String> sizes = result.get(1); // Nhóm size

			// Ghép từng thuộc tính của nhóm màu với từng thuộc tính của nhóm size
			for (String color : colors) {
				for (String size : sizes) {
					finalResult.add(color + "-" + size);
				}
			}
		} else if (result.size() == 1) { // Nếu chỉ có 1 nhóm (màu sắc)
			List<String> colors = result.get(0); // Nhóm màu sắc

			// Thêm trực tiếp màu sắc vào kết quả mà không ghép thêm size hoặc chất liệu
			finalResult.addAll(colors);
		}

		String urlImage = paramService.save(image, "/uploads");

		if (finalResult.isEmpty()) {
			product.setCategory(categoryService.checkExistCategory(category));
			product.setImage(urlImage);
			product = productService.saveProduct(product);

			ProductVariantValue pvv = new ProductVariantValue();
			pvv.setProduct(productService.saveProduct(product));
			pvv.setOldprice(oldPrice);
			pvv.setPrice(price);
			pvv.setImage(urlImage);
			pvv.setSku("-");
			pvv.setTurnbuy(0);
			pvv.setStock(stock);
			productVariantValueService.save(pvv);
		} else {

//				xử lí với mỗi finalResult (s-cam-jean)
			for (int i = 0; i < finalResult.size(); i++) {
				String[] array = finalResult.get(i).split("-");
				String sku = "";
				for (int index = 0; index < array.length; index++) {
					Variant v = variantService.checkExistVariant(variants[index]);
					VariantValue vv = new VariantValue();
					vv.setVariant(v);
					vv.setValue(array[index]);
					vv = variantValueService.checkExistVariantValue(vv);
					if (index != array.length - 1) {
						sku += (String.valueOf(vv.getId()));
						sku += "-";
					} else {
						sku += String.valueOf(vv.getId());
					}
				}

				product.setCategory(categoryService.checkExistCategory(category));
				product.setImage(urlImage);
				product = productService.saveProduct(product);

				ProductVariantValue pvv = new ProductVariantValue();
				pvv.setProduct(productService.saveProduct(product));
				pvv.setOldprice(oldPrice);
				pvv.setPrice(price);
				pvv.setSku(sku);
				pvv.setTurnbuy(0);
				pvv.setImage(urlImage);
				pvv.setStock(stock);
				productVariantValueService.save(pvv);
			}

		}

		return "redirect:/admin/products/edit-product/" + String.valueOf(product.getId());

//	        	kiểm tra xem ng dùng có nhập giá trij hay không

//        return thuoc tinhs
	}

	@GetMapping("/products/edit-product/{id}")
	public String getMethodName(@ModelAttribute("pvv") ProductVariantValue pvv,
			@ModelAttribute("product") Product product, Model model, @PathVariable("id") String idProduct) {

		model.addAttribute("listCategory", categoryService.findAllCategory());
		product = productService.findProductById(Integer.parseInt(idProduct));
		model.addAttribute("product", product);
		List<ProductVariantValue> list = productVariantValueService.findPvvByIdProduct(Integer.parseInt(idProduct));
		int sumStock = 0;
		for (ProductVariantValue pvv1 : list) {
			sumStock += pvv1.getStock();
		}
		String sku = list.get(0).getSku();
		String[] array = sku.split("-");
		for (int i = 0; i < array.length; i++) {
			Variant v = variantService.findVariantByIdPvv(Integer.parseInt(array[i]));
			array[i] = v.getName();
		}
		List<String> values = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String[] temp = list.get(i).getSku().split("-");
			String a = "";
			for (String id : temp) {
				a += variantValueService.value(Integer.parseInt(id)) + " ";
			}
			values.add(a);
		}
		model.addAttribute("values", values);
		model.addAttribute("array", array);
		model.addAttribute("sumStock", sumStock);
		model.addAttribute("listPvv", list);
		return "/admin/edit-product";
	}

	@PostMapping("/products/update/{id}")
	public String postMethodName(RedirectAttributes redirect, @ModelAttribute("product") Product product,
			@RequestParam("categoryy") String category, @PathVariable("id") String idProduct,
			@RequestPart("imagee") MultipartFile url, @RequestParam("imgSaved") String imgSaved) {
		product.setId(Integer.parseInt(idProduct));
		if (!url.isEmpty()) {
			String img = paramService.save(url, "/uploads");
			product.setImage(img);
		} else {
			product.setImage(imgSaved);
		}
		product.setCategory(categoryService.checkExistCategory(category));
		product = productService.saveProduct(product);
		redirect.addFlashAttribute("message", "Cập nhật thành công ");
		return "redirect:/admin/products/edit-product/" + String.valueOf(product.getId());
	}

	@PostMapping("/products/updatePvv/{id}")
	public String postMethodName(RedirectAttributes redirect,@ModelAttribute("pvv") ProductVariantValue pvv, @PathVariable("id") String idPro,
			@RequestPart("imagee") MultipartFile url, @RequestParam("imgSaved") String imgSaved) {
		Product product = productService.findProductById(Integer.parseInt(idPro));
		pvv.setProduct(product);
		if (!url.isEmpty()) {
			String img = paramService.save(url, "/uploads");
			pvv.setImage(img);
		} else {
			pvv.setImage(imgSaved);
		}
		pvv = productVariantValueService.save(pvv);
		sessionCartService.updateCartFromDB();
		redirect.addFlashAttribute("message", "Cập nhật thành công ");
		return "redirect:/admin/products/edit-product/" + String.valueOf(product.getId());

	}

	@PostMapping("/products/createPvv/{id}")
	public String postMethodName(RedirectAttributes redirect,@RequestParam(value = "attribute", required = false) String[] attributes,
			@ModelAttribute("pvv") ProductVariantValue pvv,@RequestPart("imagee") MultipartFile image,
			@RequestParam(value = "value", required = false) String[] values, @PathVariable("id") int idProduct) {
		String sku = "";
		
		System.err.println("độ dài "+attributes.length);
		if (attributes != null) {		
			for (int i = 0; i < attributes.length; i++) {
				Variant v = variantService.checkExistVariant(attributes[i].trim());
				VariantValue vv = new VariantValue();
				vv.setValue(values[i]);
				vv.setVariant(v);
				vv = variantValueService.checkExistVariantValue(vv);
				if (i + 1 != attributes.length) {
					sku += vv.getId();
					sku += "-";
				} else {
					sku += vv.getId();
				}
			}
		}
	
		String[] arraySku = productVariantValueService.listSkuByIdProduct(idProduct);
		Boolean check = true;
		for (String string : arraySku) {
			 if (string.equals(sku)) {
				check = false;
				System.err.println("sku trùng rồi "+check);
				break;
			}
		}
		
		if (!check) {
			redirect.addFlashAttribute("message", "Thuộc tính đã tồn tại !" );
			return "redirect:/admin/products/edit-product/" + String.valueOf(idProduct);
		}
		pvv.setId(null);
		pvv.setProduct(productService.findProductById(idProduct));
		pvv.setSku(sku);
		pvv.setImage(paramService.save(image, "/uploads"));
		pvv.setTurnbuy(0);
		productVariantValueService.save(pvv);
		redirect.addFlashAttribute("message", "Thêm thành công !" );
		return "redirect:/admin/products/edit-product/" + String.valueOf(idProduct);
	}
	@PostMapping("/products/deletePvv/{idPvv}")
	public String postMethodName(RedirectAttributes redirect,@PathVariable("idPvv") Integer idPvv,@RequestParam("idPro") Integer idPro) {
		String message = "";
		try {
			  productVariantValueService.deletePvv(idPvv);
			   message = "Xóa thành công ";

		}catch (Exception e) {
			   message = "Sản phẩm đã được mua không thể xóa ";
		}
		redirect.addFlashAttribute("message",message);
		if (productVariantValueService.findPvvByIdProduct(idPro).isEmpty()) {
			productService.deleteProductById(idPro);
			sessionCartService.updateCartFromDB();
			return "redirect:/admin/products";
		}
		sessionCartService.updateCartFromDB();
		return "redirect:/admin/products/edit-product/" + String.valueOf(idPro);
	}
	@GetMapping("/products/delete/{id}")
	public String postMethodName(RedirectAttributes redirect,@PathVariable("id") Integer idPro) {	
		String message = "";
		try {
			 productService.deleteProductById(idPro);
			 message = "Xóa thành công ";

		}catch (Exception e) {
			   message = "Sản phẩm đã được mua không thể xóa ";
		}
		sessionCartService.updateCartFromDB();
		redirect.addFlashAttribute("message",message);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/products")
	public String getMethodNam(Model model) {
		model.addAttribute("listProduct", productService.findName());
		return "/admin/product-manager";
	}
	@GetMapping("/products/statistics")
	public String getMethodName(Model model) {
		model.addAttribute("top5HotProduct",productService.top5HotProduct());
		model.addAttribute("revenueProduct", productService.revenueProduct());
		return "/admin/product-statistics";
	}
	

}
