import java.io.IOException;


public class Test {


	public static void main(String[] args) throws IOException {
       



		String filepath_1 ="src\\karate_club_network.txt";
		String filepath_2 ="src\\facebook_social_network.txt";
		BuildGraph graph_1 = new BuildGraph(filepath_1);
		BuildGraph graph_2 = new BuildGraph(filepath_2);





		CalculateCentrality centrality = new CalculateCentrality();
		System.out.println();
		System.out.println("2019510006  YASSER EL HASAN ");
		System.out.println();
		System.out.print("Zachary Karate Club Network - Betweennes: ");
		centrality.betweennessCentrality(graph_1);
		System.out.print("Zachary Karate Club Network –  Closeness: ");
		centrality.closenessCentrality(graph_1);


		System.out.println("\n");
		System.out.print("Facebook Social Network - Betweennes: ");
		centrality.betweennessCentrality(graph_2);
		System.out.print("Facebook Social Network - Closeness: ");
		centrality.closenessCentrality(graph_2);



	}
}
