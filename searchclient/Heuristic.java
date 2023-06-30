package searchclient;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public abstract class Heuristic
        implements Comparator<State>
{
    int heuristicInt;

    public Heuristic(State initialState)
    {
        // heuristicInt = 0;
    }


    public int h(State s)
    {
        return goalCount(s);
    }

    public int goalCount(State s)
    {
        heuristicInt = 0;
        for (int row = 1; row < s.goals.length - 1; row++)
        {
            for (int col = 1; col < s.goals[row].length - 1; col++)
            {
                char goal = s.goals[row][col];

                if (('A' <= goal && goal <= 'Z' && s.boxes[row][col] != goal) || ('0' <= goal && goal <= '9' && !(s.agentRows[goal - '0'] == row && s.agentCols[goal - '0'] == col)))
                {
                    heuristicInt++;
                }
            }
        }
        return this.heuristicInt;
    }


    public int improvedManDistHeuristic(State s)
    {
        heuristicInt = 0;
        List<Integer> boxRows = new ArrayList<Integer>();
        List<Integer> boxCols = new ArrayList<Integer>();
        List<Character> boxChars = new ArrayList<Character>();
        List<Integer> goalRows = new ArrayList<Integer>();
        List<Integer> goalCols = new ArrayList<Integer>();
        List<Character> goalChars = new ArrayList<Character>();

        for (int row = 1; row < s.boxes.length - 1; row++)
        {
            for (int col = 1; col < s.boxes[row].length - 1; col++)
            {
                char currentCellValueBox = s.boxes[row][col];
                char currentCellValueGoal = s.goals[row][col];

                // This is checking if the current cell we are at is a box
                if ('A' <= currentCellValueBox && currentCellValueBox <= 'Z')
                {
                    // If its a box we take its location and box letter
                    boxRows.add(row);
                    boxCols.add(col);
                    boxChars.add(currentCellValueBox);
                }
                // This is checking if the current cell we are at is a goal
                if('A' <= currentCellValueGoal && currentCellValueGoal <= 'Z')
                {
                    // IF its a goal we take the location and letter
                    goalRows.add(row);
                    goalCols.add(col);
                    goalChars.add(currentCellValueGoal);
                }
            }
        }

        for (int index = 0; index < goalChars.size(); index++)
        {
            char currentGoalChar = goalChars.get(index);
            int boxIndex = boxChars.indexOf(currentGoalChar);
            heuristicInt += Math.abs(boxRows.get(boxIndex) - goalRows.get(index)) + Math.abs(boxCols.get(boxIndex) - goalCols.get(index));
        }

        return this.heuristicInt;
    }

    public int manhattanDistance(State s)
    {
        heuristicInt = 0;
        for (int row = 1; row < s.goals.length - 1; row++)
        {
            for (int col = 1; col < s.goals[row].length - 1; col++)
            {
                char goal = s.goals[row][col];

                if (('A' <= goal && goal <= 'Z' && s.boxes[row][col] != goal) || ('0' <= goal && goal <= '9' && !(s.agentRows[goal - '0'] == row && s.agentCols[goal - '0'] == col)))
                {
                    heuristicInt += Math.abs(s.agentRows[goal - '0'] - row) + Math.abs(s.agentCols[goal - '0'] - col);
                }
            }
        }
        return this.heuristicInt;
    }

    public abstract int f(State s);

    @Override
    public int compare(State s1, State s2)
    {
        return this.f(s1) - this.f(s2);
    }
}

class HeuristicAStar
        extends Heuristic
{
    public HeuristicAStar(State initialState)
    {
        super(initialState);
    }

    @Override
    public int f(State s)
    {
        return s.g() + this.manhattanDistance(s);
        // return s.g() + this.h(s);
    }

    @Override
    public String toString()
    {
        return "A* evaluation";
    }
}

class HeuristicWeightedAStar
        extends Heuristic
{
    private int w;

    public HeuristicWeightedAStar(State initialState, int w)
    {
        super(initialState);
        this.w = w;
    }

    @Override
    public int f(State s)
    {
        return s.g() + this.w * this.h(s);
    }

    @Override
    public String toString()
    {
        return String.format("WA*(%d) evaluation", this.w);
    }
}

class HeuristicGreedy
        extends Heuristic
{
    public HeuristicGreedy(State initialState)
    {
        super(initialState);
    }

    @Override
    public int h(State s)
    {
        return goalCount(s);
        // return manhattanDistance(s);
        // return improvedManDistHeuristic(s);
    }

    @Override
    public int f(State s)
    {
        return this.h(s);
    }

    @Override
    public String toString()
    {
        return "greedy evaluation";
    }
}
