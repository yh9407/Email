
public class MethodExam {
	// Q. 두 수를 입력받아서 큰 수를 반환하는 기능의 메소드생성
	int getMaxValue(int v1, int v2) {
		// 리턴타입 , 메소드 명 ( , )
		if (v1 > v2) {
			return v1;
		} else {
			return v2;
		}
		// return = 0;
	}

	public static void main(String[] args) {
		MethodExam e = new MethodExam();
		int max = e.getMaxValue(100, 102); // 생성한 메소드를 max라는 변수에 담아 출력
		System.out.println(max);
	}
}
