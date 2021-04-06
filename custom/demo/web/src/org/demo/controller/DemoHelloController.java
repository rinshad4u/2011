/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.demo.controller;

import static org.demo.constants.DemoConstants.PLATFORM_LOGO_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.demo.service.DemoService;


@Controller
public class DemoHelloController
{
	@Autowired
	private DemoService demoService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model)
	{
		model.addAttribute("logoUrl", demoService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		return "welcome";
	}
}
