//library and 생성자파트
public class Car {
		int tire;
		double engine;
		double Break;
		String brand;
		
		public Car() {}
		public Car(int tire) {
			//tire = tire; //tire 중복. 어떤 tire인지 구분이 어려움 
			//가장 가까운걸로 함.
			this.tire = tire;
			//this란 현재 클래스에서를 의미함.
		}
		@Override   //비슷한 걸로 Over loading [예로 >syso]
		public String toString() {
			return "Car [tire=" + tire + ", engine=" + engine + ", Break=" + Break + ", brand=" + brand + "]";
		}
	}

