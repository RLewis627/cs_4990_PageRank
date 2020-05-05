
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class PageRank {

    private ArrayList<SubRedditNode> lastIterationRank;
    private ArrayList<SubRedditNode> currentIterationRank;

    public PageRank(List<SubRedditNode> list) {
        calculatePageRank(list);
    }

    public void calculatePageRank(List<SubRedditNode> list) {

        lastIterationRank = new ArrayList<>();
        currentIterationRank = new ArrayList<>();

        double initialRank = 1.0 / list.size();

        for (int i = 0; i < list.size(); i++) {
            lastIterationRank.add(new SubRedditNode(list.get(i).getsubredditName(), initialRank, list.get(i).getNumOutLinks()));
		    currentIterationRank.add(new SubRedditNode(list.get(i).getsubredditName(), initialRank, list.get(i).getNumOutLinks()));
        }

        int iterations = 10;

        while (iterations > 0) {

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getnumOfInLinks() != 0) {
                    double tempRank = 0;
                    for (int j = 0; j < list.get(i).getnumOfInLinks(); j++) {
                        String name = list.get(i).getFromName(j);
                        tempRank += calculate(name);
                    }
                    currentIterationRank.get(i).setPageRank(tempRank);
                }
            }
            if (iterations != 1) {  // lastIterationRank does not need to be updated on the last iteration
                updateLastIterationRanks();
            }
            iterations--;
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPageRank(currentIterationRank.get(i).getPageRank());
        }

        Collections.sort(list);
    }

    private double calculate(String name) {
        for (SubRedditNode subRedditNode : lastIterationRank) {
            if (name.equals(subRedditNode.getsubredditName())) {
                double pR = subRedditNode.getPageRank();
                int out = subRedditNode.getNumOutLinks();
                return pR / out;
            }
        }
        return -1;
    }

    private void updateLastIterationRanks() {
        for (int i = 0; i < lastIterationRank.size(); i++) {
            lastIterationRank.get(i).setPageRank(currentIterationRank.get(i).getPageRank());
        }
    }

    public void printRanks(List<SubRedditNode> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.size() - i + " " + list.get(i).getsubredditName() + ": " + list.get(i).getPageRank());
        }
    }
}
