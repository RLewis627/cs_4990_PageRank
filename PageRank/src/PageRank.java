
import java.util.Collections;
import java.util.List;

public class PageRank {

    public PageRank(List<SubRedditNode> list) {
        calculatePageRank(list);
    }

    public void calculatePageRank(List<SubRedditNode> list) {

        double initialRank = 1.0 / list.size();
		for (SubRedditNode subRedditNode : list) {
			subRedditNode.setPageRank(initialRank);
		}

        int iterations = 2;

        while (iterations >= 0) {

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getnumOfInLinks() != 0) {
                    float tempRank = 0;
                    for (int j = 0; j < list.get(i).getnumOfInLinks(); j++) {
                        String name = list.get(i).getFromName(j);
                        tempRank += calculate(list, name);
                    }
                    list.get(i).setPageRank(tempRank);
                }
            }
            iterations--;
        }

        Collections.sort(list);
    }


    private double calculate(List<SubRedditNode> list, String name) {
        for (SubRedditNode subRedditNode : list) {
            if (name.equals(subRedditNode.getsubredditName())) {
                int in = subRedditNode.getnumOfInLinks();
                int out = subRedditNode.getNumOutLinks();
                return (double) in / out;
            }
        }
        return -1;
    }

    public void printRanks(List<SubRedditNode> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.size() - i + " " + list.get(i).getsubredditName());
        }
    }
}
