import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

 class Coordinates {
	String id;
	String value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}


public class CoordinatesInOrder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Scanner scn=new Scanner(System.in);
			System.out.println("Enter Source X");
			int x=scn.nextInt();
			System.out.println("Enter Source Y");
			int y=scn.nextInt();
			ArrayList<Coordinates> allcoordinates=new ArrayList<Coordinates>();
			Gson gson = new GsonBuilder().create();
			JsonReader jsonReader=new JsonReader(new FileReader("C:\\Users\\Hardik\\workspace\\Assignment\\coordinates.json"));
			jsonReader.beginArray();
			while (jsonReader.hasNext()) {
				// Read data into object model
				Coordinates coor = gson.fromJson(jsonReader, Coordinates.class);
				allcoordinates.add(coor);
			}
			jsonReader.close();
			Map<String,Double> count = new HashMap<String,Double>();
			for(int i=0;i<allcoordinates.size();i++){
				Coordinates temp=allcoordinates.get(i);
				String[] tempxy=temp.getValue().split(",");
				int tempx=Integer.parseInt(tempxy[0]);
				int tempy=Integer.parseInt(tempxy[1]);
				double distance=Math.sqrt(Math.pow((tempx-x),2)+Math.pow((tempy-y),2));
				count.put(temp.getId(), distance);
				
			}
			PriorityQueue<Map.Entry<String, Double>> heap=new PriorityQueue<Map.Entry<String,Double>>(new Comparator<Map.Entry<String,Double>>() {
				public int compare(Map.Entry<String, Double> l,Map.Entry<String,Double> r){
					return (int)(l.getValue()-r.getValue());
				}
			});
			Iterator it = count.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String,Double> pair = (Map.Entry<String,Double>)it.next();
					heap.add(pair);
			}
			while(!heap.isEmpty()){
				System.out.println("ID:"+heap.peek().getKey()+",Distance from source:"+heap.peek().getValue());
				heap.poll();
			}
		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

	}

}