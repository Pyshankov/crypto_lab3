import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by pyshankov on 16.05.2016.
 */
public class CryptoAnalysis {

    private static final double P1 = 0.25;
    private static final double P2 = 0.5;
    private static final double alpha = 0.01;
    public static final NormalDistribution normalDistribution = new NormalDistribution(0, 1);

//    public static String zi = "00011000010101111000000110101001101100001111100100011011010011000001001010110110000011010000101100110101011001011001001100010100010100010100011111110110111010000101010011001010101000010101011111110100011000100100110111110100000011000001101111000110101000111010000001010010100000100100100111110111100111110100100101010011110100101100001011110000110001100100100100011100001110110010010001000011110100100000000100100001001111111101111101000000110100011110111001101110010000010001110000111000111011100101000111101011111111011111110100111000110101001000111010100001111111011111000011000011110011011110101010101101110101111001011111100100010100100000010001011110100010010010100101101110110111110100010110100001001101111010101010111101110111001000001100101100001101110001011000010010100010110001000100000101011001000010001111001010111111000101000011110001110010010011111110110001111101110000001111111001101111000001011001111001111001110011101010011111101011001011010011111110110010000001000010100010100001000001010100001010111011011111100000000101001001100000110001101101010001010100010101011110001000000011101000011110011000110101000101001111101100101100001010011111000001100101101110001100010011011110110010111100110110000111000000000110001111010111000011101010001100001000111111110100011001110010110110100001001011001000100100011011010100000001001111100001000110111011100001011000110110100001110111010001101111011000011110101100101110001100100110100001101000000111101010101110100100011000011010111010110111100110000001101111011111010101101011010100111101100011110000111010011001001101111000011011011011010010110011011101010010000110000011111010000001100000001000110110000001101000111001000001110101010101101100111110100001010011111001000010011111001101111000001010100100011111010100111101111111100100010010111111100100010000101111101000000010100010010011100100011001111111111001100011110011000001000000011100001110111100101001100011111010010100100101110000110101000100101111110000111010000111110001000100000111000000110001111010100010010110100001001110";
    public static String zi ="01001010001101000101011011011001001110110100011011101110001010100011000001100000000100101100101011010101001000100101010001011001100001011101101101010110010000100111111010000011110100001000011101111101100101011111001110010010111011111101101010110000100001000010101110000011000000001010101011011001011010111111001000100111111001100011111000000101100100100010001101010001000001000011110101110010000111011110100010100011101110011010111101001010110011110010000111110011110000001100101011111101000111010100";

    public static long[] fromStringToMass(String s){
        long[] z = new long[s.length()];
               for (int i = 0 ; i < z.length ; i++){
                        z[i] = Integer.parseInt(zi.substring(i,i+1));
                   }
        return z;
    }

    public static long R(LinearFeedbackShiftRegister l,long[] src){
    return  Arrays.stream(src).reduce(0,(x,y)->x+(y^l.generateNext()));
    }

    public  static List<Long> firstStates(long polynom,String s,double alpha){

        long degree= 63-Long.numberOfLeadingZeros(polynom);
        long numberOnElementsInField = 1 << degree ;
        double betta = 1.0/numberOnElementsInField;

        double tAlpha = normalDistribution.inverseCumulativeProbability(1-alpha);
        double tBetta = normalDistribution.inverseCumulativeProbability(1-betta);

        System.out.println("b "+tBetta);
        double a1 = tAlpha * Math.sqrt(P1 * (1 - P1));
        double a2 = tBetta * Math.sqrt(P2 * (1 - P2));
        int N = (int) pow((a1 + a2) / (P2 - P1), 2);
        double C = N * P1 + sqrt(N) * a1;

        System.out.println("N*"+ N);
        System.out.println("C "+C);

        long[] z = new long[N];
        System.arraycopy(fromStringToMass(s),0,z,0,z.length-1);

        List<Long> res = new ArrayList<>();

        for (long i = 0 ; i < numberOnElementsInField ; i ++){
            LinearFeedbackShiftRegister l = new LinearFeedbackShiftRegister(polynom,i);
            long R = R(l,z);
            if(R<=C) res.add(i);
        }
        return res;
    }

    public static void breakGeffe(long p1,long p2,long p3,String s,double alpha){

        List<Long> l1Res = firstStates(p1,s,alpha);
        List<Long> l2Res = firstStates(p2,s,alpha);

        System.out.println(l1Res.size());
        System.out.println(l2Res.size());

        long degree= 63-Long.numberOfLeadingZeros(p3);
        long numberOnElementsInField = 1 << degree ;

        long[] z = new long[s.length()];
        System.arraycopy(fromStringToMass(s),0,z,0,z.length-1);

        for (Long l1 : l1Res){
            LinearFeedbackShiftRegister L1 = new LinearFeedbackShiftRegister(p1,l1);
            for (Long l2 : l2Res){
                LinearFeedbackShiftRegister L2 = new LinearFeedbackShiftRegister(p2,l2);
                timestamp:
                for (long i = 0 ; i < numberOnElementsInField ; i++){
                    L1.reset();
                    L2.reset();
                    LinearFeedbackShiftRegister L3 = new LinearFeedbackShiftRegister(p3,i);
                    LinearFeedbackShiftRegister.GeffeGen geffeGen = new LinearFeedbackShiftRegister.GeffeGen(L1,L2,L3);
                    for(int j = 0 ; j < z.length ; j++){
                        if(z[j]!=geffeGen.getNext()){
                            continue timestamp;
                        }
                    }
                    System.out.println();
                    System.out.println(Long.toBinaryString(L1.getFirstState()));
                    System.out.println(Long.toBinaryString(L2.getFirstState()));
                    System.out.println(Long.toBinaryString(L3.getFirstState()));

                }


            }


        }

    }

}



