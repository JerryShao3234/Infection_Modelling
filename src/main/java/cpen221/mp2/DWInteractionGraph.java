package cpen221.mp2;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DWInteractionGraph {

    /* ------- Task 1 ------- */
    /* Building the Constructors */

    private String fileName = new String();
    private StringBuilder interaction = new StringBuilder();
    private Set<Integer> adjacencyMatrix[][];
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
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            for (String fileLine = reader.readLine(); fileLine != null; fileLine = reader.readLine()) {
                interaction.append(" ");
                interaction.append(fileLine);
            }
            reader.close();
        }
        catch (FileNotFoundException oof){
            System.out.println("Constructor failed!");
        }
        catch (IOException ioe){
            System.out.println("IOE exception!");
        }

        String[] charArr = interaction.toString().trim().split(" "); //for debugging purposes only
        for(int i=0; i<charArr.length; i++){
            if((i+1)%3 != 0){
                vertexSet.add(charArr[i]);
            }
        }

        int max = 0;
        for(String user : vertexSet){
            if(Integer.parseInt(user) > max){
                max = Integer.parseInt(user);
            }
        }

        //adjacencyMatrix = new int[max+1][max+1];
        adjacencyMatrix = new Set[max+1][max+1];


        for(int i=0; i<adjacencyMatrix.length; i++){
            for(int j=0; j<adjacencyMatrix.length; j++){
                //adjacencyMatrix[i][j] = -1;
                adjacencyMatrix[i][j] = new HashSet<>();
            }
        }

        for(int i=0; i<charArr.length; i+=3){
            //adjacencyMatrix[Integer.parseInt(charArr[i])][Integer.parseInt(charArr[i+1])] = Integer.parseInt(charArr[i+2]);
            adjacencyMatrix[Integer.parseInt(charArr[i])][Integer.parseInt(charArr[i+1])].add(Integer.parseInt(charArr[i+2]));
        }


        System.out.println("hi");

    }

    /**
     * Creates a new DWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     * @param timeWindow the 2 element array that specifies the timeWindow of
     *                 email interactions supposed to be recorded
     */
    public DWInteractionGraph(String fileName, int[] timeWindow) {
        this.fileName = fileName;
        File text = new File(fileName);
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            for (String fileLine = reader.readLine(); fileLine != null; fileLine = reader.readLine()) {
                interaction.append(" ");
                interaction.append(fileLine);
            }
            reader.close();
        }
        catch (FileNotFoundException oof){
            System.out.println("Constructor failed!");
        }
        catch (IOException ioe){
            System.out.println("IOE exception!");
        }

        String[] charArr = interaction.toString().trim().split(" "); //for debugging purposes only
        for(int i=0; i<interaction.toString().trim().split(" ").length; i+=3){
            if(Integer.parseInt((charArr[i+2])) <= timeWindow[1] && Integer.parseInt((charArr[i+2])) >= timeWindow[0]){
                vertexSet.add(charArr[i]);
                vertexSet.add(charArr[i+1]);
            }
        }

        int max = 0;
        for(String user : vertexSet){
            if(Integer.parseInt(user) > max){
                max = Integer.parseInt(user);
            }
        }

        //adjacencyMatrix = new int[max+1][max+1];
        adjacencyMatrix = new Set[max+1][max+1];

        for(int i=0; i<max+1; i++){
            for(int j=0; j<max+1; j++){
                adjacencyMatrix[i][j] = new HashSet<>();
            }
        }
        for(int i=0; i<charArr.length; i+=3){
            if(Integer.parseInt(charArr[i+2]) <= timeWindow[1] && Integer.parseInt(charArr[i+2]) >= timeWindow[0]){
                adjacencyMatrix[Integer.parseInt(charArr[i])][Integer.parseInt(charArr[i+1])].add(Integer.parseInt(charArr[i+2]));
            }
            /*else{
                if(i < max+1 && i+1 < max+1) { //change
                    adjacencyMatrix[Integer.parseInt(charArr[i])][Integer.parseInt(charArr[i + 1])].add(Integer.parseInt("-1"));
                }
                else{
                    continue;
                }
            }*/
        }
        System.out.println("hi");

    }

    /**
     * Creates a new DWInteractionGraph from a DWInteractionGraph object
     * and considering a time window filter.
     *
     * @param inputDWIG a DWInteractionGraph object
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created DWInteractionGraph
     *                   should only include those emails in the input
     *                   DWInteractionGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    public DWInteractionGraph(DWInteractionGraph inputDWIG, int[] timeFilter) {

        fileName = inputDWIG.helperGetFileName();
        int tempSize = inputDWIG.helperGetAdjMatx().length;
        //int[][] temp = new int[tempSize][tempSize];
        Set<Integer> temp[][] = new Set[tempSize][tempSize]; //modify
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                //temp[i][j] = inputDWIG.helperGetAdjMatx()[i][j]; //lmao does this actually work??
                //temp[i][j] = inputDWIG.helperGetAdjMatx()[i][j]; //NEED TO MAKE A COPY

                temp[i][j] = new HashSet<>();
                for(Integer time : inputDWIG.helperGetAdjMatx()[i][j]){
                    if(time >= timeFilter[0] && time <= timeFilter[1]){
                        temp[i][j].add(time);
                    }
                }
            }
        }

        this.adjacencyMatrix = temp; //risky move
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                /*if(temp[i][j] >= timeFilter[0] && temp[i][j] <= timeFilter[1]){
                    vertexSet.add(String.valueOf(i));
                    vertexSet.add(String.valueOf(j));
                }*/

                for(Integer inty : temp[i][j]){
                    if(inty >= timeFilter[0] && inty <= timeFilter[1]){ //guaranteed anyways
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
     * @param inputDWIG a DWInteractionGraph object
     * @param userFilter a List of User IDs. The created DWInteractionGraph
     *                   should exclude those emails in the input
     *                   DWInteractionGraph for which neither the sender
     *                   nor the receiver exist in userFilter.
     */
    public DWInteractionGraph(DWInteractionGraph inputDWIG, List<Integer> userFilter) {
        fileName = inputDWIG.helperGetFileName();
        int tempSize = inputDWIG.helperGetAdjMatx().length;
        //int[][] temp = new int[tempSize][tempSize];
        Set<Integer> temp[][] = new Set[tempSize][tempSize];
        for(int i=0; i<tempSize; i++){
            for(int j=0; j<tempSize; j++){
                if(userFilter.contains(i) || userFilter.contains(j)) {
                    temp[i][j] = inputDWIG.helperGetAdjMatx()[i][j];
                }
                else{
                    temp[i][j] = new HashSet<>();
                }
            }
        }
        adjacencyMatrix = temp;
        System.out.println("lmao");
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                if(!temp[i][j].isEmpty()){
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
        for(String id : vertexSet){
            Integer num = (Integer.parseInt(id));
            UserIDs.add(num);
            num = null;
        }
        return UserIDs;
    }

    /**
     * @param sender the User ID of the sender in the email transaction.
     * @param receiver the User ID of the receiver in the email transaction.
     * @return the number of emails sent from the specified sender to the specified
     * receiver in this DWInteractionGraph.
     */
    public int getEmailCount(int sender, int receiver) {
        return adjacencyMatrix[sender][receiver].size();
    }

    /*
     *Helper method
     */
    String helperGetFileName() {
        return fileName;
    }

    /*int[][] helperGetAdjMatx() {*/
    Set<Integer>[][] helperGetAdjMatx(){
        Set<Integer> temp[][] = new Set[adjacencyMatrix.length][adjacencyMatrix.length];
        for(int i=0; i<adjacencyMatrix.length; i++){
            for(int j=0; j<adjacencyMatrix.length; j++){
                temp[i][j] = new HashSet<>();
                temp[i][j].addAll(adjacencyMatrix[i][j]);
            }
        }
        return adjacencyMatrix; //copy????
    }

    Set<String> getVertexSet() {
        return vertexSet; //make a copy
    }

    /* ------- Task 2 ------- */

    /**
     * Given an int array, [t0, t1], reports email transaction details.
     * Suppose an email in this graph is sent at time t, then all emails
     * sent where t0 <= t <= t1 are included in this report.
     * @param timeWindow is an int array of size 2 [t0, t1] where t0<=t1.
     * @return an int array of length 3, with the following structure:
     * [NumberOfSenders, NumberOfReceivers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
        // TODO: Implement this method

        DWInteractionGraph time = new DWInteractionGraph(this,timeWindow );
        int[] activity = new int[]{0,0,0};

        Set<Integer> user = time.getUserIDs();
        List<Integer> userList = user.stream().toList();
        List<Integer> senderList = new ArrayList<>(0);
        List<Integer> receiverList = new ArrayList<>(0);



        for(int i = 0; i<user.size(); i++)
        {
            for(int j = 0; j< user.size(); j++)
            {
                int current = time.getEmailCount(userList.get(i), userList.get(j));

                activity[2] += current;

                if(current!= 0 &&!senderList.contains(userList.get(i)))
                {
                    senderList.add(userList.get(i));
                }

                if(current != 0 && !receiverList.contains(userList.get(j)))
                {
                    receiverList.add(userList.get(j));
                }



                current = time.getEmailCount(userList.get(j), userList.get(i));

                if(current!=0 && !senderList.contains(userList.get(j)))
                {
                    senderList.add(userList.get(j));
                }

                if(current!=0 && !receiverList.contains(userList.get(i)))
                {
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
     * @param userID the User ID of the user for which the report will be
     *               created.
     * @return an int array of length 3 with the following structure:
     * [NumberOfEmailsSent, NumberOfEmailsReceived, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in this instance of a graph,
     * returns [0, 0, 0].
     */
    public int[] ReportOnUser(int userID) {
        // TODO: Implement this method

        Set<Integer> userSet = this.getUserIDs();
        List<Integer> userList = userSet.stream().toList();
        int[] report = new int[]{0,0,0};
        boolean check = true;
        int k = 0;
        List<Integer> unique = new ArrayList<Integer>(0);

        for(int j: userList)
        {
            if(userID == j)
            {
                k = j;
                check = false;
            }

        }

        if(check)
        {
            return report;

        }

        for(int i = 0; i < userList.size(); i++)
        {
            int current = this.getEmailCount(k,userList.get(i));
            report[0]+=current;


            if(!unique.contains(userList.get(i)) && current != 0)
            {
                unique.add(userList.get(i));
            }

            current = this.getEmailCount(userList.get(i),k);
            report[1]+=current;


            if(!unique.contains(userList.get(i)) && current != 0)
            {
                unique.add(userList.get(i));
            }


        }


        report[2] = unique.size();
        return report;
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @param interactionType Represent the type of interaction to calculate the rank for
     *                        Can be SendOrReceive.Send or SendOrReceive.RECEIVE
     * @return the User ID for the Nth most active user in specified interaction type.
     * Sorts User IDs by their number of sent or received emails first. In the case of a
     * tie, secondarily sorts the tied User IDs in ascending order.
     */
    public int NthMostActiveUser(int N, SendOrReceive interactionType) {
            int arr[] = new int[adjacencyMatrix.length];
            int arr3[] = new int[adjacencyMatrix.length];
            int sum = 0; //dont forget to reset it!
            for(int i=0; i<adjacencyMatrix.length; i++){
                for(int j=0; j< adjacencyMatrix[0].length; j++){
                    if(interactionType.equals(SendOrReceive.SEND)) {sum += adjacencyMatrix[i][j].size();}
                    else {sum += adjacencyMatrix[j][i].size();}
                }
                arr[i] = sum;
                sum = 0;
            }

            HashMap<Integer, Integer> sorter = new HashMap<>();
            for(int i=0; i<arr.length; i++){
                sorter.put(i,arr[i]);
            }

            int test = 0;
            for(Integer i : sorter.keySet()){
                if(sorter.get(i)>0){test++;}
            }
            if(N>test){
                return -1;
            }

            int best = -1;
            int bestInd = 0;
            int index = 0;
            while(!sorter.isEmpty()){
                for(Integer i : sorter.keySet()){
                    if(sorter.get(i) > best){
                        bestInd = i;
                        best = sorter.get(i);
                    }
                    else if(sorter.get(i) - bestInd == 0){
                        if(i < bestInd){
                            bestInd = i;
                            best = sorter.get(i);
                        }
                    }
                }
                arr3[index] = bestInd;
                index++;
                sorter.remove(bestInd);
                best = -1;
                bestInd = 0;
            }

            return arr3[N-1];
    }

    /* ------- Task 3 ------- */

    /**
     * performs breadth first search on the DWInteractionGraph object
     * to check path between user with userID1 and user with userID2.
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, should return null.
     */
    public List<Integer> BFS(int userID1, int userID2) {
        // TODO: Implement this method
        return null;
    }

    /**
     * performs depth first search on the DWInteractionGraph object
     * to check path between user with userID1 and user with userID2.
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, should return null.
     */
    public List<Integer> DFS(int userID1, int userID2) {
        // TODO: Implement this method
        return null;
    }

    /* ------- Task 4 ------- */

    /**
     * Read the MP README file carefully to understand
     * what is required from this method.
     * @param hours
     * @return the maximum number of users that can be polluted in N hours
     */
    public int MaxBreachedUserCount(int hours) {
        // TODO: Implement this method
        return 0;
    }

}