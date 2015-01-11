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
public class MD1Simulation extends Simulation {

    public MD1Simulation(double lambda, double mu) {
        super(lambda, mu);
    }

    @Override
    public void simulate(double simLength) {
        double beginTime = System.currentTimeMillis();
        double time = 0;
        Event s1 = new Event(0, expo(this.lambda));
        this.liste.addEvent(s1);
        while (time < simLength) {
            Event next_Event = (Event) this.liste.events.get(0);
            this.liste.events.remove(0);
            time = next_Event.getTime();
            if (next_Event.getType() == 0) {
                this.liste.addEvent(new Event(0, time + expo(this.lambda)));
                double serviceTime = 1/this.mu; //pour la file M/D/1 on remplace expo(this.mu) par 1/this.mu                
                this.q.lesClients.addElement(new Client(time, serviceTime));
                if (this.q.lesClients.size() == 1) {
                    this.liste.addEvent(new Event(1, time + serviceTime));
                }
            } else {
                Client c = (Client) this.q.lesClients.get(0);
                this.q.lesClients.remove(0);
                this.nombre_Clients += 1;
                this.compteurTempsAttente += time - c.getArrivalTime();
                this.sommeCarreTempsAttente += (time - c.getArrivalTime()) * (time - c.getArrivalTime());
                this.compteurTempsService += time - c.getArrivalTime() - c.getServiceTime();
                this.sommeCarreTempsService += (time - c.getArrivalTime() - c.getServiceTime()) * (time - c.getArrivalTime() - c.getServiceTime());
                if (this.q.lesClients.size() > 0) {
                    Client client = (Client) this.q.lesClients.get(0);
                    double serviceTime = client.getServiceTime();
                    this.liste.addEvent(new Event(1, time + serviceTime));
                }
            }
        }
        System.out.println("\n===============Rapport de la simulation MD1=================");
        System.out.println("Temps de calcul: " + (System.currentTimeMillis() - beginTime) * 0.001 + " secondes");
        System.out.println("Temps d'attente moyen: " + this.getTempsAttenteMoyen());
        System.out.println("Variance du temps d'attente: " + this.getVarianceTempsAttente());
        System.out.println("Temps de service moyen: " + this.getTempsServiceMoyen());
        System.out.println("Variance du temps de service: " + this.getVarianceTempsService());
        System.out.println("Nombre de clients servis pendant toute la simulation: " + this.getNombreTotalClients());
    }
}
