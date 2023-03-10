package pl.edu.pbs.tsp.algorithm;

import pl.edu.pbs.tsp.Route;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighbourAlgorithm extends Algorithm {

    @Override
    protected Route solve(double[][] costMatrix) {
        double totalCost = 0;
        int lastVisitedCity = 0;
        ArrayList<Integer> citiesVisited = new ArrayList<>();
        citiesVisited.add(lastVisitedCity);

        for (int i = 1; i < costMatrix.length; i++) {
            double[] row = costMatrix[lastVisitedCity];

            int indexOfMinCostForUnvisitedCities = indexOfMinCostForUnvisitedCities(row, citiesVisited);
            lastVisitedCity = indexOfMinCostForUnvisitedCities;
            citiesVisited.add(indexOfMinCostForUnvisitedCities);
            totalCost += row[indexOfMinCostForUnvisitedCities];
        }

        // Adding road from last city to first city
        double[] lastCityRow = costMatrix[lastVisitedCity];
        citiesVisited.add(0);
        totalCost += lastCityRow[0];

        Route route = new Route();
        route.setTotalCost(totalCost);
        route.setCitiesOrder(citiesVisited);
        return route;
    }

    private static int indexOfMinCostForUnvisitedCities(double[] array, List<Integer> citiesVisited) {
        if (array.length == 0) {
            return -1;
        }

        int index = -1;
        double min = Double.MAX_VALUE;

        for (int i = 0; i < array.length; i++) {
            if (array[i] < min && !citiesVisited.contains(i)) {
                min = array[i];
                index = i;
            }
        }
        return index;
    }

}
