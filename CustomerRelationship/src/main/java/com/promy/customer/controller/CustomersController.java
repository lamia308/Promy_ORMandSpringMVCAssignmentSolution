package com.promy.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.promy.customer.entity.Customer;
import com.promy.customer.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomersController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/list")
	public String listCustomers(Model theModel) {

		List<Customer> theCustomers = customerService.findAll();

		theModel.addAttribute("Customers", theCustomers);

		return "list-Customers";

	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd( Model model) {
		
		Customer theCustomer = new Customer();
		model.addAttribute("Customer",theCustomer);
		
		return "Customer-form";
		}

	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname, @RequestParam("email") String email) {
		
		
		Customer theCustomer;
		if (id !=0) {
			
			theCustomer = customerService.findById(id);
			
			theCustomer.setFirstName(firstname);
			theCustomer.setLastName(lastname);
			theCustomer.setEmail(email);
			
		}
		
		else {
			
			theCustomer = new Customer (firstname, lastname, email);
			customerService.save(theCustomer);
			
		}
		
		return "redirect:/customers/list";
		
		}
	
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model theModel) {
		
		Customer theCustomer = customerService.findById(id);
		theModel.addAttribute("Customer", theCustomer);
		
		return "Customer-form";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int id) {
		
		 customerService.deleteById(id);
		
		return "redirect:/customers/list";
		
	}
}

