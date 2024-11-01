import java.util.LinkedList;import java.util.Queue;class EdmondsKarp {    private final int     vertices;    private final int[][] capacity;    public EdmondsKarp(int vertices) {        this.vertices = vertices;        capacity = new int[vertices][vertices];    }    public void addEdge(int from, int to, int cap) {        capacity[from][to] = cap;    }    private boolean bfs(int source, int sink, int[] parent) {        boolean[] visited = new boolean[vertices];        Queue<Integer> queue = new LinkedList<>();        queue.add(source);        visited[source] = true;        parent[source] = -1;        while (!queue.isEmpty()) {            int u = queue.poll();            for (int v = 0; v < vertices; v++) {                if (!visited[v] && capacity[u][v] > 0) {                    queue.add(v);                    parent[v] = u;                    visited[v] = true;                    if (v == sink) {                        return true;                    }                }            }        }        return false;    }    public int edmondsKarp(int source, int sink) {        int maxFlow = 0;        int[] parent = new int[vertices];        while (bfs(source, sink, parent)) {            int pathFlow = Integer.MAX_VALUE;            for (int v = sink; v != source; v = parent[v]) {                int u = parent[v];                pathFlow = Math.min(pathFlow, capacity[u][v]);            }            for (int v = sink; v != source; v = parent[v]) {                int u = parent[v];                capacity[u][v] -= pathFlow;                capacity[v][u] += pathFlow;            }            maxFlow += pathFlow;        }        return maxFlow;    }    public static void main(String[] args) {        int vertices = 6; // S, A, B, C, D, T 총 6개의 정점        int source = 0; // S        int sink = 5;   // T        // 에드몬즈-카프 알고리즘 사용        EdmondsKarp edmondsKarpGraph = new EdmondsKarp(vertices);        edmondsKarpGraph.addEdge(0, 1, 16); // S -> A        edmondsKarpGraph.addEdge(0, 3, 13); // S -> C        edmondsKarpGraph.addEdge(1, 2, 12); // A -> B        edmondsKarpGraph.addEdge(2, 5, 20); // B -> T        edmondsKarpGraph.addEdge(3, 1, 4);  // C -> A        edmondsKarpGraph.addEdge(3, 4, 14); // C -> D        edmondsKarpGraph.addEdge(4, 2, 7);  // D -> B        edmondsKarpGraph.addEdge(4, 5, 4);  // D -> T        System.out.println("에드몬즈-카프 최대 유량은 " + edmondsKarpGraph.edmondsKarp(source, sink) + "입니다.");    }}