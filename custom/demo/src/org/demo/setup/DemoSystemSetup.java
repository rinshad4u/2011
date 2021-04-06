/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.demo.setup;

import static org.demo.constants.DemoConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import org.demo.constants.DemoConstants;
import org.demo.service.DemoService;


@SystemSetup(extension = DemoConstants.EXTENSIONNAME)
public class DemoSystemSetup
{
	private final DemoService demoService;

	public DemoSystemSetup(final DemoService demoService)
	{
		this.demoService = demoService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		demoService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return DemoSystemSetup.class.getResourceAsStream("/demo/sap-hybris-platform.png");
	}
}
