/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2015 by the members listed in the COPYING,        *
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

package org.matsim.contrib.dvrp.router;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.router.Dijkstra;
import org.matsim.core.router.util.DijkstraNodeData;
import org.matsim.core.router.util.TravelDisutility;
import org.matsim.core.router.util.TravelTime;
import org.matsim.core.utils.collections.PseudoRemovePriorityQueue;
import org.matsim.vehicles.Vehicle;

/**
 * Calculates full Dijkstra (to all nodes in the network).
 * To calculate shortest-path to a subset of nodes, consider {@link org.matsim.core.router.MultiNodeDijkstra}
 */
public class DijkstraTree extends Dijkstra {
	private Node fromNode;
	private double startTime;

	public DijkstraTree(Network network, TravelDisutility costFunction, final TravelTime timeFunction) {
		super(network, costFunction, timeFunction);
	}

	public void calcLeastCostPathTree(Node fromNode, double startTime) {
		this.fromNode = fromNode;
		this.startTime = startTime;

		augmentIterationId();
		PseudoRemovePriorityQueue<Node> pendingNodes = new PseudoRemovePriorityQueue<>(500);// TODO other options??
		initFromNode(fromNode, null, startTime, pendingNodes);

		while (!pendingNodes.isEmpty()) {
			relaxNode(pendingNodes.poll(), null, pendingNodes);
		}
	}

	private void initFromNode(final Node fromNode, final Node toNode, final double startTime, final PseudoRemovePriorityQueue<Node> pendingNodes) {
		DijkstraNodeData data = getData(fromNode);
		visitNode(fromNode, data, pendingNodes, startTime, 0, null);
	}

	@Override
	public Path calcLeastCostPath(Node fromNode, Node toNode, double startTime, Person person, Vehicle vehicle) {
		if (fromNode != this.fromNode || startTime != this.startTime || person != null || vehicle != null) {
			throw new IllegalArgumentException();
		}

		return getLeastCostPath(toNode);
	}

	public Path getLeastCostPath(Node toNode) {
		return constructPath(fromNode, toNode, startTime, getData(toNode).getTime());
	}

	public double getTime(Node toNode) {
		return getData(toNode).getTime();
	}

	public double getCost(Node toNode) {
		return getData(toNode).getCost();
	}
}
