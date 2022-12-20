package pl.edu.pbs.tsp.algorithm.Combined;

import java.util.ArrayList;
import java.util.List;
import pl.edu.pbs.tsp.Route;
import pl.edu.pbs.tsp.algorithm.SimulatedAnnealing;
import pl.edu.pbs.tsp.algorithm.ga.CrossoverType;
import pl.edu.pbs.tsp.algorithm.ga.GeneticAlgorithm;

import pl.edu.pbs.tsp.algorithm.ga.SelectionType;

public class CombinedAlgorithm1 extends GeneticAlgorithm {

	public CombinedAlgorithm1(int populationSize, int elitismSize, int epochs, int maxEpochsNoImprovement,
			double mutationRate, SelectionType selectionType, int tournamentSize, CrossoverType crossoverType) {
		super(populationSize, elitismSize, epochs, maxEpochsNoImprovement, mutationRate, selectionType, tournamentSize,
				crossoverType);
	}

	@Override
	protected List<List<Integer>> generatePopulation(int populationSize, int citiesNumber) {
		List<List<Integer>> initialPool = new ArrayList<>();
		SimulatedAnnealing sa = new SimulatedAnnealing(1000, 0.1, 0.99, 1000, 100);

		for (int i = 0; i < populationSize; ++i) {
			Route route = sa.solve(this.costMatrix);

			// update best route and cost
			if (route.getTotalCost() < this.minCost) {
				this.minCost = route.getTotalCost();
				this.bestRoute = route.getCitiesOrder();
			}
			initialPool.add(route.getCitiesOrder());
		}
		return initialPool;
	}
}
