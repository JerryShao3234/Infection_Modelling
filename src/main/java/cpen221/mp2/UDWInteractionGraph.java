package cpen221.mp2;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class UDWInteractionGraph {

    private String fileName = new String();
    private StringBuilder interaction = new StringBuilder();
    private Set<Integer> adjacencyMatrix[][];
    private HashSet<String> vertexSet = new HashSet<>();

    /* ------- Task 1 ------- */
    /* Building the Constructors */

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

        adjacencyMatrix = new Set[max+1][max+1];


        for(int i=0; i<adjacencyMatrix.length; i++){
            for(int j=0; j<adjacencyMatrix.length; j++){
                adjacencyMatrix[i][j] = new HashSet<>();
            }
        }

        for(int i=0; i<charArr.length; i+=3){
            adjacencyMatrix[Integer.parseInt(charArr[i])][Integer.parseInt(charArr[i+1])].add(Integer.parseInt(charArr[i+2]));
        }


        System.out.println("hi");
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
        //int[][] temp = new int[tempSize][tempSize];
        Set<Integer> temp[][] = new Set[tempSize][tempSize]; //modify
        for (int i = 0; i < tempSize; i++) {
            for (int j = 0; j < tempSize; j++) {
                //temp[i][j] = inputDWIG.helperGetAdjMatx()[i][j]; //lmao does this actually work??
                //temp[i][j] = inputDWIG.helperGetAdjMatx()[i][j]; //NEED TO MAKE A COPY

                temp[i][j] = new HashSet<>();
                for(Integer time : inputUDWIG.helperGetAdjMatx()[i][j]){
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
        //int[][] temp = new int[tempSize][tempSize];
        Set<Integer> temp[][] = new Set[tempSize][tempSize];
        for(int i=0; i<tempSize; i++){
            for(int j=0; j<tempSize; j++){
                if(userFilter.contains(i) || userFilter.contains(j)) {
                    temp[i][j] = inputUDWIG.helperGetAdjMatx()[i][j];
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
     * Creates a new UDWInteractionGraph from a DWInteractionGraph object.
     *
     * @param inputDWIG a DWInteractionGraph object
     */
    public UDWInteractionGraph(DWInteractionGraph inputDWIG) {
        fileName = inputDWIG.helperGetFileName();
        int tempSize = inputDWIG.helperGetAdjMatx().length;
        //int[][] temp = new int[tempSize][tempSize];
        Set<Integer> temp[][] = new Set[tempSize][tempSize];
        for(int i=0; i<tempSize; i++){
            for(int j=0; j<tempSize; j++){
                    temp[i][j] = new HashSet<>();
                    temp[i][j] = inputDWIG.helperGetAdjMatx()[i][j];
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
     * in this UDWInteractionGraph.
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
     * @param user1 the User ID of the first user.
     * @param user2 the User ID of the second user.
     * @return the number of email interactions (send/receive) between user1 and user2
     */
    public int getEmailCount(int user1, int user2) {
        return adjacencyMatrix[user1][user2].size()+adjacencyMatrix[user2][user1].size();
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
     * @param timeWindow is an int array of size 2 [t0, t1]
     *                   where t0<=t1
     * @return an int array of length 2, with the following structure:
     *  [NumberOfUsers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
        // TODO: Implement this method
        return null;
    }

    /**
     * @param userID the User ID of the user for which
     *               the report will be created
     * @return an int array of length 2 with the following structure:
     *  [NumberOfEmails, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in this instance of a graph,
     * returns [0, 0].
     */
    public int[] ReportOnUser(int userID) {
        // TODO: Implement this method
        return null;
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @return the User ID for the Nth most active user
     */
    public int NthMostActiveUser(int N) {
        // TODO: Implement this method
        return -1;
    }

    /* ------- Task 3 ------- */

    /**
     * @return the number of completely disjoint graph
     *    components in the UDWInteractionGraph object.
     */
    public int NumberOfComponents() {
        // TODO: Implement this method
        return 0;
    }

    /**
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return whether a path exists between the two users
     */
    public boolean PathExists(int userID1, int userID2) {
        // TODO: Implement this method
        return false;
    }

}
