public class DSAGraph {
    public DSAGraphVertex[] vertices;
    public int size;
    private DSALinkedList shopList;
    private hashTable categoryTable;



/**
 * Constructs a DSAGraph object with a specified size.
 * Initializes vertices array, shopList, and categoryTable.
 */
    public DSAGraph(int size) {
        this.size = size;
        vertices = new DSAGraphVertex[size];
        shopList = new DSALinkedList();
        categoryTable = new hashTable(size);
    }
    
    
    
/**
 * Adds a vertex (shop) to the graph.
 */
    public void addVertex(String shopNum, String shopName, String category, String location) {
    for (int i = 0; i < size; i++) {
        if (vertices[i] == null) {
            vertices[i] = new DSAGraphVertex(shopNum, shopName, category, location,null);
            return;
        }
    }
    System.out.println("Graph is full. Cannot add more vertices.");
}


/**
 * Adds a shop to a category in the categoryTable.
 */
public void addShopCategory(String category, String shopInfo) {
    DSALinkedList shopsInCategory;

    if (categoryTable.containsKey(category)) {
        shopsInCategory = categoryTable.get(category); 
    } else {
        shopsInCategory = new DSALinkedList();
        categoryTable.put(category, shopsInCategory);
    }

    shopsInCategory.insertLast(shopInfo); 
    
}

/**
 * Retrieves a list of shops belonging to a specified category.
 */
    public DSALinkedList getShopsByCategory(String category) {
        if (categoryTable.containsKey(category)) {
            return (DSALinkedList) categoryTable.get(category);
        }
        return null;
    }
    
    
    
 /**
 * Updates the category of a shop.
 */
   
    public void updateShopCategory(String shopNum, String newCategory) {
    for (int i = 0; i < size; i++) {
        if (vertices[i] != null && vertices[i].getLabel().equals(shopNum)) {
            
            String oldCategory = vertices[i].getCategory();
            vertices[i].setCategory(newCategory);
            updateCategoryTable(shopNum, oldCategory, newCategory);
            System.out.println("Category of shop '" + shopNum + "' updated successfully.");
            return;
        }
    }
    System.out.println("Shop with number '" + shopNum + "' not found.");
}

private void updateCategoryTable(String shopNum, String oldCategory, String newCategory) {
    if (categoryTable.containsKey(oldCategory)) {
        
        DSALinkedList shopsInOldCategory = categoryTable.get(oldCategory);
        
        shopsInOldCategory.remove(shopNum);
        
        if (shopsInOldCategory.isEmpty()) {
            categoryTable.remove(oldCategory);
        }
    }
    
    addShopCategory(newCategory, shopNum);
}
/**
 * Deletes a vertex (shop) from the graph.
 */
public void deleteVertex(String label) {
    for (int i = 0; i < size; i++) {
        if (vertices[i] != null && vertices[i].getLabel().equals(label)) {
            DSAGraphVertex vertexToRemove = vertices[i];
    
            for (int j = 0; j < size; j++) {
                if (vertices[j] != null && vertices[j] != vertexToRemove) {
                        DSAGraphEdge edgeToRemove = vertices[j].getEdge(label);
                    if (edgeToRemove != null) {
                        vertices[j].deleteEdge(edgeToRemove);
                    }
                }
            }
            vertices[i] = null;
        }
    }
}


/**
 * Adds an edge between two vertices in the graph.
 */
public void addEdge(String label1, String label2, String edgeLabel, Object value) {
    DSAGraphVertex v1 = null, v2 = null;
    int i = 0;
    while (i < size && (v1 == null || v2 == null)) {
        if (vertices[i] != null) {
            if (vertices[i].getLabel().equals(label1)) {
                v1 = vertices[i];
            }
            if (vertices[i].getLabel().equals(label2)) {
                v2 = vertices[i];
            }
        }
        i++;
    }
    
    if (v1 != null && v2 != null) {
        DSAGraphEdge edge = new DSAGraphEdge(v1, v2, edgeLabel, value);
        v1.addEdge(edge);
        // Add bidirectional edge
        DSAGraphEdge reverseEdge = new DSAGraphEdge(v2, v1, edgeLabel, value);
        v2.addEdge(reverseEdge);
    } else {
        System.out.println("One or both vertices not found.");
    }
}
  
/**
 * Deletes an edge between two vertices in the graph.
 */
public void deleteEdge(String label1, String label2) {
    DSAGraphVertex v1 = null, v2 = null;

    
    for (int i = 0; i < size; i++) {
        if (vertices[i] != null) {
            if (vertices[i].getLabel().equals(label1)) {
                v1 = vertices[i];
            }
            if (vertices[i].getLabel().equals(label2)) {
                v2 = vertices[i];
            }
        }
    }

    if (v1 != null && v2 != null) {
    
        DSAGraphEdge edgeToRemove = null;
        for (DSAGraphEdge edge : v1.getAdjacentE()) {
            if (edge.getTo() == v2 || (!edge.isDirected() && edge.getFrom() == v2)) {
                edgeToRemove = edge;
                break;
            }
        }
        if (edgeToRemove != null) {
            v1.deleteEdge(edgeToRemove);
            v2.deleteEdge(edgeToRemove);
            System.out.println("Edge between '" + label1 + "' and '" + label2 + "' deleted successfully.");
        } else {
            System.out.println("No edge found between '" + label1 + "' and '" + label2 + "'.");
        }
    } else {
        System.out.println("One or both vertices not found.");
    }
}





    public DSAGraphVertex getVertex(String label) {
        for (int i = 0; i < size; i++) {
            if (vertices[i] != null && vertices[i].getLabel().equals(label))
                return vertices[i];
        }
        return null;
    }

    public boolean hasVertex(String label) {
        for (int i = 0; i < size; i++) {
            if (vertices[i] != null && vertices[i].getLabel().equals(label))
                return true;
        }
        return false;
    }

    public int getVertexCount() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (vertices[i] != null)
                count++;
        }
        return count;
    }

    public int getEdgeCount() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (vertices[i] != null)
                count += vertices[i].getEdgeCount();
        }
        
        return count / 2;
    }

    public DSAGraphEdge getEdge(String label) {
        for (int i = 0; i < size; i++) {
            if (vertices[i] != null) {
                DSAGraphEdge edge = vertices[i].getEdge(label);
                if (edge != null)
                    return edge;
            }
        }
        return null;
    }

    public DSAGraphVertex[] getAdjacent(String label) {
        DSAGraphVertex vertex = getVertex(label);
        if (vertex != null)
            return vertex.getAdjacent();
        return null;
    }

    public DSAGraphEdge[] getAdjacentE(String label) {
        DSAGraphVertex vertex = getVertex(label);
        if (vertex != null)
            return vertex.getAdjacentE();
        return null;
    }

/**
 * Performs Breadth-First Search (BFS) traversal starting from a specified vertex to another specified vertex
 */
public boolean bfs(String startLabel, String destinationLabel) {
    DSAGraphVertex startVertex = getVertex(startLabel);

    if (startVertex == null) {
        System.out.println("Vertex with label '" + startLabel + "' not found.");
        return false;
    }

    DSAQueue queue = new DSAQueue();
    queue.enqueue(startVertex);
    startVertex.setVisited(true);
    System.out.println("BFS Traversal starting from vertex " + startLabel);

    while (!queue.isEmpty()) {
        DSAGraphVertex currentVertex = queue.dequeue();
        System.out.println("Visited vertex: " + currentVertex.getLabel());

        if (currentVertex.getLabel().equals(destinationLabel)) {
            System.out.println("Destination vertex found: " + destinationLabel);
            resetVisitedFlags(); // Reset visited flags before returning
            return true; // Destination found, return true
        }

        DSAGraphVertex[] adjacentVertices = currentVertex.getAdjacent();

        int adjacentIndex = 0;
        while (adjacentIndex < adjacentVertices.length) {
            DSAGraphVertex adjacentVertex = adjacentVertices[adjacentIndex];
            if (!adjacentVertex.getVisited()) {
                adjacentVertex.setVisited(true);
                queue.enqueue(adjacentVertex);
            }
            adjacentIndex++;
        }
    }

    resetVisitedFlags();
    return false; // Destination not found, return false
}



 
/**
 * Performs Depth-First Search (DFS) traversal starting from a specified vertex
 * @return true if the destination vertex is found, otherwise false
 */
public boolean dfs(String startLabel, String destinationLabel) {
    
    DSAGraphVertex startVertex = getVertex(startLabel);

    
    if (startVertex == null) {
        System.out.println("Vertex with label " + startLabel + " not found.");
        return false;
    }

    
    resetVisitedFlags();

    
    stack stack = new stack();

    
    stack.push(startVertex);

    
    System.out.println("DFS Traversal starting from vertex " + startLabel);
    while (!stack.isEmpty()) {
    
        DSAGraphVertex currentVertex = (DSAGraphVertex) stack.pop();

    
        currentVertex.setVisited(true);

    
        System.out.println("Visited vertex: " + currentVertex.getLabel());

    
        if (currentVertex.getLabel().equals(destinationLabel)) {
            System.out.println("Destination vertex found: " + destinationLabel);
            return true;
        }

    
        DSAGraphVertex[] adjacentVertices = currentVertex.getAdjacent();

    
        int adjacentIndex = 0;
        while (adjacentIndex < adjacentVertices.length) {
            DSAGraphVertex adjacentVertex = adjacentVertices[adjacentIndex];
            if (!adjacentVertex.getVisited()) {
                stack.push(adjacentVertex);
            }
            adjacentIndex++;
        }
    }
    System.out.println("Destination vertex " + destinationLabel + " not found.");
    return false;
}



    public void resetVisitedFlags() {
        for (int i = 0; i < size; i++) {
            if (vertices[i] != null) {
                vertices[i].clearVisited();
            }
        }
    }
}




class DSAGraphVertex {
    private String label;
    private Object value;
    private String shopName;
    private String category;
    private String location;
    private DSAGraphEdge[] edges;
    private int edgeCount;
    private boolean visited;
    private DSAListNode shopNode;

	public void deleteEdge(DSAGraphEdge edge) {
           for (int i = 0; i < edgeCount; i++) {
            if (edges[i] == edge) {
                for (int j = i; j < edgeCount - 1; j++) {
                    edges[j] = edges[j + 1];
                }
                edges[edgeCount - 1] = null;
                edgeCount--;
                return;
            }
        }
    }


    public DSAGraphVertex(String label,String shopName, String category, String location, Object value) {
        this.label = label;
        this.value = value;
        this.shopName = shopName;
        this.category = category;
        this.location = location;
        this.shopNode = shopNode;
        edges = new DSAGraphEdge[10]; 
        edgeCount = 0;
        visited = false;
    }

    public String getLabel() {
        return label;
    }

    public Object getValue() {
        return value;
    }
	
	public String getShopName() {
        return shopName;
    }

    public String getCategory() {
        return category;
    }
    
    public String getLocation() {
    	return location;
    }
    
    public void setShopName(String shopName) {
        shopName = shopName;
    }
    
    public void setCategory(String category) {
    	category = category;
    }
    
    
    public void addEdge(DSAGraphEdge edge) {
        if (edgeCount < edges.length) {
            edges[edgeCount++] = edge;
        } else {
            System.out.println("Edge capacity exceeded.");
        }
    }

    public DSAGraphEdge[] getAdjacentE() {
        DSAGraphEdge[] adjacentEdges = new DSAGraphEdge[edgeCount];
        System.arraycopy(edges, 0, adjacentEdges, 0, edgeCount);
        return adjacentEdges;
    }

    public DSAGraphVertex[] getAdjacent() {
        DSAGraphVertex[] adjacentVertices = new DSAGraphVertex[edgeCount];
        for (int i = 0; i < edgeCount; i++) {
            adjacentVertices[i] = edges[i].getTo();
        }
        return adjacentVertices;
    }

    public DSAGraphEdge getEdge(String label) {
        for (int i = 0; i < edgeCount; i++) {
            if (edges[i].getLabel().equals(label))
                return edges[i];
        }
        return null;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void clearVisited() {
        visited = false;
    }

    public boolean getVisited() {
        return visited;
    }

    public String toString() {
        return label + " - Visited: " + visited;
    }
    public void setShopNode(DSAListNode shopNode) {
        this.shopNode = shopNode;
    }

    public DSAListNode getShopNode() {
        return shopNode;
    }
}

class DSAGraphEdge {
    private DSAGraphVertex from;
    private DSAGraphVertex to;
    private String label;
    private Object value;

    public DSAGraphEdge(DSAGraphVertex from, DSAGraphVertex to, String label, Object value) {
        this.from = from;
        this.to = to;
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public Object getValue() {
        return value;
    }

    public DSAGraphVertex getFrom() {
        return from;
    }

    public DSAGraphVertex getTo() {
        return to;
    }

    public boolean isDirected() {
        
        return false;
    }

    public String toString() {
        return label;
    }
   
}
