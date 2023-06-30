package searchclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GraphSearch {

    public static Action[][] search(State initialState, Frontier frontier)
    {
        boolean outputFixedSolution = false;

        if (outputFixedSolution) {
            //Part 1:
            //The agents will perform the sequence of actions returned by this method.
            //Try to solve a few levels by hand, enter the found solutions below, and run them:

            return new Action[][] {
                {Action.MoveS},
                {Action.MoveS},
                {Action.MoveE},
                {Action.MoveE},
                {Action.MoveE},
                {Action.MoveE},
                {Action.MoveE},
                {Action.MoveE},
                {Action.MoveE},
                {Action.MoveE},
                {Action.MoveE},
                {Action.MoveE},
                {Action.MoveS},
                {Action.MoveS}
            };
        } else {
            
            //Part 2:
            //Now try to implement the Graph-Search algorithm from R&N figure 3.7
            //In the case of "failure to find a solution" you should return null.
            //Some useful methods on the state class which you will need to use are:
            //state.isGoalState() - Returns true if the state is a goal state.
            //state.extractPlan() - Returns the Array of actions used to reach this state.
            //state.getExpandedStates() - Returns an ArrayList<State> containing the states reachable from the current state.
            //You should also take a look at Frontier.java to see which methods the Frontier interface exposes
            //
            //printSearchStates(explored, frontier): As you can see below, the code will print out status 
            //(#explored states, size of the frontier, #generated states, total time used) for every 10000th node generated.
            //You should also make sure to print out these stats when a solution has been found, so you can keep 
            //track of the exact total number of states generated!!

            int iterations = 0;

            // Initializes frontier using intial state of exercise
            frontier.add(initialState);
            HashSet<State> explored = new HashSet<>();

            while (true) {
                //Print a status message every 10000 iteration
                if (++iterations % 10000 == 0) {
                    printSearchStatus(explored, frontier);
                }

                //Your code here... Don't forget to print out the stats when a solution has been found (see above)
                // Loop here or maybe it is just the while (true) loops?

                // If frontier is empty then we have a failed search
                if (frontier.isEmpty()) {
                    return null;
                }
                
                // Otherwise, find a leaf node: lets say leafNode
                // I think we just pop from the frontier to get a leaf node?
                // pop it from the frontier (meaning we are now exploring it), and store its state
                State leafNode = frontier.pop();
                // if selected leaf node is == goal state then return solution
                if (leafNode.isGoalState()) {
                    printSearchStatus(explored, frontier);
                    return leafNode.extractPlan();
                }
                explored.add(leafNode);
                
                // Now we expand the selectedLeafNode, adding the resulting nodes to the frontier
                // adding only those that are not already in the frontier or in our explored nodes

                // Contains all the states reachable from our, now-explored, chosen leaf node
                ArrayList<State> reachableStates = leafNode.getExpandedStates();

                for (int i = 0; i < reachableStates.size(); i++) {
                    if (!frontier.contains(reachableStates.get(i)) && !explored.contains(reachableStates.get(i))){
                        frontier.add(reachableStates.get(i));
                    }
                }
            }
        }
    }

    private static long startTime = System.nanoTime();

    private static void printSearchStatus(HashSet<State> explored, Frontier frontier)
    {
        String statusTemplate = "#Expanded: %,8d, #Frontier: %,8d, #Generated: %,8d, Time: %3.3f s\n%s\n";
        double elapsedTime = (System.nanoTime() - startTime) / 1_000_000_000d;
        System.err.format(statusTemplate, explored.size(), frontier.size(), explored.size() + frontier.size(),
                          elapsedTime, Memory.stringRep());
    }
}
