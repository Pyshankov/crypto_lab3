import java.util.List;
import java.util.concurrent.*;

/**
 * Created by pyshankov on 17.05.2016.
 */
public class Main {

    public static void main(String[] args) throws Exception{

        System.out.println("10000000000000000000001001".length());
        System.out.println("100000000000000000001000111".length());
        System.out.println("1000000000000000000000100111".length());

        long l1Px = 0b1000000000000000000000001010011l;
        long l2Px = 0b10000000000000000000000000001001l;
        long l3Px = 0b100000000000000000000000010101111l;

//            long l1Px= 0b10000000000000000000001001;
//            long l2Px= 0b100000000000000000001000111;
//            long l3Px= 0b1000000000000000000000100111;




        CryptoAnalysis.breakGeffe(l1Px,l2Px,l3Px,CryptoAnalysis.zi);

        LinearFeedbackShiftRegister p1 = new LinearFeedbackShiftRegister(l1Px,17451593);
        LinearFeedbackShiftRegister p2 = new LinearFeedbackShiftRegister(l2Px,22162887);
        LinearFeedbackShiftRegister p3 = new LinearFeedbackShiftRegister(l3Px,101019995);

        StringBuilder b = new StringBuilder();

        LinearFeedbackShiftRegister.GeffeGen geffeGen = new LinearFeedbackShiftRegister.GeffeGen(p1,p2,p3);

        for (int i = 0 ; i < CryptoAnalysis.zi.length() ; i++){
            b.append(geffeGen.getNext());
        }


        System.out.println(CryptoAnalysis.zi);
        System.out.println(b.toString());

    }
}
