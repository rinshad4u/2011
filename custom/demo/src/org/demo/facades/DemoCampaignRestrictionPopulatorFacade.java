/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.demo.facades;

import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.util.localization.Localization;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.hybris.ymkt.segmentation.dto.SAPInitiative;
import com.hybris.ymkt.segmentation.services.InitiativeService;
import com.hybris.ymkt.segmentation.services.InitiativeService.InitiativeQuery;
import com.hybris.ymkt.segmentation.services.InitiativeService.InitiativeQuery.TileFilterCategory;


/**
 * Facade that provides functionality to retrieve campaigns from back end
 */
public class DemoCampaignRestrictionPopulatorFacade
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoCampaignRestrictionPopulatorFacade.class);
	public static final String DEFAULT_VALUE = "2";
	public static final String DEMO_KEY = "demo.key";

	protected InitiativeService initiativeService;

	@Resource
	private ConfigurationService configurationService;

	/**
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	/**
	 * @param configurationService
	 *           the configurationService to set
	 */
	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

	protected OptionData createOptionData(final SAPInitiative initiative)
	{
		final OptionData opData = new OptionData();
		opData.setId(initiative.getId());
		opData.setLabel(initiative.getFormattedLabel());
		return opData;
	}

	/**
	 * Get and return one campaign
	 *
	 * @param campaignId
	 *           The campaignId string
	 * @return campaign {@link OptionData} (dropdown value) with label "campaignId + campaignName + (memberCount)" and
	 *         value "campaignId".
	 */
	public OptionData getCampaignById(final String campaignId)
	{
		try
		{


			LOGGER.error("Inside getCampaignById");
			final String option = configurationService.getConfiguration().getString(DEMO_KEY, DEFAULT_VALUE);
			InitiativeQuery query = null;

			LOGGER.error("option  " + option);
			switch (option)
			{
				case "0":
					query = new InitiativeQuery.Builder() //
							.setId(campaignId) //
							.build();
					LOGGER.error("option 0 -----------" + option);
					break;
				case "1":
					query = new InitiativeQuery.Builder() //
							.setTileFilterCategories(TileFilterCategory.ACTIVE) //
							.setId(campaignId) //
							.build();
					LOGGER.error("option 1 -----------" + option);
					break;
				case "2":
					query = new InitiativeQuery.Builder() //
							.setTileFilterCategories(TileFilterCategory.ACTIVE, TileFilterCategory.PLANNED) //
							.setId(campaignId) //
							.build();
					LOGGER.error("option 2 -----------" + option);
					break;
				default:
					query = new InitiativeQuery.Builder() //
							.setTileFilterCategories(TileFilterCategory.ACTIVE, TileFilterCategory.PLANNED) //
							.setId(campaignId) //
							.build();
					LOGGER.error("option 3 -----------" + option);
			}




			final List<OptionData> temp = initiativeService.getInitiatives(query).stream() //
					.map(this::createOptionData) //
					.collect(Collectors.toList());

			if (temp.isEmpty())
			{
				throw new UnknownIdentifierException(Localization.getLocalizedString("segmentation.campaignIdError.description"));
			}

			return temp.get(0);
		}
		catch (final IOException e)
		{
			LOGGER.error("Error retrieving Campaign Initiative", e);
			return null;
		}
	}

	/**
	 * Get and return campaigns from backend
	 *
	 * @param searchTerm
	 *           value entered by user
	 * @param top
	 *           number of campaigns to show per page
	 * @param skip
	 *           number of campaigns to skip
	 * @return campaigns
	 */
	public List<OptionData> getCampaigns(final String searchTerm, final String skip, final String top)
	{
		final String actualSkip = Integer.toString(Integer.parseInt(skip) * Integer.parseInt(top));

		try
		{


			LOGGER.error("Inside getCampaigns");
			final String option = configurationService.getConfiguration().getString(DEMO_KEY, DEFAULT_VALUE);


			InitiativeQuery query = null;
			LOGGER.error("option  " + option);

			switch (option)
			{
				case "0":
					query = new InitiativeQuery.Builder() //
							.setSearchTerms(searchTerm) //
							.build();
					LOGGER.error("option  -----------" + option);
					break;
				case "1":
					query = new InitiativeQuery.Builder() //
							.setTileFilterCategories(TileFilterCategory.ACTIVE) //
							.setSearchTerms(searchTerm) //
							.build();
					LOGGER.error("option 1 -----------" + option);
					break;
				case "2":
					query = new InitiativeQuery.Builder() //
							.setTileFilterCategories(TileFilterCategory.ACTIVE, TileFilterCategory.PLANNED) //
							.setSearchTerms(searchTerm) //
							.build();
					LOGGER.error("option 2 -----------" + option);
					break;
				default:
					query = new InitiativeQuery.Builder() //
							.setTileFilterCategories(TileFilterCategory.ACTIVE, TileFilterCategory.PLANNED) //
							.setSearchTerms(searchTerm) //
							.build();
					LOGGER.error("option 3 -----------" + option);
			}



			return this.initiativeService.getInitiatives(query, actualSkip, top).stream() //
					.map(this::createOptionData) //
					.collect(Collectors.toList());
		}
		catch (final IOException e)
		{
			LOGGER.error("Error retrieving Campaign Initiatives", e);
			return Collections.emptyList();
		}

	}

	@Required
	public void setInitiativeService(final InitiativeService initiativeService)
	{
		this.initiativeService = initiativeService;
	}
}
