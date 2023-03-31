package com.arazcaptcha.arazcaptcha.controller;


import com.arazcaptcha.arazcaptcha.entity.EmployeeEntity;
import com.arazcaptcha.arazcaptcha.repository.EmployeeRepository;
import com.arazcaptcha.arazcaptcha.validation.ReCaptchaValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee") 

public class EmployeeController {  

	@Autowired
	private ReCaptchaValidationService validator;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	 @GetMapping("/register")
	 public String showRegister(Model model)
	 {  
		 model.addAttribute("employee", new EmployeeEntity());
		 return "EmployeeRegister";
	  }
	 
	 @PostMapping("/save")
	 public String saveEmployee(@ModelAttribute("employee")
	 EmployeeEntity employee,  
	 
	 @RequestParam(name="g-recaptcha-response")
	 String captcha, Model model) 
	 {  
		 if(validator.validateCaptcha(captcha))
	        {    
			 employeeRepository.save(employee); 
			 model.addAttribute("employee", new EmployeeEntity());
			 model.addAttribute("message", "Employee added!!"); 
			 } 
		     else { 
		    	 model.addAttribute("message", "Please Verify Captcha");
		    	 }      
		 return "EmployeeRegister"; 
	}  
	 
	 @GetMapping("/all")
	 public String getAllEmployees(Model model)
	 {  
		 model.addAttribute("list", employeeRepository.findAll()); 
         return "EmployeeData"; 
         
	 }  
	} 
