import java.util.List;
import java.util.concurrent.*;

/**
 * Created by pyshankov on 17.05.2016.
 */
public class Main {

    public static void main(String[] args) throws Exception{



        long l1Px = 0b1000000000000000000000001010011l;
        long l2Px = 0b10000000000000000000000000001001l;
        long l3Px = 0b100000000000000000000000010101111l;

//            long l1Px= 0b10000000000000000000001001;
//            long l2Px= 0b100000000000000000001000111;
//            long l3Px= 0b1000000000000000000000100111;

//        int l1Px = 0b100000000101;
//        int l2Px = 0b1000011011;
//        int l3Px = 0b10000001001;


            long firstl1= 17451593;
            long firstl2= 22162887;
            long firstl3= 101019995;



        CryptoAnalysis.breakGeffe(l1Px,l2Px,l3Px,CryptoAnalysis.zi);

        LinearFeedbackShiftRegister p1 = new LinearFeedbackShiftRegister(l1Px,1146);
        LinearFeedbackShiftRegister p2 = new LinearFeedbackShiftRegister(l2Px,5);
        LinearFeedbackShiftRegister p3 = new LinearFeedbackShiftRegister(l3Px,885);

        StringBuilder b = new StringBuilder();

        LinearFeedbackShiftRegister.GeffeGen geffeGen = new LinearFeedbackShiftRegister.GeffeGen(p1,p2,p3);

        for (int i = 0 ; i < CryptoAnalysis.zi.length() ; i++){
            b.append(geffeGen.getNext());
        }


        System.out.println(CryptoAnalysis.zi);
        System.out.println(b.toString());

    }
}
