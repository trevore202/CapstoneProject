package com.hcl.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.model.Product;
import com.hcl.service.ProductService;

@Controller
@SessionAttributes("name")
public class ProductController {

	@Autowired
	ProductService service;

	@GetMapping("/listproducts")
	public ModelAndView listProducts(ModelMap model) {
		model.put("products", service.retrieveProducts());
		return new ModelAndView("list-todos", model);
	}

	@GetMapping("/add-product")
	public ModelAndView showAddProductPage(ModelMap model) {
		model.addAttribute("product", new Product());
		return new ModelAndView("todo", model);
	}

	@PostMapping("/add-product")
	public ModelAndView addProduct(ModelMap model, Product product, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("todo", model);
		}
		service.addProduct(product);
		// model.put("products", service.retrieveProducts());
		return new ModelAndView("redirect:/listproducts");
	}

	@GetMapping("/delete-product")
	public ModelAndView deleteProduct(ModelMap model, @RequestParam int id) {
		service.deleteProduct(id);
		// model.put("products", service.retrieveProducts());
		return new ModelAndView("redirect:/listproducts");
	}

	@GetMapping("/update-product")
	public ModelAndView showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Product product = service.retrieveProduct(id);
		model.put("product", product);
		return new ModelAndView("todo", model);
	}

	@PostMapping("/update-product")
	public ModelAndView updateTodo(ModelMap model, Product product, BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("todo", model);
		}
		service.updateProduct(product);
		// model.put("products", service.retrieveProducts());
		return new ModelAndView("redirect:/listproducts");
	}

	@GetMapping("/product")
	public ModelAndView showProduct(ModelMap model, @RequestParam int id) {
		Product product = service.retrieveProduct(id);
		model.put("product", product);

		return new ModelAndView("showproduct", model);

	}

	@GetMapping("/gohome")
	public ModelAndView showHomePage(ModelMap model) {
		return new ModelAndView("index", model);
	}

	@PostMapping("/addtocart")
	public ModelAndView addToCart(ModelMap model, HttpSession session, @RequestParam int quantity,
			@RequestParam int id) {
		List<Product> cartItems;
		List<Integer> cartQuantities;
		if (session.getAttribute("cartItems") == null || session.getAttribute("cartQuantities") == null) {
			cartItems = new ArrayList<>();
			cartQuantities = new ArrayList<>();
		} else {
			cartItems = (ArrayList<Product>) (session.getAttribute("cartItems"));
			cartQuantities = (ArrayList<Integer>) (session.getAttribute("cartQuantities"));
		}
		Product p = service.retrieveProduct(id);
		if (cartItems.contains(p)) {
			int itemIndex = cartItems.indexOf(p);
			cartQuantities.set(itemIndex, (cartQuantities.get(itemIndex) + quantity));
		} else {
			cartItems.add(p);
			cartQuantities.add(quantity);
		}
		model.put("product", p);
		model.put("addedtocartmessage", "Item has been added to cart!");
		session.setAttribute("cartItems", cartItems);
		session.setAttribute("cartQuantities", cartQuantities);
		return new ModelAndView("showproduct",model);
	}

	@GetMapping("/checkout")
	public ModelAndView showCartPage(HttpSession session, ModelMap model) {
		List<Product> cartItems = (List) session.getAttribute("cartItems");
		List<Integer> cartQuantities = (List) session.getAttribute("cartQuantities");
		double total = 0;

		if (cartItems != null) {
			for (int i = 0; i < cartItems.size(); i++) {
				double itemTotal = cartItems.get(i).getProductPrice() * Double.valueOf(cartQuantities.get(i));
				total += itemTotal;
				total = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
			}
			session.setAttribute("TotalPrice", total);
		}
		return new ModelAndView("checkout", model);
		
	}

	@GetMapping("/finalizeorder")
	public ModelAndView showFinalizeOrderPage() {
		return new ModelAndView("finalizeorder");

	}

	@PostMapping("/finalizeorder")
	public ModelAndView showConfirmationPage(ModelMap model, HttpSession session, @RequestParam String shippingaddress,
			@RequestParam String email) {

		model.put("address", shippingaddress);
		model.put("email", email);
		model.put("TotalPrice", session.getAttribute("TotalPrice"));
		session.removeAttribute("cartItems");
		session.removeAttribute("cartQuantities");
		session.removeAttribute("TotalPrice");
		return new ModelAndView("orderconfirmation", model);
	}

	
	@GetMapping("/removeitem")
	public ModelAndView removeItemFromCart(HttpSession session, @RequestParam int id) {
		Product p = service.retrieveProduct(id);
		List<Product> cartItems = (List) session.getAttribute("cartItems");
		List<Integer> cartQuantities = (List) session.getAttribute("cartQuantities");
		int cartindex = 0;
		for(Product cartProduct : cartItems) {
			if(p.equals(cartProduct)) {
				cartindex = cartItems.indexOf(cartProduct);
				cartItems.remove(cartProduct);
				break;
			}
		}
		
		cartQuantities.remove(cartindex);
		
		session.setAttribute("cartItems", cartItems);
		session.setAttribute("cartQuantities", cartQuantities);
		
		return new ModelAndView("redirect:/checkout");
		
	}
	
	@PostMapping("/search")
	public ModelAndView searchProducts(@RequestParam String search, ModelMap model) {
		
		model.put("products", service.searchProducts(search.toLowerCase()));
		return new ModelAndView("list-todos", model);
	}
	
}
