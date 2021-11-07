package cpen221.mp2;

import java.io.*;
import java.util.*;

public class UDWInteractionGraph {

    private String fileName = new String();
    private StringBuilder interaction = new StringBuilder();
    private List<Integer> adjacencyMatrix[][];
    private HashSet<String> vertexSet = new HashSet<>();

    /* ------- Task 1 ------- */
    /* Building the Constructors */

    /**
     * Creates a new DWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName   the name of the file in the resources
     *                   directory containing email interactions
     * @param timeWindow the 2 element array that specifies the timeWindow of
     *                   email interactions supposed to be recorded
     */
    public UDWInteractionGraph(String fileName, int[] timeWindow) {
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
                interaction.toString().trim().split(" ");
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
     * Creates a new UDWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    public UDWInteractionGraph(String fileName) {
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
                    //adjacencyMatrix[i][j] = -1;
                    adjacencyMatrix[i][j] = new ArrayList<>();
                }
            }
        }
    }

    /**
     * Creates a new UDWInteractionGraph from a UDWInteractionGraph object
     * and considering a time window filter.
     *
     * @param inputUDWIG a UDWInteractionGraph object
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created UDWInteractionGraph
     *                   should only include those emails in the input
     *                   UDWInteractionGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    public UDWInteractionGraph(UDWInteractionGraph inputUDWIG, int[] timeFilter) {

        fileName = inputUDWIG.helperGetFileName();
        int tempSize = inputUDWIG.helperGetAdjMatx().length;
        List<Integer> temp[][] = new ArrayList[tempSize][tempSize]; //modify
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                temp[i][j] = new ArrayList<>();
                for (Integer time : inputUDWIG.helperGetAdjMatx()[i][j]) {
                    if (time >= timeFilter[0] && time <= timeFilter[1]) {
                        temp[i][j].add(time);
                    }
                }
            }
        }

        this.adjacencyMatrix = temp; //risky move
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                for (Integer inty : temp[i][j]) {
                    if (inty >= timeFilter[0] && inty <= timeFilter[1]) { //guaranteed anyways
                        vertexSet.add(String.valueOf(i));
                        vertexSet.add(String.valueOf(j));
                    }
                }
            }
        }
    }

    /**
     * Creates a new UDWInteractionGraph from a UDWInteractionGraph object
     * and considering a list of User IDs.
     *
     * @param inputUDWIG a UDWInteractionGraph object
     * @param userFilter a List of User IDs. The created UDWInteractionGraph
     *                   should exclude those emails in the input
     *                   UDWInteractionGraph for which neither the sender
     *                   nor the receiver exist in userFilter.
     */
    public UDWInteractionGraph(UDWInteractionGraph inputUDWIG, List<Integer> userFilter) {
        fileName = inputUDWIG.helperGetFileName();
        int tempSize = inputUDWIG.helperGetAdjMatx().length;
        List<Integer> temp[][] = new ArrayList[tempSize][tempSize];
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                if (userFilter.contains(i) || userFilter.contains(j)) {
                    temp[i][j] = inputUDWIG.helperGetAdjMatx()[i][j];
                } else {
                    temp[i][j] = new ArrayList();
                }
            }
        }
        adjacencyMatrix = temp;
        System.out.println("lmao");
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
     * Creates a new UDWInteractionGraph from a DWInteractionGraph object.
     *
     * @param inputDWIG a DWInteractionGraph object
     */
    public UDWInteractionGraph(DWInteractionGraph inputDWIG) {
        fileName = inputDWIG.helperGetFileName();
        int tempSize = inputDWIG.helperGetAdjMatx().length;
        //int[][] temp = new int[tempSize][tempSize];
        List<Integer> temp[][] = new ArrayList[tempSize][tempSize];
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                temp[i][j] = new ArrayList<>();
                temp[i][j] = inputDWIG.helperGetAdjMatx()[i][j];
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
     * in this UDWInteractionGraph.
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

    /* helper */

    /**
     * @param user1 the User ID of the first user.
     * @param user2 the User ID of the second user.
     * @return the number of email interactions (send/receive) between user1 and user2
     */
    public int getEmailCount(int user1, int user2) {
        if (!vertexSet.contains(String.valueOf(user1)) ||
            !vertexSet.contains(String.valueOf(user2))) {
            return 0;
        }
        return user1 == user2 ? adjacencyMatrix[user1][user2].size() :
            adjacencyMatrix[user1][user2].size() + adjacencyMatrix[user2][user1].size();
    }

    /**
     * Helper Method:
     *
     * @return the fileName in String format
     */
    private String helperGetFileName() {
        return fileName;
    }

    /**
     * Helper Method:
     *
     * @return a copy of the adjacencyMatrix
     */
    private List<Integer>[][] helperGetAdjMatx() {
        List<Integer> temp[][] = new ArrayList[adjacencyMatrix.length][adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                temp[i][j] = new ArrayList();
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
        return vertexSet; //make a copy
    }

    /* ------- Task 2 ------- */

    /**
     * @param timeWindow is an int array of size 2 [t0, t1]
     *                   where t0<=t1
     * @return an int array of length 2, with the following structure:
     * [NumberOfUsers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
        UDWInteractionGraph time = new UDWInteractionGraph(this, timeWindow);
        int[] activity = new int[2];

        Set<Integer> user = time.getUserIDs();
        List<Integer> userList = user.stream().toList();
        activity[0] = user.size();
        activity[1] = 0;

        for (int i = 0; i < user.size(); i++) {
            for (int j = i + 1; j < user.size(); j++) {
                activity[1] += time.getEmailCount(i, userList.get(j));
            }
        }

        return activity;
    }

    /**
     * @param userID the User ID of the user for which
     *               the report will be created
     * @return an int array of length 2 with the following structure:
     * [NumberOfEmails, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in this instance of a graph,
     * returns [0, 0].
     */
    public int[] ReportOnUser(int userID) {
        if (!vertexSet.contains(String.valueOf(userID))) {
            return new int[] {0, 0};
        }
        Set<Integer> userSet = this.getUserIDs();
        List<Integer> userList = userSet.stream().toList();
        int[] report = new int[] {0, 0};
        boolean check = true;
        int k = 0;

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
            report[0] += this.getEmailCount(k, userList.get(i));
            if (this.getEmailCount(k, i) > 0) {
                report[1]++;
            }
        }

        return report;
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @return the User ID for the Nth most active user
     */
    public int NthMostActiveUser(int N) {
        int helperArr[] = new int[adjacencyMatrix.length];
        int sortedUsers[] = new int[adjacencyMatrix.length];
        int sum = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[0].length; j++) {
                sum += adjacencyMatrix[i][j].size();
                sum += adjacencyMatrix[j][i].size();
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

    /**
     * Helper Method:
     *
     * @param node an int representing a node
     * @return a set of the neighbours of node
     */
    private Set<Integer> getNeighbors(int node) {
        Set<Integer> users = new HashSet<>();

        for (int j = 0; j < adjacencyMatrix.length; j++) {
            if (!adjacencyMatrix[node][j].isEmpty()) {
                users.add(j);
            }
        }
        for (int j = 0; j < adjacencyMatrix.length; j++) {
            if (!adjacencyMatrix[j][node].isEmpty()) {
                users.add(j);
            }
        }
        return users;
    }

    /**
     * @return the number of completely disjoint graph
     * components in the UDWInteractionGraph object.
     */
    public int NumberOfComponents() {
        int count = 0;
        Set<Integer> visited = new HashSet<>();
        for (String node : getVertexSet()) {
            int node2 = Integer.parseInt(node);
            if (!visited.contains(node2)) {
                DFSRecur(node2, visited);
                count++;
            }
        }
        return count;
    }

    /**
     * Helper method that does DFS recursively
     *
     * @param v       a starting node for DFS
     * @param visited a set of visited nodes
     */
    private void DFSRecur(int v, Set<Integer> visited) {
        visited.add(v);
        for (Integer i : getNeighbors(v)) {
            if (!visited.contains(i)) {
                DFSRecur(i, visited);
            }
        }
    }

    /**
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return whether a path exists between the two users
     */
    public boolean PathExists(int userID1, int userID2) {
        if (userID1 == userID2) {
            return true;
        }
        if (!vertexSet.contains(String.valueOf(userID1)) ||
            !getVertexSet().contains(String.valueOf(userID2))) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> order = new ArrayList<>();

        visited.add(userID1);
        queue.add(userID1);
        order.add(userID1);
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            Set<Integer> neighbours = getNeighbors(node);

            for (Integer n : neighbours) {
                if (n == userID2) {
                    order.add(userID2);
                    return true;
                }

                if (!visited.contains(n)) {
                    visited.add(n);
                    queue.add(n);
                    order.add(n);
                }
            }

        }
        return false;
    }
}