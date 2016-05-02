package kernel_four;

import java.util.*;

public class Main {
    private static int executionTime = 30;
    private static int createNewGraph = 10;
    private static double gaussianMean = 30;
    private static double gaussianVariance = 10;
    private static int nodesToAdd = 100;
    private static int nodesToRemove = 5;
    private static Random rn = new Random();
    private static boolean timerFinished = false;
    private static boolean deletePermanent = false;

    private static double totalConnections;

    public static void main(String[] args) {
        // write your code here
        readArgs(args);
        Graph graph = new Graph();
        double sum;

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerFinished = true;
                System.out.println("Timer is finished");
                System.out.println("Total Connections: " + totalConnections);
                System.exit(0);
            }
        }, executionTime * 1000);

        final Timer permanentListTimer = new Timer();
        permanentListTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                deletePermanent = true;
                System.out.println("deleting permanent list");
            }
        }, new Date(), createNewGraph * 1000);

        //doing this until the timer goes off
        System.out.println("Starting Kernel");
        totalConnections = 0;
        double numSamples = 0;
        while(true){
            if(deletePermanent) {
                deletePermanent = false;
                graph = new Graph();
            }
            totalConnections += graph.performCalculation();
            ++numSamples;
            graph.addNodes(nodesToAdd, gaussianMean, gaussianVariance);
            Set<Node> nodes = graph.pickNodesToRemove(nodesToRemove);
            graph.removeNodes(nodes);
        }
    }

    private static void readArgs(String[] args){
        for (int i = 0; i < args.length; i+=2) {
            String key = args[i];
            double value = 0;
            try {
                value = Double.parseDouble(args[i+1]);
            }catch(NumberFormatException e){
                System.err.println("Argument " + args[i+1] + "must be a double");
            }
            switch (key) {
                case "--mean":
                    gaussianMean = value;
                    break;
                case "--variance":
                    gaussianVariance = value;
                    break;
                case "--addNodes":
                    nodesToAdd = (int)value;
                    break;
                case "--createNewGraph":
                    createNewGraph = (int)value;
                    break;
                case "--removeNodes":
                    nodesToRemove = (int)value;
                    break;
                case "--time":
                    executionTime = (int)value;
                    break;
                default:
                    System.err.println("unable to read argument");
                    System.exit(-1);
                    break;

            }
        }
    }
}
