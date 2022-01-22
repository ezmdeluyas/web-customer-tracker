package com.zmd.springdemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zmd.springdemo.entity.Customer;
import com.zmd.springdemo.service.CustomerService;
import com.zmd.springdemo.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject the customer serivce
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model, @RequestParam(required=false) String sort) {
		
		// get customers from the service
		List<Customer> customers = null;
		
		// check for sort field
		if (sort != null) {
			int sortField = Integer.parseInt(sort);
			customers = customerService.getCustomers(sortField);
		}else {
			// no sort field provided ... default to sorting by last name
			customers = customerService.getCustomers(SortUtils.LAST_NAME);
		}
		
		// add the customers to the model
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		
		// create model attribute to bind form data
		Customer customer = new Customer();
		
		model.addAttribute("customer", customer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
		
		System.out.println(bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "customer-form";
		}else {
			customerService.saveCustomer(customer);
			return "redirect:/customer/list";
		}
		
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id,
								    Model model) {
		
		// get the customer from our service
		Customer customer = customerService.getCustomer(id);
		
		// set customer as a model attribute to pre-populate the form
		model.addAttribute("customer", customer);
		
		// send over to our form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {
		
		// delete the customer
		customerService.deleteCustomer(id);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam(value="theSearchName", required=false) String theSearchName,
            Model theModel) {
		
		// search customers from the service
        List<Customer> customers = customerService.searchCustomers(theSearchName);
        
        // add the customers to the model
        theModel.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

}
