/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.demo.service;

public interface DemoService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}
