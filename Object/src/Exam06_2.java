public class Exam06_2 {
	int Plus(int v1, int v2) { //Plus 메소드
		return v1 + v2;
	}

	int Minus(int v1, int v2) { //Minus 메소드
		return v1 - v2;
	}

	public static void main(String[] args) {
		int result1 = 0;
		int result2 = 0;
		Exam06_2 e = new Exam06_2();
		result1 = e.Plus(5, 3);
		result2 = e.Minus(5, 3);
		System.out.println("plus => " + result1);
		System.out.println("minus => " + result2);
	}
}