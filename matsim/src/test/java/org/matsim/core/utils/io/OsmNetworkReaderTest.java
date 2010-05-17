/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2010 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.core.utils.io;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.ScenarioImpl;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.algorithms.NetworkCleaner;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.testcases.MatsimTestUtils;
import org.xml.sax.SAXException;

/**
 * @author mrieser
 */
public class OsmNetworkReaderTest {

	@Rule public MatsimTestUtils utils = new MatsimTestUtils();

	@Test
	public void testConversion() throws SAXException, ParserConfigurationException, IOException {
		String filename = this.utils.getClassInputDirectory() + "adliswil.osm.gz";

		Scenario sc = new ScenarioImpl();
		Network net = sc.getNetwork();

		CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, TransformationFactory.CH1903_LV03);

		new OsmNetworkReader(net,ct).parse(filename);

		Assert.assertEquals("number of nodes is wrong.", 399, net.getNodes().size());
		Assert.assertEquals("number of links is wrong.", 874, net.getLinks().size());

		new NetworkCleaner().run(net);
		Assert.assertEquals("number of nodes is wrong.", 344, net.getNodes().size());
		Assert.assertEquals("number of links is wrong.", 794, net.getLinks().size());
	}

	@Test
	public void testConversionWithDetails() throws SAXException, ParserConfigurationException, IOException {
		String filename = this.utils.getClassInputDirectory() + "adliswil.osm.gz";

		Scenario sc = new ScenarioImpl();
		Network net = sc.getNetwork();

		CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, TransformationFactory.CH1903_LV03);

		OsmNetworkReader reader = new OsmNetworkReader(net,ct);
		reader.setKeepPaths(true);
		reader.parse(filename);

		Assert.assertEquals("number of nodes is wrong.", 1844, net.getNodes().size());
		Assert.assertEquals("number of links is wrong.", 3537, net.getLinks().size());

		new NetworkCleaner().run(net);
		Assert.assertEquals("number of nodes is wrong.", 1561, net.getNodes().size());
		Assert.assertEquals("number of links is wrong.", 3168, net.getLinks().size());
	}

	@Test
	public void testConversionWithSettings() throws SAXException, ParserConfigurationException, IOException {
		String filename = this.utils.getClassInputDirectory() + "adliswil.osm.gz";

		Scenario sc = new ScenarioImpl();
		Network net = sc.getNetwork();

		CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, TransformationFactory.CH1903_LV03);

		OsmNetworkReader reader = new OsmNetworkReader(net,ct);
		reader.setHierarchyLayer(47.4, 8.5, 47.2, 8.6, 5);
		reader.parse(filename);

		Assert.assertEquals("number of nodes is wrong.", 67, net.getNodes().size());
		Assert.assertEquals("number of links is wrong.", 122, net.getLinks().size());
		new NetworkCleaner().run(net);
		Assert.assertEquals("number of nodes is wrong.", 57, net.getNodes().size());
		Assert.assertEquals("number of links is wrong.", 114, net.getLinks().size());
	}

	@Test
	public void testConversionWithSettingsAndDetails() throws SAXException, ParserConfigurationException, IOException {
		String filename = this.utils.getClassInputDirectory() + "adliswil.osm.gz";

		Scenario sc = new ScenarioImpl();
		Network net = sc.getNetwork();

		CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, TransformationFactory.CH1903_LV03);

		OsmNetworkReader reader = new OsmNetworkReader(net,ct);
		reader.setKeepPaths(true);
		reader.setHierarchyLayer(47.4, 8.5, 47.2, 8.6, 5);
		reader.parse(filename);

		Assert.assertEquals("number of nodes is wrong.", 769, net.getNodes().size());
		Assert.assertEquals("number of links is wrong.", 1016, net.getLinks().size());
		new NetworkCleaner().run(net);
		Assert.assertEquals("number of nodes is wrong.", 441, net.getNodes().size());
		Assert.assertEquals("number of links is wrong.", 841, net.getLinks().size());
	}
}
