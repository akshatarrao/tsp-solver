package pl.edu.pbs.tsp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pbs.tsp.algorithm.particleswarm.Swap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Route implements Comparable<Route> {

    public double calculationTime;
    public Double totalCost;
    public ArrayList<Integer> citiesOrder;
    public static final Random rand = new Random();

    public static Route getNeighbor(Route r, double[][] costMatrix) throws Exception {
        ArrayList<Integer> order = r.getOrderDeepCopy();
        int index1 = 0, index2 = 0;
        while (index1 == index2) {
            index1 = rand.nextInt(order.size());
            index2 = rand.nextInt(order.size());
        }

        int temp = order.get(index2);
        order.set(index2, order.get(index1));
        order.set(index1, temp);

        Route route = new Route();
        route.setCitiesOrder(order);
        route.setTotalCost(calculateCost(order, costMatrix));
        return route;

    }

    protected static double calculateCost(List<Integer> route, double[][] costMatrix) {
        double cost = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            cost += costMatrix[route.get(i)][route.get(i + 1)];
        }
        return cost;
    }

    public ArrayList<Integer> getOrderDeepCopy() {
        return new ArrayList<>(this.citiesOrder);
    }

    public static ArrayList<Route> getNeighbors(Route r, int num_neighbors, double[][] costMatrix) throws Exception {
        ArrayList<Route> neighbors = new ArrayList<Route>();

        while (neighbors.size() < num_neighbors) {
            neighbors.add(getNeighbor(r, costMatrix));
        }

        return neighbors;
    }

    @Override
    public int compareTo(Route other) {

        return this.totalCost.compareTo(other.totalCost);

    }

    public Route deepCopy() throws Exception {
        Route route = new Route();
        route.setCitiesOrder(this.getOrderDeepCopy());
        route.setTotalCost(this.totalCost);
        return route;

    }

    public static ArrayList<Swap> swapsBetweenRoutes(Route r1, Route r2) {
        ArrayList<Swap> swaps = new ArrayList<Swap>();

        List<Integer> r1_order = r1.citiesOrder;
        List<Integer> r2_order = r2.getOrderDeepCopy();

        for (int i = 0; i < r1_order.size(); ++i) {
            int i1 = findIndexInArray(r1_order, i);
            int i2 = findIndexInArray(r2_order, i);
            if (i1 == i2)
                continue;
            else {
                swaps.add(new Swap(i1, i2));
                int temp = r1_order.get(i);
                r1_order.set(i1, r2_order.get(i2));
                r2_order.set(i2, temp);

            }
        }

        return swaps;
    }

    private static int findIndexInArray(List<Integer> arr, int target) {
        for (int i = 0; i < arr.size(); ++i) {
            if (arr.get(i) == target)
                return i;
        }
        return -1;
    }

    public Route applyVelocities(List<Swap> velocity, double[][] costMatrix) throws Exception {

        // create a deep copy of the order
        ArrayList<Integer> order_copy = new ArrayList<>(this.citiesOrder);
        // ArrayList<Integer> order_copy = this.getOrderDeepCopy();

        try {
            for (Swap swap : velocity) {
                int i1 = swap.getFirstIndex(), i2 = swap.getSecondIndex();

                Integer temp = order_copy.get(i1);
                order_copy.set(i1, order_copy.get(i2));
                order_copy.set(i2, temp);

            }
        } catch (Exception e) {
            System.out.println("order_copy length: " + order_copy.size());
            System.out.println("velocity length: " + velocity.size());
            // print order_copy
            for (int i = 0; i < order_copy.size(); ++i) {
                System.out.println("order_copy[" + i + "] = " + order_copy.get(i));
            }
            throw e;
        }

        Route route = new Route();
        route.setCitiesOrder(order_copy);
        route.setTotalCost(calculateCost(order_copy, costMatrix));
        return route;

    }

}
