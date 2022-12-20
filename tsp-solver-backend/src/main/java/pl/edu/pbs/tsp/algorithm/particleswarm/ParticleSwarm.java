package pl.edu.pbs.tsp.algorithm.particleswarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.edu.pbs.tsp.City;
import pl.edu.pbs.tsp.Route;
// import Algorithm
import pl.edu.pbs.tsp.algorithm.Algorithm;
import pl.edu.pbs.tsp.algorithm.HillClimbing;

public class ParticleSwarm extends Algorithm {

    private static final int NUM_PARTICLE = 100; // number of particles
    private static final int num_iteration = 5000;

    private Route bestRoute;
    private ArrayList<Particle> particles;

    @Override
    protected Route solve(double[][] costMatrix) {

        this.bestRoute = null;

        this.particles = new ArrayList<Particle>();
        try {
            for (int i = 0; i < NUM_PARTICLE; ++i) {

                Particle p = new Particle();
                p.costMatrix = costMatrix;
                HillClimbing hc = new HillClimbing();
                ArrayList<Integer> randoRoute = (ArrayList<Integer>) generateRandomRoute(costMatrix.length);
                Route randRoute = new Route();
                randRoute.setCitiesOrder(randoRoute);
                randRoute.setTotalCost(calculateCost(randoRoute, costMatrix));
                hc.hillClimb(randRoute, 5000, costMatrix);
                p.route = hc.getBestRoute();
                p.pbest = p.route;
                p.initializeVelocity();
                this.particles.add(p);

                if (this.bestRoute == null || this.particles.get(i).getRoute().compareTo(bestRoute) > 0) {
                    this.bestRoute = this.particles.get(i).getRoute().deepCopy();
                }

            }

            for (int counter = 0; counter < num_iteration; ++counter) {
                for (int i = 0; i < NUM_PARTICLE; ++i) {
                    this.particles.get(i).updateVelocity(this.bestRoute);
                    this.particles.get(i).updatePosition(costMatrix);
                }

                for (int i = 0; i < NUM_PARTICLE; ++i) {
                    if (this.particles.get(i).getPBest().compareTo(bestRoute) > 0) {
                        this.bestRoute = this.particles.get(i).getPBest().deepCopy();
                    }
                }
            }

        } catch (

        Exception e) {
            e.printStackTrace();
        }

        return this.bestRoute;
    }

}
