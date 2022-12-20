package pl.edu.pbs.tsp.algorithm;

import java.util.ArrayList;
import java.util.Random;

import pl.edu.pbs.tsp.Route;

public class HillClimbing extends Algorithm {

    private double minCost = Double.MAX_VALUE;
    private ArrayList<Integer> bestRoute = new ArrayList<>();
    private static int num_iteration = 5000;
    private static int num_neighbors = 100;
    private static final Random rand = new Random();
    protected Route best = null;

    public void reset() {
        best = null;
    }

    public Route getBestRoute() {

        return this.best;
    }

    public int hillClimb(Route startRoute, int remainingIterations, double[][] costMatrix) throws Exception {
        ArrayList<Route> neighbors = null;
        Route currentRoute = startRoute;
        boolean improvedOverLastIteration = true;

        while (remainingIterations-- > 0) {
            if (!improvedOverLastIteration) {
                return remainingIterations;
            }

            improvedOverLastIteration = false;
            neighbors = Route.getNeighbors(currentRoute, num_neighbors, costMatrix);

            for (Route r : neighbors) {
                if (r.compareTo(currentRoute) > 0) {
                    currentRoute = r;
                    improvedOverLastIteration = true;
                }
            }

            if (best == null || currentRoute.compareTo(best) > 0) {

                best = currentRoute.deepCopy();
            }
        }

        return remainingIterations;
    }

    @Override
    protected Route solve(double[][] costMatrix) {

        reset();

        ArrayList<Integer> rr = (ArrayList<Integer>) generateRandomRoute(costMatrix.length);
        double cost = calculateCost(rr, costMatrix);

        Route randomRoute = new Route();
        randomRoute.setTotalCost(cost);
        randomRoute.setCitiesOrder(rr);

        int ite = num_iteration;
        try {
            while ((ite = hillClimb(randomRoute, ite, costMatrix)) > 0)
                ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return best;

    }

}
