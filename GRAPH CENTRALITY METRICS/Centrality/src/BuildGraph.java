import java.io.File;
import java.io.IOException;
import java.util.*;


public class BuildGraph {

	private HashMap<Integer, ArrayList<Integer>> adjList;
	private ArrayList<ArrayList<Integer>> components;


	public BuildGraph(String path) throws IOException {
		adjList = new HashMap<>();
		components = new ArrayList<ArrayList<Integer>>();
		generateGraph(path);
		identifyComponents();
	}


	public ArrayList<Integer> getConnectedNodes(int u) {
		return adjList.get(u);
	}


	public Set<Integer> getNodes() {
		Set<Integer> nodes = adjList.keySet();
		return nodes;
	}




	public ArrayList<ArrayList<Integer>> getComponents() {
		return components;
	}


	public String toString() {
		String s = "";
		for (int key : adjList.keySet()) {
			s += key + ", " + adjList.get(key).toString() + "\n";
		}
		return s;
	}


	private void identifyComponents() {
		Queue<Integer> queue = new LinkedList<>();
		ArrayList<Integer> visited = new ArrayList<>();
		ArrayList<Integer> componentSet = new ArrayList<>();
		Iterator<Integer> it = getNodes().iterator();
		int current = it.next();

		visited.add(current);
		componentSet.add(current);

		while (visited.size() != getNodes().size()) {
			for (Integer node : getConnectedNodes(current)) {
				if (!visited.contains(node)) {
					visited.add(node);
					queue.offer(node);
					componentSet.add(node);
				}
			}
			if (visited.size() == getNodes().size()) {
				// graph is complete
				components.add(componentSet);
			} else if (queue.isEmpty()) {
				// component is complete
				components.add(componentSet);
				componentSet = new ArrayList<>();

				// identify next node to visit
				int nextCurrent = current;
				while (nextCurrent == current) {
					int next = it.next();
					if (!visited.contains(next)) {
						current = next;
						visited.add(current);
						componentSet.add(current);
					}
				}
			} else {
				// component is not complete
				current = queue.poll();
			}
		}
	}


	private void generateGraph(String path) throws IOException {
		File file = new File(path);
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()){

			String[] node = scanner.nextLine().split(" ");
			int u = Integer.parseInt(node[0]);
			int v = Integer.parseInt(node[1]);
			addConnection(u, v);
			addConnection(v, u);
		}


	}


	private void addConnection(int u, int v) {
		ArrayList<Integer> connected;
		if (!adjList.containsKey(u)) {
			connected = new ArrayList<>();
			connected.add(v);
			adjList.put(u, connected);
		} else {
			if (!contains(adjList.get(u), v)) {
				connected = adjList.get(u);
				connected.add(v);
				Collections.sort(connected);
				adjList.put(u, connected);
			}
		}
	}


	private boolean contains(ArrayList<Integer> list, int item) {
		int lower = 0;
		int upper = list.size() - 1;

		while (lower <= upper) {
			int middle = (lower + upper) / 2;
			if (list.get(middle) < item) {
				lower = middle + 1;
			} else if (list.get(middle) > item) {
				upper = middle - 1;
			} else if (list.get(middle) == item) {
				return true;
			}
		}
		return false;
	}
}