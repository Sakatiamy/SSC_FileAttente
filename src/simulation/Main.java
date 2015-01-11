/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

/**
 *
 * @author Samy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Simulation mM1Simulation = new MM1Simulation(0.8, 1.0);
        Simulation mD1Simulation = new MD1Simulation(0.8, 1.0);
        mM1Simulation.simulate(100000);
        mD1Simulation.simulate(100000);
    }
    
}
