
public class House {
	public static void main(String[] args) {
		
		User user = new User();
		user.age = 20;
		user.name = "È«±æµ¿";
		Car car = new Car();
		car.tire=4;
		car.engine = 3.0;
		car.brand = "Çö±â";
		user.car = car;
		System.out.println(user);
	}
}
