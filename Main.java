import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		DSAGraph graph = new DSAGraph(10); //creates a new grap with a maximum of 10 shops
		DSALinkedList shopList = new DSALinkedList();
		Scanner sc = new Scanner(System.in);
		int Answer;
		boolean restart = true;
		
		do
		{
			//interactive menu for the user, if the user picks a certain number, it performs a different function
			System.out.println("Please pick a number below to do the respective funtion:\n");
			System.out.print("(1) Add Shop\n(2) Delete Shop\n(3) Update an Existing Shop\n(4)Display Shops based on their categories\n(5)Depth-First-Traversal between 2 points\n(6)Breadth-First-Traversal\n(0) Exit the Program\n\nAnswer: ");
		
		
			Answer = sc.nextInt();
		
			switch(Answer)
			{
				case 1:	//case 1 adds  a shop to the graph
					addShop(graph, shopList);
					System.out.println("\nChosen Option 1");
					break;
				
				case 2:// case 2 deletes a shop from the graph
					deleteShop(graph, shopList);
					
					System.out.println("\nChosen Option 2");
					break;
					
				case 3: //case 3 updates a shop in the graph and their details
					updateShop(graph, shopList);
					System.out.println("\nChosen Option 3");
					break;
				case 4: //case 4 searches for shops by category by utilizing a hashtable
					searchByCategory(graph);
					break;
				case 5: //case 5 uses depth first search to find the destination shop from the starting shop
					dfsTraversal(graph);
                			break;
                		case 6: // case 6 does the samme but utilizes breadth first search instead of deapth first search
                			bfsTraversal(graph);
                			break;
				case 0: //case 0 simply exits the program
					 
					restart = false;
					break;
				default: //if the user inputs something else thats not recognized then the menu prints again and tells the user that they inputted an invalid choice.
					System.out.println("\nInvalid choice, pick one of the following options or press 0 to exit");
					restart = false;
			}
		}while (restart);
		
			
	}

//function for adding shops to the graph
public static void addShop(DSAGraph graph, DSALinkedList shopList) {
    Scanner input = new Scanner(System.in);
    boolean continueAdding = true;
    String shopNum;

    do {
        do {//user inputs details of  the shop
            System.out.println("Enter Shop Number (must be 3 digits): ");
            shopNum = input.nextLine();
            if (shopNum.length() != 3) {
                System.out.println("Shop number must be 3 digits long.");
            }
        } while (shopNum.length() != 3);

        if (!graph.hasVertex(shopNum)) {
            System.out.print("Enter Shop Name: ");
            String shopName = input.nextLine();

            System.out.print("Enter Shop Category: ");
            String category = input.nextLine();

            String location;
            boolean locationExists;

            do {
                System.out.println("Enter the location '12 means floor 1, aisle 2': ");
                location = input.nextLine(); //checks if the location the user inputted already exists wiithin another shop as 2 shops cannot exist in the same location
                locationExists = graph.hasVertex(location) || shopList.containsLocation(location);
                if (locationExists || location.length() != 2) {
                    System.out.println("Please enter a valid location.");
                }
            } while (locationExists || location.length() != 2);

            // Validate rating
            int rating;
            do {
                System.out.print("Enter Shop Rating (1 to 5): ");
                rating = input.nextInt();
                input.nextLine(); // Consume newline
                if (rating < 1 || rating > 5) { //error checking for rating as it cant be higher than 5 and lower than 1
                    System.out.println("Invalid rating. Rating must be between 1 and 5.");
                }
            } while (rating < 1 || rating > 5);

            // Add shop information to the linked list
            String shopInfo = shopNum + "," + shopName + "," + category + "," + location + "," + rating;
            shopList.insertLast(shopInfo);

            // Add the shop to the graph
            graph.addVertex(shopNum, shopName, category, location);

            // Updates the category table
            graph.addShopCategory(category, shopInfo);

            System.out.println("Shop added successfully!");
		//every time a shop is added, the program asks if it wants to add adjacency to another shop then asks forhe shops they want to add adjacency for
            System.out.print("Do you want to add adjacency with another shop? (1 = yes, 2 = no): ");
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                System.out.print("Enter Shop Number to add adjacency with: ");
                String adjShopNum = input.nextLine();

                if(graph.hasVertex(adjShopNum)) {
                    System.out.print("Enter Label for the adjacency: ");
                    String edgeLabel = input.nextLine();
                    graph.addEdge(shopNum, adjShopNum, edgeLabel, null); 
                    graph.addEdge(adjShopNum, shopNum, edgeLabel, null); 
                    System.out.println("Adjacency added between Shop " + shopNum + " and Shop " + adjShopNum);
                    continueAdding = false;
                } else { //if the inputted shop isnt found then adjacency isnt added
                    System.out.println("Adjacent shop not found.");
                    continueAdding = false;
                }
            } else if (choice == 2) {
                continueAdding = false;
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Shop already exists.");
        }
    } while (continueAdding);
}





public static void searchByCategory(DSAGraph graph) {
    Scanner input = new Scanner(System.in);
    System.out.print("Enter the shop category to search: ");
    String category = input.nextLine();
    
    DSALinkedList shops = graph.getShopsByCategory(category);
    if (shops != null && !shops.isEmpty()) {
        System.out.println("Shops in category '" + category + "':");
        Iterator iterator = shops.iterator();
        while (iterator.hasNext()) {
            String shopInfo = (String) iterator.next();
            String[] shopData = shopInfo.split(",");
            if (shopData.length >= 4) { // Check if shopData has at least four elements
                String shopNum = shopData[0];
                String shopName = shopData[1];
                String shopCategory = shopData[2];
                String shopLocation = shopData[3];
                int shopRating = Integer.parseInt(shopData[4]); // Extract rating
                System.out.println("Shop Number: " + shopNum);
                System.out.println("Shop Name: " + shopName);
                System.out.println("Category: " + shopCategory);
                System.out.println("Location: " + shopLocation);
                System.out.println("Rating: " + shopRating); // Display rating
                System.out.println();
            } else {
                System.out.println("Invalid shop information: " + shopInfo);
            }
        }
    } else {
        System.out.println("No shops found in category '" + category + "'.");
    }
}

public static void deleteShop(DSAGraph graph, DSALinkedList shopList) { //function for deleting a shop
    Scanner input = new Scanner(System.in);
    System.out.print("Enter Shop Number to delete: ");
    String shopNum = input.nextLine();
    
    if (graph.hasVertex(shopNum)) {
        // Delete the shop from the graph
        graph.deleteVertex(shopNum);
        
        // Remove the shop from the linked list
        Iterator iterator = shopList.iterator();
        while (iterator.hasNext()) {
            String shopInfo = (String) iterator.next();
            String[] shopData = shopInfo.split(",");
            if (shopData[0].equals(shopNum)) {
                iterator.remove();
                System.out.println("Shop deleted successfully!");
                return;
            }
        }
        System.out.println("Shop not found in the list.");
    } else {
        System.out.println("Shop with number '" + shopNum + "' not found in the graph.");
    }
}

public static void updateShop(DSAGraph graph, DSALinkedList shopList) { //function for updating shop information
    Scanner input = new Scanner(System.in);

    System.out.print("Enter Shop Number to update: "); //prompts the user to input the shop number (by which it will retrieve the shop information) then it will ask for the new shop information
    String shopNum = input.nextLine();

    if (graph.hasVertex(shopNum)) {
        boolean shopFound = false;

        while (!shopList.isEmpty()) {
            String shopInfo = (String) shopList.peekFirst();
            String[] shopData = shopInfo.split(",");

            if (shopData[0].equals(shopNum)) {
                System.out.println("Enter new Shop Name: ");
                String newShopName = input.nextLine();

                System.out.println("Enter new Shop Category: ");
                String newCategory = input.nextLine();

                System.out.println("Enter new Shop Location: ");
                String newLocation = input.nextLine();

                // Update rating
                int newRating;
                do {
                    System.out.print("Enter new Shop Rating (1 to 5): ");
                    newRating = input.nextInt();
                    input.nextLine();
                    if (newRating < 1 || newRating > 5) {
                        System.out.println("Invalid rating. Rating must be between 1 and 5.");
                    }
                } while (newRating < 1 || newRating > 5);

                // Remove the current shop from graph and linked list
                graph.deleteVertex(shopNum);
                shopList.removeFirst();

                // Create a new node with updated information
                String updatedShopInfo = shopNum + "," + newShopName + "," + newCategory + "," + newLocation + "," + newRating;
                shopList.insertLast(updatedShopInfo);

                // Add the shop to the graph with updated information
                graph.addVertex(shopNum, newShopName, newCategory, newLocation);

                // Update the shop category in the graph
                graph.updateShopCategory(shopNum, newCategory);

                System.out.println("Shop updated successfully!");
                shopFound = true;
                break; // Exit the loop after updating the shop
            } else {
                // Move to the next shop
                shopList.removeFirst();
            }
        }

        if (!shopFound) {
            System.out.println("Shop with number '" + shopNum + "' not found in the list.");
        }
    } else {
        System.out.println("Shop with number '" + shopNum + "' not found in the graph.");
    }
}


public static void displayAllShops(DSAGraph graph, DSALinkedList shopList) { //function for displaying the shops
    System.out.println("All Shops:");

    Iterator iterator = shopList.iterator();
    DSALinkedList printedShops = new DSALinkedList(); // Keep track of printed shops
    
    // Iterate through each shop in the list
    while (iterator.hasNext()) {
        String shopInfo = (String) iterator.next();
        String[] shopData = shopInfo.split(",");
        String shopNum = shopData[0];
        String shopName = shopData[1];
        String category = shopData[2];
        String location = shopData[3];

        // Check if the shop has already been printed
        boolean alreadyPrinted = false;
        Iterator printedIterator = printedShops.iterator();
        while (printedIterator.hasNext()) {
            String printedShop = (String) printedIterator.next();
            String printedShopNum = printedShop.split(",")[0];
            if (printedShopNum.equals(shopNum)) {
                alreadyPrinted = true;
                break;
            }
        }

        // If the shop hasn't been printed yet, print its details
        if (!alreadyPrinted) {
            System.out.println("Shop Number: " + shopNum);
            System.out.println("Shop Name: " + shopName);
            System.out.println("Category: " + category);
            System.out.println("Location: " + location);
            printedShops.insertLast(shopInfo); // Add the shop to printedShops

            // Retrieve adjacent shops from the graph for the current shop
            DSAGraphVertex currentShop = graph.getVertex(shopNum);
            if (currentShop != null) {
                DSAGraphVertex[] adjacentVertices = graph.getAdjacent(shopNum);
                if (adjacentVertices != null) {
                    System.out.println("Adjacent Shops:");
                    for (int i = 0; i < adjacentVertices.length; i++) {
                        DSAGraphVertex adjacentVertex = adjacentVertices[i];
                        String adjShopInfo = adjacentVertex.getLabel() + "," + adjacentVertex.getShopName() + "," + adjacentVertex.getCategory() + "," + adjacentVertex.getLocation();
                        System.out.println("  Shop Number: " + adjacentVertex.getLabel());
                        System.out.println("  Shop Name: " + adjacentVertex.getShopName());
                        System.out.println("  Category: " + adjacentVertex.getCategory());
                        System.out.println("  Location: " + adjacentVertex.getLocation());
                        printedShops.insertLast(adjShopInfo); // Add the adjacent shop to printedShops
                    }
                } else {
                    System.out.println("No adjacent shops found.");
                }
            } else {
                System.out.println("Shop not found in the graph.");
            }
        }

        System.out.println();
    }
}

public static void bfsTraversal(DSAGraph graph) { //a function to prompt the user to input the starting and ending shop number, then calls a function in dsagraph  to perform bfs.
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the starting shop number: ");
    String startShopNum = scanner.nextLine();

    System.out.print("Enter the destination shop number: ");
    String destinationShopNum = scanner.nextLine();

    if (!graph.hasVertex(startShopNum) || !graph.hasVertex(destinationShopNum)) { //if they shops inputte dont exist in the graph, bsf will be cancelled
        System.out.println("Start or destination shop number does not exist in the graph.");
        return;
    }

    graph.bfs(startShopNum, destinationShopNum);
}


public static void dfsTraversal(DSAGraph graph) { //a function to prompt the user to input the starting and ending shop number, then calls a function in dsagraph to perform dfs.
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the starting shop label for DFS traversal: ");
    String startLabel = scanner.nextLine();

    System.out.print("Enter the destination shop label for DFS traversal: ");
    String destinationLabel = scanner.nextLine();

    // Validate if both start and destination labels exist in the graph
    if (!graph.hasVertex(startLabel) || !graph.hasVertex(destinationLabel)) {
        System.out.println("Start or destination shop label does not exist in the graph.");
        return;
    }

    // Perform DFS traversal from startLabel to destinationLabel
    graph.dfs(startLabel, destinationLabel);
}




private static boolean isVisited(DSALinkedList visited, DSAGraphVertex vertex) {
    Iterator iter = visited.iterator();
    while (iter.hasNext()) {
        DSAGraphVertex visitedVertex = (DSAGraphVertex) iter.next();
        if (visitedVertex.getLabel().equals(vertex.getLabel())) {
            return true;
        }
    }
    return false;
}
public static void deleteShop(DSAGraph graph, DSALinkedList shopList, DSAGraph dsGraph, String shopNum) { //function for deletingshop
    // Remove the shop from the linked list
    shopList.remove(shopNum);

    // Remove the shop from the graph
    graph.deleteVertex(shopNum);

    System.out.println("Shop deleted successfully!");
}


}
