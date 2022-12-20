package pl.edu.pbs.tsp.algorithm.particleswarm;

import java.util.ArrayList;
import java.util.Random;

import pl.edu.pbs.tsp.City;
import pl.edu.pbs.tsp.Route;

public class Particle {

    public double[][] costMatrix;
    public Route route;
    public Route pbest;
    private ArrayList<Swap> velocity;
    private static final Random rand = new Random();
    private static final int NUM_INITIAL_VELOCITY = 2;
    private static final double alpha = 0.85;
    private static final double beta = 0.85;
    private static final double random_factor = 0.1;

    public void initializeVelocity() {
        this.velocity = new ArrayList<Swap>();

        for (int i = 0; i < NUM_INITIAL_VELOCITY; ++i) {
            int index1 = 0, index2 = 0;
            do {
                index1 = rand.nextInt(this.costMatrix.length);
                index2 = rand.nextInt(this.costMatrix.length);
            } while (index1 == index2);

            this.velocity.add(new Swap(index1, index2));
        }
    }

    public void updateVelocity(Route gbest) {
        ArrayList<Swap> pbest_diff = Route.swapsBetweenRoutes(this.route, this.pbest);
        ArrayList<Swap> gbest_diff = Route.swapsBetweenRoutes(this.route, gbest);

        if (rand.nextDouble() < random_factor) {
            int index1 = 0, index2 = 0;
            do {
                index1 = rand.nextInt(this.costMatrix.length);
                index2 = rand.nextInt(this.costMatrix.length);
            } while (index1 == index2);

            this.velocity.add(new Swap(index1, index2));
        }

        for (Swap newSwap : pbest_diff) {
            if (rand.nextDouble() < alpha)
                this.velocity.add(newSwap);
        }

        for (Swap newSwap : gbest_diff) {
            if (rand.nextDouble() < beta)
                this.velocity.add(newSwap);
        }
    }

    public void updatePosition(double[][] costMatrix) throws Exception {
        this.route = this.route.applyVelocities(this.velocity, costMatrix);

        if (this.route.compareTo(this.pbest) > 0)
            this.pbest = this.route;

        this.velocity.clear();
    }

    public Route getRoute() {
        return this.route;
    }

    public Route getPBest() {
        return this.pbest;
    }

}
