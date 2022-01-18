import java.time.Duration;
import java.time.Instant;

public class Main {

	public static void main(String[] args) {
		// Sériové øešení pro na sobì nezávislý algoritmus
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < NQueen.N; i++)
        {
            NQueen.solve(i, 1);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("èas mìøení : " + (end1 - start1));
        
        NQueen.count = 0;
        
        // Paralelní øešení pro na sobì nezávislý algoritmus
        long start2 = System.currentTimeMillis();
        NQueen.solveParallel();
        long end2 = System.currentTimeMillis();
        System.out.println("èas mìøení : " + (end2 - start2));
	}
}
