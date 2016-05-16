import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * Created by pyshankov on 16.05.2016.
 */
public class CryptoAnalysis {
    private static final double P1 = 0.25;
    private static final double P2 = 0.5;
    private static double ta001=2.326;
    private static double tb230=6.0093;
    private static double tb231=6.12;

    private static final NormalDistribution normalDistribution = new NormalDistribution(0, 1);

    public static int countCoincidingBits(LinearFeedbackShiftRegister l, int[] outputBits) {
        int count = 0;
        for (int i = 0; i < outputBits.length; i++) {
            count += outputBits[i] ^ l.generateNextSequence();
        }
        return count;
    }

    public static int R(LinearFeedbackShiftRegister l1,int[] src){
        int res = 0;
        for(int i = 0 ; i < src.length ; i++)
            res=res+(l1.generateNextSequence()^src[i]);
        return res;
    }

    public static double N(double ta,double tb){
        return 3*ta*ta+4*tb*tb+4*1.73*ta*tb;
    }

    public static double C(double tb,int N){
        return 0.5*(N-tb*Math.sqrt(N));
    }

    public double C(int N,double p1,double t1){
        return N*p1+t1*Math.sqrt(N*p1*(1-p1));
    }

}



