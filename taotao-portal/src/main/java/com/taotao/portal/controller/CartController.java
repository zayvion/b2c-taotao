package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.service.CartService;
import com.taotao.protal.pojo.CartItem;

/**
 * 购物车Controller
 * @author Zayvion
 * @date Apr 23, 2019
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	/**
	 * 添加商品到购物车
	 * @Title addCartItem   
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return String
	 * @throws
	 */
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, 
			@RequestParam(defaultValue="1")Integer num, 
			HttpServletRequest request, HttpServletResponse response) {
		 TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}
	
	@RequestMapping("/success")
	public String showSuccess() {
		return "cartSuccess";
	}
	/**
	 * 展示购物车商品
	 * @Title showCart   
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws
	 */
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> list = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", list);
		return "cart";
	}
	
	/**
	 * 在input中直接修改商品数量
	 * @Title updateCartItem   
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return TaotaoResult
	 */
	@RequestMapping("/update/{itemId}")
	public String updateCartItem(@PathVariable Long itemId, 
			@RequestParam(defaultValue="1")Integer num, 
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = cartService.updateCartItem(itemId, num, request, response);
		return "redirect:/cart/cart.html";
	}
	
	/**
	 * 删除购物车商品
	 * @Title deleteCartItem   
	 * @param itemId
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}


	
}

