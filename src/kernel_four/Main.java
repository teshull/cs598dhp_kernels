package kernel_four;

import java.util.*;

public class Main {
    private static int executionTime = 6;
    private static double gaussianMean = 100;
    private static double gaussianVariance = 10;
    private static Random rn = new Random();
    private static boolean timerFinished = false;

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
                System.exit(0);
            }
        }, executionTime * 1000);

        //doing this until the timer goes off
        System.out.println("Starting Kernel");
        double totalConnections = 0;
        double numSamples = 0;
        while(true){
            //TODO maybe improve the computation
            totalConnections +=  graph.performCalculation();
            ++numSamples;
            graph.addNodes(400, gaussianMean, gaussianVariance);
            Set<Node> nodes = graph.pickNodesToRemove(4);
            graph.removeNodes(nodes);
        }
    }

    private static void readArgs(String[] args){
        for (int i = 0; i < args.length; i++) {
            double value = 0;
            try {
                value = Double.parseDouble(args[i]);
            }catch(NumberFormatException e){
                System.err.println("Argument " + args[i] + "must be an integer");
            }
            switch (i) {
                case 0:
                    gaussianMean = value;
                    break;
                case 1:
                    gaussianVariance = value;
                    break;
                case 2:
                    executionTime = (int) value;
                    break;
                default:
                    System.err.println("too many arguments");
                    System.exit(-1);
                    break;

            }
        }
    }
}
