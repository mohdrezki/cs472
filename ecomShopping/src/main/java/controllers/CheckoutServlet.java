package controllers;

import models.Product;
import utilities.ApplicationFunctions;
import utilities.ApplicationParams;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        if(session.getAttribute(ApplicationParams.SESSION_CART_PRODUCT_PARAM) != null) {
            List<Product> products = (List<Product>) session.getAttribute(ApplicationParams.SESSION_CART_PRODUCT_PARAM);
            if(products.size() > 0) {
                int total = 0;
                for (Product product: products) {
                    total += Integer.parseInt(product.getPrice());
                }
                request.setAttribute("products", products);
                request.setAttribute("total_price", Integer.toString(total));
                request.getRequestDispatcher(ApplicationParams.CHECKOUT_PAGE_LINK).forward(request, response);
            }else{
                ApplicationFunctions.redirectToProductsPage(response, request);
            }
        }else{
            ApplicationFunctions.redirectToProductsPage(response, request);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String address = request.getParameter("address");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String zip = request.getParameter("zip");

        String card_holder = request.getParameter("card_holder");
        String card_number = request.getParameter("card_number");
        String billing_address = request.getParameter("billing_address");
        String cvc = request.getParameter("cvc");
        String expiration = request.getParameter("expiration");

        System.out.println("----------------------------");
        System.out.println("Address: "+address);
        System.out.println("Country: "+country);
        System.out.println("City: "+city);
        System.out.println("Zip Code: "+zip);
        System.out.println("Card holder: "+card_holder);
        System.out.println("Card number: "+card_number);
        System.out.println("cvc: "+cvc);
        System.out.println("Billing address: "+billing_address);
        System.out.println("expiration: "+expiration);
        System.out.println("----------------------------");

        HttpSession session = request.getSession();
        session.removeAttribute(ApplicationParams.SESSION_CART_PRODUCT_PARAM);
        request.getRequestDispatcher(ApplicationParams.CONFIRMATION_PAGE_LINK).forward(request, response);
    }
}
