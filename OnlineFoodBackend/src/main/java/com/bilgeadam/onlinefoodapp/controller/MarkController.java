package com.bilgeadam.onlinefoodapp.controller;

import com.bilgeadam.onlinefoodapp.domain.Cart;
import com.bilgeadam.onlinefoodapp.domain.Customer;
import com.bilgeadam.onlinefoodapp.domain.Delivery;
import com.bilgeadam.onlinefoodapp.domain.Meal;
import com.bilgeadam.onlinefoodapp.dto.CartDTO;
import com.bilgeadam.onlinefoodapp.jwt.JwtTokenUtil;
import com.bilgeadam.onlinefoodapp.repo.EmployeeService;
import com.bilgeadam.onlinefoodapp.service.CartService;
import com.bilgeadam.onlinefoodapp.service.CustomerService;
import com.bilgeadam.onlinefoodapp.service.DeliveryService;
import com.bilgeadam.onlinefoodapp.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/meal")
public class MarkController {

    private boolean esit;

    private MealService mealService;
    private CartService cartService;
    private CustomerService customerService;
    private DeliveryService deliveryService;
    private EmployeeService employeeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public MarkController(MealService mealService, CartService cartService, CustomerService customerService, DeliveryService deliveryService, EmployeeService employeeService) {
        this.mealService = mealService;
        this.cartService = cartService;
        this.customerService = customerService;
        this.deliveryService = deliveryService;
        this.employeeService = employeeService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findById/{id}") //http://localhost:8080/meal/findById/2
    public Optional<Customer> findById(@PathVariable(value = "id") Long id) {
        return customerService.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findByCartId/{id}")
    public Optional<Cart> findByCartId(@PathVariable(value = "id") Long id) {
        return cartService.findAllByCart(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findAllByCart")
    public List<Cart> findAllByCart() {
        return cartService.findAllByCart();
    }


    @RequestMapping(method = RequestMethod.GET, path = "/findByMealId")
    public Optional<Meal> findByMealId(@RequestBody String id) {
        return mealService.findByCode(id);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/findAllByMeal")
    public List<Meal> findAllByMeal() {
        return mealService.getAllMeals();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/DeleteByMealCodeId")
    public Cart DeleteByMealCodeId(@RequestBody String mealCode, HttpServletRequest req) {
        String customer_Id = findCustomerIdFromToken(req);
        long id = Long.parseLong(customer_Id); // Customer id elimde

        Cart cart1 = cartService.findByCustomer_IdAndStatusIsFalse(id);

        List<String> x = Arrays.asList(mealCode.split("="));
        mealCode = x.get(0);

        mealCode = mealCode.replace("\"", ""); //Mobile de ""CRB"" gonderdigi icin replace yapıtım

        List<Meal> mealList = cart1.getMeals();

        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getCode().equals(mealCode)) {
                mealList.remove(i);
                break;
            }
        }
        cart1.setMeals(mealList);
        cartService.save(cart1);
        System.err.println(cart1.getMeals());
        return cart1;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findMealByCustomer_Id")
    public Cart findMealByCustomer_Id(HttpServletRequest req) {
        String customer_Id = findCustomerIdFromToken(req);
        long id = Long.parseLong(customer_Id); // Customer id elimde

        double price = 0;

        Cart crt = cartService.findByCustomer_IdAndStatusIsFalse(id);
        if (crt == null) {
            return new Cart();
        }

        for (Meal meal : crt.getMeals()) {
            price = meal.getPrice().doubleValue() + price;
        }
        crt.setTotalPrice(String.valueOf(price));

        return crt;

    }

    @RequestMapping(method = RequestMethod.GET, path = "/findCartByCustomer_Id") // Mobile
    public List<Meal> findCartByCustomer_Id(HttpServletRequest req) {
        String customer_Id = findCustomerIdFromToken(req);
        long id = Long.parseLong(customer_Id); // Customer id elimde

        Cart crt = cartService.findByCustomer_IdAndStatusIsFalse(id);
        if (crt == null) {
            return new ArrayList<Meal>();
        }
        CartDTO cartDTO = new CartDTO();

        return crt.getMeals();

    }

    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public void addToCarts(@RequestBody String mealCode, HttpServletRequest req) {
        String customer_Id = findCustomerIdFromToken(req); // Customer id elimde
        long id = Long.parseLong(customer_Id);
        esit = true;

        Cart cart1 = cartService.findByCustomer_IdAndStatusIsFalse(id);
        if (cart1 == null) {
            cart1 = new Cart();
        }

        List<String> x = Arrays.asList(mealCode.split("=")); // Webde "CRB=" koyup gonderidigi icin split yaptim
        mealCode = x.get(0);

        mealCode = mealCode.replace("\"", ""); //Mobile de ""CRB"" gonderdigi icin repalce yapıtım

        Optional<Meal> mealOpt = mealService.findByCode(mealCode);
        System.err.println(mealCode);
        if (mealOpt.isPresent()) {
            Meal meal = mealOpt.get();

            List<Meal> mealList = cart1.getMeals();

            mealList.add(meal); // Yeni ürün ekledim
            cart1.setMeals(mealList); // Yeni sepetin yemeklerini girdim
            cart1.setStatus(false);
            if (cart1.getCustomer() == null) { // buradan emin değilim gereksiz gibi
                customerService.findById(id).ifPresent(cart1::setCustomer); // Yeni sepetin müsteri id'sini girdim
            }

            double price = 0;

            for (Meal meals : mealList) {
                price = meals.getPrice().doubleValue() + price;
                cart1.setTotalPrice(String.valueOf(price));
            }

            cartService.save(cart1); // Sepete Ürünü ekledim
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/odeme")
    public void odeme(HttpServletRequest req) {
        String customer_Id = findCustomerIdFromToken(req);
        long id = Long.parseLong(customer_Id); // Customer id elimde

        Cart cart = cartService.findByCustomer_IdAndStatusIsFalse(id);
        cart.setStatus(true);
        cartService.save(cart);

        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(false);
        delivery.setTip(null);
        delivery.setOrderDate(orderDate());
        delivery.setDeliveredDate(null);
        delivery.setCart(cart);
        delivery.setEmployee(employeeService.findById(1L).get());
        deliveryService.deliverySave(delivery);

    }

    @RequestMapping(method = RequestMethod.GET, path = "/findYolda")
    public List<Delivery> findYolda(HttpServletRequest req) {
        String customer_Id = findCustomerIdFromToken(req);
        long id = Long.parseLong(customer_Id); // Customer id elimde
        double price;
        Delivery delivery;
        List<Delivery> deliveryList = new ArrayList<>();
        List<Cart> crt = cartService.findByCustomer_IdAndStatusIsTrue(id);

        for (Cart cart : crt) {
            price = 0;
            delivery = deliveryService.findByDeliveryStatusIsFalseAndCart(cart);
            if (delivery != null) {
                deliveryList.add(delivery);
                for (Meal meal : cart.getMeals()) {
                    price = meal.getPrice().doubleValue() + price;
                }
                cart.setTotalPrice(String.valueOf(price));
            }
        }

        return deliveryList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findGeldi")
    public List<Delivery> findGeldi(HttpServletRequest req) { // burda sadece true olanları çektim. aslında tarihi boş null olanları çekelim çünkü teslim edildiyse tarihi vardır.
        String customer_Id = findCustomerIdFromToken(req);
        long id = Long.parseLong(customer_Id); // Customer id elimde
        double price;
        Delivery delivery;
        List<Delivery> deliveryList = new ArrayList<>();
        List<Cart> crt = cartService.findByCustomer_IdAndStatusIsTrue(id);

        for (Cart cart : crt) {
            price = 0;
            delivery = deliveryService.findByDeliveryStatusIsTrueAndCart(cart);
            if (delivery != null) {
                deliveryList.add(delivery);
                for (Meal meal : cart.getMeals()) {
                    price = meal.getPrice().doubleValue() + price;
                }
                cart.setTotalPrice(String.valueOf(price));
            }
        }

        return deliveryList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/campaigns")
    public List<Meal> getCampaignMeals() {
        return mealService.getCampaignMeals();
    }

    public String findCustomerIdFromToken(HttpServletRequest req) {
        String token = req.getHeader(AUTHORIZATION).substring(7);
        return jwtTokenUtil.getIdFromToken(token);

    }

    public String orderDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = localDateTime.format(formatter);
        return formatDateTime;
    }

}
