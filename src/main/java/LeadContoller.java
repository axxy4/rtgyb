package com.marketing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketing.dto.LeadData;
import com.marketing.entities.Lead;
import com.marketing.services.LeadService;
import com.marketing.util.EmailService;

@Controller
public class LeadContoller {
	
	@Autowired
	private LeadService leadService;
	
	@Autowired
	private EmailService emailService;
	
	
	//Handler Methods
	
	//http://localhost:8080/create
	@RequestMapping("/create")
	public String viewCreateLeadPage () {
		return "create_lead";
	}
		
//	@RequestMapping("/savelead")
//	public String saveonelead(@ModelAttribute("l")Lead lead, Model model) {
//		leadService.saveLeadInfo(lead);
//		model.addAttribute("msg", "Record is Save");
//		return "create_lead";
//	}
	
//	------------------------------Email Sender---------------------------------------------
	@RequestMapping("/savelead")
	public String saveonelead(@ModelAttribute("l")Lead lead, Model model) {
		leadService.saveLeadInfo(lead);
		emailService.sendEmail(lead.getEmail(), "Welcome To Nakum Family", "Test Email From Spring BootNew");
		model.addAttribute("msg", "Record is Save");
		return "create_lead";
	}
//	---------------------------------------------------------------------------

	
//	@RequestMapping("/savelead")
//	public String saveonelead(@RequestParam("firstname") String firstname,@RequestParam("lastname") String lastname, @RequestParam("email") String email, @RequestParam("mobile") String mobile, Model model) {
//		
//		Lead lead = new Lead();
//		lead.setFirstname(firstname);
//		lead.setLastname(lastname);
//		lead.setEmail(email);
//		lead.setMobile(mobile);
//		
//		leadService.saveLeadInfo(lead);
//		model.addAttribute("msg", "Record id Save");
//		return "create_lead";
//	}
	
//	@RequestMapping("/savelead")
//	public String saveonelead(LeadData leadData, ModelMap model) {
//		
//		Lead lead = new Lead();
//		lead.setFirstname(leadData.getFirstname());
//		lead.setLastname(leadData.getLastname());
//		lead.setEmail(leadData.getEmail());
//		lead.setMobile(leadData.getMobile());
//		
//		leadService.saveLeadInfo(lead);
//		model.addAttribute("msg", "Record id Save");
//		return "create_lead";
//	}
	
	
	//http://localhost:8080/listall
	@RequestMapping("/listall")
	public String listAllLeads(Model model) {
		List<Lead> leads = leadService.getLeads();
		model.addAttribute("leads", leads);
		return "list_leads";
		
	}
	
	@RequestMapping("/delete")
	public String deleteOneLead(@RequestParam("id") long id, Model model) {
		leadService.deleteLead(id);
		List<Lead> leads = leadService.getLeads();
		model.addAttribute("leads", leads);
		return "list_leads";
	}
	
	@RequestMapping("/update")
	public String getOneLead(@RequestParam("id") long id, Model model) {
		Lead lead = leadService.getOneLead(id);
		model.addAttribute("lead", lead);
		return "update_lead";
	}
	
	@RequestMapping("/updatelead")
	public String updateLeadInfo(LeadData data, Model model) {
		Lead l = new Lead();
		l.setId(data.getId());
		l.setFirstname(data.getFirstname());
		l.setLastname(data.getLastname());
		l.setEmail(data.getEmail());
		l.setMobile(data.getMobile());
		
		leadService.saveLeadInfo(l);
		List<Lead> leads = leadService.getLeads();
		model.addAttribute("leads", leads);
		return "list_leads";
	}
}


