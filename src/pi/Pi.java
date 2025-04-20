package pi;

public class Pi {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		double s = 1;
		double p = 3;
		double m = -1;
		for (long i = 1; i < 10000000000l; ++i) {
			s += m / p;
			p += 2;
			m = -m;
		}
		s *= 4;
		System.out.println("pi=" + s + " time=" + (System.currentTimeMillis() - startTime));
	}
}
