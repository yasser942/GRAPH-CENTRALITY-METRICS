import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class CalculateCentrality {



	//finding the closeness centrality
	public void closenessCentrality(BuildGraph graph) {
		int componentSize = graph.getComponents().size();
		for (int i = 0; i < componentSize; i++) {
			// stores the closeness centrality value for each node
			ArrayList<Node> centralityValue = new ArrayList<>();

			ArrayList<Integer> component = graph.getComponents().get(i);
			for (Integer source : component) {

				HashMap<Integer, Integer> distance = new HashMap<>();
				Queue<Integer> queue = new LinkedList<>();
				distance.put(source, 0);
				int current = source;

				while (distance.size() != component.size()) {
					for (Integer node : graph.getConnectedNodes(current)) {
						if (!distance.containsKey(node)) {
							distance.put(node, distance.get(current) + 1);
							queue.add(node);
						}
					}
					if (!queue.isEmpty()) {
						current = queue.poll();
					}
				}

				int totalDistance = 0;
				for (Integer nodeDistance : distance.values()) {
					totalDistance += nodeDistance;
				}
				centralityValue.add(new Node(source, totalDistance));
			}


			Collections.sort(centralityValue);
			System.out.println("Node: "+centralityValue.get(0).id+"   "+"Value: "+1/(centralityValue.get(0).value));
			break;

		}

	}

	// finding the betweeness centrality
	public void betweennessCentrality(BuildGraph graph) {
		int componentSize = graph.getComponents().size();

		for (int i = 0; i < componentSize; i++) {
			ArrayList<Integer> component = graph.getComponents().get(i);

			// stores the betweenness Centrality value for each node
			HashMap<Integer, Double> centrality = new HashMap<>();

			for (Integer source : component) {

				Stack<Integer> stack = new Stack<>();
				Queue<Integer> queue = new LinkedList<>();
				HashMap<Integer, ArrayList<Integer>> precedingNode = new HashMap<>();
				HashMap<Integer, Integer> distance = new HashMap<>();
				HashMap<Integer, Integer> paths = new HashMap<>();

				queue.add(source);
				paths.put(source, 1);
				distance.put(source, 0);

				while (!queue.isEmpty()) {
					int current = queue.poll();

					stack.push(current);

					for (Integer node : graph.getConnectedNodes(current)) {

						// node is founded for the first time
						if (!distance.containsKey(node)) {
							queue.add(node);
							distance.put(node, distance.get(current) + 1);
							paths.put(node, paths.get(current));
							ArrayList<Integer> parent = new ArrayList<>();
							parent.add(current);
							precedingNode.put(node, parent);

							// found another shortest path for existing node
						} else if (distance.get(node) == distance.get(current) + 1) {
							paths.put(node, (paths.get(node) + paths.get(current)));
							ArrayList<Integer> precede = precedingNode.get(node);
							precede.add(current);
							precedingNode.put(node, precede);
						}
					}
				}

				HashMap<Integer, Double> dependency = new HashMap<>();
				for (Integer node : component) {
					dependency.put(node, 0.0);
				}
				while (!stack.isEmpty()) {
					int current = stack.pop();
					if (current != source) {
						for (Integer node : precedingNode.get(current)) {
							double result = ((double) paths.get(node)
									/ paths.get(current)) * (1 + dependency.get(current));
							dependency.put(node, dependency.get(node) + result);
						}
						if (!centrality.containsKey(current)) {
							// divided by 2 due to the graph's undirected nature
							centrality.put(current, dependency.get(current) / 2);
						} else {
							centrality.put(current, centrality.get(current)
									+ dependency.get(current) / 2);
						}
					}
				}
			}
			// stores the centrality value for each node as a Node object
			ArrayList<Node> centralityValue = new ArrayList<>();
			for (Integer node : centrality.keySet()) {
				centralityValue.add(new Node(node, centrality.get(node)));
			}

			Collections.sort(centralityValue, Collections.reverseOrder());


			System.out.println("Node: "+centralityValue.get(0).id+"   "+"Value: "+(centralityValue.get(0).value));
			break;


		}

	}



	private class Node implements Comparable<Node> {
		private int id;
		private double value;


		public Node(int id, double value) {
			this.id = id;
			this.value = value;
		}


		@Override
		public int compareTo(Node node) {
			return Double.compare(value, node.value);
		}
	}
}
