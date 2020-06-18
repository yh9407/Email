//library
public class User {
	int age;
	String name;
	Car car; //나만의 자료형 'Car'
	
	//source - > generate toString//
	@Override
	public String toString() {
		return "User [age=" + age + ", name=" +
	name + ", car=" + car + "]";
		
	}
}
