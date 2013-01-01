package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Customer;
import models.Order;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class TemplateApp extends Controller {

	public static Result templateTest() {
		Customer customer = new Customer();
		customer.setName("mori");
		customer.setAge(27);

		List<Order> orders = new ArrayList<Order>();
		Order order = new Order();
		order.setTitle("steak");
		order.setPrice(3000);
		orders.add(order);
		Order order2 = new Order();
		order2.setTitle("fish");
		order2.setPrice(2500);
		orders.add(order2);

		return ok(views.html.template.render(customer, orders));
	}

	static Form<User> userForm = form(User.class);

	public static Result formTest() {

		Map<String, String> anyData = new HashMap();
		anyData.put("email", "bob@gmail.com");
		anyData.put("password", "secret");

		User user = userForm.bind(anyData).get();
		return ok(views.html.form.render(userForm));
	}

	public static Result submit() {
		Form<User> filledForm = userForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.form.render(userForm));
		} else {
			User user = filledForm.get();
			return ok("Got user " + user.email);
		}
	}

}
