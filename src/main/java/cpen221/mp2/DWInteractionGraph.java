package cpen221.mp2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DWInteractionGraph {

    /* ------- Task 1 ------- */
    /* Building the Constructors */

    private String fileName = new String();
    private StringBuilder interaction = new StringBuilder();
    private List<Integer> adjacencyMatrix[][];
    private HashSet<String> vertexSet = new HashSet<>();

    /**
     * Creates a new DWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    public DWInteractionGraph(String fileName) {
        this.fileName = fileName;
        File text = new File(fileName);

        if (text.length() != 0) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                for (String fileLine = reader.readLine(); fileLine != null;
                     fileLine = reader.readLine()) {
                    interaction.append(" ");
                    interaction.append(fileLine);
                }
                reader.close();
            } catch (FileNotFoundException oof) {
                System.out.println("Constructor failed!");
            } catch (IOException ioe) {
                System.out.println("IOE exception!");
            }

            String[] charArr =
                interaction.toString().trim().split(" "); //for debugging purposes only
            for (int i = 0; i < charArr.length; i++) {
                if ((i + 1) % 3 != 0) {
                    vertexSet.add(charArr[i]);
                }
            }

            int max = 0;
            for (String user : vertexSet) {
                if (Integer.parseInt(user) > max) {
                    max = Integer.parseInt(user);
                }
            }

            adjacencyMatrix = new ArrayList[max + 1][max + 1];

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    adjacencyMatrix[i][j] = new ArrayList<>();
                }
            }

            for (int i = 0; i < charArr.length; i += 3) {
                adjacencyMatrix[Integer.parseInt(charArr[i])][Integer.parseInt(charArr[i + 1])].add(
                    Integer.parseInt(charArr[i + 2]));
            }
        } else {
            int max = 0;
            for (String user : vertexSet) {
                if (Integer.parseInt(user) > max) {
                    max = Integer.parseInt(user);
                }
            }

            adjacencyMatrix = new ArrayList[max + 1][max + 1];

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    adjacencyMatrix[i][j] = new ArrayList<>();
                }
            }
        }
    }

    /**
     * Creates a new DWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName   the name of the file in the resources
     *                   directory containing email interactions
     * @param timeWindow the 2 element array that specifies the timeWindow of
     *                   email interactions supposed to be recorded
     */
    public DWInteractionGraph(String fileName, int[] timeWindow) {
        this.fileName = fileName;
        File text = new File(fileName);
        if (text.length() != 0) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                for (String fileLine = reader.readLine(); fileLine != null;
                     fileLine = reader.readLine()) {
                    interaction.append(" ");
                    interaction.append(fileLine);
                }
                reader.close();
            } catch (FileNotFoundException oof) {
                System.out.println("Constructor failed!");
            } catch (IOException ioe) {
                System.out.println("IOE exception!");
            }

            String[] charArr =
                interaction.toString().trim().split(" "); //for debugging purposes only
            for (int i = 0; i < interaction.toString().trim().split(" ").length; i += 3) {
                if (Integer.parseInt((charArr[i + 2])) <= timeWindow[1] &&
                    Integer.parseInt((charArr[i + 2])) >= timeWindow[0]) {
                    vertexSet.add(charArr[i]);
                    vertexSet.add(charArr[i + 1]);
                }
            }

            int max = 0;
            for (String user : vertexSet) {
                if (Integer.parseInt(user) > max) {
                    max = Integer.parseInt(user);
                }
            }

            adjacencyMatrix = new ArrayList[max + 1][max + 1];

            for (int i = 0; i < max + 1; i++) {
                for (int j = 0; j < max + 1; j++) {
                    adjacencyMatrix[i][j] = new ArrayList<>();
                }
            }
            for (int i = 0; i < charArr.length; i += 3) {
                if (Integer.parseInt(charArr[i + 2]) <= timeWindow[1] &&
                    Integer.parseInt(charArr[i + 2]) >= timeWindow[0]) {
                    adjacencyMatrix[Integer.parseInt(charArr[i])][Integer.parseInt(
                        charArr[i + 1])].add(Integer.parseInt(charArr[i + 2]));
                }
            }
        } else {
            int max = 0;
            for (String user : vertexSet) {
                if (Integer.parseInt(user) > max) {
                    max = Integer.parseInt(user);
                }
            }

            adjacencyMatrix = new ArrayList[max + 1][max + 1];

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    adjacencyMatrix[i][j] = new ArrayList<>();
                }
            }
        }
    }

    /**
     * Creates a new DWInteractionGraph from a DWInteractionGraph object
     * and considering a time window filter.
     *
     * @param inputDWIG  a DWInteractionGraph object
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created DWInteractionGraph
     *                   should only include those emails in the input
     *                   DWInteractionGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    public DWInteractionGraph(DWInteractionGraph inputDWIG, int[] timeFilter) {

        fileName = inputDWIG.helperGetFileName();
        int tempSize = inputDWIG.helperGetAdjMatx().length;
        List<Integer> temp[][] = new ArrayList[tempSize][tempSize]; //modify
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                temp[i][j] = new ArrayList<>();
                for (Integer time : inputDWIG.helperGetAdjMatx()[i][j]) {
                    if (time >= timeFilter[0] && time <= timeFilter[1]) {
                        temp[i][j].add(time);
                    }
                }
            }
        }

        this.adjacencyMatrix = temp;
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                for (Integer inty : temp[i][j]) {
                    if (inty >= timeFilter[0] && inty <= timeFilter[1]) {
                        vertexSet.add(String.valueOf(i));
                        vertexSet.add(String.valueOf(j));
                    }
                }
            }
        }
    }

    /**
     * Creates a new DWInteractionGraph from a DWInteractionGraph object
     * and considering a list of User IDs.
     *
     * @param inputDWIG  a DWInteractionGraph object
     * @param userFilter a List of User IDs. The created DWInteractionGraph
     *                   should exclude those emails in the input
     *                   DWInteractionGraph for which neither the sender
     *                   nor the receiver exist in userFilter.
     */
    public DWInteractionGraph(DWInteractionGraph inputDWIG, List<Integer> userFilter) {
        fileName = inputDWIG.helperGetFileName();
        int tempSize = inputDWIG.helperGetAdjMatx().length;
        List<Integer> temp[][] = new ArrayList[tempSize][tempSize];
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                if (userFilter.contains(i) || userFilter.contains(j)) {
                    temp[i][j] = inputDWIG.helperGetAdjMatx()[i][j];
                } else {
                    temp[i][j] = new ArrayList<>();
                }
            }
        }
        adjacencyMatrix = temp;
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                if (!temp[i][j].isEmpty()) {
                    vertexSet.add(String.valueOf(i));
                    vertexSet.add(String.valueOf(j));
                }
            }
        }

    }

    /**
     * @return a Set of Integers, where every element in the set is a User ID
     * in this DWInteractionGraph.
     */
    public Set<Integer> getUserIDs() {
        Set<Integer> UserIDs = new HashSet<>();
        for (String id : vertexSet) {
            Integer num = (Integer.parseInt(id));
            UserIDs.add(num);
            num = null;
        }
        return UserIDs;
    }

    /**
     * @param sender   the User ID of the sender in the email transaction.
     * @param receiver the User ID of the receiver in the email transaction.
     * @return the number of emails sent from the specified sender to the specified
     * receiver in this DWInteractionGraph.
     */
    public int getEmailCount(int sender, int receiver) {
        if (sender < adjacencyMatrix.length && receiver < adjacencyMatrix.length) {
            return adjacencyMatrix[sender][receiver].size();
        } else {
            return 0;
        }
    }

    /**
     * Helper Method:
     *
     * @return the fileName in String format
     */
    String helperGetFileName() {
        return fileName;
    }

    /**
     * Helper Method:
     *
     * @return a copy of the adjacencyMatrix
     */
    List<Integer>[][] helperGetAdjMatx() {
        List<Integer> temp[][] = new ArrayList[adjacencyMatrix.length][adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                temp[i][j] = new ArrayList<>();
                temp[i][j].addAll(adjacencyMatrix[i][j]);
            }
        }
        return adjacencyMatrix;
    }

    /**
     * Helper Method:
     *
     * @return the vertexSet
     */
    Set<String> getVertexSet() {
        return vertexSet;
    }

    /* ------- Task 2 ------- */

    /**
     * Given an int array, [t0, t1], reports email transaction details.
     * Suppose an email in this graph is sent at time t, then all emails
     * sent where t0 <= t <= t1 are included in this report.
     *
     * @param timeWindow is an int array of size 2 [t0, t1] where t0<=t1.
     * @return an int array of length 3, with the following structure:
     * [NumberOfSenders, NumberOfReceivers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
        DWInteractionGraph time = new DWInteractionGraph(this, timeWindow);
        int[] activity = new int[] {0, 0, 0};

        Set<Integer> user = time.getUserIDs();
        List<Integer> userList = user.stream().collect(Collectors.toList());
        List<Integer> senderList = new ArrayList<>(0);
        List<Integer> receiverList = new ArrayList<>(0);

        for (int i = 0; i < user.size(); i++) {
            for (int j = 0; j < user.size(); j++) {
                int current = time.getEmailCount(userList.get(i), userList.get(j));

                activity[2] += current;

                if (current != 0 && !senderList.contains(userList.get(i))) {
                    senderList.add(userList.get(i));
                }

                if (current != 0 && !receiverList.contains(userList.get(j))) {
                    receiverList.add(userList.get(j));
                }

                current = time.getEmailCount(userList.get(j), userList.get(i));

                if (current != 0 && !senderList.contains(userList.get(j))) {
                    senderList.add(userList.get(j));
                }

                if (current != 0 && !receiverList.contains(userList.get(i))) {
                    receiverList.add(userList.get(i));
                }

            }

        }

        activity[0] = senderList.size();
        activity[1] = receiverList.size();

        return activity;
    }

    /**
     * Given a User ID, reports the specified User's email transaction history.
     *
     * @param userID the User ID of the user for which the report will be
     *               created.
     * @return an int array of length 3 with the following structure:
     * [NumberOfEmailsSent, NumberOfEmailsReceived, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in this instance of a graph,
     * returns [0, 0, 0].
     */
    public int[] ReportOnUser(int userID) {
        if (!vertexSet.contains(String.valueOf(userID))) {
            return new int[] {0, 0, 0};
        }
        Set<Integer> userSet = this.getUserIDs();
        List<Integer> userList = userSet.stream().collect(Collectors.toList());
        int[] report = new int[] {0, 0, 0};
        boolean check = true;
        int k = 0;
        List<Integer> unique = new ArrayList<Integer>(0);

        for (int j : userList) {
            if (userID == j) {
                k = j;
                check = false;
            }

        }

        if (check) {
            return report;

        }

        for (int i = 0; i < userList.size(); i++) {
            int current = this.getEmailCount(k, userList.get(i));
            report[0] += current;


            if (!unique.contains(userList.get(i)) && current != 0) {
                unique.add(userList.get(i));
            }

            current = this.getEmailCount(userList.get(i), k);
            report[1] += current;


            if (!unique.contains(userList.get(i)) && current != 0) {
                unique.add(userList.get(i));
            }


        }


        report[2] = unique.size();
        return report;
    }

    /**
     * @param N               a positive number representing rank. N=1 means the most active.
     * @param interactionType Represent the type of interaction to calculate the rank for
     *                        Can be SendOrReceive.Send or SendOrReceive.RECEIVE
     * @return the User ID for the Nth most active user in specified interaction type.
     * Sorts User IDs by their number of sent or received emails first. In the case of a
     * tie, secondarily sorts the tied User IDs in ascending order.
     */
    public int NthMostActiveUser(int N, SendOrReceive interactionType) {
        int helperArr[] = new int[adjacencyMatrix.length];
        int sortedUsers[] = new int[adjacencyMatrix.length];
        int sum = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[0].length; j++) {
                if (interactionType.equals(SendOrReceive.SEND)) {
                    sum += adjacencyMatrix[i][j].size();
                } else {
                    sum += adjacencyMatrix[j][i].size();
                }
            }
            helperArr[i] = sum;
            sum = 0;
        }

        HashMap<Integer, Integer> sorter = new HashMap<>();
        for (int i = 0; i < helperArr.length; i++) {
            sorter.put(i, helperArr[i]);
        }

        int test = 0;
        for (Integer i : sorter.keySet()) {
            if (sorter.get(i) > 0) {
                test++;
            }
        }
        if (N > test) {
            return -1;
        }

        int best = -1;
        int bestInd = 0;
        int index = 0;
        while (!sorter.isEmpty()) {
            for (Integer i : sorter.keySet()) {
                if (sorter.get(i) > best) {
                    bestInd = i;
                    best = sorter.get(i);
                } else if (sorter.get(i) - bestInd == 0) {
                    if (i < bestInd) {
                        bestInd = i;
                        best = sorter.get(i);
                    }
                }
            }
            sortedUsers[index] = bestInd;
            index++;
            sorter.remove(bestInd);
            best = -1;
            bestInd = 0;
        }

        return sortedUsers[N - 1];
    }

    /* ------- Task 3 ------- */

    /* Helpers */

    /**
     * @param node a positive integer representing a node
     * @return a list of node's neighbouring nodes in ascending order
     */
    private List<Integer> getNeighbors(int node) {
        List<Integer> users = new ArrayList<>();

        for (int j = 0; j < adjacencyMatrix.length; j++) {
            if (!adjacencyMatrix[node][j].isEmpty()) {
                users.add(j);
            }
        }

        return users;
    }

    /**
     * @param node a positive integer representing a node
     * @return a list of node's neighbouring nodes in descending order
     */
    private List<Integer> getNeighbors2(int node) {
        List<Integer> users = new ArrayList<>();

        for (int j = adjacencyMatrix.length - 1; j > 0; j--) {
            if (!adjacencyMatrix[node][j].isEmpty()) {
                users.add(j);
            }
        }

        return users;
    }

    /**
     * performs breadth first search on the DWInteractionGraph object
     * to check path between user with userID1 and user with userID2.
     *
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, should return null.
     */
    public List<Integer> BFS(int userID1, int userID2) {

        if (!vertexSet.contains(String.valueOf(userID1)) ||
            !getVertexSet().contains(String.valueOf(userID2))) {
            return null;
        }

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> order = new ArrayList<>();

        visited.add(userID1);
        queue.add(userID1);
        order.add(userID1);
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            List<Integer> neighbours = getNeighbors(node);

            for (Integer n : neighbours) { //preserves ascending order
                if (n == userID2) {
                    order.add(userID2);
                    return order;
                }

                if (!visited.contains(n)) {
                    visited.add(n);
                    queue.add(n);
                    order.add(n);
                }
            }
        }
        return null;
    }

    /**
     * performs depth first search on the DWInteractionGraph object
     * to check path between user with userID1 and user with userID2.
     *
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, should return null.
     */
    public List<Integer> DFS(int userID1, int userID2) {

        if (!vertexSet.contains(String.valueOf(userID1)) ||
            !getVertexSet().contains(String.valueOf(userID2))) {
            return null;
        }

        List<Integer> order = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        stack.push(userID1);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (node == userID2) {
                order.add(userID2);
                return order;
            }
            if (!visited.contains(node)) {
                visited.add(node);
                order.add(node);
                for (Integer neighbour : getNeighbors2(
                    node)) { //getNeighbors2 gets neighbours in reverse order
                    stack.push(neighbour);
                }
            }
        }

        return order;

    }

    /* ------- Task 4 ------- */

    /**
     * Read the MP README file carefully to understand
     * what is required from this method.
     *
     * @param hours
     * @return the maximum number of users that can be polluted in N hours
     */
    public int MaxBreachedUserCount(int hours) {

        int seconds = hours * 3600;
        int node = 0;
        int maxInfected = 0;
        String[] charArr = interaction.toString().trim().split(" ");
        Set<String> interactionTime = new HashSet<>();

        interactionTime.add("0");

        for (int i = 0; i < charArr.length; i++) {
            if ((i + 1) % 3 == 0) {
                interactionTime.add(charArr[i]);
            }
        }

        for (String str : interactionTime) {
            for (String i : getVertexSet()) {
                node = Integer.parseInt(i);
                maxInfected =
                    Math.max(ModifiedBFS(node, seconds, interactionTime, Integer.parseInt(str)),
                        maxInfected);
            }
        }

        return maxInfected;
    }

    /**
     * Helper Method:
     *
     * @param user1           a node to be polluted
     * @param seconds         the seconds the virus has before firewall starts
     * @param interactionTime a set of edge times
     * @param offset          the starting time offset
     * @return the maximum number of users that can be polluted in N hours
     */
    private int ModifiedBFS(int user1, int seconds, Set<String> interactionTime, int offset) {

        Boolean[] infected = new Boolean[adjacencyMatrix.length];
        for (int i = 0; i < infected.length; i++) {
            infected[i] = false;
        }

        infected[user1] = true;
        for (int time = offset; time < offset + seconds; time++) {
            if (interactionTime.contains(String.valueOf(time))) {
                for (int x = 0; x < adjacencyMatrix.length; x++) {
                    if (infected[x]) {
                        for (int y = 0; y < adjacencyMatrix.length; y++) {
                            if (adjacencyMatrix[x][y].contains((time))) {
                                infected[y] = true;
                            }
                        }
                    }
                }
            }
        }
        int infectCount = 0;
        for (int i = 0; i < infected.length; i++) {
            if (infected[i]) {
                infectCount++;
            }
        }
        return infectCount;
    }

}